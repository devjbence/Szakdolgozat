package com.szakdoga.services.interfaces;

import java.util.List;

import com.szakdoga.entities.Attribute;
import com.szakdoga.entities.AttributeCore;
import com.szakdoga.entities.Category;
import com.szakdoga.entities.Product;
import com.szakdoga.entities.Role;
import com.szakdoga.entities.User;

public interface ITestDataCreator {
	List<Role> createRoles();
	List<User> createUsers();
	List<Attribute> createAttributes();
	List<AttributeCore> createAttributeCores();
	List<Product> createProducts();
	List<Category> createCategories();
}
