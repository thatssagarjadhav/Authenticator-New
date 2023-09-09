/**
 * 
 */
package com.authenticator.authenticate.dto.response;

import java.io.Serializable;
import java.util.List;

/**
 * @author sagar.jadhav
 *
 */
public class AuthUserResDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7819830375426656072L;

	private String accessToken;

	private Integer userId;

	private String userName;

	private Integer roleType;

	private String platformSelectMessage;

	private List<PlatformResDTO> platformList;

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
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	 * @return the roleType
	 */
	public Integer getRoleType() {
		return roleType;
	}

	/**
	 * @param roleType the roleType to set
	 */
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	/**
	 * @return the platformSelectMessage
	 */
	public String getPlatformSelectMessage() {
		return platformSelectMessage;
	}

	/**
	 * @param platformSelectMessage the platformSelectMessage to set
	 */
	public void setPlatformSelectMessage(String platformSelectMessage) {
		this.platformSelectMessage = platformSelectMessage;
	}

	/**
	 * @return the platformList
	 */
	public List<PlatformResDTO> getPlatformList() {
		return platformList;
	}

	/**
	 * @param platformList the platformList to set
	 */
	public void setPlatformList(List<PlatformResDTO> platformList) {
		this.platformList = platformList;
	}

}
