package sv.com.masbytes.shopinv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import sv.com.masbytes.shopinv.service.ProductService;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public String getAllProducts(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "products";		
	}

}