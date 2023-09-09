/**
 * 
 */
package com.authenticator.authenticate.dto.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author sagar.jadhav
 *
 */

public class UserMenuRoleDetailsResDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2423672487518254961L;

	@JsonInclude(Include.NON_NULL)
	private String userName;

	@JsonInclude(Include.NON_NULL)
	private String accessToken;

	@JsonInclude(Include.NON_NULL)
	private Integer tokenExpiryTimeInSeconds;

	@JsonInclude(Include.NON_NULL)
	private String userEmailAdd;

	@JsonInclude(Include.NON_NULL)
	private Integer roleType;

	@JsonInclude(Include.NON_NULL)
	private String firstName;

	@JsonInclude(Include.NON_NULL)
	private String lastName;

	@JsonInclude(Include.NON_NULL)
	private String phoneNumber;

	@JsonInclude(Include.NON_NULL)
	private EntityResDTO entity;

	@JsonInclude(Include.NON_NULL)
	private DepartmentResDTO department;

	@JsonInclude(Include.NON_NULL)
	private List<PlatformResDTO> platformList;

	@JsonInclude(Include.NON_NULL)
	private List<RoleResDTO> roleList;

	@JsonInclude(Include.NON_NULL)
	private List<PlatformMenuResponseDto> menuList;

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
	 * @return the tokenExpiryTimeInSeconds
	 */
	public Integer getTokenExpiryTimeInSeconds() {
		return tokenExpiryTimeInSeconds;
	}

	/**
	 * @param tokenExpiryTimeInSeconds the tokenExpiryTimeInSeconds to set
	 */
	public void setTokenExpiryTimeInSeconds(Integer tokenExpiryTimeInSeconds) {
		this.tokenExpiryTimeInSeconds = tokenExpiryTimeInSeconds;
	}

	/**
	 * @return the userEmailAdd
	 */
	public String getUserEmailAdd() {
		return userEmailAdd;
	}

	/**
	 * @param userEmailAdd the userEmailAdd to set
	 */
	public void setUserEmailAdd(String userEmailAdd) {
		this.userEmailAdd = userEmailAdd;
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
	 * @return the entity
	 */
	public EntityResDTO getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(EntityResDTO entity) {
		this.entity = entity;
	}

	/**
	 * @return the department
	 */
	public DepartmentResDTO getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(DepartmentResDTO department) {
		this.department = department;
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

	/**
	 * @return the roleList
	 */
	public List<RoleResDTO> getRoleList() {
		return roleList;
	}

	/**
	 * @param roleList the roleList to set
	 */
	public void setRoleList(List<RoleResDTO> roleList) {
		this.roleList = roleList;
	}

	/**
	 * @return the menuList
	 */
	public List<PlatformMenuResponseDto> getMenuList() {
		return menuList;
	}

	/**
	 * @param menuList the menuList to set
	 */
	public void setMenuList(List<PlatformMenuResponseDto> menuList) {
		this.menuList = menuList;
	}

}
