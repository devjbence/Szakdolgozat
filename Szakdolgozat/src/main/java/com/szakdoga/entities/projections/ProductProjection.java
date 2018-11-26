package com.szakdoga.entities.projections;


import org.springframework.data.rest.core.config.Projection;
import com.szakdoga.entities.Product;

@Projection(name="product", types= {Product.class})
public interface ProductProjection {
	Integer getId();
	String getName();
	String getDescription();
}
