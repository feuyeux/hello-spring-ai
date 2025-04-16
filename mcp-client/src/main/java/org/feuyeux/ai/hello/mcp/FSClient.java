package org.feuyeux.ai.hello.mcp;

import static org.feuyeux.ai.hello.utils.DirUtils.getUserDir;
import static org.feuyeux.ai.hello.utils.NpxUtils.buildNpxCmd;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.ServerParameters;
import io.modelcontextprotocol.client.transport.StdioClientTransport;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FSClient {
  public static McpSyncClient buildFSClient() {
    String userDir = getUserDir();
    var stdioParams =
        ServerParameters.builder(buildNpxCmd())
            .args("-y", "@modelcontextprotocol/server-filesystem", userDir)
            .build();
    McpSyncClient mcpClient =
        McpClient.sync(new StdioClientTransport(stdioParams))
            .requestTimeout(Duration.ofSeconds(10))
            .build();
    var init = mcpClient.initialize();
    log.info("MCP Initialized: {}", init);
    return mcpClient;
  }
}
