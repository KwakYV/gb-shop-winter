package ru.gb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.api.product.dto.ProductDto;
import ru.gb.dao.CategoryDao;
import ru.gb.dao.ManufacturerDao;
import ru.gb.dao.ProductDao;
import ru.gb.entity.Category;
import ru.gb.entity.Product;
import ru.gb.entity.enums.Status;
import ru.gb.exceptions.ProductException;
import ru.gb.web.dto.ProductManufacturerDto;
import ru.gb.web.dto.mapper.ProductMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductDao productDao;
    private final ManufacturerDao manufacturerDao;
    private final CategoryDao categoryDao;
    private final ProductMapper productMapper;

    @Transactional(propagation = Propagation.NEVER, isolation = Isolation.DEFAULT)
    public long count() {
        System.out.println(productDao.count());
        return productDao.count();
    }

    @Transactional
    public ProductDto save(final ProductDto productDto) throws Exception{
        Product product = productMapper.toProduct(productDto, manufacturerDao, categoryDao);

        if (product.getId() != null) {
            productDao.findById(productDto.getId()).ifPresent(
                    (p) -> product.setVersion(p.getVersion())
            );
        } else {

            if (productDto.getCategory() == null || productDto.getManufacturer() == null) {
                throw new ProductException("Category or Manufacturer is empty. Please fill current fields.");
            } else {
                Optional<Category> optionalCategory = categoryDao.findByTitle(productDto.getCategory());
                if (optionalCategory.isEmpty() ||
                        manufacturerDao.findByName(productDto.getManufacturer()).isEmpty()) {
                    throw new ProductException("Category or Manufacturer does not exist in DB. Please correct values.");
                }
                product.setCategories(new HashSet<>(Arrays.asList(optionalCategory.get())));
            }

        }



        return productMapper.toProductDto(productDao.save(product));
    }


    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        return productMapper.toProductDto(productDao.findById(id).orElse(null));
    }


    public List<ProductDto> findAll() {
        return productDao.findAll().stream().map(productMapper::toProductDto).collect(Collectors.toList());
    }

    public List<Product> findAllActive() {
        return productDao.findAllByStatus(Status.ACTIVE);
    }

    public void deleteById(Long id) {
        try {
            productDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
        }
    }

    public void disable(Long id) {
        Optional<Product> product = productDao.findById(id);
        product.ifPresent(p -> {
            p.setStatus(Status.DISABLED);
            productDao.save(p);
        });
    }

    public List<Product> findAll(int page, int size) {
        return productDao.findAllByStatus(Status.ACTIVE, PageRequest.of(page, size));
    }

    public List<Product> findAllSortedById() {
        return productDao.findAllByStatus(Status.ACTIVE, Sort.by("id"));
    }

    public List<Product> findAllSortedById(int page, int size) {
        return productDao.findAllByStatus(Status.ACTIVE, PageRequest.of(page, size, Sort.by("id")));
    }


    public List<ProductManufacturerDto> findAllInfo() {
        return productDao.findAll().stream().map(productMapper::toProductManufacturerDto).collect(Collectors.toList());
    }

}
