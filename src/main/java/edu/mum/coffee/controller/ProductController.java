package edu.mum.coffee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.mum.coffee.domain.Product;
import edu.mum.coffee.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@GetMapping("/list")
	public String list(Model model) {
		List<Product> products =productService.getAllProduct();
		model.addAttribute("products", products);
		return "productList";
	}
	
	@PostMapping("/add")
	public String add(Product product) {
		productService.save(product);
		return "redirect:/product/list";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		productService.delete(id);;
		return "redirect:/product/list";
	}

	
}
