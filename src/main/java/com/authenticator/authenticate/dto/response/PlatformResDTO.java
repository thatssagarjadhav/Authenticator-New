/**
 * 
 */
package com.authenticator.authenticate.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author sagar.jadhav
 *
 */
public class PlatformResDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5155970022687507599L;

	@JsonInclude(Include.NON_NULL)
	private String platformCode;

	@JsonInclude(Include.NON_NULL)
	private String platformName;

	@JsonInclude(Include.NON_NULL)
	private String platformActionUrl;

	@JsonInclude(Include.NON_NULL)
	private String platformMessage;

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
	 * @return the platformName
	 */
	public String getPlatformName() {
		return platformName;
	}

	/**
	 * @param platformName the platformName to set
	 */
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	/**
	 * @return the platformActionUrl
	 */
	public String getPlatformActionUrl() {
		return platformActionUrl;
	}

	/**
	 * @param platformActionUrl the platformActionUrl to set
	 */
	public void setPlatformActionUrl(String platformActionUrl) {
		this.platformActionUrl = platformActionUrl;
	}

	/**
	 * @return the platformMessage
	 */
	public String getPlatformMessage() {
		return platformMessage;
	}

	/**
	 * @param platformMessage the platformMessage to set
	 */
	public void setPlatformMessage(String platformMessage) {
		this.platformMessage = platformMessage;
	}

}
