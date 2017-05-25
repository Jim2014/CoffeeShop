package edu.mum.coffee.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mum.coffee.domain.Product;
import edu.mum.coffee.service.ProductService;

@RestController
@RequestMapping("/api/product/")
public class ProductAPI {

	@Autowired
	private ProductService prodService;

	@GetMapping("/list")
	List<Product> list() {
		return prodService.getAllProduct();
	}
	
	@PostMapping("/add")
	Product add(@RequestBody Product product){
		return prodService.save(product);
	}
	
	@PutMapping("/update")
	Product update(@RequestBody Product product){
		return prodService.save(product);
	}
	
	@DeleteMapping("/delete/{id}")
	void delete(@PathVariable int id){
		prodService.delete(id);
	}
	
}
