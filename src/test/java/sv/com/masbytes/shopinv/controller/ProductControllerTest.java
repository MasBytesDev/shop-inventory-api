package sv.com.masbytes.shopinv.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import sv.com.masbytes.shopinv.model.Product;
import sv.com.masbytes.shopinv.service.ProductService;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService productService;

	private ObjectMapper objectMapper = new ObjectMapper();

	private Product product;

	@BeforeEach
	public void setUp() {
		objectMapper.registerModule(new JavaTimeModule());
		product = new Product("P001", "Product 1", "Description 1", 100.0, 10, "Category 1", LocalDate.now());
	}

	@Test
	public void testGetAllProducts() throws Exception {
		when(productService.getAllProducts()).thenReturn(Arrays.asList(product));
		mockMvc.perform(get("/products")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].productCode").value(product.getProductCode()));
	}

	@Test
	public void testGetProductByCode() throws Exception {
		when(productService.getProductByCode("P001")).thenReturn(Optional.of(product));

		mockMvc.perform(get("/products/P001")).andExpect(status().isOk())
				.andExpect(jsonPath("$.productCode").value(product.getProductCode()));
	}

	@Test
	public void testCreateProduct() throws Exception {
		when(productService.createProduct(product)).thenReturn(product);

		mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(product))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.productCode").value(product.getProductCode()));
	}

	@Test
	public void testUpdateProduct() throws Exception {
		when(productService.updateProduct("P001", product)).thenReturn(product);

		mockMvc.perform(put("/products/P001").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(product))).andExpect(status().isOk())
				.andExpect(jsonPath("$.productCode").value(product.getProductCode()));
	}

	@Test
	public void testDeleteProduct() throws Exception {
		mockMvc.perform(delete("/products/P001")).andExpect(status().isNoContent());
	}

}