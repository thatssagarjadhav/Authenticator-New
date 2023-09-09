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
public class HttpDetailsReqDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6696337617998437083L;

	@NotNull(message = ErrorConstant.CLIENTBROWSER_MANDATAORY)
	private String clientBrowser;

	@NotNull(message = ErrorConstant.CLIENT_IP_ADDRESS_MANDATAORY)
	private String clientIPAddr;

	@NotNull(message = ErrorConstant.CLIENT_OS_MANDATAORY)
	private String clientOS;

	@NotNull(message = ErrorConstant.FULL_URL_MANDATAORY)
	private String fullURL;

	@NotNull(message = ErrorConstant.REFERNCE_MANDATAORY)
	private String referer;

	@NotNull(message = ErrorConstant.USER_AGENT_MANDATAORY)
	private String userAgent;

	/**
	 * @return the clientBrowser
	 */
	public String getClientBrowser() {
		return clientBrowser;
	}

	/**
	 * @param clientBrowser the clientBrowser to set
	 */
	public void setClientBrowser(String clientBrowser) {
		this.clientBrowser = clientBrowser;
	}

	/**
	 * @return the clientIPAddr
	 */
	public String getClientIPAddr() {
		return clientIPAddr;
	}

	/**
	 * @param clientIPAddr the clientIPAddr to set
	 */
	public void setClientIPAddr(String clientIPAddr) {
		this.clientIPAddr = clientIPAddr;
	}

	/**
	 * @return the clientOS
	 */
	public String getClientOS() {
		return clientOS;
	}

	/**
	 * @param clientOS the clientOS to set
	 */
	public void setClientOS(String clientOS) {
		this.clientOS = clientOS;
	}

	/**
	 * @return the fullURL
	 */
	public String getFullURL() {
		return fullURL;
	}

	/**
	 * @param fullURL the fullURL to set
	 */
	public void setFullURL(String fullURL) {
		this.fullURL = fullURL;
	}

	/**
	 * @return the referer
	 */
	public String getReferer() {
		return referer;
	}

	/**
	 * @param referer the referer to set
	 */
	public void setReferer(String referer) {
		this.referer = referer;
	}

	/**
	 * @return the userAgent
	 */
	public String getUserAgent() {
		return userAgent;
	}

	/**
	 * @param userAgent the userAgent to set
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

}
