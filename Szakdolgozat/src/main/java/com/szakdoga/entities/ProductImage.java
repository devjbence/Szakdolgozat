package com.szakdoga.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="product_image")
public class ProductImage extends EntityBase{
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@JsonIgnore
	@Lob
	@Column(name = "product_image")
	private byte[] productImage;
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public byte[] getProductImage() {
		return productImage;
	}

	public void setProductImage(byte[] productImage) {
		this.productImage = productImage;
	}
	
	
}
