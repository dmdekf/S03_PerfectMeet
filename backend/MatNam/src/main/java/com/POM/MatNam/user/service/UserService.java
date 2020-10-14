package com.POM.MatNam.user.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.POM.MatNam.user.dao.UserAuthDao;
import com.POM.MatNam.user.dao.UserDao;
import com.POM.MatNam.user.dto.LoginRequestDTO;
import com.POM.MatNam.user.dto.SignupRequestDTO;
import com.POM.MatNam.user.dto.UpdateRequestDTO;
import com.POM.MatNam.user.dto.User;
import com.POM.MatNam.user.dto.UserAuth;
import com.POM.MatNam.user.util.EncryptHelper;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private UserAuthDao authDao;
	
	@Autowired
	private EncryptHelper encryptHelper;

	public UserAuth signup(SignupRequestDTO request, String key) {
		UserAuth user = new UserAuth();
		user.setEmail(request.getEmail());
		user.setNickname(request.getNickname());
		String encrypted = encryptHelper.encrypt(request.getPassword());
		user.setPassword(encrypted);
		user.setAge(request.getAge());
		user.setGender(request.isGender());
		user.setToken(key);
		return authDao.save(user);
	}

	public int duplicateCheck(String email, String nickname) {
		if (userDao.findByEmail(email).isPresent() || authDao.findByEmail(email).isPresent()) {
			return 1;
		} else if (userDao.findByNickname(nickname).isPresent() || authDao.findByNickname(nickname).isPresent()) {
			return 2; 
		}
		return 3;
	}

	public int login(LoginRequestDTO request) {
		Optional<User> userOpt = userDao.findByEmail(request.getEmail());
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			if (encryptHelper.isMatch(request.getPassword(), user.getPassword())) {
				return 3;
			} else {
				return 2;
			}
		} else {
			return 1;
		}
	}

	public void withdraw(String nickname) {
		User user = userDao.findByNickname(nickname).get();
		userDao.deleteById(user.getId());
	}

	public User update(UpdateRequestDTO request, String nickname) {
		User user = new User();
		User temp = selectByNickname(nickname);
		user.setEmail(temp.getEmail());
		user.setId(temp.getId());
		user.setNickname(request.getNickname());
		String encrypted = encryptHelper.encrypt(request.getPassword());
		user.setPassword(encrypted);
		user.setProfileImg(request.getProfileImg());
		user.setAge(temp.getAge());
		user.setGender(temp.isGender());
		user = userDao.save(user);
		return user;
	}

	public User selectByEmail(String email) {
		return userDao.findByEmail(email).orElse(null);
	}

	public User selectByNickname(String nickname) {
		return userDao.findByNickname(nickname).orElse(null);
	}

	public User authentication(long id, String key) {
		Optional<UserAuth> ua = authDao.findByIdAndToken(id, key);
		if(ua.isPresent()) {
			UserAuth auth = ua.get();
			User user = new User();
			user.setEmail(auth.getEmail());
			user.setNickname(auth.getNickname());
			user.setPassword(auth.getPassword());
			user.setAge(auth.getAge());
			user.setGender(auth.isGender());
			authDao.delete(auth);
			user = userDao.save(user);
			return user;
		}else {
			return null;
		}
	}
	private static String generateRandomPassword(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        
        for (int i = 0; i < length; i++) {
            int type = random.nextInt(2);
            int value;
            
            switch(type) {
            case 0:
                value = random.nextInt(10);
                sb.append(value + '0');
                break;
            case 1:
                value = random.nextInt(26);
                sb.append(value + 'a');
                break;
            case 2:
                value = random.nextInt(15);
                sb.append(value + '!');
                break;
            }
        }
        
        return sb.toString();
    }
	
	public String findPw(String email, String nickname) {
        Optional<User> ou = userDao.findByEmailAndNickname(email, nickname);
        String res = null;
        if (ou.isPresent()) {
            User u = ou.get();
            String temppw = generateRandomPassword(8);

            u.setPassword(encryptHelper.encrypt(encryptHelper.sha256(temppw)));

            userDao.save(u);
            
            res = temppw;
        } else {
            if (userDao.findByEmail(email).isPresent()) {
                res = "nickname";
            } else {
                res = "email";
            }
        }

        return res;
    }
}
