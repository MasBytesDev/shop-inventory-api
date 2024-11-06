package sv.com.masbytes.shopinv.service;

import java.util.List;
import java.util.Optional;

import sv.com.masbytes.shopinv.model.Product;

public interface ProductService {

	Product createProduct(Product product);

	Optional<Product> getProductByCode(String productCode);

	List<Product> getProductsByCategory(String category);

	Product updateProduct(String productCode, Product product);

	void deleteProductByCode(String productCode);

	// Método para obtener todos los productos
	public List<Product> getAllProducts(); // Llama al repositorio
}
