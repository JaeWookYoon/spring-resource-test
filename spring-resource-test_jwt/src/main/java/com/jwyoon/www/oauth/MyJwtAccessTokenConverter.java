package com.jwyoon.www.oauth;

import java.util.Map;

import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

public class MyJwtAccessTokenConverter extends JwtAccessTokenConverter{
	
	private String verifierKey = "encode";

	private Signer signer = new MacSigner(verifierKey);

	private String signingKey = verifierKey;
	
	public Map<String, Object> decode(String token) {
		return super.decode(token);
	}
}
