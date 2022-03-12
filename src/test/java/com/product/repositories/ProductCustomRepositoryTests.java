package com.product.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.product.models.Product;

@DataJpaTest
public class ProductCustomRepositoryTests {

	@Autowired
	private ProductRepository productRepository;

	private long existingId;

	private long noExistingId;

	private long countTotalProducts;

	@BeforeEach
	void setUp() throws Exception {

		existingId = 1L;
		noExistingId = 1000L;
		countTotalProducts = 6L;
	}

	@Test
	public void deleteShoulDeleteObjectWhenIdExists() {

		productRepository.deleteById(existingId);

		Optional<Product> result = productRepository.findById(existingId);
		Assertions.assertFalse(result.isPresent());
	}

	@Test
	public void deleteShouldThrowEmptyResultDataAcessExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {

			productRepository.deleteById(noExistingId);
		});
	}

	@Test
	public void saveShouldPersistWhithAutoIncrementWhenIdISNull() {

		Product product = new Product(7L, "Tablet", "Tablet Positivo 1.5", 800.0);

		product = productRepository.save(product);
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1, product.getId());

	}

	@Test
	public void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {

		Optional<Product> result = productRepository.findById(existingId);

		Assertions.assertTrue(result.isPresent());

	}

	@Test
	public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExists() {

		Optional<Product> result = productRepository.findById(noExistingId);

		Assertions.assertTrue(result.isEmpty());

	}

}
