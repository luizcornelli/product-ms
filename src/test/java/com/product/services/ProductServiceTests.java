package com.product.services;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.product.dto.ProductDTO;
import com.product.factory.FactoryProducts;
import com.product.models.Product;
import com.product.repositories.ProductRepository;
import com.product.services.exceptions.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService productService;

	@Mock
	private ProductRepository productRepository;

	private long existingId;
	private long nonExistingId;
	private Product product;
	private ProductDTO productDTO;

	@BeforeEach
	void setUp() throws Exception {
		
		existingId = 1L;
		nonExistingId = 2L;
		product = FactoryProducts.createProduct();
		productDTO = FactoryProducts.createProductDTO();

		Mockito.when(productRepository.save(ArgumentMatchers.any())).thenReturn(product);

		Mockito.when(productRepository.getById(existingId)).thenReturn(product);
		Mockito.when(productRepository.getById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

		Mockito.when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
		Mockito.when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());
		Mockito.doThrow(ResourceNotFoundException.class).when(productRepository).findById(nonExistingId);

		Mockito.doNothing().when(productRepository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(nonExistingId);
	}

	@Test
	public void updateShouldThowsResourceNotFoundExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			productService.update(nonExistingId, productDTO);
		});

		Mockito.doThrow(ResourceNotFoundException.class).when(productRepository).getById(nonExistingId);
	}

	@Test
	public void updateShouldReturnProductDTOWhenIdExists() {

		ProductDTO result = productService.update(existingId, productDTO);

		Assertions.assertNotNull(result);

	}

	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() {

		ProductDTO result = productService.findById(existingId);

		Assertions.assertNotNull(result);
		Mockito.verify(productRepository).findById(existingId);

	}

	@Test
	public void findByIdShouldThowsResourceNotFoundExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			productService.findById(nonExistingId);
		});

		Mockito.doThrow(ResourceNotFoundException.class).when(productRepository).findById(nonExistingId);
	}

	@Test
	public void deleteShouldThowsResourceNotFoundExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			productService.delete(nonExistingId);
		});

		Mockito.doThrow(ResourceNotFoundException.class).when(productRepository).deleteById(nonExistingId);
	}

	@Test
	public void deleteShouldDoNothingWhenIdExists() {

		Assertions.assertDoesNotThrow(() -> {
			productService.delete(existingId);
		});

		Mockito.verify(productRepository, Mockito.times(1)).deleteById(existingId);
	}

}
