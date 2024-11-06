package sv.com.masbytes.shopinv.model;

import java.time.LocalDate;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "products")
public class Product {
	
	@Id
	private ObjectId id;
	private String productCode;
	private String name;
	private String description;
	private double price;
	private int stock;
	private String category;
	private LocalDate createdDate;	

}