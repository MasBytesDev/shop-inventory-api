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

import sv.com.masbytes.shopinv.exception.ProductNotFoundException;
import sv.com.masbytes.shopinv.model.Product;
import sv.com.masbytes.shopinv.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	@GetMapping("/{productCode}")
	public ResponseEntity<Product> getProductByCode(@PathVariable String productCode) {
		return productService.getProductByCode(productCode).map(product -> new ResponseEntity<>(product, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		Product createdProduct = productService.createProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
	}

	@PutMapping("/{productCode}")
	public ResponseEntity<Product> updateProduct(@PathVariable String productCode,
			@RequestBody Product updatedProduct) {
		try {
			// Llamamos al servicio para actualizar el producto
			Product updated = productService.updateProduct(productCode, updatedProduct);

			// Si todo sale bien, respondemos con el producto actualizado
			return ResponseEntity.ok(updated);
		} catch (ProductNotFoundException ex) {
			// Si el producto no se encuentra, respondemos con un error 404
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@DeleteMapping("/{productCode}")
	public ResponseEntity<Void> deleteProduct(@PathVariable String productCode) {
		try {
			productService.deleteProductByCode(productCode);
			return ResponseEntity.noContent().build();
		} catch (ProductNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}