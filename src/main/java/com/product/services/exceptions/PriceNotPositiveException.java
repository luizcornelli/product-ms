package com.product.services.exceptions;

public class PriceNotPositiveException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PriceNotPositiveException(String msg) {
		super(msg);
	}

}
