package com.projects.mytodolist.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI getInfo() {
        Contact contact = new Contact();
        contact.setName("Cristian");
        contact.setEmail("nem.cristianrc@gmail.com");
        contact.setUrl("https://github.com/cristian-95/");

        Info info = new Info()
                .title("My to-do List")
                .description("Um gerenciador de tarefas simples, para fins de estudo")
                .version("1.0")
                .contact(contact);

        return new OpenAPI().info(info);

    }

}
