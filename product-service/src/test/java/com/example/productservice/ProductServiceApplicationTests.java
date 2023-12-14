package com.example.productservice;

import com.example.productservice.dto.ProductRequest;
import com.example.productservice.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
        dynamicPropertyRegistry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
    }
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductService productService;

    static {
        mongoDBContainer.start();
    }
    //整合测试
    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequest productRequest=getProductRequest();
        String s = objectMapper.writeValueAsString(productRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product").contentType(MediaType.APPLICATION_JSON)
                .content(s)).andExpect(status().isCreated());
        Assertions.assertTrue(productService.getAllProducts().size()==1);
    }

    private ProductRequest getProductRequest() {
        return ProductRequest.builder().name("iphone 13").description("iphone   13").price(BigDecimal.valueOf(1200)).build();

    }

}
