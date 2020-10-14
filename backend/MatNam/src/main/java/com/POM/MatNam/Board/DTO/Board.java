package com.POM.MatNam.Board.DTO;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity (name="board")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Board {
   @Id
   private int id;
   
   private String subject;
   private String content;
   @Column(insertable =false)
   private Date created;
   private String nickname;
   private int hit;
}


		
