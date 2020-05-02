package ir.core.lib.management.configuration;

import ir.core.lib.management.configuration.properties.SwaggerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
@ConditionalOnProperty(
        value = "swagger.enable",
        havingValue = "true"
)
public class SwaggerConfig {

    private final SwaggerProperties swaggerProperties;

    public SwaggerConfig(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                swaggerProperties.getApiInfo() != null ? swaggerProperties.getApiInfo().getTitle() : "",
                swaggerProperties.getApiInfo() != null ? swaggerProperties.getApiInfo().getDescription() : "",
                swaggerProperties.getApiInfo() != null ? swaggerProperties.getApiInfo().getVersion() : "",
                swaggerProperties.getApiInfo() != null ? swaggerProperties.getApiInfo().getTermsOfServiceUrl() : "",
                new Contact(
                        swaggerProperties.getApiInfo() != null ? swaggerProperties.getApiInfo().getContact() != null ? swaggerProperties.getApiInfo().getContact().getName() : "" : "",
                        swaggerProperties.getApiInfo() != null ? swaggerProperties.getApiInfo().getContact() != null ? swaggerProperties.getApiInfo().getContact().getUrl() : "" : "",
                        swaggerProperties.getApiInfo() != null ? swaggerProperties.getApiInfo().getContact() != null ? swaggerProperties.getApiInfo().getContact().getEmail() : "" : ""
                ),
                swaggerProperties.getApiInfo() != null ? swaggerProperties.getApiInfo().getLicense() : "",
                swaggerProperties.getApiInfo() != null ? swaggerProperties.getApiInfo().getLicenseUrl() : "",
                Collections.emptyList());
    }
}
