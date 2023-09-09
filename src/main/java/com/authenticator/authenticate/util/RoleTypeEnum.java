/**
 * 
 */
package com.authenticator.authenticate.util;

/**
 * @author sagar.jadhav
 *
 */
public enum RoleTypeEnum {

	ENTITY_ROLE_TYPE(2, "grid.roleType.entity"), REGULATOR_ROLE_TYPE(1, "grid.roleType.regulator");

	private Integer roleTypeId;

	private String roleType;

	private RoleTypeEnum(Integer roleTypeId, String roleType) {
		this.roleTypeId = roleTypeId;
		this.roleType = roleType;
	}

	/**
	 * @return the roleTypeId
	 */
	public Integer getRoleTypeId() {
		return roleTypeId;
	}

	/**
	 * @return the roleType
	 */
	public String getRoleType() {
		return roleType;
	}

}
