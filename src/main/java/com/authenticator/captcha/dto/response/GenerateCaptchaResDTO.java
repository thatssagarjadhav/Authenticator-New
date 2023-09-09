package com.authenticator.captcha.dto.response;

import java.io.Serializable;

/**
 * @author vinay.rajagopalan
 */
public class GenerateCaptchaResDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5351186996379167147L;
	private String value;
	private String identifier;
	
	/**
	 * 
	 * @return value of the captcha
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * 
	 * @param value for the Captcha
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * 
	 * @return identifier
	 */
	public String getIdentifier() {
		return identifier;
	}
	
	/**
	 * 
	 * @param identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	

}
