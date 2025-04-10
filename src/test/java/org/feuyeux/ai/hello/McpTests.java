package org.feuyeux.ai.hello;

import static org.feuyeux.ai.hello.ai.ModelClient.buildModel;
import static org.feuyeux.ai.hello.mcp.FSClient.buildFSClient;
import static org.feuyeux.ai.hello.mcp.MapClient.buildMapClient;
import static org.feuyeux.ai.hello.utils.DirUtils.getUserDir;

import io.modelcontextprotocol.client.McpSyncClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;

@Slf4j
public class McpTests {

  @Test
  public void testFSMcp() {
    try (McpSyncClient mcpClient = buildFSClient()) {
      ChatClient chatClient =
          ChatClient.builder(buildModel())
              .defaultTools(new SyncMcpToolCallbackProvider(mcpClient))
              .build();
      String question = "Can you explain the content of the README.md in " + getUserDir() + "?";
      log.info("FS QUESTION: {}", question);
      log.info("FS ASSISTANT: {}", chatClient.prompt(question).call().content());
    }
  }

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
}
