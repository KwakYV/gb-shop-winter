package ru.gb.gbexternalapi.service.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.gb.gbexternalapi.web.dto.ManufacturerDto;

import java.util.List;

public interface ManufacturerApi {
    public List<ManufacturerDto> getManufacturerList();
    public ResponseEntity<?> getManufacturer(Long id);
    public ResponseEntity<?> handlePost(ManufacturerDto manufacturerDto);
    public ResponseEntity<?> handleUpdate(Long id,
                                          ManufacturerDto manufacturerDto);
    public void deleteById(Long id);
}
