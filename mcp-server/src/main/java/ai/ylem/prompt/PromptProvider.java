package ai.ylem.prompt;

import io.modelcontextprotocol.spec.McpSchema.GetPromptResult;
import io.modelcontextprotocol.spec.McpSchema.PromptMessage;
import io.modelcontextprotocol.spec.McpSchema.Role;
import io.modelcontextprotocol.spec.McpSchema.TextContent;
import java.util.List;
import org.springaicommunity.mcp.annotation.McpArg;
import org.springaicommunity.mcp.annotation.McpPrompt;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * PromptProvider
 *
 * @since 2025/09/14
 **/
@Component
public class PromptProvider {

    @McpPrompt(name = "personalized-message", description = "Generate a personalized message")
    public GetPromptResult personalizedMessage(@McpArg(name = "name", required = true) String name,
        @McpArg(name = "age") Integer age, @McpArg(name = "interests") String interests) {

        StringBuilder message = new StringBuilder();
        message.append("Hello, ").append(name).append("!\n\n");

        if (age != null) {
            message.append("At ").append(age).append(" years old, ");
        }

        if (StringUtils.hasText(interests)) {
            message.append("Your interest in ").append(interests);
        }

        return new GetPromptResult("Personalized Message",
            List.of(new PromptMessage(Role.ASSISTANT, new TextContent(message.toString()))));
    }
}
