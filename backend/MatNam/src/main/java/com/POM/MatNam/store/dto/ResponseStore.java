package com.POM.MatNam.store.dto;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class ResponseStore {
	private Long id;
	
	private String name;
	
	private String address;
	
	private String tel;
	
	private String image;
	
}
