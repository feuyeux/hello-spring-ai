# hello-spring-ai

![spring-ai-integration-diagram-3](https://docs.spring.io/spring-ai/reference/_images/spring-ai-integration-diagram-3.svg)

RAG

![](https://docs.spring.io/spring-ai/reference/_images/spring-ai-rag.jpg)

Function Calling

![](https://docs.spring.io/spring-ai/reference/_images/function-calling-basic-flow.jpg)

Spring AI Message API

![](https://docs.spring.io/spring-ai/reference/_images/spring-ai-message-api.jpg)

## Run
```sh
touch .env
```

```env
ZHIPUAI_API_KEY=智谱大模型APIKEY
AMAP_MAPS_API_KEY=高德地图APIKEY
```

## Test

```java
@Test
public void testMapMcp() {
  try (McpSyncClient mapClient = buildMapClient();
      McpSyncClient fsClient = buildFSClient()) {
    ChatClient chatClient =
        ChatClient.builder(buildModel())
            .defaultTools(new SyncMcpToolCallbackProvider(mapClient, fsClient))
            .build();
    String question =
        "我要开车从 '北京海淀区互联网金融中心' 到 '国家游泳中心(水立方)'。帮我规划路线，将结果以表格形式保存到 " + getUserDir() + " 目录";
    log.info("MAP QUESTION: {}", question);
    String content = chatClient.prompt(question).call().content();
    log.info("MAP ASSISTANT: {}", content);
  }
}
```

```sh
$ mvn test -Dtest=org.feuyeux.ai.hello.McpTests#testMapMcp

[INFO] Running org.feuyeux.ai.hello.McpTests
14:03:41.756 STDERR Message received: Amap Maps MCP Server running on stdio
14:03:41.821 Server response with Protocol: 2024-11-05, Capabilities: ServerCapabilities[experimental=null, logging=null, prompts=null, resources=null, tools=ToolCapabilities[listChanged=null]], Info: Implementation[name=mcp-server/amap-maps, version=0.1.0] and Instructions null
14:03:41.841 MCP Initialized: InitializeResult[protocolVersion=2024-11-05, capabilities=ServerCapabilities[experimental=null, logging=null, prompts=null, resources=null, tools=ToolCapabilities[listChanged=null]], serverInfo=Implementation[name=mcp-server/amap-maps, version=0.1.0], instructions=null]
14:03:43.379 STDERR Message received: Secure MCP Filesystem Server running on stdio
14:03:43.381 STDERR Message received: Allowed directories: [ 'D:\\ai\\hello-spring-ai' ]
14:03:43.403 Server response with Protocol: 2024-11-05, Capabilities: ServerCapabilities[experimental=null, logging=null, prompts=null, resources=null, tools=ToolCapabilities[listChanged=null]], Info: Implementation[name=secure-filesystem-server, version=0.2.0] and Instructions null
14:03:43.404 MCP Initialized: InitializeResult[protocolVersion=2024-11-05, capabilities=ServerCapabilities[experimental=null, logging=null, prompts=null, resources=null, tools=ToolCapabilities[listChanged=null]], serverInfo=Implementation[name=secure-filesystem-server, version=0.2.0], instructions=null]
14:03:43.759 MAP QUESTION: 我要开车从 '北京海淀区互联网金融中心' 到 '国家游泳中心(水立方)'。帮我规划路线，将结果以表格形式保存到 D:\ai\hello-spring-ai 目录
14:04:11.033 MAP ASSISTANT: 已经为您规划了从北京海淀区互联网金融中心到国家游泳中心(水立方)的驾车路线，并将结果保存为表格形式到D:\ai\hello-spring-ai\driving_route.csv文件中。您可以根据件内容查看详细的路线信息。
```

## Coding

org.springframework.ai.zhipuai.ZhiPuAiChatModel#call

![tool_call](tool_call.png)

JavaSDKMCPClient_maps_geo

```
AssistantMessage [
messageType=ASSISTANT, 
toolCalls=[
ToolCall[id=call_-8802637004939132034, type=function, name=JavaSDKMCPClient_maps_geo, arguments={"address": "北京海淀区互联网金融中心"}], ToolCall[id=call_-8802637004939132033, type=function, name=JavaSDKMCPClient_maps_geo, arguments={"address": "国家游泳中心(水立方)"}]
], 
textContent=null, 
metadata={finishReason=TOOL_CALLS, id=2025041212291987bc34201aa24335, role=ASSISTANT, messageType=ASSISTANT}
]
```

JavaSDKMCPClient_maps_direction_driving

```
AssistantMessage [
messageType=ASSISTANT, 
toolCalls=[
ToolCall[id=call_-8802646694386850541, type=function, name=JavaSDKMCPClient_maps_direction_driving, arguments={"origin": "116.313133,39.979318", "destination": "116.390397,39.992834"}]
], 
textContent=null, 
metadata={finishReason=TOOL_CALLS, id=202504121231322f677f6360134feb, role=ASSISTANT, messageType=ASSISTANT}
]
```

JavaSDKMCPClient_write_file

```
AssistantMessage [
messageType=ASSISTANT, 
toolCalls=[
ToolCall[id=call_-8802639959877074140, type=function, name=JavaSDKMCPClient_write_file, arguments={"content": "| 步骤 | 指令 | 距离(米) | 预计时间(秒) |\n| --- | --- | --- | --- |\n| 1 | 向西行驶26米右转 | 26 | 14 |\n| 2 | 沿海淀东三街向西北行驶130米右转 | 130 | 26 |\n| 3 | 沿善缘街向东北行驶168米右转 | 168 | 33 |\n| 4 | 沿海淀东一街向东行驶267米左转进入主路 | 267 | 64 |\n| 5 | 沿中关村大街向北行驶487米右转 | 487 | 84 |\n| 6 | 沿北四环西路辅路向东行驶342米向左前方行驶进入主路 | 342 | 57 |\n| 7 | 沿北四环西路入口途径北四环西路、北四环中路向东行驶6.6千米向左前方行驶 | 6584 | 344 |\n| 8 | 沿北四环中路出口途径北辰东路向北行驶1.4千米左转 | 1367 | 218 |\n| 9 | 沿国家体育场北路向西行驶750米左转 | 750 | 170 |\n| 10 | 沿天辰东路向南行驶444米右转 | 444 | 90 |\n| 11 | 向西行驶16米左转 | 16 | 11 |\n| 12 | 向西南行驶290米到达目的地 | 290 | 97 |", "path": "D:\\ai\\hello-spring-ai\\amap.md"}]
], 
textContent=null, 
metadata={finishReason=TOOL_CALLS, id=2025041212321259a77dc78cf84665, role=ASSISTANT, messageType=ASSISTANT}
]
```

无工具调用

```
AssistantMessage [
messageType=ASSISTANT, 
toolCalls=[], textContent=路线规划已完成，并以表格形式保存到文件 `D:\ai\hello-spring-ai\amap.md` 中。您可以根据该文件中的指引开车从 '北京海淀区互联网金融中心' 到 '国家游泳中心(水立方)'。祝您旅途愉快！, metadata={finishReason=STOP, id=20250412123324a6991a3f70ad4d1c, role=ASSISTANT, messageType=ASSISTANT}
]
````



## Reference

- <https://docs.spring.io/spring-ai/reference>
- <https://github.com/spring-projects/spring-ai>
- <https://pedrolopesdev.com/intro-to-spring-ai-ollama/>
- <https://github.com/danvega/awesome-spring-ai>
