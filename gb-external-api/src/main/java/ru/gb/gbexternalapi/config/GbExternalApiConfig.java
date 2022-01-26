package ru.gb.gbexternalapi.config;


import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import ru.gb.gbexternalapi.service.feign.CategoryService;
import ru.gb.gbexternalapi.service.feign.ManufacturerService;
import ru.gb.gbexternalapi.service.feign.ProductService;

@Configuration
@EnableFeignClients(basePackageClasses = {ProductService.class, ManufacturerService.class, CategoryService.class})
public class GbExternalApiConfig {
}
