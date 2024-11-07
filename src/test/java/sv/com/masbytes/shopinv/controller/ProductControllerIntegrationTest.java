package sv.com.masbytes.shopinv.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import sv.com.masbytes.shopinv.model.Product;
import sv.com.masbytes.shopinv.repository.ProductRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private TestRestTemplate restTemplate;

	private Product product;
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp() {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		// Limpiar la base de datos antes de cada prueba
		productRepository.deleteAll();

		// Crear un producto de prueba
		product = new Product("P002", "Product 2", "Description 2", 30.0, 7, "Category 2", LocalDate.now());
		productRepository.save(product); // Guardamos el producto en la base de datos
	}

	@Test
	void shouldReturnBadRequestWhenDuplicateProductCode() {
		// Intentamos crear un producto con el mismo productCode
		ResponseEntity<String> response = restTemplate.postForEntity("/products", product, String.class);

		// Verificamos que el código de estado sea 400 Bad Request
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		// Verificamos que el mensaje de error contenga el texto esperado
		assertTrue(response.getBody().contains("Product with productCode P002 already exists."));
	}

	@Test
	public void testGetAllProducts() throws Exception {
		// Verificar que el producto esté presente en la base de datos
		mockMvc.perform(get("/products")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].productCode").value(product.getProductCode()))
				.andExpect(jsonPath("$[0].name").value(product.getName()));
	}

	@Test
	public void testCreateProduct() throws Exception {
		Product newProduct = new Product("P003", "Product 3", "Description 3", 200.0, 20, "Category 3",
				LocalDate.now());

		mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newProduct))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.productCode").value(newProduct.getProductCode()));
	}

	@Test
	public void testUpdateProduct() throws Exception {
		// Crear un producto de prueba y guardarlo en la base de datos
		Product productToUpdate = new Product("P001", "Product 1", "Description 1", 100.0, 10, "Category 1",
				LocalDate.now());
		productRepository.save(productToUpdate);

		// Producto actualizado con nuevos valores
		Product updatedProduct = new Product("P001", "Updated Product", "Updated Description", 150.0, 15,
				"Updated Category", LocalDate.now());

		// Realizar la solicitud PUT para actualizar el producto
		mockMvc.perform(put("/products/P001").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatedProduct))).andExpect(status().isOk())
				.andExpect(jsonPath("$.productCode").value(updatedProduct.getProductCode()))
				.andExpect(jsonPath("$.name").value(updatedProduct.getName()))
				.andExpect(jsonPath("$.description").value(updatedProduct.getDescription()))
				.andExpect(jsonPath("$.price").value(updatedProduct.getPrice()))
				.andExpect(jsonPath("$.stock").value(updatedProduct.getStock()))
				.andExpect(jsonPath("$.category").value(updatedProduct.getCategory()));

		// Verificar que el producto en la base de datos se haya actualizado
		Product dbProduct = productRepository.findByProductCode("P001").orElse(null);
		assertNotNull(dbProduct);
		assertEquals(updatedProduct.getName(), dbProduct.getName());
		assertEquals(updatedProduct.getDescription(), dbProduct.getDescription());
		assertEquals(updatedProduct.getPrice(), dbProduct.getPrice());
		assertEquals(updatedProduct.getStock(), dbProduct.getStock());
		assertEquals(updatedProduct.getCategory(), dbProduct.getCategory());
	}

	@Test
	public void testDeleteProduct() throws Exception {
		// Crear un producto de prueba y guardarlo en la base de datos
		Product productToDelete = new Product("P001", "Product 1", "Description 1", 100.0, 10, "Category 1",
				LocalDate.now());
		productRepository.save(productToDelete);

		// Realizar la solicitud DELETE para eliminar el producto
		mockMvc.perform(delete("/products/P001")).andExpect(status().isNoContent());

		// Verificar que el producto ha sido eliminado de la base de datos
		Optional<Product> deletedProduct = productRepository.findByProductCode("P001");
		assertTrue(deletedProduct.isEmpty());
	}
}