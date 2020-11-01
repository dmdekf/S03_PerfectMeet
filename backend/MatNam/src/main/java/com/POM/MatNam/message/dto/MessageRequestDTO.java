package com.POM.MatNam.message.dto;

import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Valid
@Getter
@Setter

public class MessageRequestDTO {
	@ApiModelProperty(required = true)
	@NotBlank(message = "발신자는 필 수 입력 값 입니다.")
	String sender;
	
	@ApiModelProperty(required = true)
	@NotBlank(message = "수신자는 필 수 입력 값 입니다.")
	String receiver;
	
	@ApiModelProperty(required = true)
	@NotBlank(message = "메세지 내용은 필 수 입력 값 입니다.")
	String content;
}
