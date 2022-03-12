package com.product.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.product.models.Product;

@Repository
public class ProductCustomRepository {

	@Autowired
	private EntityManager entityManager;

	public List<Product> findAll(String q, Double min_price, Double max_price) {

		String query = "SELECT p FROM Product p ";
		String conditional = "WHERE ";

		// Structuring where clause
		if (q != null && !q.isEmpty()) {

			query += conditional + "(LOWER(p.name) LIKE :q OR LOWER(p.description) LIKE :q)";
			conditional = " AND ";
		}
		if (min_price != null && min_price > 0) {

			query += conditional + "p.price >= :min_price";
			conditional = " AND ";
		}
		if (max_price != null && max_price > 0) {

			query += conditional + "p.price <= :max_price";
			conditional = " AND ";
		}

		TypedQuery<Product> entityManagerQuery = entityManager.createQuery(query, Product.class);

		// Replacing parameters
		if (q != null && !q.isEmpty()) {

			entityManagerQuery.setParameter("q", "%" + q.toLowerCase() + "%");
		}
		if (min_price != null && min_price > 0) {

			entityManagerQuery.setParameter("min_price", min_price);
		}
		if (max_price != null && max_price > 0) {

			entityManagerQuery.setParameter("max_price", max_price);
		}

		return entityManagerQuery.getResultList();
	}

}
