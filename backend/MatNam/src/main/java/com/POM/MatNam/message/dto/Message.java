package com.POM.MatNam.message.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity (name="message")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Message {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String sender;
	
	@Column
	private String receiver;
	
	@Column
	private String content;
	
	@Column
	private boolean check_message;
}
