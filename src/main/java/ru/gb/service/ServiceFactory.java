package ru.gb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceFactory {
    private final ProductService productService;
    private final ManufacturerService manufacturerService;
    private final CategoryService categoryService;
    private final ProductImageService productImageService;

    public ManufacturerService getManufacturerService() {
        return manufacturerService;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public ProductImageService getProductImageService() {
        return productImageService;
    }


    public ProductService getProductService() {
        return productService;
    }


}
