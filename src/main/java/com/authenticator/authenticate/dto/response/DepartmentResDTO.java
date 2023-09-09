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
public class DepartmentResDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2244638410182320974L;

	@JsonInclude(Include.NON_NULL)
	private String deptCode;

	@JsonInclude(Include.NON_NULL)
	private String deptName;

	@JsonInclude(Include.NON_NULL)
	private Boolean isMainDept;

	/**
	 * @return the deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * @param deptCode the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
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

}
