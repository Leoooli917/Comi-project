package net.qihoo.corp.umapp.service.comi.config;

/**
 * @quthor cnn
 * @date 2024/4/8
 */
public class Configs {

    //    开始提交
    public static final Integer STORY_STATUS_FIRST_COMMIT = 1;
    //    编辑场景后提交
    public static final Integer STORY_STATUS_FIRST_COMMIT_SCENE = 2;
    //    提交角色
    public static final Integer STORY_STATUS_FIRST_COMMIT_ACTOR = 3;

    public static final Integer STORY_STATUS_FINISH = 4;
    //    生成完成并且主动发布
    public static final Integer STORY_STATUS_FINISH_PUBLISH = 5;

    //图片是历史
    public static final Integer STORY_PICTURE_IS_NOT_CHOOSE = 0;//表示备选图片

    public static final Integer STORY_PICTURE_CHOOSE = 1;//使用图片



    public static final String ACTOR_PIC_STATUS_BEGIN = "begin";//
    public static final String ACTOR_PIC_STATUS_FINISH = "finish";//

//小说
    public static final Integer BUS_STORY = 1;
    //单张
    public static final Integer BUS_PIC = 2;
//    演员
    public static final Integer BUS_ACTOR = 3;
    public static final Integer MAX_ACTOR_SIZE = 3;


}
