package ru.gb.gbexternalapi.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbexternalapi.service.api.CategoryApi;
import ru.gb.gbexternalapi.web.dto.CategoryDto;

import java.util.List;

@FeignClient(url="localhost:8080/api/v1/category", value="category-client")
public interface CategoryService extends CategoryApi {
    @Override
    @GetMapping(produces = "application/json;charset=UTF-8",
            consumes = "application/json;charset=UTF-8")
    List<CategoryDto> getCategoryList();

    @Override
    @GetMapping(produces = "application/json;charset=UTF-8",
            consumes = "application/json;charset=UTF-8", value="/{categoryId}")
    ResponseEntity<?> getCategory(@PathVariable("categoryId") Long id);

    @Override
    @PostMapping(produces = "application/json;charset=UTF-8",
            consumes = "application/json;charset=UTF-8")
    ResponseEntity<?> handlePost(@Validated @RequestBody CategoryDto categoryDto);

    @Override
    @PutMapping(produces = "application/json;charset=UTF-8",
            consumes = "application/json;charset=UTF-8", value = "/{categoryId}")
    ResponseEntity<?> handleUpdate(@PathVariable("categoryId") Long id,
                                   @Validated @RequestBody CategoryDto categoryDto);

    @Override
    @DeleteMapping(produces = "application/json;charset=UTF-8",
            consumes = "application/json;charset=UTF-8", value = "/{categoryId}")
    void deleteById(@PathVariable("categoryId") Long id);
}
