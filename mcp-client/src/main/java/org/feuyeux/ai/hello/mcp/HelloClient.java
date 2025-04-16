package org.feuyeux.ai.hello.mcp;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.WebFluxSseClientTransport;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * HelloClient类
 *
 * <p>此类负责创建与MCP服务器的连接，用于调用元素周期表相关的工具。 使用WebFluxSseClientTransport实现基于SSE（Server-Sent Events）的MCP通信。
 */
@Slf4j
public class HelloClient {

  /**
   * 构建HelloMCP客户端
   *
   * <p>此方法创建一个同步MCP客户端，连接到本地运行的服务器。 设置了10秒的请求超时时间，确保在网络延迟时能够适当响应。
   *
   * @return McpSyncClient 配置好的同步MCP客户端实例
   */
  public static McpSyncClient buildHelloClient() {
    // 创建基于SSE的传输层，连接到本地8061端口
    var transport =
        new WebFluxSseClientTransport(WebClient.builder().baseUrl("http://localhost:8061"));
    // 构建同步MCP客户端，设置10秒超时
    McpSyncClient mcpClient =
        McpClient.sync(transport).requestTimeout(Duration.ofSeconds(10)).build();
    // 初始化客户端连接
    var init = mcpClient.initialize();
    log.info("MCP Initialized: {}", init);
    return mcpClient;
  }
}
