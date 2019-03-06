package com.szakdoga.services.interfaces;

import com.szakdoga.entities.Comment;
import com.szakdoga.entities.DTOs.CommentDTO;

public interface CommentService extends BaseService<Comment,CommentDTO>{
	void ValidateDto(CommentDTO dto);
}
