package sv.com.masbytes.shopinv.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import sv.com.masbytes.shopinv.model.Product;

public interface ProductRepository extends MongoRepository<Product, ObjectId> {
	
	Optional<Product> findByProductCode(String productCode);
	
	List<Product> findByCategory(String category);
	
	void deleteByProductCode(String productCode);
	
	List<Product> findAll();

}