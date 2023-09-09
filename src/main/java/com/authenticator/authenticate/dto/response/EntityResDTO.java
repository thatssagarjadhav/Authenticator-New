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
public class EntityResDTO implements Serializable {

	@JsonInclude(Include.NON_NULL)
	private String entityCode;

	@JsonInclude(Include.NON_NULL)
	private String entityName;

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

}
