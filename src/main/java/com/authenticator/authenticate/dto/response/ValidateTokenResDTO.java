/**
 * 
 */
package com.authenticator.authenticate.dto.response;

import java.io.Serializable;

/**
 * @author sagar.jadhav
 *
 */
public class ValidateTokenResDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6256786430074213676L;

	private Boolean isValidToken;

	/**
	 * @return the isValidToken
	 */
	public Boolean getIsValidToken() {
		return isValidToken;
	}

	/**
	 * @param isValidToken the isValidToken to set
	 */
	public void setIsValidToken(Boolean isValidToken) {
		this.isValidToken = isValidToken;
	}

}
