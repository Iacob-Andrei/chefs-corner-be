package com.chefscorner.ingredient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
//@OpenAPIDefinition(
//        info = @Info(title = "Ingredient APIs", version = "1.0", description = "Ingredient Service APIs")
//)
public class IngredientServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(IngredientServiceApplication.class, args);
    }
}
