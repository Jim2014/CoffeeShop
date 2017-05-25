package edu.mum.coffee;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.mum.coffee.domain.Address;
import edu.mum.coffee.domain.Order;
import edu.mum.coffee.domain.Orderline;
import edu.mum.coffee.domain.Person;
import edu.mum.coffee.domain.Product;
import edu.mum.coffee.domain.ProductType;
import edu.mum.coffee.service.OrderService;
import edu.mum.coffee.service.PersonService;
import edu.mum.coffee.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

	@Autowired
	private OrderService orderService;
	@Autowired
	private PersonService personService;
	@Autowired
	private ProductService productService;

	private Order[] testOrder;
	private Product[] testProduct;
	private Person[] testPerson;

	final static int TEST_NumberOfOrders = 5;
	final static int TEST_NumberOfOrderLines = 7;
	final static int TEST_NumberOfProducts = 20;
	final static int TEST_NumberOfPersons = 3;

	@Before
	public void setUp() throws Exception {
		// Remove previous objects
		deleteTestObjects();

		// Related objects
		createPersons();
		createProducts();

		// Creating objects to use during the test
		testOrder = new Order[TEST_NumberOfOrders];

		int countPerson = 0;
		int countProduct = 0;

		for (int i = 0; i < TEST_NumberOfOrders; i++) {
			Order order = new Order();
			order.setOrderDate(new Date());
			order.setPerson(testPerson[countPerson++ % TEST_NumberOfPersons]);

			for (int l = 0; l < TEST_NumberOfOrderLines; l++) {
				Orderline line = new Orderline();
				line.setProduct(testProduct[countProduct++ % TEST_NumberOfProducts]);
				line.setQuantity(countProduct);
				order.addOrderLine(line);
			}
			testOrder[i] = order;
		}

		// Store test objects in database -- Skip 0 for Create Test
		for (int i = 1; i < TEST_NumberOfOrders; i++) {
			testOrder[i] = orderService.save(testOrder[i]);
		}
	}

	@After
	public void tearDown() throws Exception {
		deleteTestObjects();
	}

	@Test
	public void testSave() {
		Order stored = orderService.save(testOrder[0]);
		Order saved = orderService.findById(stored.getId());
		if (!compareOrder(testOrder[0], saved)) {
			fail("Not storing or retrieving Person");
		}
	}

	@Test
	public void testFindById() {
		Order saved = orderService.findById(testOrder[1].getId());
		if (!compareOrder(testOrder[1], saved)) {
			fail("Not storing or retrieving Person");
		}
	}

	@Test
	public void testFindByProduct() {
		List<Order> saved = orderService.findByProduct(testProduct[1]);
		boolean found = false;

		for (Order order : saved) {
			found = true;
			boolean inList = false;
			for (Orderline line : order.getOrderLines()) {
				if (line.getProduct().getId() == testProduct[1].getId()) {
					inList = true;
					break;
				}
				if (!inList) {
					fail("Not retrieving Orders by product");
				}
			}
		}
		if (!found) {
			fail("Not retrieving Orders by product");
		}

	}

	@Test
	public void testFindByPerson() {
		List<Order> saved = orderService.findByPerson(testPerson[1]);
		boolean found = false;

		for (Order order : saved) {
			found = true;
			if (order.getPerson().getId() != testPerson[1].getId()) {
				found = false;
				break;
			}
		}
		if (!found) {
			fail("Not storing or retrieving Orders");
		}
	}

	@Test
	public void testFindByDate() {
		List<Order> saved = orderService.findByDate(testOrder[1].getOrderDate(), testOrder[1].getOrderDate());
		boolean found = false;

		for (Order order : saved) {
			if (order.getId() != testOrder[1].getId()) {
				found = true;
				break;
			}
		}
		if (!found) {
			fail("Not retrieving Orders by Date");
		}
	}

	@Test
	public void testFindAll() {
		List<Order> saved = orderService.findAll();
		if (saved.size() < TEST_NumberOfOrders - 1) {
			fail("Not storing or retrieving Orders");
		}
	}

	private void createPersons() {
		// Creating objects to use during the test
		testPerson = new Person[TEST_NumberOfPersons];

		for (int i = 0; i < TEST_NumberOfPersons; i++) {
			Person person = new Person();
			person.setEmail(i + "test@email.com");
			person.setEnable((i % 2 == 0));
			person.setFirstName("firstName Test" + i);
			person.setLastName("lastName Test" + i);
			person.setPhone("111888777" + i);
			person.setAddress(new Address());
			person.getAddress().setZipcode("5527" + i);
			person.getAddress().setCity("Fairfield" + i);
			person.getAddress().setState("IA" + i);
			person.getAddress().setCountry("USA" + i);
			testPerson[i] = person;
		}

		// Store test objects in database
		for (int i = 0; i < TEST_NumberOfPersons; i++) {
			testPerson[i] = personService.savePerson(testPerson[i]);
		}
	}

	private void createProducts() {
		testProduct = new Product[TEST_NumberOfProducts];

		for (int i = 0; i < TEST_NumberOfProducts; i++) {
			Product product = new Product();
			product.setProductName("TEST-productName" + i);
			product.setDescription("TEST-Item Description" + i);
			product.setPrice(i + 100.0);
			ProductType productType = null;
			switch (i % 3) {
			case 0:
				productType = ProductType.BREAKFAST;
				break;
			case 1:
				productType = ProductType.LUNCH;
				break;
			default:
				productType = ProductType.DINNER;
			}
			product.setProductType(productType);
			testProduct[i] = product;
		}
		// Store test objects in database
		for (int i = 0; i < TEST_NumberOfProducts; i++) {
			testProduct[i] = productService.save(testProduct[i]);
		}
	}

	private void deleteTestObjects() {
		for (int i = 0; i < TEST_NumberOfOrders; i++) {
			try {
				Order order = orderService.findById(testOrder[i].getId());
				orderService.delete(order);
			} catch (Exception e) {
				// Do not log exceptions
			}
		}
		for (int i = 0; i < TEST_NumberOfProducts; i++) {
			try {
				List<Product> products = productService.findByTextSearch(testProduct[i].getProductName());
				for (Product product : products) {
					productService.delete(product);
				}
			} catch (Exception e) {
				// Do not log exceptions
			}
		}
		for (int i = 0; i < TEST_NumberOfPersons; i++) {
			try {
				Person person = personService.findById(testPerson[i].getId());
				personService.removePerson(person);
			} catch (Exception e) {
				// Do not log exceptions
			}
		}
	}

	private boolean compareOrder(Order a, Order b) {
		if ((a == null) || (b == null)) {
			return false;
		}
		if (a.getPerson().getId() != b.getPerson().getId()) {
			return false;
		}
		if (a.getTotalAmount() != b.getTotalAmount()) {
			return false;
		}
		return true;
	}
}
