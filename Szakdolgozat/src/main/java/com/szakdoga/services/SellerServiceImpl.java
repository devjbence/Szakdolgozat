package com.szakdoga.services;

import java.io.IOException;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.szakdoga.entities.ProductCategory;
import com.szakdoga.entities.Seller;
import com.szakdoga.entities.User;
import com.szakdoga.entities.DTOs.SellerDTO;
import com.szakdoga.exceptions.ImageSizeIsTooBigException;
import com.szakdoga.exceptions.UserDoesNotExistsException;
import com.szakdoga.repos.ProductCategoryRepository;
import com.szakdoga.repos.SellerRepository;
import com.szakdoga.repos.UserRepository;
import com.szakdoga.utils.Utils;

@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProductCategoryRepository categoryRepository;
	@Autowired
	private SellerRepository sellerRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;

	@Override
	public void updateProfile(SellerDTO sellerDTO) {

		User user = userService.checkUserValues(sellerDTO.getUsername());
		Seller sellerFromDb = sellerRepository.findByUser(user);

		mapNonNullFields(sellerDTO, sellerFromDb);
		updateCategories(sellerDTO, sellerFromDb);
		sellerRepository.save(sellerFromDb);
	}

	private void updateCategories(SellerDTO sellerDTO, Seller sellerFromDb) {
		if (sellerDTO.getCategories() == null)
			return;

		for (int categoryId : sellerDTO.getCategories()) {
			if (categoryId < 0) {
				ProductCategory category = categoryRepository.findById(categoryId * (-1));
				sellerFromDb.removeCategory(category);
			} else {
				ProductCategory category = categoryRepository.findById(categoryId);
				if (category != null) {
					if (!sellerFromDb.getCategories().contains(category)) {
						sellerFromDb.addCategory(category);
					}
				}
			}
		}
	}

	private void mapNonNullFields(SellerDTO sellerDTO, Seller sellerFromDb) {
		if (sellerDTO.getAboutMe() != null)
			sellerFromDb.setAboutMe(sellerDTO.getAboutMe());
		if (sellerDTO.getFirstName() != null)
			sellerFromDb.setFirstName(sellerDTO.getFirstName());
		if (sellerDTO.getLastName() != null)
			sellerFromDb.setLastName(sellerDTO.getLastName());
	}

	@Override
	public void saveImage(String username, MultipartFile imageFile) {

		User user = userRepository.findByUsername(username);

		if (user == null)
			throw new UserDoesNotExistsException("The given user by the username: " + username + " does not exists!");

		userService.checkIfActivated(user);

		// max file méret < 64kb
		if (imageFile.getSize() > Utils.MAX_IMAGEFILE_SIZE)
			throw new ImageSizeIsTooBigException(
					"The imagesize is more than: " + Utils.MAX_IMAGEFILE_SIZE / 1000 + " KB");

		Seller seller = sellerRepository.findByUser(user);
		try {
			seller.setProfileImage(imageFile.getBytes());
			sellerRepository.save(seller);
		} catch (IOException e) {
			// TODO
		}
	}

	@Override
	public byte[] getProfileImage(String username) {

		User user = userRepository.findByUsername(username);
		if (user == null)
			throw new UserDoesNotExistsException("The given user by the username: " + username + " does not exists!");

		Seller seller = sellerRepository.findByUser(user);
		byte[] profileImage = seller.getProfileImage();
		if (profileImage == null) {
			return Utils.getDefaultProfileImage();
		}
		return profileImage;
	}

	public void removeDescendants(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null)
			throw new UserDoesNotExistsException("The given user by the username: " + username + " does not exists!");

		Seller seller = sellerRepository.findByUser(user);

		Iterator<ProductCategory> categoryIterator = seller.getCategories().iterator();
		while (categoryIterator.hasNext()) {
			ProductCategory category = categoryIterator.next();
			categoryIterator.remove();
		}

		productService.removeAllProducts(seller);
	}
}
