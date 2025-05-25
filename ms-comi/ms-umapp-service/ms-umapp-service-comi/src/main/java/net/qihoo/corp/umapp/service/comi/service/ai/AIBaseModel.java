package net.qihoo.corp.umapp.service.comi.service.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @quthor cnn
 * @date 2024/4/7
 */
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class AIBaseModel {

    @JsonProperty("data")
    private DataDTO data;
    @JsonProperty("message")
    private String message;
    @JsonProperty("code")
    private Integer code;
    @JsonProperty("duration")
    private Integer duration;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("image")
        private String image;
        @JsonProperty("images")
        private List<String> images;
        @JsonProperty("params")
        private ParamsDTO params;
        @JsonProperty("all_params")
        private AllParamsDTO allParams;

        @NoArgsConstructor
        @Data
        public static class ParamsDTO {
            @JsonProperty("scheduler")
            private String scheduler;
            @JsonProperty("base_name")
            private String baseName;
            @JsonProperty("lora_name")
            private String loraName;
            @JsonProperty("seed")
            private Integer seed;
            @JsonProperty("negative_prompt")
            private String negativePrompt;
            @JsonProperty("hr_scale")
            private Integer hrScale;
            @JsonProperty("width")
            private Integer width;
            @JsonProperty("count")
            private Integer count;
            @JsonProperty("content")
            private String content;
            @JsonProperty("height")
            private Integer height;
            @JsonProperty("num_inference_steps")
            private Integer numInferenceSteps;
        }

        @NoArgsConstructor
        @Data
        public static class AllParamsDTO {
            @JsonProperty("txt2img_params")
            private Txt2imgParamsDTO txt2imgParams;

            @NoArgsConstructor
            @Data
            public static class Txt2imgParamsDTO {
                @JsonProperty("bdm_info")
                private BdmInfoDTO bdmInfo;
                @JsonProperty("controlnet_infos")
                private List<?> controlnetInfos;
                @JsonProperty("clip_skip")
                private Integer clipSkip;
                @JsonProperty("refiner_name")
                private String refinerName;
                @JsonProperty("refiner_switch")
                private Double refinerSwitch;
                @JsonProperty("base_name")
                private String baseName;
                @JsonProperty("vae_name")
                private String vaeName;
                @JsonProperty("lora_name_2_weights")
                private LoraName2WeightsDTO loraName2Weights;
                @JsonProperty("prompt")
                private String prompt;
                @JsonProperty("prompt_text_inversions")
                private List<?> promptTextInversions;
                @JsonProperty("negative_prompt")
                private String negativePrompt;
                @JsonProperty("negative_prompt_text_inversions")
                private List<?> negativePromptTextInversions;
                @JsonProperty("tiling")
                private Boolean tiling;
                @JsonProperty("height")
                private Integer height;
                @JsonProperty("width")
                private Integer width;
                @JsonProperty("seed")
                private Long seed;
                @JsonProperty("num_inference_steps")
                private Integer numInferenceSteps;
                @JsonProperty("num_images_per_prompt")
                private Integer numImagesPerPrompt;
                @JsonProperty("guidance_scale")
                private Integer guidanceScale;
                @JsonProperty("scheduler")
                private String scheduler;

                @NoArgsConstructor
                @Data
                public static class BdmInfoDTO {
                    @JsonProperty("enable")
                    private Boolean enable;
                    @JsonProperty("weight")
                    private Integer weight;
                    @JsonProperty("cn_prompt")
                    private String cnPrompt;
                    @JsonProperty("cn_neg_prompt")
                    private String cnNegPrompt;
                }

                @NoArgsConstructor
                @Data
                public static class LoraName2WeightsDTO {
                    @JsonProperty("genshin_all")
                    private Integer genshinAll;
                }
            }
        }
    }
}
