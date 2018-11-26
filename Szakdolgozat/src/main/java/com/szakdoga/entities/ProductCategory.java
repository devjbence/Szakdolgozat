package com.szakdoga.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="product_category")
public class ProductCategory extends EntityBase{

	@ManyToMany(mappedBy = "categories")
	Set<Buyer> buyers;
	
	@ManyToMany(mappedBy = "categories")
	Set<Seller> sellers;
	
	@ManyToMany(mappedBy = "categories")
	Set<Product> jobs;
	
	@Column(name="product_name")
	private String productName;
	
	@Column(name="parent_id")
	private Integer parentId;
	
	private String about;
	
	@Column(columnDefinition = "TINYINT(1)")
	private boolean active;

	public boolean isActive() {
		return active;
	}

	public Integer getResourceId()
	{
		return id;
	}

	public Set<Buyer> getBuyers() {
		return buyers;
	}

	public void setBuyers(Set<Buyer> buyers) {
		this.buyers = buyers;
	}

	public Set<Seller> getSellers() {
		return sellers;
	}

	public void setSellers(Set<Seller> sellers) {
		this.sellers = sellers;
	}

	public Set<Product> getJobs() {
		return jobs;
	}

	public void setJobs(Set<Product> jobs) {
		this.jobs = jobs;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
