package ru.gb.gbexternalapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.gbexternalapi.service.feign.CategoryService;
import ru.gb.gbexternalapi.service.feign.ManufacturerService;
import ru.gb.gbexternalapi.service.feign.ProductService;
import ru.gb.gbexternalapi.web.dto.CategoryDto;
import ru.gb.gbexternalapi.web.dto.ManufacturerDto;
import ru.gb.gbexternalapi.web.dto.ProductDto;
import ru.gb.gbexternalapi.web.dto.ProductManufacturerDto;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/gb-shop")
public class ShopController {

    private final ProductService productService;
    private final ManufacturerService manufacturerService;
    private final CategoryService categoryService;

    @GetMapping("/product")
    public List<ProductDto> getProducts() {
        return productService.getProductList();
    }

    @GetMapping("/product/info")
    public List<ProductManufacturerDto> getProductInfo() {
        return productService.getInfoProductList();
    }

    @GetMapping("/manufacturer")
    public List<ManufacturerDto> getManufacturers() {
        return manufacturerService.getManufacturerList();
    }

    @GetMapping("/category")
    public List<CategoryDto> getCategories() {
        return categoryService.getCategoryList();
    }
}
