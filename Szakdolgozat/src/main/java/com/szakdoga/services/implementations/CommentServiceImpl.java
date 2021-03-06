package com.szakdoga.services.implementations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szakdoga.entities.Comment;
import com.szakdoga.entities.Product;
import com.szakdoga.entities.DTOs.CommentDTO;
import com.szakdoga.exceptions.ProductDoesNotExistsException;
import com.szakdoga.repositories.BuyerRepository;
import com.szakdoga.repositories.CommentRepository;
import com.szakdoga.repositories.ProductRepository;
import com.szakdoga.services.interfaces.CommentService;
import com.szakdoga.services.interfaces.UserService;

@Service
public class CommentServiceImpl extends BaseServiceClass<Comment,CommentDTO> implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private BuyerRepository buyerRepository;
	@Autowired
	private UserService userService;

	@Override
	public CommentDTO add(CommentDTO dto) {
		Comment entity = new Comment();
		Product product = productRepository.findById(dto.getProductId()).get();

		mapDtoToEntity(dto, entity);

		commentRepository.save(entity);
		
		product.addComment(entity);
		productRepository.save(product);
		
		mapEntityToDto(entity, dto);
		
		return dto;
	}

	@Override
	public CommentDTO get(Integer id) {
		Comment entity = commentRepository.findById(id).get();

		if (entity == null)
			return null;

		CommentDTO dto = new CommentDTO();

		mapEntityToDto(entity, dto);

		return dto;
	}

	@Override
	public CommentDTO update(int id, CommentDTO dto) {
		Comment entity = commentRepository.findById(id).get();

		mapDtoToEntityNonNullsOnly(dto, entity);

		commentRepository.save(entity);
		
		mapEntityToDto(entity, dto);
		
		return dto;
	}

	@Override
	public void delete(Integer id) {

		Comment entity = commentRepository.findById(id).get();

		entity.getProduct().removeComment(entity);
		entity.getBuyer().removeComment(entity);

		commentRepository.save(entity);

		commentRepository.delete(entity);
	}

	@Override
	public List<CommentDTO> getAll() {
		List<CommentDTO> dtos = new ArrayList<CommentDTO>();

		for (Comment entity : commentRepository.findAll()) {
			CommentDTO dto = new CommentDTO();

			mapEntityToDto(entity, dto);

			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public List<CommentDTO> getAll(int page, int size) {
		if (page < 0 || size < 0)
			return new ArrayList<CommentDTO>();

		List<CommentDTO> dtos = getAll();
		List<CommentDTO> pagedDtos = new ArrayList<CommentDTO>();

		int count = dtos.size();
		int firstElement = page * size;
		int endElement = size + page * size;

		if (firstElement > count)
			return new ArrayList<CommentDTO>();

		for (int i = firstElement; i < endElement; i++) {
			if (i > count - 1)
				break;
			pagedDtos.add(dtos.get(i));
		}

		return pagedDtos;
	}

	@Override
	public void mapDtoToEntity(CommentDTO dto, Comment entity) {
		entity.setMessage(dto.getMessage());
		entity.setBuyer(userService.getCurrentUser().getBuyer());
		entity.setId(dto.getId());
		entity.setProduct(productRepository.findById(dto.getProductId()).get());
	}

	@Override
	public void mapEntityToDto(Comment entity, CommentDTO dto) {
		dto.setMessage(entity.getMessage());
		dto.setId(entity.getId());
		dto.setProductId(entity.getProduct().getId());
		dto.setUsername(entity.getBuyer().getUser().getUsername());
		dto.setBuyerId(entity.getBuyer().getId());
	}

	@Override
	public void mapDtoToEntityNonNullsOnly(CommentDTO dto, Comment entity) {
		if (dto.getMessage() != null)
			entity.setMessage(dto.getMessage());
	}

	@Override
	public int size() {
		return Math.toIntExact(buyerRepository.count());
	}

	@Override
	public void ValidateDto(CommentDTO dto) {
		Product product = productRepository.findById(dto.getProductId()).get();

		if (product == null) {
			throw new ProductDoesNotExistsException("The given product does not exists");
		}
	}

}
