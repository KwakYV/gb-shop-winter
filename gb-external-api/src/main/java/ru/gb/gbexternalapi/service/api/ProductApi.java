package ru.gb.gbexternalapi.service.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import ru.gb.gbexternalapi.web.dto.ProductDto;
import ru.gb.gbexternalapi.web.dto.ProductManufacturerDto;

import java.util.List;


public interface ProductApi {
    public List<ProductDto> getProductList();
    public List<ProductManufacturerDto> getInfoProductList();
    public ResponseEntity<?> getProduct(Long id);
    public ResponseEntity<?> handlePost(ProductDto productDto);
    public ResponseEntity<?> handleUpdate(Long id, ProductDto productDto);
    public void deleteById(Long id);
}
