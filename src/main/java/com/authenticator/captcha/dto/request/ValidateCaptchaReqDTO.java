package com.authenticator.captcha.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.authenticator.authenticate.util.ErrorConstant;

public class ValidateCaptchaReqDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5034298839726729046L;
	
	@NotNull(message = ErrorConstant.CAPTCHA_VALUE_MANDATATORY)
	private String value;
	
	@NotNull(message = "Captcha Identifier is mandatory")
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
