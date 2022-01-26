package ru.gb.gbexternalapi.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbexternalapi.service.api.ProductApi;
import ru.gb.gbexternalapi.web.dto.ProductDto;
import ru.gb.gbexternalapi.web.dto.ProductManufacturerDto;

import java.util.List;

@FeignClient(url="localhost:8080/api/v1/product", value="product-client")
public interface ProductService extends ProductApi {

    @Override
    @GetMapping(produces = "application/json;charset=UTF-8",
            consumes = "application/json;charset=UTF-8")
    public List<ProductDto> getProductList();

    @Override
    @GetMapping(produces = "application/json;charset=UTF-8",
            consumes = "application/json;charset=UTF-8", value = "/info")
    public List<ProductManufacturerDto> getInfoProductList();

    @Override
    @GetMapping(produces = "application/json;charset=UTF-8",
            consumes = "application/json;charset=UTF-8", value = "/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable("productId") Long id);

    @Override
    @PostMapping(produces = "application/json;charset=UTF-8",
            consumes = "application/json;charset=UTF-8")
    public ResponseEntity<?> handlePost(@Validated @RequestBody ProductDto productDto);

    @Override
    @PutMapping(produces = "application/json;charset=UTF-8",
            consumes = "application/json;charset=UTF-8", value = "/{productId}")
    public ResponseEntity<?> handleUpdate(@PathVariable("productId") Long id,
                                          @Validated @RequestBody ProductDto productDto);

    @Override
    @DeleteMapping(produces = "application/json;charset=UTF-8",
            consumes = "application/json;charset=UTF-8", value = "/{productId}")
    public void deleteById(@PathVariable("productId") Long id);
}
