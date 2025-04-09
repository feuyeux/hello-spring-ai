package org.feuyeux.ai.hello.mcp;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.ServerParameters;
import io.modelcontextprotocol.client.transport.StdioClientTransport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.ai.zhipuai.ZhiPuAiChatOptions;
import org.springframework.ai.zhipuai.api.ZhiPuAiApi;

import java.nio.file.Paths;
import java.time.Duration;

@Slf4j
public class FSClient {
    public static String buildNpxCmd() {
        return System.getProperty("os.name").toLowerCase().contains("win") ? "npx.cmd" : "npx";
    }

    public static void main(String[] args) {
        String homeDir = Paths.get(System.getProperty("user.dir")).toString();
        var stdioParams = ServerParameters.builder(buildNpxCmd())
                .args("-y", "@modelcontextprotocol/server-filesystem", homeDir)
                .build();
        McpSyncClient mcpClient = McpClient.sync(new StdioClientTransport(stdioParams))
                .requestTimeout(Duration.ofSeconds(10)).build();
        var init = mcpClient.initialize();
        log.info("MCP Initialized: {}", init);

        var apiKey = System.getenv("ZHIPUAI_API_KEY");
        var zhiPuAiApi = new ZhiPuAiApi("https://open.bigmodel.cn/api/paas", apiKey);
        var chatOptions = ZhiPuAiChatOptions.builder().model("GLM-4-Plus").temperature(0.7).build();
        var chatModel = new ZhiPuAiChatModel(zhiPuAiApi, chatOptions);
        ChatClient chatClient = ChatClient.builder(chatModel).defaultTools(new SyncMcpToolCallbackProvider(mcpClient)).build();
        String question = "Can you explain the content of the README.md in " + homeDir + "?";
        log.info("QUESTION: {}", question);
        log.info("ASSISTANT: {}", chatClient.prompt(question).call().content());
        mcpClient.close();
    }
}
