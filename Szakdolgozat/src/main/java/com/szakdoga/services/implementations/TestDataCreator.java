package com.szakdoga.services.implementations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;
import com.szakdoga.entities.Buyer;
import com.szakdoga.entities.Role;
import com.szakdoga.entities.Seller;
import com.szakdoga.entities.User;
import com.szakdoga.repositories.BuyerRepository;
import com.szakdoga.repositories.RoleRepository;
import com.szakdoga.repositories.SellerRepository;
import com.szakdoga.repositories.UserRepository;
import com.szakdoga.services.interfaces.ITestDataCreator;

@Service
public class TestDataCreator implements ITestDataCreator {

	@Autowired
	private Faker faker;
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
	
	final static int NumberOfRoles=2;
	final static int NumberOfUsers = 10;

	
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
			user.setEmail(String.format("testmail%d@gmail.com", i));
			user.setActivated(i>NumberOfUsers/4);
			user.setUsername(String.format("user%d", i));
			user.setPassword(bCryptPasswordEncoder.encode("password"));
			
			userRepository.save(user);
			
			//profiles
			String firstName = faker.firstName();
			String lastName = faker.lastName();
			
			Buyer buyer = new Buyer();
			Seller seller = new Seller();

			buyer.setAboutMe(faker.paragraph());
			buyer.setFirstName(firstName);
			buyer.setLastName(lastName);
			buyer.setProfileImage(null);
			buyer.setUser(user);

			seller.setAboutMe(faker.paragraph());
			seller.setFirstName(firstName);
			seller.setLastName(lastName);
			seller.setProfileImage(null);
			seller.setUser(user);
			
			sellerRepository.save(seller);
			buyerRepository.save(buyer);
			
			users.add(user);
		}
		
		return users;
	}
}




























