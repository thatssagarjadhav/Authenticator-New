package com.authenticator.authenticate.dto.custom;

import java.io.Serializable;

public class UserRolePlatformRepoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8152385757607129068L;

	private Integer userId;

	private String userName;

	private Integer roleType;

	private String firstName;

	private String lastName;

	private String phoneNumber;

	private String userRole;

	private Integer userRoleId;

	private String platformName;

	private Integer platformId;

	private String entityName;

	private String entityCode;

	private String regulatorName;

	private String regulatorCode;

	private Boolean isMainDept;

	private Boolean isAdminRole;

	private String entityUserEmail;

	private String deptUserEmail;

	private String platFormCode;

	private String platFormActionUrl;

	private String platFormMessage;

	private String userRoleCode;

	public UserRolePlatformRepoDTO(Long userId, String userName) {

	}

	public UserRolePlatformRepoDTO(Long userId, String userName, Long roleType, String firstName, String lastName, String userRole, Long userRoleId, String platformName, Integer platformId, String entityName, String entityCode, String regulatorName, String regulatorCode, String entityUserEmail, String deptUserEmail, String platFormCode, String platFormActionUrl, String platFormMessage, String phoneNumber, Boolean isMainDept, Boolean isAdminRole, String roleCode) {
		super();
		this.userId = userId.intValue();
		this.userName = userName;
		this.roleType = roleType.intValue();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userRole = userRole;
		this.userRoleId = userRoleId.intValue();

		this.platformName = platformName;
		this.platformId = platformId;
		this.platFormCode = platFormCode;
		this.platFormActionUrl = platFormActionUrl;
		this.platFormMessage = platFormMessage;
		this.entityName = entityName;
		this.entityCode = entityCode;
		this.regulatorName = regulatorName;
		this.regulatorCode = regulatorCode;
		this.entityUserEmail = entityUserEmail;
		this.deptUserEmail = deptUserEmail;
		this.phoneNumber = phoneNumber;
		this.isMainDept = isMainDept;
		this.isAdminRole = isAdminRole;
		this.userRoleCode = roleCode;
	}

	/**
	 * @return the roleCode
	 */
	public String getUserRoleCode() {
		return userRoleCode;
	}

	/**
	 * @param roleCode the roleCode to set
	 */
	public void setUserRoleCode(String roleCode) {
		this.userRoleCode = roleCode;
	}

	/**
	 * @return the isAdminRole
	 */
	public Boolean getIsAdminRole() {
		return isAdminRole;
	}

	/**
	 * @param isAdminRole the isAdminRole to set
	 */
	public void setIsAdminRole(Boolean isAdminRole) {
		this.isAdminRole = isAdminRole;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the isMainDept
	 */
	public Boolean getIsMainDept() {
		return isMainDept;
	}

	/**
	 * @param isMainDept the isMainDept to set
	 */
	public void setIsMainDept(Boolean isMainDept) {
		this.isMainDept = isMainDept;
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
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the userRole
	 */
	public String getUserRole() {
		return userRole;
	}

	/**
	 * @param userRole the userRole to set
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	/**
	 * @return the userRoleId
	 */
	public Integer getUserRoleId() {
		return userRoleId;
	}

	/**
	 * @param userRoleId the userRoleId to set
	 */
	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
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
	 * @return the platformId
	 */
	public Integer getPlatformId() {
		return platformId;
	}

	/**
	 * @param platformId the platformId to set
	 */
	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	/**
	 * @return the entityName
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * @param entityName the entityName to set
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	/**
	 * @return the entityCode
	 */
	public String getEntityCode() {
		return entityCode;
	}

	/**
	 * @param entityCode the entityCode to set
	 */
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	/**
	 * @return the regulatorName
	 */
	public String getRegulatorName() {
		return regulatorName;
	}

	/**
	 * @param regulatorName the regulatorName to set
	 */
	public void setRegulatorName(String regulatorName) {
		this.regulatorName = regulatorName;
	}

	/**
	 * @return the regulatorCode
	 */
	public String getRegulatorCode() {
		return regulatorCode;
	}

	/**
	 * @param regulatorCode the regulatorCode to set
	 */
	public void setRegulatorCode(String regulatorCode) {
		this.regulatorCode = regulatorCode;
	}

	/**
	 * @return the entityUserEmail
	 */
	public String getEntityUserEmail() {
		return entityUserEmail;
	}

	/**
	 * @param entityUserEmail the entityUserEmail to set
	 */
	public void setEntityUserEmail(String entityUserEmail) {
		this.entityUserEmail = entityUserEmail;
	}

	/**
	 * @return the deptUserEmail
	 */
	public String getDeptUserEmail() {
		return deptUserEmail;
	}

	/**
	 * @param deptUserEmail the deptUserEmail to set
	 */
	public void setDeptUserEmail(String deptUserEmail) {
		this.deptUserEmail = deptUserEmail;
	}

	/**
	 * @return the platFormCode
	 */
	public String getPlatFormCode() {
		return platFormCode;
	}

	/**
	 * @param platFormCode the platFormCode to set
	 */
	public void setPlatFormCode(String platFormCode) {
		this.platFormCode = platFormCode;
	}

	/**
	 * @return the platFormActionUrl
	 */
	public String getPlatFormActionUrl() {
		return platFormActionUrl;
	}

	/**
	 * @param platFormActionUrl the platFormActionUrl to set
	 */
	public void setPlatFormActionUrl(String platFormActionUrl) {
		this.platFormActionUrl = platFormActionUrl;
	}

	/**
	 * @return the platFormMessage
	 */
	public String getPlatFormMessage() {
		return platFormMessage;
	}

	/**
	 * @param platFormMessage the platFormMessage to set
	 */
	public void setPlatFormMessage(String platFormMessage) {
		this.platFormMessage = platFormMessage;
	}

}
