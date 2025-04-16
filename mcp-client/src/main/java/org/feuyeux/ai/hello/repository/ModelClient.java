package org.feuyeux.ai.hello.repository;

import static org.feuyeux.ai.hello.utils.DotEnv.getZhipuAiKey;

import java.util.List;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.ai.zhipuai.ZhiPuAiChatOptions;
import org.springframework.ai.zhipuai.api.ZhiPuAiApi;

public class ModelClient {

  public static ZhiPuAiChatModel buildModel() {
    var zhiPuAiApi = new ZhiPuAiApi("https://open.bigmodel.cn/api/paas", getZhipuAiKey());
    var chatOptions = ZhiPuAiChatOptions.builder().model("GLM-4-Plus").temperature(0.7).build();
    return new ZhiPuAiChatModel(zhiPuAiApi, chatOptions);
  }

  public static ZhiPuAiChatModel buildModel(List<FunctionCallback> callbacks) {
    var zhiPuAiApi = new ZhiPuAiApi("https://open.bigmodel.cn/api/paas", getZhipuAiKey());
    var chatOptions =
        ZhiPuAiChatOptions.builder()
            .model("GLM-4-Plus")
            .temperature(0.7)
            .functionCallbacks(callbacks)
            .build();
    return new ZhiPuAiChatModel(zhiPuAiApi, chatOptions);
  }
}
