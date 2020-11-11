package com.POM.MatNam.storeres.DTO;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreResRequest {
	@ApiModelProperty(required = true)
    String address;
	
	@ApiModelProperty(required = true)
	String name;
	
	@ApiModelProperty(required = true)
	String tel;
	
	@ApiModelProperty(required = true)
	String nickname;
}
