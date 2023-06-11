package ru.alsu.coffeehouse.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import ru.alsu.coffeehouse.domain.model.Product;
import ru.alsu.coffeehouse.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository);
    }

    @Test
    void testGetAllProducts() {
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1);
        product1.setName("Product1");
        Product product2 = new Product();
        product2.setId(2);
        product2.setName("Product2");
        productList.add(product1);
        productList.add(product2);
        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.getAllProducts();

        assertEquals(productList.size(), result.size());
        assertEquals(product1.getName(), result.get(0).getName());
        assertEquals(product2.getName(), result.get(1).getName());
    }

    @Test
    void testFindById() {
        Product product = new Product();
        product.setId(1);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.findById(1);

        assertEquals(product.getId(), result.get().getId());
    }

    @Test
    void testGetById() {
        Product product = new Product();
        product.setId(1);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Product result = productService.getById(1);

        assertEquals(product.getId(), result.getId());
    }

    @Test
    void testGetAll() {
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1);
        product1.setName("Product1");
        Product product2 = new Product();
        product2.setId(2);
        product2.setName("Product2");
        productList.add(product1);
        productList.add(product2);
        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.getAll();

        assertEquals(productList.size(), result.size());
        assertEquals(product1.getName(), result.get(0).getName());
        assertEquals(product2.getName(), result.get(1).getName());
    }

    @Test
    void testDeleteById() {
        productService.deleteById(1);

        verify(productRepository, times(1)).deleteById(1);
    }

    @Test
    void testSave() {
        Product product = new Product();
        productService.save(product);

        verify(productRepository, times(1)).save(product);
    }
}
