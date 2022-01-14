package ru.gb.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.gb.web.dto.CategoryDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryControllerTest {

    final private static String CATEGORY_TITLE = "FRESH";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Order(1)
    public void saveCategoryTest() throws Exception {
        mockMvc.perform(post("/api/v1/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(CategoryDto.builder()
                                .title(CATEGORY_TITLE)
                                .build())
                ))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    public void findAllTest() throws Exception {
        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].title").value(CATEGORY_TITLE));
    }

    @Test
    @Order(3)
    public void findByIdOkTest() throws Exception {
        mockMvc.perform(get("/api/v1/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value(CATEGORY_TITLE));
    }

    @Test
    @Order(4)
    public void findByIdNotFoundTest() throws Exception {
        mockMvc.perform(get("/api/v1/category/222"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(5)
    public void findByTitleOkTest() throws Exception {
        mockMvc.perform(get("/api/v1/category/title/FRESH"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value(CATEGORY_TITLE));
    }

    @Test
    @Order(6)
    public void findByTitleNotFoundTest() throws Exception {
        mockMvc.perform(get("/api/v1/category/title/MEAT"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(7)
    public void updateById() throws Exception {
        mockMvc.perform(put("/api/v1/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(CategoryDto.builder()
                                        .title("MEAT")
                                        .build()
                                )
                        ))
                .andExpect(status().isNoContent());
    }

    /**
     * This test for checking results of test 7
     */

    @Test
    @Order(8)
    public void checkUpdateTest() throws Exception {
        mockMvc.perform(get("/api/v1/category/title/MEAT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("MEAT"));
    }

    @Test
    @Order(9)
    public void deleteByIdTest() throws Exception {
        mockMvc.perform(delete("/api/v1/category/1"))
                .andExpect(status().isNoContent());
    }

    /**
     * This test for checking results of test 9
     */

    @Test
    @Order(10)
    public void checkDeleteTest() throws Exception {
        mockMvc.perform(get("/api/v1/category/1"))
                .andExpect(status().isNotFound());
    }


}