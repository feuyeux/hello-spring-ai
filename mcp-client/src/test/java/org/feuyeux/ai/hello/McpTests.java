package org.feuyeux.ai.hello;

import static org.feuyeux.ai.hello.mcp.FSClient.buildFSClient;
import static org.feuyeux.ai.hello.mcp.HelloClient.buildHelloClient;
import static org.feuyeux.ai.hello.mcp.MapClient.buildMapClient;
import static org.feuyeux.ai.hello.repository.ModelClient.buildModel;
import static org.feuyeux.ai.hello.utils.DirUtils.getUserDir;
import static org.feuyeux.ai.hello.utils.DotEnv.loadEnv;

import io.modelcontextprotocol.client.McpSyncClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;

/**
 * MCP客户端功能的单元测试类
 *
 * <p>此类测试Model Context Protocol客户端与服务端的交互功能， 包括元素周期表查询、文件系统操作和地图服务等功能。
 */
@Slf4j
public class McpTests {

  /** 测试类初始化，加载环境变量 */
  @BeforeAll
  public static void init() {
    loadEnv();
  }

  /**
   * 测试元素周期表MCP功能 - 按原子序数查询
   *
   * <p>通过MCP协议向服务器请求元素周期表中第14个元素（硅）的信息
   */
  @Test
  @DisplayName("测试按原子序数查询元素")
  public void testHelloMcp() {
    try (McpSyncClient mcpClient = buildHelloClient()) {
      ChatClient chatClient =
          ChatClient.builder(buildModel())
              .defaultTools(new SyncMcpToolCallbackProvider(mcpClient))
              .build();
      String question = "列出元素周期表的第14个元素的详细信息";
      log.info("QUESTION: {}", question);
      log.info("ASSISTANT: {}", chatClient.prompt(question).call().content());
    }
  }

  /**
   * 测试元素周期表MCP功能 - 按元素名称查询
   *
   * <p>通过MCP协议向服务器请求元素名称为"氧"的元素信息
   */
  @Test
  @DisplayName("测试按元素名称查询元素")
  public void testElementByName() {
    try (McpSyncClient mcpClient = buildHelloClient()) {
      ChatClient chatClient =
          ChatClient.builder(buildModel())
              .defaultTools(new SyncMcpToolCallbackProvider(mcpClient))
              .build();
      String question = "请告诉我元素'氧'的详细信息";
      log.info("QUESTION: {}", question);
      log.info("ASSISTANT: {}", chatClient.prompt(question).call().content());
    }
  }

  /**
   * 测试元素周期表MCP功能 - 查询多个元素
   *
   * <p>通过MCP协议向服务器请求多个元素的信息并比较它们
   */
  @Test
  @DisplayName("测试查询多个元素并比较")
  public void testMultipleElements() {
    try (McpSyncClient mcpClient = buildHelloClient()) {
      ChatClient chatClient =
          ChatClient.builder(buildModel())
              .defaultTools(new SyncMcpToolCallbackProvider(mcpClient))
              .build();
      String question = "比较元素周期表中的铁(Fe)和金(Au)的特性";
      log.info("QUESTION: {}", question);
      log.info("ASSISTANT: {}", chatClient.prompt(question).call().content());
    }
  }

  /**
   * 测试文件系统MCP功能
   *
   * <p>通过MCP协议读取文件系统中的Periodic_table.md文件内容
   */
  @Test
  @DisplayName("测试文件系统功能")
  public void testFSMcp() {
    try (McpSyncClient mcpClient = buildFSClient()) {
      ChatClient chatClient =
          ChatClient.builder(buildModel())
              .defaultTools(new SyncMcpToolCallbackProvider(mcpClient))
              .build();
      String question =
          "Can you explain the content of the Periodic_table.md in " + getUserDir() + "?";
      log.info("FS QUESTION: {}", question);
      log.info("FS ASSISTANT: {}", chatClient.prompt(question).call().content());
    }
  }

  /**
   * 测试地图MCP功能
   *
   * <p>通过MCP协议规划从起点到终点的驾车路线，并将结果保存到文件
   */
  @Test
  @DisplayName("测试地图功能")
  public void testMapMcp() {
    try (McpSyncClient mapClient = buildMapClient();
        McpSyncClient fsClient = buildFSClient()) {
      ChatClient chatClient =
          ChatClient.builder(buildModel())
              .defaultTools(new SyncMcpToolCallbackProvider(mapClient, fsClient))
              .build();
      String question =
          "我要开车从 '北京海淀区互联网金融中心' 到 '国家游泳中心(水立方)'。帮我规划路线，将结果以表格形式保存到 "
              + getUserDir()
              + " 目录，文件名为 'amap.md'。";
      log.info("MAP QUESTION: {}", question);
      ChatClient.ChatClientRequestSpec requestSpec = chatClient.prompt(question);
      ChatClient.CallResponseSpec responseSpec = requestSpec.call();
      String content = responseSpec.content();
      log.info("MAP ASSISTANT: {}", content);
    }
  }

  /**
   * 测试无效元素查询处理
   *
   * <p>测试当查询不存在的元素时，系统是否能正确处理并返回适当的错误信息
   */
  @Test
  @DisplayName("测试无效元素查询处理")
  public void testInvalidElementQuery() {
    try (McpSyncClient mcpClient = buildHelloClient()) {
      ChatClient chatClient =
          ChatClient.builder(buildModel())
              .defaultTools(new SyncMcpToolCallbackProvider(mcpClient))
              .build();
      String question = "请告诉我元素'未知元素'的详细信息";
      log.info("QUESTION: {}", question);
      log.info("ASSISTANT: {}", chatClient.prompt(question).call().content());
    }
  }
}
