package com.POM.MatNam.review.DTO;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity (name="review")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Review {
	   @Id
	   private long id;
	   
	   private String nickname;
	   @Column
	   private long storeId;
	   private String content;
	   private String img;
	   private double score;

}
