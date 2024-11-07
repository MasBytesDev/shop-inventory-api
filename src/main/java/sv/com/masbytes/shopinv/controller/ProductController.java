package sv.com.masbytes.shopinv.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import sv.com.masbytes.shopinv.model.Product;
import sv.com.masbytes.shopinv.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	// Obtener todos los productos
	@GetMapping
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	// Obtener producto por productCode
	@GetMapping("/{productCode}")
	public ResponseEntity<Product> getProductByCode(@PathVariable String productCode) {
		return productService.getProductByCode(productCode).map(product -> new ResponseEntity<>(product, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	// Crear un nuevo producto
	@PostMapping
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		// Intentamos crear el producto
		Product createdProduct = productService.createProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
	}

	// Actualizar un producto
	@PutMapping("/{productCode}")
	public ResponseEntity<Product> updateProduct(@PathVariable String productCode,
			@RequestBody Product updatedProduct) {
		Product updated = productService.updateProduct(productCode, updatedProduct);
		return ResponseEntity.ok(updated);
	}

	// Eliminar un producto
	@DeleteMapping("/{productCode}")
	public ResponseEntity<Void> deleteProduct(@PathVariable String productCode) {
		productService.deleteProductByCode(productCode);
		return ResponseEntity.noContent().build();
	}

}