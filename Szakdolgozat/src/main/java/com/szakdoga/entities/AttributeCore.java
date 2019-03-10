package com.szakdoga.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.szakdoga.enums.AttributeType;

import lombok.Getter;
import lombok.Setter;

@Entity(name="attribute_core")
@Getter
@Setter
public class AttributeCore extends EntityBase{
	private String name;
	@Enumerated(EnumType.ORDINAL)
	private AttributeType type;
	
	@OneToMany(mappedBy = "attributeCore", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Attribute> attributes;
	
	public void addAttribute(Attribute attribute)
	{
		if(attributes==null)
			attributes = new ArrayList<Attribute>();
		attributes.add(attribute);
	}
	public void removeAttribute(Attribute attribute)
	{
		if(attributes==null)
			return;
		attributes.remove(attribute);
	}
}
