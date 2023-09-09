/**
 * 
 */
package com.authenticator.authenticate.util;

/**
 * @author sagar.jadhav
 *
 */
public enum AuthValCheckEnum {

	API_AUTH_ERR_001("API_AUTH_ERR_001", "AppId is mandatory to be provided"), API_AUTH_ERR_002("API_AUTH_ERR_001", "Invalid AppId provided"), API_AUTH_ERR_003("API_AUTH_ERR_001", "httpReqDetails is mandatory to be provided"), API_AUTH_ERR_004("API_AUTH_ERR_001", "userName is mandatory to be provided"), API_AUTH_ERR_005("API_AUTH_ERR_001", "password is mandatory to be provided"), API_AUTH_ERR_006("API_AUTH_ERR_001", "langCode should match with the value present in the application"), API_AUTH_ERR_007("API_AUTH_ERR_001", "User authentication failed."), API_AUTH_ERR_010("API_AUTH_ERR_010", "Internal Server Error"), API_AUTH_ERROR_011("API_AUTH_ERR_011","Captcha Code Mismatch Error"), API_AUTH_ERROR_012("API_AUTH_ERR_012","Captcha Code Expired Error");

	private String errroCode;

	private String errorMessage;

	private AuthValCheckEnum(String errroCode, String errorMessage) {
		this.errroCode = errroCode;
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the errroCode
	 */
	public String getErrroCode() {
		return errroCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

}
