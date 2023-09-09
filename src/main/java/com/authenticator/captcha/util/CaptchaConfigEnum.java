package com.authenticator.captcha.util;

/**
 * @author vinay.rajagopalan
 */
public enum CaptchaConfigEnum {
	
	SIX(6);

	private int value;


	private CaptchaConfigEnum(int value) {
		this.value = value;
	}


	/**
	 * 
	 * @return value associated with the string
	 */
	public int getValue() {
		return value;
	}

}
