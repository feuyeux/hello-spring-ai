package org.feuyeux.ai.hello;

import org.feuyeux.ai.hello.service.HelloMcpService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * HelloMcpServer类
 *
 * <p>这是MCP服务器应用程序的主入口点。该服务器提供了元素周期表相关的工具API， 可通过MCP（Model Context Protocol）协议被客户端调用。
 */
@SpringBootApplication
public class HelloMcpServer {

  /**
   * 应用程序的入口点
   *
   * @param args 命令行参数
   */
  public static void main(String[] args) {
    SpringApplication.run(HelloMcpServer.class, args);
  }

  /**
   * 注册元素周期表工具
   *
   * <p>此Bean将HelloMcpService中的方法注册为可通过MCP协议调用的工具。 ToolCallbackProvider负责处理工具调用请求并执行相应的方法。
   *
   * @param helloMcpService 元素周期表服务
   * @return ToolCallbackProvider 工具回调提供者
   */
  @Bean
  public ToolCallbackProvider helloTools(HelloMcpService helloMcpService) {
    return MethodToolCallbackProvider.builder().toolObjects(helloMcpService).build();
  }
}
