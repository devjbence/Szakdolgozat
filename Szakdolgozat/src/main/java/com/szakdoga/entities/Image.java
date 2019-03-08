package com.szakdoga.entities;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="image")
public class Image extends EntityBase{
	
	@JsonIgnore
	@Lob
	private byte[] file;
}
