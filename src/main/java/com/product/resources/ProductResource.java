package com.product.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.product.dto.ProductDTO;
import com.product.services.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/products")
@Api(value = "API REST Catalog of Products")
public class ProductResource {

	@Autowired
	private ProductService productService;

	@ApiOperation(value = "Return all Products with parameters")
	@GetMapping(value = "/search")
	public ResponseEntity<List<ProductDTO>> findAll(@RequestParam(value = "q", required = false) String q,
			@RequestParam(value = "min_price", required = false) Double min_price,
			@RequestParam(value = "max_price", required = false) Double max_price) {

		List<ProductDTO> listProductDTO = productService.findByParameters(q, min_price, max_price);
		return ResponseEntity.ok(listProductDTO);
	}

	@ApiOperation(value = "Return all Products")
	@GetMapping
	public ResponseEntity<List<ProductDTO>> findAll() {

		List<ProductDTO> listProductDTO = productService.findAll();
		return ResponseEntity.ok(listProductDTO);
	}

	@ApiOperation(value = "Return Product with id")
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {

		ProductDTO productDTO = productService.findById(id);
		return ResponseEntity.ok().body(productDTO);
	}

	@ApiOperation(value = "Insert Product")
	@PostMapping
	public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO pNewProductDTO) {

		pNewProductDTO = productService.insert(pNewProductDTO);

		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(pNewProductDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).body(pNewProductDTO);

	}

	@ApiOperation(value = "Update Product")
	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO pProductDTO) {

		pProductDTO = productService.update(id, pProductDTO);
		return ResponseEntity.ok().body(pProductDTO);
	}

	@ApiOperation(value = "Delete Product")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {

		productService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
