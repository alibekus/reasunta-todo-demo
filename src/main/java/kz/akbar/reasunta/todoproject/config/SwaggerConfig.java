package kz.akbar.reasunta.todoproject.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Reasunta To Do Project")
                                .version("0.0.1")
                                .contact(
                                        new Contact()
                                                .email("alibek.akbar@gmail.com")
                                                .name("Alibek Akbarov")
                                )
                );
    }
}
