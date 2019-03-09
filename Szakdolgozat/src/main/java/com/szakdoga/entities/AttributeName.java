package com.szakdoga.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity(name="attribute_name")
@Getter
@Setter
public class AttributeName extends EntityBase{
	private String name;
	
	@OneToMany(mappedBy = "attributeName", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
