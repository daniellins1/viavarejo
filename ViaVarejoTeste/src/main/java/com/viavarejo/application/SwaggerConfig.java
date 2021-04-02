package com.viavarejo.application;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = "com.viavarejo")
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,  globalResponseMessages())
                .globalResponseMessage(RequestMethod.POST,  globalResponseMessages())
                .globalResponseMessage(RequestMethod.PATCH,  globalResponseMessages())
                .select()
                .apis(RequestHandlerSelectors.any())
                .build();
    }

    private List<ResponseMessage> globalResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder().code(401).message("Voce nao esta autorizado a visualizar este recurso").build(),
                new ResponseMessageBuilder().code(403).message("O acesso a este recurso nao e permitido").build(),
                new ResponseMessageBuilder().code(404).message("O recurso que voce esta tentando acessar nao foi localizado").build(),
                new ResponseMessageBuilder().code(500).message("Ocorreu um erro durante o processamento da requisicao").build()
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Via Varejo Rest API",
                "Microservico REST que tem a função de servir como uma interface e fornecer endpoints\r\n",
                "Versão 1.0.0",
                "Termos de Serviço",
                new Contact("Daniel Moreira Lins",
                        "https://www.linkedin.com/in/danielmlins/", "danie_mlins@yahoo.com.br"),
                "Licenca de Utilizacao: ",
                "https://www.linkedin.com/in/danielmlins/",
                Collections.emptyList());
    }


}
