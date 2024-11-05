package com.springboot.blogging.entities;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

public class BaseObject {
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	private String	id;
	@JsonIgnore
	private boolean	deleted			= false;
	@JsonIgnore
	private boolean	updated			= false;
	@JsonIgnore
	private boolean	isActive		= false;
	private String	createdByUser;
	private Date	creationDate	= new Date ();

	private Date	lastModifiedDate;
	private String	lastModifiedUserId;
	private int		schemaVersion;
}

