package com.POM.MatNam.reserve.dto;

import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Valid
@Getter
@Setter
public class ReserveWaitRequestDTO {
	@ApiModelProperty(required = true)
	@NotNull(message = "매장 번호는 필 수 입력 값입니다.")
	Long store_id;
	
	@ApiModelProperty(required = true)
	@NotBlank(message = "닉네임은 필 수 입력 값 입니다.")
	String nickname;
	
	@ApiModelProperty(required = true)
	LocalDateTime date;
	
	@ApiModelProperty(required = true)
	@NotNull(message = "예약 인원은 필 수 입니다.")
	int people_num;
}
