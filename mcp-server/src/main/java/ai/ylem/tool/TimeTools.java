package ai.ylem.tool;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * TimeTools
 *
 * @since 2025/09/14
 **/
@Component
public class TimeTools {

    @McpTool(name = "current_time", description = "获取当前时间")
    public String currentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @McpTool(name = "md5", description = "获取一段文本的md5值")
    public String md5(@McpToolParam(description = "文本内容") String text) {
        return DigestUtils.md5DigestAsHex(text.getBytes());
    }

}
