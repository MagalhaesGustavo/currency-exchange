package com.currencyexchange.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI springDoc() {
        return new OpenAPI()
                .info(getInfo())
                .servers(getServers());
    }

    private Info getInfo() {
        return new Info()
                .title("Currency Exchange")
                .version("1.0.0")
                .description("This microservice is responsible for registering accounts and performing transactions between different currencies.");
    }

    private List<Server> getServers() {
        Server serverLocal = new Server();
        serverLocal.setDescription("LOCAL");
        serverLocal.setUrl("http://localhost:8015/");
        return List.of(serverLocal);
    }
}