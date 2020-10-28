package com.POM.MatNam.reserve.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity (name = "reserve_wait")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ReserveWait {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private long store_id;
	
	@Column
	private String nickname;
	
	@Column 
	private LocalDateTime reserve_date;
	
	@Column
	private int people_num;
	
	
	
}
