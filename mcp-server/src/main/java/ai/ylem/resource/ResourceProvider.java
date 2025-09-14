package ai.ylem.resource;

import org.springaicommunity.mcp.annotation.McpResource;
import org.springframework.stereotype.Component;
import org.springframework.util.SystemPropertyUtils;

/**
 * ResourceProvider
 *
 * @since 2025/09/14
 **/
@Component
public class ResourceProvider {

    @McpResource(uri = "config://{key}", name = "Configuration", description = "Provides configuration data")
    public String getConfig(String key) {
        return SystemPropertyUtils.resolvePlaceholders(key);
    }
}
