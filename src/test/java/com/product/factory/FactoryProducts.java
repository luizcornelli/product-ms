package com.product.factory;

import com.product.dto.ProductDTO;
import com.product.models.Product;

public class FactoryProducts {

	public static Product createProduct() {
		
		Product product = new Product(1L, "Livros", "Livros de Ficção", 25.0);
		return product;
	}

	public static ProductDTO createProductDTO() {

		Product product = createProduct();
		return new ProductDTO(product);
	}

}
