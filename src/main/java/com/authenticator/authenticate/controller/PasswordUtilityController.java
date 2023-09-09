/**
 * 
 */
package com.authenticator.authenticate.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authenticator.authenticate.dto.request.ResetPasswordReqDTO;
import com.authenticator.authenticate.dto.response.ServiceResDTO;
import com.authenticator.authenticate.service.PasswordUtilityService;
import com.authenticator.exception.ApplicationException;

/**
 * @author sagar.jadhav
 *
 */
@RestController
@RequestMapping(value = "/accessMng")
public class PasswordUtilityController {
	static final Logger LOGGER = LogManager.getLogger(PasswordUtilityController.class);

	@Autowired
	private PasswordUtilityService passwordUtilityService;

	@PostMapping(value = "/v1/resetAccess")
	public ServiceResDTO resetPassword(@RequestBody ResetPasswordReqDTO resetPwdReqDTO) {
		try {
			passwordUtilityService.resetUserPassword(resetPwdReqDTO);
			return new ServiceResDTO.ServiceResponseBuilder().setStatus(true).build();
		} catch (ApplicationException e) {
			e.printStackTrace();
			return new ServiceResDTO.ServiceResponseBuilder().setStatus(false).build();
		} catch (Exception e) {
			e.printStackTrace();
			return new ServiceResDTO.ServiceResponseBuilder().setStatus(false).build();
		}
	}
}
