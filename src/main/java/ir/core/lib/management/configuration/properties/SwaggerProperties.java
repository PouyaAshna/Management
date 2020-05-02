package ir.core.lib.management.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "swagger", ignoreUnknownFields = false)
public class SwaggerProperties {

    private boolean enable;
    private ApiInfo apiInfo;

    @Getter
    @Setter
    public static class ApiInfo {
        private String title;
        private String description;
        private String version;
        private String termsOfServiceUrl;
        private String license;
        private String licenseUrl;
        private Contact contact;

        @Getter
        @Setter
        public static class Contact {
            private String name;
            private String url;
            private String email;
        }
    }
}
