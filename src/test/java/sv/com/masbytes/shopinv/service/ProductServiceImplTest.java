package sv.com.masbytes.shopinv.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import sv.com.masbytes.shopinv.exception.DuplicateProductCodeException;
import sv.com.masbytes.shopinv.exception.ProductNotFoundException;
import sv.com.masbytes.shopinv.model.Product;
import sv.com.masbytes.shopinv.repository.ProductRepository;

public class ProductServiceImplTest {

	@Mock
	private ProductRepository productRepository;

	private ProductServiceImpl productService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		productService = new ProductServiceImpl(productRepository);
	}

	@Test
	public void testCreateProduct_ShouldThrowDuplicateProductCodeException_WhenProductCodeExists() {
		// Arrange: Crear un producto con un código de producto ya existente
		Product product = new Product();
		product.setProductCode("ABC123");

		// Simulamos que el repositorio ya tiene ese código
		when(productRepository.findByProductCode("ABC123")).thenReturn(java.util.Optional.of(product));

		// Act & Assert: Verificamos que se lanza la excepción
		assertThrows(DuplicateProductCodeException.class, () -> {
			productService.createProduct(product);
		});
	}

	@Test
	public void testCreateProduct_ShouldSaveProduct_WhenProductCodeIsUnique() {
		// Arrange: Crear un producto con un código único
		Product product = new Product();
		product.setProductCode("XYZ456");

		// Simulamos que el repositorio no tiene ese código
		when(productRepository.findByProductCode("XYZ456")).thenReturn(java.util.Optional.empty());
		when(productRepository.save(any(Product.class))).thenReturn(product);

		// Act: Crear el producto
		Product createdProduct = productService.createProduct(product);

		// Assert: Verificar que el producto fue guardado
		assertNotNull(createdProduct);
		assertEquals("XYZ456", createdProduct.getProductCode());
		verify(productRepository).save(product); // Verificamos que se llamó al save
	}

	@Test
	public void testGetProductByCode_ShouldReturnProduct_WhenProductExists() {
		// Arrange: Crear un producto con un código existente
		Product product = new Product();
		product.setProductCode("ABC123");

		// Simulamos que el repositorio encuentra ese producto
		when(productRepository.findByProductCode("ABC123")).thenReturn(Optional.of(product));

		// Act: Obtener el producto
		Optional<Product> result = productService.getProductByCode("ABC123");

		// Assert: Verificar que el resultado es el producto esperado
		assertTrue(result.isPresent());
		assertEquals("ABC123", result.get().getProductCode());
	}

	@Test
	public void testGetProductByCode_ShouldReturnEmpty_WhenProductDoesNotExist() {
		// Arrange: Simulamos que el repositorio no encuentra el producto
		when(productRepository.findByProductCode("XYZ456")).thenReturn(Optional.empty());

		// Act: Intentar obtener el producto
		Optional<Product> result = productService.getProductByCode("XYZ456");

		// Assert: Verificar que el resultado es Optional.empty()
		assertFalse(result.isPresent());
	}

	@Test
	public void testGetProductsByCategory_ShouldReturnProducts_WhenCategoryExists() {
		// Arrange: Crear productos con la misma categoría
		Product product1 = new Product();
		product1.setCategory("Electronics");

		Product product2 = new Product();
		product2.setCategory("Electronics");

		// Simulamos que el repositorio devuelve los productos de la categoría
		// "Electronics"
		when(productRepository.findByCategory("Electronics")).thenReturn(List.of(product1, product2));

		// Act: Obtener los productos de la categoría "Electronics"
		List<Product> result = productService.getProductsByCategory("Electronics");

		// Assert: Verificar que la lista contiene los productos correctos
		assertEquals(2, result.size());
		assertEquals("Electronics", result.get(0).getCategory());
		assertEquals("Electronics", result.get(1).getCategory());
	}

	@Test
	public void testGetProductsByCategory_ShouldReturnEmptyList_WhenNoProductsInCategory() {
		// Arrange: Simulamos que no hay productos para la categoría "Toys"
		when(productRepository.findByCategory("Toys")).thenReturn(List.of());

		// Act: Obtener los productos de la categoría "Toys"
		List<Product> result = productService.getProductsByCategory("Toys");

		// Assert: Verificar que la lista está vacía
		assertTrue(result.isEmpty());
	}

	@Test
	public void testUpdateProduct_ShouldReturnUpdatedProduct_WhenProductExists() {
		// Arrange: Crear un producto de prueba para actualización
		String productCode = "ABC123";
		Product existingProduct = new Product();
		existingProduct.setProductCode(productCode);
		existingProduct.setName("Old Product");
		existingProduct.setPrice(100.0);
		existingProduct.setStock(10);
		existingProduct.setCategory("Electronics");

		Product updatedProduct = new Product();
		updatedProduct.setName("Updated Product");
		updatedProduct.setPrice(120.0);
		updatedProduct.setStock(15);
		updatedProduct.setCategory("Electronics");

		// Simulamos que el producto existe
		when(productRepository.findByProductCode(productCode)).thenReturn(Optional.of(existingProduct));
		when(productRepository.save(existingProduct)).thenReturn(existingProduct);

		// Act: Llamar al método update
		Product result = productService.updateProduct(productCode, updatedProduct);

		// Assert: Verificar que los datos fueron actualizados
		assertEquals("Updated Product", result.getName());
		assertEquals(120.0, result.getPrice());
		assertEquals(15, result.getStock());
		assertEquals("Electronics", result.getCategory());
	}

	@Test
	public void testUpdateProduct_ShouldThrowProductNotFoundException_WhenProductDoesNotExist() {
		// Arrange: Simulamos que el producto no existe
		String productCode = "XYZ999";
		Product updatedProduct = new Product();
		updatedProduct.setName("Updated Product");
		updatedProduct.setPrice(120.0);
		updatedProduct.setStock(15);
		updatedProduct.setCategory("Electronics");

		when(productRepository.findByProductCode(productCode)).thenReturn(Optional.empty());

		// Act & Assert: Verificar que se lanza la excepción ProductNotFoundException
		assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(productCode, updatedProduct));
	}

	@Test
	public void testDeleteProductByCode_ShouldDeleteProduct_WhenProductExists() {
		// Arrange: Crear un producto de prueba para eliminar
		String productCode = "ABC123";
		Product existingProduct = new Product();
		existingProduct.setProductCode(productCode);
		existingProduct.setName("Product to Delete");
		existingProduct.setPrice(100.0);
		existingProduct.setStock(10);
		existingProduct.setCategory("Electronics");

		// Simulamos que el producto existe
		when(productRepository.findByProductCode(productCode)).thenReturn(Optional.of(existingProduct));

		// Act: Llamar al método delete
		productService.deleteProductByCode(productCode);

		// Assert: Verificar que se haya llamado a delete
		verify(productRepository, times(1)).delete(existingProduct);
	}

	@Test
	public void testDeleteProductByCode_ShouldThrowProductNotFoundException_WhenProductDoesNotExist() {
		// Arrange: Simulamos que el producto no existe
		String productCode = "XYZ999";

		when(productRepository.findByProductCode(productCode)).thenReturn(Optional.empty());

		// Act & Assert: Verificar que se lanza la excepción ProductNotFoundException
		assertThrows(ProductNotFoundException.class, () -> productService.deleteProductByCode(productCode));
	}

}