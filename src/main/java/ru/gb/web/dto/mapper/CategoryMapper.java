package ru.gb.web.dto.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gb.dao.ManufacturerDao;
import ru.gb.entity.Category;
import ru.gb.entity.Manufacturer;
import ru.gb.entity.Product;
import ru.gb.web.dto.CategoryDto;
import ru.gb.web.dto.ProductManufacturerDto;

import java.util.NoSuchElementException;

@Mapper(uses = ProductMapper.class)
public interface CategoryMapper {
    @Mapping(source = "categoryId", target = "id")
    Category toCategory(CategoryDto categoryDto,  @Context ManufacturerDao manufacturerDao);

    @Mapping(source = "id", target = "categoryId")
    CategoryDto toCategoryDto(Category category,  @Context ManufacturerDao manufacturerDao);

}
