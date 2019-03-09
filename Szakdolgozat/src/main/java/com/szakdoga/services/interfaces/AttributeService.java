package com.szakdoga.services.interfaces;

import com.szakdoga.entities.Attribute;
import com.szakdoga.entities.DTOs.AttributeDTO;

public interface AttributeService extends BaseService<Attribute, AttributeDTO>{	
	int getIntValue(String value);
	double getDoubleValue(String value);
	void validate(AttributeDTO dto);
}
