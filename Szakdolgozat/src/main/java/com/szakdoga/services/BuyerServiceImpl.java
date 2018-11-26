package com.szakdoga.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.szakdoga.entities.ProductCategory;
import com.szakdoga.entities.Buyer;
import com.szakdoga.entities.User;
import com.szakdoga.entities.DTOs.BuyerDTO;
import com.szakdoga.exceptions.ImageSizeIsTooBigException;
import com.szakdoga.exceptions.UserDoesNotExistsException;
import com.szakdoga.repos.ProductCategoryRepository;
import com.szakdoga.repos.BuyerRepository;
import com.szakdoga.repos.UserRepository;
import com.szakdoga.utils.Utils;

@Service
public class BuyerServiceImpl implements BuyerService {

	@Autowired
	private ProductCategoryRepository categoryRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BuyerRepository buyerRepository;
	@Autowired
	private UserService userService;

	@Override
	public void updateProfile(BuyerDTO buyerDTO) {

		User user = userService.checkUserValues(buyerDTO.getUsername());
		Buyer buyerFromDb = buyerRepository.findByUser(user);

		mapNonNullFields(buyerDTO, buyerFromDb);
		updateCategories(buyerDTO, buyerFromDb);
		buyerRepository.save(buyerFromDb);
	}

	private void updateCategories(BuyerDTO buyerDTO, Buyer buyerFromDb) {
		if (buyerDTO.getCategories() == null)
			return;

		for (int categoryId : buyerDTO.getCategories()) {
			if (categoryId < 0) {
				ProductCategory category = categoryRepository.findById(categoryId * (-1));
				buyerFromDb.removeCategory(category);
			} else {
				ProductCategory category = categoryRepository.findById(categoryId);
				if (category != null) {
					if (!buyerFromDb.getCategories().contains(category)) {
						buyerFromDb.addCategory(category);
					}
				}
			}
		}
	}

	private void mapNonNullFields(BuyerDTO buyerDTO, Buyer buyerFromDb) {
		if (buyerDTO.getAboutMe() != null)
			buyerFromDb.setAboutMe(buyerDTO.getAboutMe());
		if (buyerDTO.getFirstName() != null)
			buyerFromDb.setFirstName(buyerDTO.getFirstName());
		if (buyerDTO.getLastName() != null)
			buyerFromDb.setLastName(buyerDTO.getLastName());
	}

	@Override
	public void saveImage(String username, MultipartFile imageFile) {
		User user = userRepository.findByUsername(username);

		if (user == null)
			throw new UserDoesNotExistsException("The given user by the username: " + username + " does not exists!");

		userService.checkIfActivated(user);

		//max file méret < 64kb
		if(imageFile.getSize() > Utils.MAX_IMAGEFILE_SIZE)
			throw new ImageSizeIsTooBigException("The imagesize is more than: "+ Utils.MAX_IMAGEFILE_SIZE/1000 + " KB");
			
		System.out.println("\n\n"+imageFile.getContentType()+"\n\n");
		
		Buyer buyer = buyerRepository.findByUser(user);
		try {
			buyer.setProfileImage(imageFile.getBytes());
			buyerRepository.save(buyer);
		} catch (IOException e) {
			// TODO
		}
	}

	@Override
	public byte[] getProfileImage(String username) {

		User user = userRepository.findByUsername(username);
		if (user == null)
			throw new UserDoesNotExistsException("The given user by the username: " + username + " does not exists!");

		Buyer buyer = buyerRepository.findByUser(user);
		byte[] profileImage = buyer.getProfileImage();
		if (profileImage == null) {
			return Utils.getDefaultProfileImage();
		}
		return profileImage;

	}

}
