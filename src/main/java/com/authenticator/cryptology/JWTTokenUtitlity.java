/**
 * 
 */
package com.authenticator.cryptology;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author sagar.jadhav
 *
 */
@Component
public class JWTTokenUtitlity {

	private static final Logger LOGGER = LogManager.getLogger(JWTTokenUtitlity.class);

	private PublicKey publicKey;

	private PrivateKey privateKey;

	public JWTTokenUtitlity() throws Exception {
		LOGGER.info("In JWT Token Utitlity");

		Path path = Paths.get(this.getClass().getResource("/keys/publicKey").toURI());
		List<String> publicKeyContents = Files.readAllLines(path);

		path = Paths.get(this.getClass().getResource("/keys/privateKey").toURI());
		List<String> privateKeyContents = Files.readAllLines(path);

		publicKey = getPublicKey(publicKeyContents.get(1));
		privateKey = getPrivateKey(privateKeyContents.get(1));
	}

	public String generateJwtToken(Map<String, Object> payloadMap) throws Exception {
		ObjectMapper Obj = new ObjectMapper();
		JwtBuilder builder = Jwts.builder().setPayload(Obj.writeValueAsString(payloadMap)).signWith(SignatureAlgorithm.RS256, privateKey);

		String token = builder.compact();
		return token;
	}

	// Print structure of JWT
	public Map<String, Object> getValueFromToken(String token) {
		Jws<Claims> parseClaimsJws = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
		return parseClaimsJws.getBody();
	}

	private PublicKey getPublicKey(String publicKeyContents) throws Exception {
		byte[] byteKey = Base64.getDecoder().decode(publicKeyContents);
		X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
		KeyFactory kf = KeyFactory.getInstance("RSA");

		return kf.generatePublic(X509publicKey);
	}

	private PrivateKey getPrivateKey(String privateKeyContents) throws Exception {
		byte[] byteKey = Base64.getDecoder().decode(privateKeyContents);
		PKCS8EncodedKeySpec X509publicKey = new PKCS8EncodedKeySpec(byteKey);
		KeyFactory kf = KeyFactory.getInstance("RSA");

		return kf.generatePrivate(X509publicKey);
	}
}
