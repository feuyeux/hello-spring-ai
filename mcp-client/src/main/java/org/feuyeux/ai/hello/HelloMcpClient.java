package org.feuyeux.ai.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * HelloMcpClient类
 *
 * <p>这是MCP客户端应用程序的主入口点。MCP（Model Context Protocol）是一个用于 连接大型语言模型（LLM）和工具的协议。本类启动Spring
 * Boot应用程序，使客户端 能够与MCP服务器进行通信。
 */
@SpringBootApplication
public class HelloMcpClient {

  /**
   * 应用程序的入口点
   *
   * @param args 命令行参数
   */
  public static void main(String[] args) {
    SpringApplication.run(HelloMcpClient.class, args);
  }
}
