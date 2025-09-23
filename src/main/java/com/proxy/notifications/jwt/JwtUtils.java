package com.proxy.notifications.jwt;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.AeadAlgorithm;

@Component
public class JwtUtils {
	@Value("${token.secret.key}")
	private String secret;
	@Value("${token.expirationms}")
	private long expirationTime;
	private PublicKey pubKey;
	private PrivateKey privKey;
	Map<String, Object> rsaKeys;
	public JwtUtils() {
		try {
			rsaKeys = getRSAKeys();
			this.pubKey = (PublicKey) rsaKeys.get("public");
			this.privKey = (PrivateKey) rsaKeys.get("private");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String createJwt(String name)
	{
		try {
			return Jwts
					.builder()
					.subject(name)
					.issuedAt(new Date(System.currentTimeMillis()))
					.expiration(new Date(System.currentTimeMillis()+expirationTime))
					.signWith(privKey)
					.compact();
		}catch(Exception E)
		{
			return null;
		}
			//cfgInputOutput.log(LocalDateTime.now(), 0, "cache top 1 boolean null. set to false");				
		
	}
	public String extractUsername(String token)
	{
		Base64.Decoder decoder = Base64.getUrlDecoder();
		try {
			return Jwts
					.parser()
					.verifyWith(pubKey)
					.build()
					.parseSignedClaims(token)
					.getPayload()
					.getSubject();
		}catch(Exception E)
		{
			try{
				String payload = new String(decoder.decode(token.split("\\.")[1]));
				payload = payload.substring(payload.indexOf("sub\":")+6,payload.length()-1);
				payload = payload.substring(0, payload.indexOf("\""));
				return payload;
			}catch(Exception F)
			{	
				return null;
			}
		}
	}
	public boolean validateToken(String token, String username)
	{
		try {
			String tokenUsername = extractUsername(token);	
			return (tokenUsername.equals(username) && !isTokenExpired(token));
		}catch(Exception E)
		{
			return false;
		}
	}
	public boolean isTokenExpired(String token)
	{
		try {
			return Jwts
					.parser()
					.verifyWith(pubKey)
					.build()	
					.parseSignedClaims(token)
					.getPayload()
					.getExpiration()
					.before(new Date(System.currentTimeMillis()));
		}catch(Exception E)
		{
			return true;
		}
	}
	private static Map<String, Object> getRSAKeys() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        Map<String, Object> keys = new HashMap<String, Object>();
        keys.put("private", privateKey);
        keys.put("public", publicKey);
        return keys;
    }
}
