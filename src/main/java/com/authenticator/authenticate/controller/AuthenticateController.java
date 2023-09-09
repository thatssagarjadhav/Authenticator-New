/**
 * 
 */
package com.authenticator.authenticate.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authenticator.authenticate.dto.request.AuthUserReqDTO;
import com.authenticator.authenticate.dto.request.DestroyTokenReqDTO;
import com.authenticator.authenticate.dto.request.UserMenuRoleDetailsReqDTO;
import com.authenticator.authenticate.dto.request.ValidateTokenReqDTO;
import com.authenticator.authenticate.dto.response.AuthUserResDTO;
import com.authenticator.authenticate.dto.response.ServiceResDTO;
import com.authenticator.authenticate.dto.response.UserMenuRoleDetailsResDTO;
import com.authenticator.authenticate.dto.response.ValidateTokenResDTO;
import com.authenticator.authenticate.service.AuthenticatorService;
import com.authenticator.authenticate.util.AuthValCheckEnum;
import com.authenticator.exception.ApplicationException;

/**
 * @author sagar.jadhav
 *
 */
@RestController
@RequestMapping(value = "/authenticate")
public class AuthenticateController {

	private static final Logger LOGGER = LogManager.getLogger(AuthenticateController.class);

	@Autowired
	private AuthenticatorService authenticatorService;

	@PostMapping(value = "/v1/authenticateUser")
	public ServiceResDTO authenticateUser(@RequestBody @Valid AuthUserReqDTO authUserReqDTO) {
		try {
			AuthUserResDTO authUserResDTO = authenticatorService.authenticateUserAndSendPlatformDetails(authUserReqDTO);
			return new ServiceResDTO.ServiceResponseBuilder().setStatus(true).setResponse(authUserResDTO).build();
		} catch (ApplicationException e) {
			LOGGER.error("Customized Exception occured  exception : {} ", e);
			return new ServiceResDTO.ServiceResponseBuilder().setStatus(false).setStatusCode(e.getErrorCode()).setStatusMessage(e.getMessage()).build();
		} catch (Exception e) {
			LOGGER.error("Internal server exception occured : {} ", e);
			return new ServiceResDTO.ServiceResponseBuilder().setStatus(false).setStatusCode(AuthValCheckEnum.API_AUTH_ERR_010.getErrroCode()).setStatusMessage(AuthValCheckEnum.API_AUTH_ERR_010.getErrorMessage()).build();
		}
	}

	@PostMapping(value = "/v1/validateToken")
	public ServiceResDTO validateToken(@RequestBody ValidateTokenReqDTO validateTokenReqDTO) {
		ValidateTokenResDTO validateTokenResDTO = new ValidateTokenResDTO();
		validateTokenResDTO.setIsValidToken(Boolean.TRUE);
		return new ServiceResDTO.ServiceResponseBuilder().setStatus(true).setResponse(validateTokenResDTO).build();
	}

	@PostMapping(value = "/v1/destroyToken")
	public ServiceResDTO destroyToken(@RequestBody DestroyTokenReqDTO destroyTokenReqDTO) {
		//		ValidateTokenResDTO validateTokenResDTO = new ValidateTokenResDTO();
		//		validateTokenResDTO.setIsValidToken(Boolean.TRUE);
		//		List<UserMaster> userMasterList =  passwordUtilityRepo.findAll();
		//		System.out.println(userMasterList.size());
		return new ServiceResDTO.ServiceResponseBuilder().setStatus(true).build();
	}

	@PostMapping(value = "/v1/getUserMenuRoleDetails")
	public ServiceResDTO getUserMenuRoleDetails(@RequestBody UserMenuRoleDetailsReqDTO userMenuRoleDetailsReqDTO) {
		try {
			UserMenuRoleDetailsResDTO userMenuRoleDetailsResDTO = authenticatorService.getUserMenuRoleDetails(userMenuRoleDetailsReqDTO);
			return new ServiceResDTO.ServiceResponseBuilder().setStatus(true).setResponse(userMenuRoleDetailsResDTO).build();
		} catch (ApplicationException e) {
			LOGGER.error("Customized Exception occured  exception : {} ", e);
			return new ServiceResDTO.ServiceResponseBuilder().setStatus(false).setStatusCode(e.getErrorCode()).setStatusMessage(e.getMessage()).build();
		} catch (Exception e) {
			LOGGER.error("Internal server exception occured : {} ", e);
			return new ServiceResDTO.ServiceResponseBuilder().setStatus(false).setStatusCode(AuthValCheckEnum.API_AUTH_ERR_010.getErrroCode()).setStatusMessage(AuthValCheckEnum.API_AUTH_ERR_010.getErrorMessage()).build();
		}
	}

}
