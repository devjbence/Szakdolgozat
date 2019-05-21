package com.szakdoga.services.implementations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;
import com.szakdoga.entities.Attribute;
import com.szakdoga.entities.AttributeCore;
import com.szakdoga.entities.Buyer;
import com.szakdoga.entities.Category;
import com.szakdoga.entities.Image;
import com.szakdoga.entities.Product;
import com.szakdoga.entities.Role;
import com.szakdoga.entities.Seller;
import com.szakdoga.entities.User;
import com.szakdoga.enums.AttributeType;
import com.szakdoga.enums.ProductType;
import com.szakdoga.repositories.AttributeCoreRepository;
import com.szakdoga.repositories.AttributeRepository;
import com.szakdoga.repositories.BuyerRepository;
import com.szakdoga.repositories.CategoryRepository;
import com.szakdoga.repositories.ImageRepository;
import com.szakdoga.repositories.ProductRepository;
import com.szakdoga.repositories.RoleRepository;
import com.szakdoga.repositories.SellerRepository;
import com.szakdoga.repositories.UserRepository;
import com.szakdoga.services.interfaces.ITestDataCreator;
import com.szakdoga.utils.Utils;

@Service
public class TestDataCreator implements ITestDataCreator {

	private Faker faker = new Faker();
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BuyerRepository buyerRepository;
	@Autowired
	private SellerRepository sellerRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private AttributeRepository attributeRepository;
	@Autowired
	private AttributeCoreRepository attributeCoreRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ImageRepository imageRepository;
	
	final static int NumberOfRoles=2;
	final static int NumberOfUsers = 10;
	final static int NumberOfProducts=80;	
	final static int NumberOfAttributes=40; //shoudl be divisible by 4
	final static int NumberOfCategories=5;
	
	@Override
	public List<Role> createRoles() {
		List<Role> roles = roleRepository.findAll();
		
		if(roles.size() == 0)
		{
			roles = addRoles();
		}
		
		return roles;
	}
	
	private List<Role> addRoles() {
		List<Role> roles = new ArrayList<Role>();
		
		Role user = new Role();
		Role admin = new Role();
		
		roles.add(user);
		roles.add(admin);
		
		user.setName("ROLE_USER");
		admin.setName("ROLE_ADMIN");
		
		roleRepository.save(user);
		roleRepository.save(admin);
		
		return roles;
	}

	@Override
	public List<User> createUsers() {
		
		List<User> users = userRepository.findAll();
		
		if(users.size() == 0)
		{
			users = addUsers();
		}
		
		return users;
	}

	private List<User> addUsers()
	{
		List<User> users = new ArrayList<User>();
		
		//depenendecies
		List<Role> roles = createRoles();
		System.out.println(roles);
		
		for(int i=0;i<NumberOfUsers;i++)
		{
			User user = new User();
			
			user.addRole(roles.get(1));
			user.setEmail(faker.internet().emailAddress());
			user.setActivated(i>NumberOfUsers/4);
			user.setUsername(String.format("user%d", i));
			user.setPassword(bCryptPasswordEncoder.encode("password"));
			
			userRepository.save(user);
			
			//profiles
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			
			Buyer buyer = new Buyer();
			Seller seller = new Seller();

			buyer.setAboutMe(faker.lorem().paragraph(20));
			buyer.setFirstName(firstName);
			buyer.setLastName(lastName);
			buyer.setProfileImage(null);
			buyer.setUser(user);

			seller.setAboutMe(faker.lorem().paragraph(20));
			seller.setFirstName(firstName);
			seller.setLastName(lastName);
			seller.setProfileImage(null);
			seller.setUser(user);
			
			sellerRepository.save(seller);
			buyerRepository.save(buyer);
			
			user.setSeller(seller);
			user.setBuyer(buyer);
			userRepository.save(user);
			
			users.add(user);
		}
		
		return users;
	}

	@Override
	public List<AttributeCore> createAttributeCores() {
		List<AttributeCore> attributeCores = attributeCoreRepository.findAll();
		
		if(attributeCores.size() == 0)
		{
			attributeCores = addAttributeCores();
		}
		
		return attributeCores;
	}
	
	private List<AttributeCore> addAttributeCores() {
		List<AttributeCore> cores = new ArrayList<AttributeCore>();
		
		//characters
		AttributeCore color = new AttributeCore();
		//integer
		AttributeCore height = new AttributeCore();
		AttributeCore width = new AttributeCore();
		//float
		AttributeCore weight = new AttributeCore();
		
		color.setName("Color");
		color.setType(AttributeType.characters);
		height.setName("Height");
		height.setType(AttributeType.integer);
		width.setName("Width");
		width.setType(AttributeType.integer);
		weight.setName("Weight");
		weight.setType(AttributeType.floatingpoint);
		
		attributeCoreRepository.save(color);
		attributeCoreRepository.save(height);
		attributeCoreRepository.save(width);
		attributeCoreRepository.save(weight);
		
		cores.add(color);
		cores.add(height);
		cores.add(width);
		cores.add(weight);
		
		return cores;
	}
	
	@Override
	public List<Attribute> createAttributes() {
		List<Attribute> attributes = attributeRepository.findAll();
		
		if(attributes.size() == 0)
		{
			attributes = addAttributes();
		}
		
		return attributes;
	}
	
	private List<Attribute> addAttributes() {
		List<Attribute> attributes = new ArrayList<Attribute>();
		
		//dependencies
		List<AttributeCore> cores = createAttributeCores();
		List<Product> products = createProducts();
		
		for(int i=0;i<NumberOfProducts;i++)
		{
			Attribute color = new Attribute();
			Attribute height = new Attribute();
			Attribute width = new Attribute();
			Attribute weight = new Attribute();
			
			color.setAttributeCore((AttributeCore)cores.toArray()[0]);
			height.setAttributeCore((AttributeCore)cores.toArray()[1]);
			width.setAttributeCore((AttributeCore)cores.toArray()[2]);
			weight.setAttributeCore((AttributeCore)cores.toArray()[3]);
			
			color.setProduct(products.get(i));
			height.setProduct(products.get(i));
			width.setProduct(products.get(i));
			weight.setProduct(products.get(i));
			
			color.setValue(faker.color().name());
			height.setValue((String.valueOf(Utils.random(1, 500))));
			width.setValue((String.valueOf(Utils.random(1, 500))));
			weight.setValue(String.format("%d.%d", Utils.random(4, 95),Utils.random(4, 95)));
			
			attributeRepository.save(color);
			attributeRepository.save(height);
			attributeRepository.save(width);
			attributeRepository.save(weight);
			
			attributes.add(color);
			attributes.add(height);
			attributes.add(width);
			attributes.add(weight);
		}
		
		return attributes;
	}
	
	@Override
	public List<Category> createCategories() {
		List<Category> categories = categoryRepository.findAll();
		
		if(categories.size() == 0)
		{
			categories = addCategories();
		}
		
		return categories;
	}
	
	public List<Category> addCategories()
	{
		List<Category> categories = new ArrayList<Category>();
		List<String> categoryNames = Arrays.asList("Phone","Car","Sunglassess","Television","Bicycle");
		
		for(int i=0;i<NumberOfCategories;i++)
		{
			Category category = new Category();
			
			category.setAbout(faker.lorem().sentence(15));
			category.setActive(true);
			category.setName(categoryNames.get(i));
			
			categoryRepository.save(category);
			categories.add(category);
		}
		
		return categories;
	}
	
	@Override
	public List<Product> createProducts() {
		List<Product> products = productRepository.findAll();
		
		if(products.size() == 0)
		{
			products = addProducts();
		}
		
		return products;
	}
	
	private List<Product> addProducts() {
		List<Product> products = new ArrayList<Product>();
		
		//dependencies
		List<User> users = createUsers();
		List<Category> categories = createCategories();
		
		for(int i=0;i<NumberOfProducts;i++)
		{
			String productName= faker.commerce().productName();
			
			Image image = new Image();
			image.setFile(Utils.getImageWithText(productName));
			imageRepository.save(image);
			
			Product product = new Product();
			User user = users.get(Utils.random(0, NumberOfUsers -1));
			Seller seller = user.getSeller();
			
			product.setName(productName);
			product.setActive(true);
			
			product.setDescription(faker.lorem().paragraph(1000));
			product.setCategories(new HashSet<Category>());
			product.setSeller(seller);
			product.addImage(image);
			
			if(i <= NumberOfProducts/2)
			{
				product.setType(ProductType.Bidding);
			}
			else {
				product.setType(ProductType.FixedPrice);
				product.setPrice(Utils.random(500, 300000));
			}
			
			product.setEnd(LocalDateTime.now().plusDays(Utils.random(0, 5)).plusHours(Utils.random(0, 5)).plusMinutes(Utils.random(0, 20)));
			
			for(int cat = 0; cat<Utils.random(0, NumberOfCategories);cat++)
			{
				product.getCategories().add(categories.get(cat));
			}
			
			productRepository.save(product);
			products.add(product);
			seller.addProduct(product);
			sellerRepository.save(seller);
		}
		
		return products;
	}
}




























