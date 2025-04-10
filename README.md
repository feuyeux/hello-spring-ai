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


## Reference

- <https://docs.spring.io/spring-ai/reference>
- <https://github.com/spring-projects/spring-ai>
- <https://pedrolopesdev.com/intro-to-spring-ai-ollama/>
- <https://github.com/danvega/awesome-spring-ai>