package com.POM.MatNam.user.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class SaltEncrypt implements EncryptHelper{

	@Override
	public String encrypt(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	@Override
	public boolean isMatch(String password, String hashed) {
		return BCrypt.checkpw(password, hashed);
	}

	@Override
	public String sha256(String plain) {
		try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(plain.getBytes());
            byte[] bytes = md.digest();
            
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException ignored) {
            return "";
        }
	}
	
}
