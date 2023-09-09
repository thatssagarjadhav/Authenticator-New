/**
 * 
 */
package com.authenticator.authenticate.dto.request;

import java.io.Serializable;

/**
 * @author sagar.jadhav
 *
 */
public class DestroyTokenReqDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1778589955659359687L;

	private String userName;

	private String accessToken;

	private HttpDetailsReqDTO httpReqDetailsDTO;

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
	 * @return the httpReqDetailsDTO
	 */
	public HttpDetailsReqDTO getHttpReqDetailsDTO() {
		return httpReqDetailsDTO;
	}

	/**
	 * @param httpReqDetailsDTO the httpReqDetailsDTO to set
	 */
	public void setHttpReqDetailsDTO(HttpDetailsReqDTO httpReqDetailsDTO) {
		this.httpReqDetailsDTO = httpReqDetailsDTO;
	}

}
