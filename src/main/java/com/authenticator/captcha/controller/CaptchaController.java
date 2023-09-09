package com.authenticator.captcha.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authenticator.authenticate.dto.response.ServiceResDTO;
import com.authenticator.authenticate.util.AuthValCheckEnum;
import com.authenticator.captcha.dto.request.ValidateCaptchaReqDTO;
import com.authenticator.captcha.dto.response.GenerateCaptchaResDTO;
import com.authenticator.captcha.service.CaptchaHistoryService;
import com.authenticator.exception.ApplicationException;

/**
 * @author vinay.rajagopalan
 */
@RestController
@RequestMapping(value = "/captcha")
public class CaptchaController {
	
	private static final Logger LOGGER = LogManager.getLogger(CaptchaController.class);

	@Autowired
	private CaptchaHistoryService capHistoryService;

	@PostMapping(value = "/1.0.0/generateCaptcha")
	public ServiceResDTO generateCaptcha() {
		try {
			GenerateCaptchaResDTO generateCaptchaRes = capHistoryService.generateCatcha();
			return new ServiceResDTO.ServiceResponseBuilder().setStatus(true).setResponse(generateCaptchaRes).build();
		} catch (ApplicationException e) {
			LOGGER.error("Customized Exception occured  exception : {} ", e);
			return new ServiceResDTO.ServiceResponseBuilder().setStatus(false).setStatusCode(e.getErrorCode()).setStatusMessage(e.getMessage()).build();
		} catch (Exception e) {
			LOGGER.error("Internal server exception occured : {} ", e);
			return new ServiceResDTO.ServiceResponseBuilder().setStatus(false).setStatusCode(AuthValCheckEnum.API_AUTH_ERR_010.getErrroCode()).setStatusMessage(AuthValCheckEnum.API_AUTH_ERR_010.getErrorMessage()).build();
		}
	}
	
	
	@PostMapping(value = "/1.0.0/validateCaptcha")
	public ServiceResDTO validateCaptcha(@RequestBody @Valid ValidateCaptchaReqDTO valiaCaptchaReqDTO) {
		try {
			Boolean status = capHistoryService.validateCaptcha(valiaCaptchaReqDTO);
			return new ServiceResDTO.ServiceResponseBuilder().setStatus(status).setResponse(valiaCaptchaReqDTO).build();
		} catch (ApplicationException e) {
			LOGGER.error("Customized Exception occured  exception : {} ", e);
			return new ServiceResDTO.ServiceResponseBuilder().setStatus(false).setStatusCode(e.getErrorCode()).setStatusMessage(e.getMessage()).build();
		} catch (Exception e) {
			LOGGER.error("Internal server exception occured : {} ", e);
			return new ServiceResDTO.ServiceResponseBuilder().setStatus(false).setStatusCode(AuthValCheckEnum.API_AUTH_ERR_010.getErrroCode()).setStatusMessage(AuthValCheckEnum.API_AUTH_ERR_010.getErrorMessage()).build();
		}
	}

}
