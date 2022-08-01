package com.springframework.csscapstone.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.ADMIN;
import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.COLLABORATOR;
import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.ENTERPRISE;
import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.MODERATOR;
import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.USER;

@Configuration
public class Swagger3Config {

    @Bean
    public OpenAPI customOpenAPI() {

        OpenAPI openApi = new OpenAPI()
                .info(new Info().title("CSS API")
                        .description("Make It Great Again")
                        .contact(new Contact().email("css@gmail.com").name("Css-System").url(""))
                        .license(new License().name("Apache 2.0").url("www.apache.org/licenses/LICENSE-2.0.html"))
                        .version("1.0.0"));

        openApi.components(
                new Components().addSecuritySchemes("BEARER_JWT",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                ));

        openApi.addSecurityItem(new SecurityRequirement()
                .addList("BEARER_JWT", Arrays.asList("read", "write")));

        return openApi;
    }

    @Bean
    public GroupedOpenApi userGroupOpenApi() {
        String[] _user = {USER + "/**"};
        return GroupedOpenApi.builder()
                .group("css_system_api user")
                .pathsToMatch(_user)
                .build();
    }

    @Bean
    public GroupedOpenApi adminGroupApi() {
        String[] api = {ADMIN + "/**"};
        String nameGroup = "css_system_api admin";
        return createGroupApi(api, nameGroup);
    }

    @Bean
    public GroupedOpenApi moderatorGroupApi() {
        String[] api = {MODERATOR + "/**"};
        String nameGroup = "css_system_api moderator";
        return createGroupApi(api, nameGroup);
    }
    @Bean
    public GroupedOpenApi enterpriseGroupApi() {
        String[] api = {ENTERPRISE + "/**"};
        String nameGroup = "css_system_api enterprise";
        return createGroupApi(api, nameGroup);
    }

    @Bean
    public GroupedOpenApi collaboratorGroupApi() {
        String[] api = {COLLABORATOR + "/**"};
        String nameGroup = "css_system_api collaborator";
        return createGroupApi(api, nameGroup);
    }


    private GroupedOpenApi createGroupApi(String[] path, String nameGroup) {
        return GroupedOpenApi.builder()
                .group(nameGroup)
                .pathsToMatch(path)
                .build();
    }

}
