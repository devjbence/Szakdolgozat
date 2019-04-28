package com.szakdoga;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.szakdoga.entities.AccessTokenEntity;
import com.szakdoga.entities.Product;
import com.szakdoga.entities.Seller;
import com.szakdoga.entities.RefreshTokenEntity;
import com.szakdoga.entities.User;
import com.szakdoga.repositories.AccessTokenRepository;
import com.szakdoga.repositories.BuyerRepository;
import com.szakdoga.repositories.CategoryRepository;
import com.szakdoga.repositories.RefreshTokenRepository;
import com.szakdoga.repositories.RoleRepository;
import com.szakdoga.repositories.SellerRepository;
import com.szakdoga.repositories.UserRepository;
import com.szakdoga.services.interfaces.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SzakdolgozatApplicationTests {

	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	BuyerRepository buyerRepository;
	@Autowired
	SellerRepository sellerRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	AccessTokenRepository accTokenRepository;
	@Autowired
	RefreshTokenRepository refTokenRepository;
	@Autowired
	UserService userService;
	
	
	@Test
	public void createProducts()
	{/*
		User user = userRepository.findByUsername("user");
		Seller seller = sellerRepository.findByUser(user);
		
		Product pro1 = new Product();
		pro1.setDescription("Description");
		pro1.setName("job1");
		pro1.setSeller(seller);
		
		Product pro2 = new Product();
		pro2.setDescription("Description");
		pro2.setName("job2");
		pro2.setSeller(seller);
		
		seller.addProduct(pro1);
		seller.addProduct(pro2);
		
		sellerRepository.save(seller);*/
	}
	
	@Test
	public void checkTokens()
	{/*
		List<AccessTokenEntity> findAll = accTokenRepository.findAll();
		for(AccessTokenEntity token : findAll)
		{
			RefreshTokenEntity refToken = refTokenRepository.findByTokenId(token.getRefreshToken());
			
			if(refToken !=null)
				System.out.println("\n\n\n"+"------- ref token id: " + refToken.getTokenId()+"\n\n\n");
			
			System.out.println("\n\n\n"+token.getUsername()+"\n\n\n");
		}
	*/}
}



