package ai.ylem.util;

import java.util.Map;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

/**
 * 资源实用程序
 *
 * @since 2025/09/27
 **/
public abstract class ResourceUtils {

    /**
     * 获取资源
     *
     * @param resourceUrl 资源 URL
     * @return {@link Resource }
     */
    public static Resource getResource(String resourceUrl) {
        return (new DefaultResourceLoader()).getResource(resourceUrl);
    }

    /**
     * 获取元数据
     *
     * @param resource 资源
     * @return {@link Map }<{@link String },{@link Object }>
     */
    public static Map<String, Object> getMetadata(Resource resource) {
        String filename = resource.getFilename();
        if (StringUtils.hasText(filename)) {
            return Map.of("filename", filename);
        }
        return Map.of();
    }

}
