package com.POM.MatNam.reserve.dto;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WaitDeleteRequestDTO {
	@ApiModelProperty(required = true)
	Long store_id;
	
	@ApiModelProperty(required = true)
	String nickname;
	
	@ApiModelProperty(required = true)
	LocalDateTime date;
}
