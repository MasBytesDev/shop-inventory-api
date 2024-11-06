package sv.com.masbytes.shopinv.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import sv.com.masbytes.shopinv.exception.DuplicateProductCodeException;
import sv.com.masbytes.shopinv.exception.ProductNotFoundException;
import sv.com.masbytes.shopinv.model.Product;
import sv.com.masbytes.shopinv.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	private ProductRepository productRepository;	
	
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        // Verificar si ya existe un producto con el mismo 'productCode'
        if (productRepository.findByProductCode(product.getProductCode()).isPresent()) {
            // Si ya existe, lanzar una excepción de duplicado
            throw new DuplicateProductCodeException(product.getProductCode());
        }
        
        // Si no existe, guardar el nuevo producto en la base de datos
        return productRepository.save(product);
    }

	@Override
	public Optional<Product> getProductByCode(String productCode) {
		// Llamamos al repositorio para buscar el producto por el 'productCode'
		return productRepository.findByProductCode(productCode);
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
	    // Llamamos al repositorio para obtener los productos por categoría
	    return productRepository.findByCategory(category);
	}

	@Override
	public Product updateProduct(String productCode, Product product) {
	    // Buscar el producto por el productCode
	    Product existingProduct = productRepository.findByProductCode(productCode)
				.orElseThrow(() -> new ProductNotFoundException("Product not found with productCode: " + productCode));

	    // Actualizar los atributos del producto
	    existingProduct.setName(product.getName());
	    existingProduct.setDescription(product.getDescription());
	    existingProduct.setPrice(product.getPrice());
	    existingProduct.setStock(product.getStock());
	    existingProduct.setCategory(product.getCategory());
	    existingProduct.setCreatedDate(product.getCreatedDate());

	    // Guardar el producto actualizado
	    return productRepository.save(existingProduct);
	}


	@Override
	public void deleteProductByCode(String productCode) {
	    // Buscar el producto por el productCode
	    Product existingProduct = productRepository.findByProductCode(productCode)
	            .orElseThrow(() -> new ProductNotFoundException("Product not found with productCode: " + productCode));
	    
	    // Eliminar el producto encontrado
	    productRepository.delete(existingProduct);
	}	

}