package com.szakdoga.services.interfaces;

import com.szakdoga.entities.Attribute;
import com.szakdoga.entities.DTOs.AttributeDTO;

public interface AttributeService extends BaseService<Attribute, AttributeDTO>{	
	void validate(AttributeDTO dto);
}
