package clock.rh.userManagement.config.documentation;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("ClockRH User Management")
                        .description("Serviço de gerenciamento de usuários da ClockRH. Cadastre, atualize, liste e remova usuários.")
                        .contact(new Contact()
                                .name("Flávio Alexandre")
                                .email("flavioalexandrework@gmail.com"))
                        .license(new License()
                                .name("MIT")
                                .url("http://clockrh/api/licenca")));
    }
}