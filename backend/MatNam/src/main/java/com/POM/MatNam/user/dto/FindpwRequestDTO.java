package com.POM.MatNam.user.dto;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Valid
@Getter
@Setter
public class FindpwRequestDTO {
	@ApiModelProperty(required = true)
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    String email;

    @ApiModelProperty(required = true)
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    String nickname;
}
