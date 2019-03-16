package com.szakdoga.services.implementations;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szakdoga.entities.Buyer;
import com.szakdoga.entities.Product;
import com.szakdoga.repositories.BuyerRepository;
import com.szakdoga.repositories.ProductRepository;
import com.szakdoga.utils.EmailUtil;

@Service
public class ProductDeactivationService {
	@Autowired
	private BuyerRepository buyerRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private EmailUtil emailUtil;

	public void productDeactivationJob() {

		LocalDateTime now = LocalDateTime.now();

		for (Product product : productRepository.findAll()) 
		{
			LocalDateTime end = product.getEnd().toInstant().atZone(ZoneId.of("Europe/Prague")).toLocalDateTime().minusHours(1);
			
			if(end.isBefore(now) && product.getActive())
			{
				switch(product.getType())
				{
				case Bidding:
					if(product.getBiddings() != null && !product.getBiddings().isEmpty())
					{
						int maxPrice = product.getBiddings().stream().mapToInt(x->x.getPrice()).max().getAsInt();
						Buyer buyer = product.getBiddings().stream().filter(x->x.getPrice() == maxPrice).map(x->x.getBuyer()).findFirst().get();
						
						product.setBuyer(buyer);
						product.setActive(false);
						productRepository.save(product);
						
					    buyer.addProduct(product);
					    buyerRepository.save(buyer);
					    emailUtil.sendSimpleMessage(
					    		buyer.getUser().getEmail(), "You have won a bidding", "Your bidding for the product: '"+ product.getName()+"' had resulted that, you have won it!");
					}
					break;
				case FixedPrice: 
					break;
				}
			}
		}

	}
}
