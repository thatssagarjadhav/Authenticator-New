/**
 * 
 */
package com.authenticator.authenticate.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.authenticator.authenticate.util.ErrorConstant;

/**
 * @author sagar.jadhav
 *
 */
public class UserMenuRoleDetailsReqDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6722088119761844200L;

	@NotNull(message = ErrorConstant.HTTP_REQUEST_DETAILS_MANDATORY)
	private HttpDetailsReqDTO httpReqDetails;

	@NotNull(message = ErrorConstant.ACCESS_TOKEN_MANDATORY)
	private String accessToken;

	@NotNull(message = ErrorConstant.USERNAME_MANDATORY)
	private String userName;

	@NotNull(message = ErrorConstant.PLATFORM_CODE_MANDATORY)
	private String platformCode;
	
	
	@NotNull(message = ErrorConstant.LANGUAGECODE_MANDATORY)
	private String langCode;

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
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
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
	 * @return the platformCode
	 */
	public String getPlatformCode() {
		return platformCode;
	}

	/**
	 * @param platformCode the platformCode to set
	 */
	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
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
