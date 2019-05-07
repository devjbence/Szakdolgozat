package com.szakdoga.entities;

import java.time.Instant;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class EntityBase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer id;
	@JsonIgnore
	@CreationTimestamp //https://www.thoughts-on-java.org/persist-creation-update-timestamps-hibernate/
	@Column(updatable = false)
	protected Instant created;
	
	@JsonIgnore
	@UpdateTimestamp
	protected Instant modified;	
}
