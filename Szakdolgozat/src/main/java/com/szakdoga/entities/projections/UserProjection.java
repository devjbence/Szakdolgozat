package com.szakdoga.entities.projections;


import org.springframework.data.rest.core.config.Projection;
import com.szakdoga.entities.Seller;
import com.szakdoga.entities.Buyer;
import com.szakdoga.entities.User;

@Projection(name = "user", types = { User.class })
public interface UserProjection {
	
	String getUsername();
	String getEmail();
	Buyer getBuyer();
	Seller getSeller();
}
