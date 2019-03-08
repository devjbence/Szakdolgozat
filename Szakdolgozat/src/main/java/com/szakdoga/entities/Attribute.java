package com.szakdoga.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.szakdoga.enums.AttributeType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Attribute extends EntityBase{
	
	private String name;
	@Enumerated(EnumType.ORDINAL)
	private AttributeType type;
	private String value;
}
