package net.qihoo.corp.umapp.service.comi.service.bus;

import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiActorPri;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiPicture;
import net.qihoo.corp.ms.umapp.feign.comi.entity.model.ComiStory;
import net.qihoo.corp.ms.umapp.feign.comi.entity.resp.ComiPictureRes;
import net.qihoo.corp.ms.umapp.feign.comi.entity.resp.ComiStoryRes;
import net.qihoo.corp.umapp.service.comi.config.Configs;
import net.qihoo.corp.umapp.service.comi.service.ActorServicePrivate;
import net.qihoo.corp.umapp.service.comi.service.PictureService;
import net.qihoo.corp.umapp.service.comi.service.RatioService;
import net.qihoo.corp.umapp.service.comi.service.StoryService;
import net.qihoo.corp.umapp.service.comi.service.ai.AIBaseModel;
import net.qihoo.corp.umapp.service.comi.service.ai.CallBack;
import net.qihoo.corp.umapp.service.comi.service.ai.HttpAIService;
import org.apache.kafka.clients.consumer.CommitFailedException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

import static net.qihoo.corp.umapp.service.comi.config.Configs.*;
import static net.qihoo.corp.umapp.service.comi.service.bus.MyMessageProducer.TOPIC_NAME;

/**
 * @quthor cnn
 * @date 2024/4/8
 */
@Component
public class MyMessageConsumer {

    @Autowired
    StoryService storyService;
    @Autowired
    PictureService pictureService;
    @Autowired
    RatioService ratioService;
    @Autowired
    ActorServicePrivate actorServicePrivate;
    @Autowired
    HttpAIService aiService;

    @Autowired
    ActorServicePrivate aPrivate;
    @Autowired
    private Environment environment;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;



    @KafkaListener(topics =TOPIC_NAME, groupId = "${spring.kafka.consumer.group-id}")
    public void receiveMessage(String message, Acknowledgment acknowledgment) {
        handleBegin(message, acknowledgment);
    }

    private void handleBegin(String message, Acknowledgment acknowledgment) {
        System.out.println("Received message: " +message);
        Gson gson = new Gson();
        BusBaseItem busBaseItem = gson.fromJson(message, BusBaseItem.class);
        if (groupId.equals(busBaseItem.getGroupId())){
            String message1 = busBaseItem.getMessage();
            if (busBaseItem.getType().equals(BUS_STORY)) {
                handleStory(gson, message1);
            } else if (busBaseItem.getType().equals(BUS_PIC)) {
                handleOnePic(gson, busBaseItem, message1);

            } else if (busBaseItem.getType().equals(BUS_ACTOR)) {
                handleActor(gson, message1);
            }
            ack(acknowledgment);
        }
    }

    private void ack(Acknowledgment acknowledgment) {
        acknowledgment.acknowledge();
        int maxRetries = 3;
        int retryCount = 0;
        boolean committed = false;

        while (!committed && retryCount < maxRetries) {
            try {
                acknowledgment.acknowledge();
                committed = true; // 标记提交成功
            } catch (CommitFailedException e) {
                // 处理提交位移失败的情况
                // 可以记录错误日志
                retryCount++;
                // 可以添加一些延迟，避免过于频繁地重试
                try {
                    Thread.sleep(1000); // 1秒钟的延迟
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }

    }


    private void handleActor(Gson gson, String message1) {
        ComiActorPri actorPri = gson.fromJson(message1, ComiActorPri.class);
        if (checkStoryActor(actorPri))
        aiService.doRequest(actorPri, (pri, aiBaseModel) -> {
            try {
                if (aiBaseModel!=null){
                    String image = aiBaseModel.getData().getImage();
                    if (image!=null)
                        actorPri.setPosterImage(image);
                    actorPri.setStatus(ACTOR_PIC_STATUS_FINISH);
                    aPrivate.updateOne(actorPri);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void handleStory(Gson gson, String message1) {
        ComiStoryRes comiStoryRes = gson.fromJson(message1, ComiStoryRes.class);
        if (checkStory(comiStoryRes.getId()))
            aiService.doRequest(comiStoryRes, -1, new CallBack() {
                @Override
                public void finishOne(ComiStoryRes story, ComiPictureRes p, AIBaseModel aiBaseModel) {
                    try {
                        List<String> images = aiBaseModel.getData().getImages();
                        String image = images.get(0);
                        ComiPicture pp = new ComiPicture();
                        BeanUtils.copyProperties(p, pp);
                        if (StrUtil.isBlankIfStr(storyService.get(story.getId()).getPosterImage())) {
                            ComiStory c = new ComiStory();
                            BeanUtils.copyProperties(story, c);
                            c.setPosterImage(image);
                            storyService.updateStory(c);
                            pp.setIsPoster(1);
                        }
                        pp.setImgUrl(image);
                        pictureService.updatePicture(pp);
                    } catch (BeansException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void finishAll() {
                    updateStoryStatus(comiStoryRes.getId(), Configs.STORY_STATUS_FINISH);
                }
            });
    }

    private void handleOnePic(Gson gson, BusBaseItem busBaseItem, String message1) {
        ComiStoryRes comiStoryRes = gson.fromJson(message1, ComiStoryRes.class);
        Integer historyId = busBaseItem.getExtraId();
        if (checkStoryPic(comiStoryRes.getId(), historyId))
            aiService.doRequest(comiStoryRes, historyId, new CallBack() {
                @Override
                public void finishOne(ComiStoryRes story, ComiPictureRes p, AIBaseModel aiBaseModel) {
                    try {
                        List<String> images = aiBaseModel.getData().getImages();
                        String image = images.get(0);
                        ComiPicture pp = new ComiPicture();
                        BeanUtils.copyProperties(p, pp);
                        if (StrUtil.isBlankIfStr(storyService.get(story.getId()).getPosterImage())) {
                            ComiStory c = new ComiStory();
                            BeanUtils.copyProperties(story, c);
                            c.setPosterImage(image);
                            storyService.updateStory(c);
                            pp.setIsPoster(1);
                        }
                        pp.setImgUrl(image);
                        pictureService.updatePicture(pp);
                    } catch (BeansException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void finishAll() {
                }
            });
    }

    //    检查，如何已经生成地址，就不再重复生成
    private boolean checkStoryActor(ComiActorPri actorPri) {
        ComiActorPri actorPri1 = aPrivate.get(actorPri.getId());
        if (!actorPri1.getStatus().equals(ACTOR_PIC_STATUS_FINISH)){
            return true;
        }
        return false;
    }

    private boolean checkStoryPic(Integer id, Integer historyId) {
        return true;
    }

    /**
     * 防止重复生成，只有首次并且 未生成成功
     * @param storyId
     * @return
     */
    private boolean checkStory(Integer storyId) {
        ComiStory comiStory = storyService.get(storyId);
        List<ComiPicture> list = pictureService.getList(storyId);
        if (comiStory.getStatus()<STORY_STATUS_FINISH)return true;
        return false;
    }


    private void updateStoryStatus(Integer storyId, Integer status) {
        storyService.updateState(storyId, status);
        pictureService.updateState(storyId, status);
    }



    public  String getTopicName() {
        String[] activeProfiles = environment.getActiveProfiles();
        String active = activeProfiles[0];
        return "comi_"+active;
    }
}
