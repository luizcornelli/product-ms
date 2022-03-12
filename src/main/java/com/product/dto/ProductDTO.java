package com.product.dto;

import java.io.Serializable;

import com.product.models.Product;

public class ProductDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String description;
	private Double price;

	public ProductDTO() {
	}

	public ProductDTO(Long id, String name, String description, Double price) {
		this.id = String.valueOf(id);
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public ProductDTO(Product product) {
		this.id = String.valueOf(product.getId());
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
