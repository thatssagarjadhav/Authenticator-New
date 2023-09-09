/**
 * 
 */
package com.authenticator.authenticate.dto.request;

import java.io.Serializable;

/**
 * @author sagar.jadhav
 *
 */
public class ResetPasswordReqDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7908527464163128252L;

	private String userName;

	private Integer securityQuesId;

	private String securityQuesAns;

	private String newPassword;

	private String resetPasswordLink;

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
	 * @return the securityQuesId
	 */
	public Integer getSecurityQuesId() {
		return securityQuesId;
	}

	/**
	 * @param securityQuesId the securityQuesId to set
	 */
	public void setSecurityQuesId(Integer securityQuesId) {
		this.securityQuesId = securityQuesId;
	}

	/**
	 * @return the securityQuesAns
	 */
	public String getSecurityQuesAns() {
		return securityQuesAns;
	}

	/**
	 * @param securityQuesAns the securityQuesAns to set
	 */
	public void setSecurityQuesAns(String securityQuesAns) {
		this.securityQuesAns = securityQuesAns;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the resetPasswordLink
	 */
	public String getResetPasswordLink() {
		return resetPasswordLink;
	}

	/**
	 * @param resetPasswordLink the resetPasswordLink to set
	 */
	public void setResetPasswordLink(String resetPasswordLink) {
		this.resetPasswordLink = resetPasswordLink;
	}

}
