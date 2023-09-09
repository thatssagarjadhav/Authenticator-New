/**
 * 
 */
package com.authenticator.authenticate.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.authenticator.authenticate.util.ErrorConstant;
import com.authenticator.captcha.dto.request.ValidateCaptchaReqDTO;

/**
 * @author sagar.jadhav
 *
 */
public class AuthUserReqDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3653024246092734352L;

	@NotNull(message = ErrorConstant.HTTP_REQUEST_DETAILS_MANDATORY)
	private HttpDetailsReqDTO httpReqDetails;

	@NotNull(message = ErrorConstant.PASSWORD_MANDATORY)
	private String password;

	@NotNull(message = ErrorConstant.USERNAME_MANDATORY)
	private String userName;

	@NotNull(message = ErrorConstant.LANGUAGECODE_MANDATORY)
	private String langCode;
	
	@NotNull(message = ErrorConstant.CAPTCHA_VALUE_MANDATATORY)
	private ValidateCaptchaReqDTO captchaDetails;
	
	
	/**
	 * @return the captchaDetails
	 */
	public ValidateCaptchaReqDTO getCaptchaDetails() {
		return captchaDetails;
	}

	/**
	 * @param captchaDetails the captchaDetails to set
	 */
	public void setCaptchaDetails(ValidateCaptchaReqDTO captchaDetails) {
		this.captchaDetails = captchaDetails;
	}

	/**
	 * @return the httpReqDetails
	 */
	public HttpDetailsReqDTO getHttpReqDetails() {
		return httpReqDetails;
	}

	/**
	 * @param httpReqDetails the httpReqDetails to set
	 */
	public void setHttpReqDetails(HttpDetailsReqDTO httpReqDetails) {
		this.httpReqDetails = httpReqDetails;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the langCode
	 */
	public String getLangCode() {
		return langCode;
	}

	/**
	 * @param langCode the langCode to set
	 */
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

}
