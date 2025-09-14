package ai.ylem.handler;

import io.modelcontextprotocol.spec.McpSchema.LoggingMessageNotification;
import lombok.extern.slf4j.Slf4j;
import org.springaicommunity.mcp.annotation.McpLogging;
import org.springframework.stereotype.Component;

/**
 * LoggingHandler
 *
 * @since 2025/09/14
 **/
@Slf4j
@Component
public class LoggingHandler {

    @McpLogging(clients = "my-server")
    public void handleLoggingMessage(LoggingMessageNotification notification) {
        log.info("Received {} log: {}", notification.level(), notification.data());
    }
}
