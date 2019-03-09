package com.szakdoga.services.implementations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.szakdoga.entities.Comment;
import com.szakdoga.entities.Product;
import com.szakdoga.entities.DTOs.CommentDTO;
import com.szakdoga.exceptions.ProductDoesNotExistsException;
import com.szakdoga.repos.BuyerRepository;
import com.szakdoga.repos.CommentRepository;
import com.szakdoga.repos.ProductRepository;
import com.szakdoga.services.interfaces.CommentService;
import com.szakdoga.services.interfaces.UserService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private BuyerRepository buyerRepository;
	@Autowired
	private UserService userService;

	@Override
	public void add(CommentDTO dto) {
		Comment entity = new Comment();
		Product product = productRepository.findById(dto.getProductId());

		mapDtoToEntity(dto, entity);

		commentRepository.save(entity);
		
		product.addComment(entity);
		productRepository.save(product);
	}

	@Override
	public CommentDTO get(Integer id) {
		Comment entity = commentRepository.findById(id);

		if (entity == null)
			return null;

		CommentDTO dto = new CommentDTO();

		mapEntityToDto(entity, dto);

		return dto;
	}

	@Override
	public void update(int id, CommentDTO dto) {
		Comment entity = commentRepository.findById(id);

		mapDtoToEntityNonNullsOnly(dto, entity);

		commentRepository.save(entity);
	}

	@Override
	public void delete(Integer id) {

		Comment entity = commentRepository.findById(id);

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
		entity.setProduct(productRepository.findOne(dto.getProductId()));
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
		Product product = productRepository.findById(dto.getProductId());

		if (product == null) {
			throw new ProductDoesNotExistsException("The given product does not exists");
		}
	}

}
