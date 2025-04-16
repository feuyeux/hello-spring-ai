import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.WebFluxSseClientTransport;
import io.modelcontextprotocol.spec.McpSchema;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.feuyeux.ai.hello.HelloMcpServer;
import org.feuyeux.ai.hello.service.HelloMcpService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * ClientSse测试类
 *
 * <p>该类使用Spring Boot Test框架测试MCP服务端功能。 通过随机端口启动嵌入式服务器，不需要事先手动启动服务。 测试包括服务连接、工具列表查询以及元素信息获取功能。
 */
@Slf4j
@SpringBootTest(
    classes = HelloMcpServer.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ApiSseTests {

  /** 随机端口号，由Spring Boot Test自动注入 */
  @LocalServerPort private int port;

  /** Web测试客户端 */
  @Autowired private WebTestClient webTestClient;

  /** 应用程序上下文 */
  @Autowired private ApplicationContext context;

  /** 元素周期表服务 */
  @Autowired private HelloMcpService helloMcpService;

  /**
   * 获取MCP客户端
   *
   * <p>使用当前测试服务器的随机端口创建MCP客户端
   *
   * @return MCP同步客户端实例
   */
  private McpSyncClient getMcpClient() {
    String baseUrl = "http://localhost:" + port;
    log.info("使用测试服务器地址: {}", baseUrl);

    var transport = new WebFluxSseClientTransport(WebClient.builder().baseUrl(baseUrl));
    var mcpClient = McpClient.sync(transport).build();
    mcpClient.initialize();
    return mcpClient;
  }

  /**
   * 测试基本连接和元素查询功能
   *
   * <p>验证服务器连接、工具列表查询以及通过名称查询元素（硅）信息的功能
   */
  @Test
  @DisplayName("测试MCP服务基本功能")
  public void testBasicConnection() {
    try (McpSyncClient client = getMcpClient()) {
      // 测试ping功能
      client.ping();
      log.info("Ping成功，服务器连接正常");

      // 列出并展示可用工具
      McpSchema.ListToolsResult toolsList = client.listTools();
      log.info("可用工具列表 = " + toolsList);
      // 断言工具列表不为空
      Assertions.assertNotNull(toolsList);
      Assertions.assertEquals(2, toolsList.tools().size());
      Assertions.assertTrue(
          toolsList.tools().stream().anyMatch(tool -> "getElement".equals(tool.name())));
      Assertions.assertTrue(
          toolsList.tools().stream().anyMatch(tool -> "getElementByPosition".equals(tool.name())));

      // 调用getElement工具查询"硅"元素信息
      McpSchema.CallToolResult helloResult =
          client.callTool(new McpSchema.CallToolRequest("getElement", Map.of("name", "硅")));
      log.info("查询结果: {}", helloResult);
      // 断言结果不为空且包含预期内容
      Assertions.assertNotNull(helloResult);
      Assertions.assertFalse(helloResult.isError());
      Assertions.assertEquals(1, helloResult.content().size());
      String resultText = helloResult.content().get(0).toString();
      Assertions.assertTrue(resultText.contains("硅"));
      Assertions.assertTrue(resultText.contains("Si"));
      Assertions.assertTrue(resultText.contains("14"));
    }
  }

  /**
   * 测试按元素名称查询功能
   *
   * <p>测试使用getElement工具查询不同元素信息的功能
   */
  @Test
  @DisplayName("测试按元素名称查询")
  public void testElementByName() {
    try (McpSyncClient client = getMcpClient()) {
      // 查询氢元素信息
      McpSchema.CallToolResult hydrogenResult =
          client.callTool(new McpSchema.CallToolRequest("getElement", Map.of("name", "氢")));
      log.info("氢元素查询结果: {}", hydrogenResult);
      // 断言氢元素查询结果
      Assertions.assertNotNull(hydrogenResult);
      Assertions.assertFalse(hydrogenResult.isError());
      String hydrogenText = hydrogenResult.content().get(0).toString();
      Assertions.assertTrue(hydrogenText.contains("氢"));
      Assertions.assertTrue(hydrogenText.contains("H"));
      Assertions.assertTrue(hydrogenText.contains("1"));

      // 查询氧元素信息
      McpSchema.CallToolResult oxygenResult =
          client.callTool(new McpSchema.CallToolRequest("getElement", Map.of("name", "氧")));
      log.info("氧元素查询结果: {}", oxygenResult);
      // 断言氧元素查询结果
      Assertions.assertNotNull(oxygenResult);
      Assertions.assertFalse(oxygenResult.isError());
      String oxygenText = oxygenResult.content().get(0).toString();
      Assertions.assertTrue(oxygenText.contains("氧"));
      Assertions.assertTrue(oxygenText.contains("O"));
      Assertions.assertTrue(oxygenText.contains("8"));

      // 查询铁元素信息
      McpSchema.CallToolResult ironResult =
          client.callTool(new McpSchema.CallToolRequest("getElement", Map.of("name", "铁")));
      log.info("铁元素查询结果: {}", ironResult);
      // 断言铁元素查询结果
      Assertions.assertNotNull(ironResult);
      Assertions.assertFalse(ironResult.isError());
      String ironText = ironResult.content().get(0).toString();
      Assertions.assertTrue(ironText.contains("铁"));
      Assertions.assertTrue(ironText.contains("Fe"));
      Assertions.assertTrue(ironText.contains("26"));
    }
  }

  /**
   * 测试按元素位置查询功能
   *
   * <p>测试使用getElementByPosition工具查询不同位置元素信息的功能
   */
  @Test
  @DisplayName("测试按元素位置查询")
  public void testElementByPosition() {
    try (McpSyncClient client = getMcpClient()) {
      // 查询位置1的元素（氢）
      McpSchema.CallToolResult position1Result =
          client.callTool(
              new McpSchema.CallToolRequest("getElementByPosition", Map.of("position", 1)));
      log.info("位置1元素查询结果: {}", position1Result);
      // 断言位置1元素查询结果
      Assertions.assertNotNull(position1Result);
      Assertions.assertFalse(position1Result.isError());
      String position1Text = position1Result.content().get(0).toString();
      Assertions.assertTrue(position1Text.contains("氢"));
      Assertions.assertTrue(position1Text.contains("H"));
      Assertions.assertTrue(position1Text.contains("1"));

      // 查询位置6的元素（碳）
      McpSchema.CallToolResult position6Result =
          client.callTool(
              new McpSchema.CallToolRequest("getElementByPosition", Map.of("position", 6)));
      log.info("位置6元素查询结果: {}", position6Result);
      // 断言位置6元素查询结果
      Assertions.assertNotNull(position6Result);
      Assertions.assertFalse(position6Result.isError());
      String position6Text = position6Result.content().get(0).toString();
      Assertions.assertTrue(position6Text.contains("碳"));
      Assertions.assertTrue(position6Text.contains("C"));
      Assertions.assertTrue(position6Text.contains("6"));

      // 查询位置79的元素（金）
      McpSchema.CallToolResult position79Result =
          client.callTool(
              new McpSchema.CallToolRequest("getElementByPosition", Map.of("position", 79)));
      log.info("位置79元素查询结果: {}", position79Result);
      // 断言位置79元素查询结果
      Assertions.assertNotNull(position79Result);
      Assertions.assertFalse(position79Result.isError());
      String position79Text = position79Result.content().get(0).toString();
      Assertions.assertTrue(position79Text.contains("金"));
      Assertions.assertTrue(position79Text.contains("Au"));
      Assertions.assertTrue(position79Text.contains("79"));
    }
  }

  /**
   * 测试错误处理功能
   *
   * <p>测试服务器对无效请求的处理能力，包括不存在的元素和无效的位置
   */
  @Test
  @DisplayName("测试错误处理")
  public void testErrorHandling() {
    try (McpSyncClient client = getMcpClient()) {
      // 查询不存在的元素
      McpSchema.CallToolResult nonExistentResult =
          client.callTool(new McpSchema.CallToolRequest("getElement", Map.of("name", "不存在元素")));
      log.info("不存在元素查询结果: {}", nonExistentResult);
      // 断言不存在元素的错误处理
      Assertions.assertNotNull(nonExistentResult);
      Assertions.assertFalse(nonExistentResult.isError());
      String nonExistentText =
          ((io.modelcontextprotocol.spec.McpSchema.TextContent) nonExistentResult.content().get(0))
              .text();
      Assertions.assertEquals("元素不存在", nonExistentText.replaceAll("^\"|\"$", ""));

      // 查询无效位置（0是无效的原子序数）
      McpSchema.CallToolResult invalidPositionResult =
          client.callTool(
              new McpSchema.CallToolRequest("getElementByPosition", Map.of("position", 0)));
      log.info("无效位置查询结果: {}", invalidPositionResult);
      // 断言无效位置的错误处理
      Assertions.assertNotNull(invalidPositionResult);
      Assertions.assertFalse(invalidPositionResult.isError());
      String invalidPositionText =
          ((io.modelcontextprotocol.spec.McpSchema.TextContent)
                  invalidPositionResult.content().get(0))
              .text();
      Assertions.assertTrue(invalidPositionText.contains("元素位置无效"));
      Assertions.assertTrue(invalidPositionText.contains("1到118之间"));

      // 查询超出范围的位置（假设周期表最多118个元素）
      McpSchema.CallToolResult outOfRangeResult =
          client.callTool(
              new McpSchema.CallToolRequest("getElementByPosition", Map.of("position", 200)));
      log.info("超出范围位置查询结果: {}", outOfRangeResult);
      // 断言超出范围位置的错误处理
      Assertions.assertNotNull(outOfRangeResult);
      Assertions.assertFalse(outOfRangeResult.isError());
      String outOfRangeText =
          ((io.modelcontextprotocol.spec.McpSchema.TextContent) outOfRangeResult.content().get(0))
              .text();
      Assertions.assertTrue(outOfRangeText.contains("元素位置无效"));
      Assertions.assertTrue(outOfRangeText.contains("1到118之间"));
    }
  }

  /**
   * 直接测试服务类
   *
   * <p>通过直接调用服务类方法测试元素查询功能，不需要通过MCP协议
   */
  @Test
  @DisplayName("直接测试元素服务类")
  public void testServiceDirectly() {
    // 测试获取元素信息
    String siliconInfo = helloMcpService.getElement("硅");
    log.info("硅元素信息: {}", siliconInfo);
    // 断言硅元素信息内容
    Assertions.assertNotNull(siliconInfo);
    Assertions.assertTrue(siliconInfo.contains("硅"));
    Assertions.assertTrue(siliconInfo.contains("Si"));
    Assertions.assertTrue(siliconInfo.contains("28.085"));
    Assertions.assertTrue(siliconInfo.contains("14"));
    Assertions.assertTrue(siliconInfo.contains("3"));
    Assertions.assertTrue(siliconInfo.contains("IVA"));

    // 测试获取指定位置元素信息
    String position14Info = helloMcpService.getElementByPosition(14);
    log.info("位置14元素信息: {}", position14Info);
    // 断言位置14元素信息内容
    Assertions.assertNotNull(position14Info);
    Assertions.assertTrue(position14Info.contains("硅"));
    Assertions.assertTrue(position14Info.contains("Si"));

    // 确认两种方式获取的是同一元素
    log.info("两种方式是否获取相同元素: {}", siliconInfo.equals(position14Info));
    // 断言两种方式获取的是相同结果
    Assertions.assertEquals(siliconInfo, position14Info);
  }
}
