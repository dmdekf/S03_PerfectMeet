package com.POM.MatNam.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.POM.MatNam.response.BasicResponse;
import com.POM.MatNam.response.ErrorResponse;
import com.POM.MatNam.review.DAO.ReviewDao;
import com.POM.MatNam.review.DTO.Review;
import com.POM.MatNam.user.dto.FindpwRequestDTO;
import com.POM.MatNam.user.dto.LoginRequestDTO;
import com.POM.MatNam.user.dto.SignupRequestDTO;
import com.POM.MatNam.user.dto.UpdateRequestDTO;
import com.POM.MatNam.user.dto.User;
import com.POM.MatNam.user.dto.UserAuth;
import com.POM.MatNam.user.service.JwtService;
import com.POM.MatNam.user.service.MailSendService;
import com.POM.MatNam.user.service.UserService;

import io.swagger.annotations.ApiOperation;
import lombok.val;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private MailSendService mailSendService;

	@Autowired
	private ReviewDao reviewDao;
	
	@PostMapping
	@ApiOperation(value = "회원 가입")
	public Object signup(@Valid @RequestBody SignupRequestDTO request) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object> errors = new HashMap<>();
		int check = userService.duplicateCheck(request.getEmail(), request.getNickname());
		if (check == 1) {
			errors.put("field", "email");
			errors.put("data", request.getEmail());
			final ErrorResponse result = setErrors("E-4001", "이미 존재하는 이메일입니다.", errors);

			response = new ResponseEntity<BasicResponse>(result, HttpStatus.CONFLICT);
		} else if (check == 2) {
			errors.put("field", "nickname");
			errors.put("data", request.getNickname());
			final ErrorResponse result = setErrors("E-4002", "이미 존재하는 닉네임입니다.", errors);

			response = new ResponseEntity<BasicResponse>(result, HttpStatus.CONFLICT);
		} else {
			String key = mailSendService.getKey(false, 20);
			
			try {
				UserAuth user = userService.signup(request,key);
				mailSendService.mailSendWithUserKey(user.getEmail(), user.getNickname(), key, user.getId());
				 
			}catch(MessagingException e) {
				errors.put("field", "sendMail");

                final ErrorResponse result = setErrors("E-4006", "메일 발송에 실패했습니다.", errors);

                return new ResponseEntity<>(result, HttpStatus.CONFLICT);
			}
			final BasicResponse result = new BasicResponse();
			result.status = "S-200";
			result.message = "회원가입에 성공했습니다.";
			response = new ResponseEntity<BasicResponse>(result, HttpStatus.CREATED);

		}
		return response;
	}

	@PostMapping("/login") 
	@ApiOperation(value = "로그인")
	public Object login(@Valid @RequestBody LoginRequestDTO request, HttpServletResponse res) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object> errors = new HashMap<>();
		int check = userService.login(request);
		if (check == 1) {
			errors.put("field", "email");
			errors.put("data", request.getEmail());
			final ErrorResponse result = setErrors("E-4003", "존재하지 않는 이메일입니다.", errors);

			response = new ResponseEntity<BasicResponse>(result, HttpStatus.NOT_FOUND);
		} else if (check == 2) {
			errors.put("field", "password");
			errors.put("data", request.getPassword());
			final ErrorResponse result = setErrors("E-4004", "비밀번호가 일치하지 않습니다.", errors);

			response = new ResponseEntity<BasicResponse>(result, HttpStatus.CONFLICT);
		} else {
			final BasicResponse result = new BasicResponse();
			User user = userService.selectByEmail(request.getEmail());
			String token = jwtService.create(user);
			String nickname = user.getNickname();
			res.setHeader("jwt-auth-token", token);
			res.setHeader("nickname", nickname);
			result.status = "S-200";
			result.message = "로그인에 성공했습니다.";
			response = new ResponseEntity<BasicResponse>(result, HttpStatus.OK);
		}
		return response;
	}

	@GetMapping
	@ApiOperation(value = "회원 정보 조회")
	public Object select(@RequestParam String nickname) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object> errors = new HashMap<>();
		User user = userService.selectByNickname(nickname);
		if (user == null) {
			errors.put("field", "nickname");
			errors.put("data", nickname);
			final ErrorResponse result = setErrors("E-4005", "해당 유저가 존재하지 않습니다.", errors);
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		} else {
			final BasicResponse result = new BasicResponse();
			Map<String, Object> data = new HashMap<>();
			List<Review> reviews = reviewDao.findByNickname(nickname);
			result.status = "S-200";
			result.message = "회원 정보 조회에 성공했습니다.";
			data.put("user", user);
			data.put("reviews",reviews);
			result.data = data;
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		return response;
	}

	@PutMapping
	@ApiOperation(value = "회원 정보 수정")
	public Object update(@Valid @RequestBody UpdateRequestDTO request,
			@RequestHeader(value = "nickname", required = true) String nickname, HttpServletResponse res) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object> errors = new HashMap<>();
		User checkUser = userService.selectByNickname(nickname);
		int check = userService.duplicateCheck("", request.getNickname());
		if (!checkUser.getNickname().equals(request.getNickname()) && check == 2) {
			errors.put("field", "nickname");
			errors.put("data", request.getNickname());
			final ErrorResponse result = setErrors("E-4002", "이미 존재하는 닉네임입니다.", errors);
			response = new ResponseEntity<>(result, HttpStatus.CONFLICT);
		} else {
			final BasicResponse result = new BasicResponse();
			User user = userService.update(request, nickname);
			String token = jwtService.create(user);
			res.setHeader("jwt-auth-token", token);
			res.setHeader("nickname",user.getNickname());
			result.status = "S-200";
			result.message = "회원 정보 수정이 완료되었습니다.";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		return response;
	}

	@DeleteMapping
	@ApiOperation(value = "회원탈퇴")
	public Object withDraw(@RequestParam String nickname) {
		ResponseEntity<BasicResponse> response = null;
		Map<String, Object> errors = new HashMap<>();
		User user = userService.selectByNickname(nickname);
		if (user == null) {
			errors.put("field", "nickname");
			errors.put("data", nickname);
			final ErrorResponse result = setErrors("E-4005", "해당 유저가 존재하지 않습니다.", errors);
			response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		} else {
			userService.withdraw(nickname);
			final BasicResponse result = new BasicResponse();
			result.status = "S-200";
			result.message = "회원 탈퇴에 성공했습니다.";
			response = new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
		}
		return response;
	}
	
	@GetMapping("/auth")
	@ApiOperation(value="이메일 인증")
	public Object confirm(@RequestParam long id, @RequestParam String key) {
		 ResponseEntity<BasicResponse> response = null;
	        Map<String, Object> errors = new HashMap<>();
	        User user = userService.authentication(id, key);
	        if (user == null) {
	            errors.put("field", "key");
	            errors.put("data", key);
	            final ErrorResponse result = setErrors("E-4007", "이메일 인증에 실패했습니다.", errors);
	            response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
	        } else {
	            final BasicResponse result = new BasicResponse();
	            result.status = "S-200";
	            result.message = "이메일 인증에 성공했습니다.";
	            response = new ResponseEntity<>(result, HttpStatus.OK);
	        }
	        return response;
	}
	
	@PostMapping("/user/findpw")
    @ApiOperation(value = "비밀 번호 찾기")
    public Object findpw(@RequestBody FindpwRequestDTO request) {
        ResponseEntity<BasicResponse> response = null;
        Map<String, Object> errors = new HashMap<>();
        String pw = userService.findPw(request.getEmail(), request.getNickname());
        if (pw.equals("email")) {
        	errors.put("field", "email");
            errors.put("data", request.getEmail());
            final ErrorResponse result = setErrors("E-4003", "존재하지 않는 이메일 입니다.", errors);
            response = new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else if (pw.equals("nickname")) {
            errors.put("field", "nickname");
            errors.put("data", request.getNickname());
            final ErrorResponse result = setErrors("E-4008", "닉네임이 일치하지 않습니다.", errors);
            response = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else {
            try {
                mailSendService.mailSendWithPassword(request.getEmail(), request.getNickname(), pw);
            } catch (MessagingException e) {
                errors.put("field", "sendMail");
                final ErrorResponse result = setErrors("E-4006", "메일 발송에 실패했습니다.", errors);
                return new ResponseEntity<>(result, HttpStatus.CONFLICT);
            }
            final BasicResponse result = new BasicResponse();
            result.status = "S-200";
            result.message = "비밀번호 찾기에 성공했습니다.";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        }

        return response;
    }

	private ErrorResponse setErrors(String status, String message, Map<String, Object> errors) {
		ErrorResponse res = new ErrorResponse();
		res.status = status;
		res.message = message;
		res.errors = errors;
		return res;
	}
}
