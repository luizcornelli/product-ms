package com.product.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.product.dto.ProductDTO;
import com.product.models.Product;
import com.product.repositories.ProductCustomRepository;
import com.product.repositories.ProductRepository;
import com.product.services.exceptions.InvalidFieldException;
import com.product.services.exceptions.PriceNotPositiveException;
import com.product.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductCustomRepository productCustomRepository;

	@Transactional(readOnly = true)
	public ProductDTO findById(Long pId) {

		Optional<Product> optional = productRepository.findById(pId);
		Product product = optional.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));

		return new ProductDTO(product);
	}

	@Transactional
	public ProductDTO insert(ProductDTO pProductDTO) {

		verifyFieldsIsValid(pProductDTO);

		verifyPricePositive(pProductDTO);

		Product product = new Product();

		product.setDescription(pProductDTO.getDescription());
		product.setName(pProductDTO.getName());
		product.setPrice(pProductDTO.getPrice());

		product = productRepository.save(product);

		return new ProductDTO(product);
	}

	@Transactional
	public ProductDTO update(Long pId, ProductDTO pProductDTO) {

		verifyFieldsIsValid(pProductDTO);

		verifyPricePositive(pProductDTO);

		try {

			Product product = productRepository.getById(pId);

			product.setDescription(pProductDTO.getDescription());
			product.setName(pProductDTO.getName());
			product.setPrice(pProductDTO.getPrice());

			product = productRepository.save(product);

			return new ProductDTO(product);

		} catch (EntityNotFoundException e) {

			throw new ResourceNotFoundException("Id not found " + pId);
		}

	}

	@Transactional(readOnly = true)
	public List<ProductDTO> findAll() {

		List<Product> products = productRepository.findAll();

		return products.stream().map(obj -> new ProductDTO(obj)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<ProductDTO> findByParameters(String q, Double min_price, Double max_price) {

		List<Product> products = productCustomRepository.findAll(q, min_price, max_price);

		return products.stream().map(obj -> new ProductDTO(obj)).collect(Collectors.toList());
	}

	public void delete(Long id) {

		findById(id);

		productRepository.deleteById(id);
	}

	private void verifyPricePositive(ProductDTO pProductDTO) {

		if (pProductDTO.getPrice() != null && pProductDTO.getPrice() <= 0) {

			throw new PriceNotPositiveException(
					"The price of product is not positive. - Price: " + pProductDTO.getPrice());
		}

	}

	private void verifyFieldsIsValid(ProductDTO pProductDTO) {

		boolean isNameValid = pProductDTO.getName() == null || pProductDTO.getName().isEmpty();
		boolean isDescriptionValid = pProductDTO.getDescription() == null || pProductDTO.getDescription().isEmpty();
		boolean isPriceValid = pProductDTO.getPrice() == null;

		if (isNameValid || isDescriptionValid || isPriceValid) {

			throw new InvalidFieldException("Invalid required fields.");
		}

	}

}
