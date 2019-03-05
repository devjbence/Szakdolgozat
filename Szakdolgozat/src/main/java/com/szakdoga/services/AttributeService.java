package com.szakdoga.services;

import java.util.List;

import com.szakdoga.entities.Attribute;
import com.szakdoga.entities.DTOs.AttributeDTO;

public interface AttributeService extends BaseService<Attribute, AttributeDTO>{	
	int getIntValue(String value);
	double getDoubleValue(String value);
}
