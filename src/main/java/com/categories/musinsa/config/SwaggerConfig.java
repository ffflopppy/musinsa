package com.categories.musinsa.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SwaggerConfig {

    @Value("${spring.profiles.active}")
    private String active;

    @Bean
    public OpenAPI customOpenAPI( ) {

        OpenAPI openAPI = new OpenAPI();
        openAPI.components(new Components().addSecuritySchemes("basicScheme", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")));

        openAPI.info(new Info()
                .title("["+active+"] I want to work for Musinsa")
                .version(active +"." + "yoonjin.v1" )
                .description("Categories"
                        +"<br> '목록' 조건 [없을 경우] : 전체 검색"
                        +"<br> '목록' 조건 [카테고리 이름]: 해당 카테고리의 모든 하위 카테고리"
                        +"<br> '등록' : 카테고리 이름 중복 가능"
                        +"<br> '등록' : 상위 카테고리가 설정 가능 / 상위 카테고리가 없으면 최상위 카테고리"
                        +"<br> '수정' : 카테고리 이름 수정 가능"
                        +"<br> '수정' : 상위 카테고리 수정 불가능"
                        +"<br> '삭제' : 상위 카테고리 수정 불가능"
                    )

                )
                .servers(null);

        return openAPI;
    }
}
