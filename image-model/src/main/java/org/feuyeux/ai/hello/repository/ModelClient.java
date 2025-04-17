package org.feuyeux.ai.hello.repository;

import org.springframework.ai.qianfan.QianFanImageModel;
import org.springframework.ai.qianfan.QianFanImageOptions;
import org.springframework.ai.qianfan.api.QianFanApi;
import org.springframework.ai.qianfan.api.QianFanImageApi;
import org.springframework.ai.zhipuai.ZhiPuAiImageModel;
import org.springframework.ai.zhipuai.ZhiPuAiImageOptions;
import org.springframework.ai.zhipuai.api.ZhiPuAiImageApi;
import org.springframework.retry.support.RetryTemplate;

import static org.feuyeux.ai.hello.utils.DotEnv.*;

public class ModelClient {
    public static ZhiPuAiImageModel buildZhipuAiImageModel() {
        var zhiPuAiApi = new ZhiPuAiImageApi(getZhipuAiKey());
        var imageOptions = ZhiPuAiImageOptions.builder().model(ZhiPuAiImageApi.ImageModel.CogView_3.getValue()).build();
        RetryTemplate retryTemplate = RetryTemplate.builder().build();
        return new ZhiPuAiImageModel(zhiPuAiApi, imageOptions, retryTemplate);
    }
    public static QianFanImageModel buildQianFanImageModel() {
        String[] tokenKeys = getQianfanTokenKeys();
        var qianFanImageApi = new QianFanImageApi(tokenKeys[0], tokenKeys[1]);
        var imageOptions = QianFanImageOptions.builder().N(4).height(1024)
                .width(1024).build();
        return new QianFanImageModel(qianFanImageApi, imageOptions);
    }
}
