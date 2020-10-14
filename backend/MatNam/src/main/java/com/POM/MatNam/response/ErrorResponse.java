package com.POM.MatNam.response;

import io.swagger.annotations.ApiModelProperty;

public class ErrorResponse extends BasicResponse{
	@ApiModelProperty(value = "errors", position = 4)
    public Object errors; // response-> errors
}
