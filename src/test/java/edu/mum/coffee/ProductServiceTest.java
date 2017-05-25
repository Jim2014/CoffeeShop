package edu.mum.coffee;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.mum.coffee.domain.Product;
import edu.mum.coffee.domain.ProductType;
import edu.mum.coffee.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

	@Autowired
	private ProductService productService;
	private Product[] testProduct;
	final static int TEST_NumberOfProducts = 10;

	@Before
	public void setUp() throws Exception {
		// Remove previous objects
		deleteTestObjects();

		// Creating objects to use during the test
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

		// Store test objects in database -- Skipping 0 for Create Test
		for (int i = 1; i < TEST_NumberOfProducts; i++) {
			testProduct[i] = productService.save(testProduct[i]);
		}
	}

	@After
	public void tearDown() throws Exception {
		deleteTestObjects();
	}

	@Test
	public void testSave() {
		Product stored = productService.save(testProduct[0]);
		Product saved = productService.getProduct(stored.getId());
		if (!compareProduct(testProduct[0], saved)) {
			fail("Not storing or retrieving Person");
		}
	}

	@Test
	public void testGetProduct() {
		Product saved = productService.getProduct((testProduct[1].getId()));
		if (!compareProduct(testProduct[1], saved)) {
			fail("Not storing or retrieving Product");
		}
	}

	@Test
	public void testGetAllProduct() {
		List<Product> saved = productService.getAllProduct();
		if (saved.size() < TEST_NumberOfProducts - 1) {
			fail("Not storing or retrieving Products");
		}
	}

	@Test
	public void testFindByTextSearch() {
		List<Product> saved = productService.findByTextSearch(testProduct[2].getDescription());
		if (!compareProduct(saved, testProduct[2])) {
			fail("Search couldn't retrieve Product");
		}
	}

	@Test
	public void testFindByPrice() {
		double price = testProduct[3].getPrice();
		List<Product> saved = productService.findByPrice(price, price);
		if (!compareProduct(saved, testProduct[3])) {
			fail("Search couldn't retrieve Product");
		}
	}

	@Test
	public void testFindByProductType() {
		for (int i = 4; i < 7; i++) {
			List<Product> saved = productService.findByProductType(testProduct[i].getProductType());
			if (!compareProduct(saved, testProduct[i])) {
				fail("Search couldn't retrieve Product by Type" + testProduct[i].getProductType());
			}
		}
	}

	private void deleteTestObjects() {
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
	}

	private boolean compareProduct(Product a, Product b) {
		if ((a == null) || (b == null)) {
			return false;
		}
		if (!a.getDescription().equals(b.getDescription())) {
			return false;
		}
		if (a.getPrice() != b.getPrice()) {
			return false;
		}
		if (!a.getProductName().equals(b.getProductName())) {
			return false;
		}
		if (!a.getProductType().equals(b.getProductType())) {
			return false;
		}
		return true;
	}

	private boolean compareProduct(List<Product> products, Product compare) {
		boolean found = false;
		for (Product product : products) {
			if (compareProduct(product, compare)) {
				found = true;
				break;
			}
		}
		return found;
	}

}
