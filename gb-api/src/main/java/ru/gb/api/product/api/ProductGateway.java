package ru.gb.api.product.api;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.product.dto.ProductDto;

import java.net.URI;
import java.util.List;

@FeignClient(url = "http://127.0.0.1:8080/internal/api/v1/product", name = "ProductGateway")
public interface ProductGateway {

    @GetMapping(produces = "application/json;charset=UTF-8",
            consumes = "application/json;charset=UTF-8")
    public List<ProductDto> getProductList();

    @GetMapping(produces = "application/json;charset=UTF-8",
            consumes = "application/json;charset=UTF-8", value = "/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable("productId") Long id);

    @PostMapping(produces = "application/json;charset=UTF-8",
            consumes = "application/json;charset=UTF-8")
    public ResponseEntity<?> handlePost(@Validated @RequestBody ProductDto productDto);

    @PutMapping(produces = "application/json;charset=UTF-8",
            consumes = "application/json;charset=UTF-8", value = "/{productId}")
    public ResponseEntity<?> handleUpdate(@PathVariable("productId") Long id,
                                          @Validated @RequestBody ProductDto productDto);

    @DeleteMapping(produces = "application/json;charset=UTF-8",
            consumes = "application/json;charset=UTF-8", value = "/{productId}")
    public void deleteById(@PathVariable("productId") Long id);
}
