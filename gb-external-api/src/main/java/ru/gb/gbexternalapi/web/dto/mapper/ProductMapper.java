package ru.gb.gbexternalapi.web.dto.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gb.gbexternalapi.dao.CategoryDao;
import ru.gb.gbexternalapi.dao.ManufacturerDao;
import ru.gb.gbexternalapi.entity.Category;
import ru.gb.gbexternalapi.entity.Manufacturer;
import ru.gb.gbexternalapi.entity.Product;
import ru.gb.gbexternalapi.web.dto.ProductDto;
import ru.gb.gbexternalapi.web.dto.ProductManufacturerDto;


import java.util.NoSuchElementException;

@Mapper(uses = ManufacturerMapper.class)
public interface ProductMapper {
    Product toProduct(ProductDto productDto, @Context ManufacturerDao manufacturerDao, @Context CategoryDao categoryDao);

    ProductDto toProductDto(Product product);

    @Mapping(source = "manufacturer", target = "manufacturerDto")
    ProductManufacturerDto toProductManufacturerDto(Product product);

    default Manufacturer getManufacturer(String manufacturer, @Context ManufacturerDao manufacturerDao) {
        return manufacturerDao.findByName(manufacturer).orElseThrow(NoSuchElementException::new);
    }

    default String getManufacturer(Manufacturer manufacturer) {
        return manufacturer.getName();
    }

    default Category getCategory(String category, @Context CategoryDao categoryDao) {
        return categoryDao.findByTitle(category).orElseThrow(NoSuchElementException::new);
    }

    default String getCategory(Category category) {
        return category.getTitle();
    }

}
