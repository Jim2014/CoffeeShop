package edu.mum.coffee.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mum.coffee.domain.Order;
import edu.mum.coffee.service.OrderService;

@RestController
@RequestMapping("/api/order/")
public class OrderAPI {

	@Autowired
	private OrderService orderService;

	@GetMapping("/list")
	List<Order> list() {
		return orderService.findAll();
	}
	
	@PostMapping("/add")
	Order add(@RequestBody Order order){
		return orderService.save(order);
	}
	

	

	
}
