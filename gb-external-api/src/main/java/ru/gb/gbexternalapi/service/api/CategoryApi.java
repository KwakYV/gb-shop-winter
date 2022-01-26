package ru.gb.gbexternalapi.service.api;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.gb.gbexternalapi.web.dto.CategoryDto;

import java.util.List;

public interface CategoryApi {
    public List<CategoryDto> getCategoryList();
    public ResponseEntity<?> getCategory(Long id);
    public ResponseEntity<?> handlePost(CategoryDto categoryDto);
    public ResponseEntity<?> handleUpdate(Long id,
                                          CategoryDto categoryDto);
    public void deleteById(Long id);
}
