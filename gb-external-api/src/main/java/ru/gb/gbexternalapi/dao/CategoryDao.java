package ru.gb.gbexternalapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.gbexternalapi.entity.Category;

import java.util.Optional;

public interface CategoryDao extends JpaRepository<Category, Long> {
    Optional<Category> findByTitle(String title);
}
