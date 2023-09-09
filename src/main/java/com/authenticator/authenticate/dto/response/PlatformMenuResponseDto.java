package com.authenticator.authenticate.dto.response;

/**
 * 
 */

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author sagar.jadhav
 *
 */
public class PlatformMenuResponseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6620799136718295846L;

	@JsonInclude(Include.NON_NULL)
	private String menuName;

	@JsonInclude(Include.NON_NULL)
	private Float orderNo;

	@JsonInclude(Include.NON_NULL)
	private String actionName;

	@JsonInclude(Include.NON_NULL)
	private String menuIcon;

	@JsonInclude(Include.NON_NULL)
	private Boolean isApplicableForDept;

	@JsonInclude(Include.NON_NULL)
	private Boolean isApplicableForEnt;

	@JsonInclude(Include.NON_NULL)
	private Boolean isApplicableForAudt;

	@JsonInclude(Include.NON_NULL)
	private List<PlatformMenuResponseDto> childMenus;

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj.getClass() == this.getClass()) {
			PlatformMenuResponseDto platformMenuMasterDto = (PlatformMenuResponseDto) obj;
			if (platformMenuMasterDto.getActionName().equalsIgnoreCase(this.actionName) && platformMenuMasterDto.getMenuName().equalsIgnoreCase(this.menuName)) {
				return true;
			}
		}
		return false;
	}

	//	@Override
	//	public int hashCode() {
	//		return this.getSubCategoryId().hashCode();
	//	}

	@Override
	public int hashCode() {
		return this.actionName.hashCode() + this.menuName.hashCode();
	}

	/**
	 * @return the menuName
	 */
	public String getMenuName() {
		return menuName;
	}

	/**
	 * @param menuName the menuName to set
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**
	 * @return the orderNo
	 */
	public Float getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(Float orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the actionName
	 */
	public String getActionName() {
		return actionName;
	}

	/**
	 * @param actionName the actionName to set
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	/**
	 * @return the menuIcon
	 */
	public String getMenuIcon() {
		return menuIcon;
	}

	/**
	 * @param menuIcon the menuIcon to set
	 */
	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	/**
	 * @return the isApplicableForDept
	 */
	public Boolean getIsApplicableForDept() {
		return isApplicableForDept;
	}

	/**
	 * @param isApplicableForDept the isApplicableForDept to set
	 */
	public void setIsApplicableForDept(Boolean isApplicableForDept) {
		this.isApplicableForDept = isApplicableForDept;
	}

	/**
	 * @return the isApplicableForEnt
	 */
	public Boolean getIsApplicableForEnt() {
		return isApplicableForEnt;
	}

	/**
	 * @param isApplicableForEnt the isApplicableForEnt to set
	 */
	public void setIsApplicableForEnt(Boolean isApplicableForEnt) {
		this.isApplicableForEnt = isApplicableForEnt;
	}

	/**
	 * @return the isApplicableForAudt
	 */
	public Boolean getIsApplicableForAudt() {
		return isApplicableForAudt;
	}

	/**
	 * @param isApplicableForAudt the isApplicableForAudt to set
	 */
	public void setIsApplicableForAudt(Boolean isApplicableForAudt) {
		this.isApplicableForAudt = isApplicableForAudt;
	}

	/**
	 * @return the childMenus
	 */
	public List<PlatformMenuResponseDto> getChildMenus() {
		return childMenus;
	}

	/**
	 * @param childMenus the childMenus to set
	 */
	public void setChildMenus(List<PlatformMenuResponseDto> childMenus) {
		this.childMenus = childMenus;
	}

}
