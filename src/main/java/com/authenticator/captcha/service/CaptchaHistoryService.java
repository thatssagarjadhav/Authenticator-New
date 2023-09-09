package com.authenticator.captcha.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.authenticator.authenticate.util.AuthValCheckEnum;
import com.authenticator.captcha.dto.request.ValidateCaptchaReqDTO;
import com.authenticator.captcha.dto.response.GenerateCaptchaResDTO;
import com.authenticator.captcha.repo.CaptchaHistoryRepo;
import com.authenticator.captcha.util.CaptchaConfigConst;
import com.authenticator.captcha.util.CaptchaConfigEnum;
import com.authenticator.config.UserRegulatorConf;
import com.authenticator.cryptology.SHAHashingUtil;
import com.authenticator.exception.ApplicationException;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.hibernate.model.CaptchaHistory;

/**
 * @author vinay.rajagopalan
 */
@Service
public class CaptchaHistoryService {
	
	@Autowired
	private CaptchaHistoryRepo captchaHistoryRepo;
	
	@Autowired
	private UserRegulatorConf userRegulatorConf;
	
	private Producer captchaProducer;
	
	
	public GenerateCaptchaResDTO generateCatcha() throws Exception {
		int length = CaptchaConfigEnum.SIX.getValue();
		String captchaText = RandomStringUtils.random(length, CaptchaConfigConst.USELETTERS, CaptchaConfigConst.USENUMBERS);
		
		//Get the currentDateTime and set expiry as required
		Instant currentDateTime = Instant.now();
		// Confirm the method to call for obtaining the timeout in seconds.
		long expirySeconds = (long)userRegulatorConf.getCaptchaExpiry();
		long currentDateTimeLong =  currentDateTime.toEpochMilli();
		long expiryTimeLong = currentDateTimeLong + expirySeconds*10000;
		Instant expiryDateTime =  Instant.ofEpochMilli(expiryTimeLong);
		
		// Concat Captcha String and expiry dateTime
		StringBuilder sb = new StringBuilder();
		sb.append(captchaText);
		sb.append(expiryTimeLong);
		String concatedText = sb.toString();
		String concatedTextHash = null;
		
		try {
			concatedTextHash = SHAHashingUtil.hashUsingSHA256(concatedText);
		} catch (Exception e) {
			ApplicationException applicationException = new ApplicationException(AuthValCheckEnum.API_AUTH_ERR_010.getErrroCode(), AuthValCheckEnum.API_AUTH_ERR_007.getErrorMessage());
			throw applicationException;
		}
		
		String value = convertTextToImage(captchaText);
		
		// Save the object that is created
		CaptchaHistory captchaHistory =  new CaptchaHistory();
		captchaHistory.setCaptachValue(captchaText);
		captchaHistory.setCaptachHashValue(concatedTextHash);
		//captchaHistory.setCreatedOn(new Date(currentDateTime.toEpochMilli()));
		captchaHistory.setExpiryOn(new Date(expiryDateTime.toEpochMilli()));
		captchaHistoryRepo.save(captchaHistory);
		
		GenerateCaptchaResDTO generateCaptchaRes =  new GenerateCaptchaResDTO();
		generateCaptchaRes.setValue(value);
		generateCaptchaRes.setIdentifier(concatedTextHash);
		return generateCaptchaRes;
		
		
	}
	
	private String convertTextToImage(String text) throws ApplicationException {
		Properties properties = new Properties();
//      properties.setProperty("kaptcha.image.width", "105");
//      properties.setProperty("kaptcha.image.height", "42");
		properties.setProperty("kaptcha.textproducer.font.size", "40");
		properties.setProperty("kaptcha.textproducer.font.color", "red");
//      properties.setProperty("kaptcha.textproducer.char.string", "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYAZ");
		properties.setProperty("kaptcha.textproducer.char.length", "" + CaptchaConfigEnum.SIX.getValue());
//      properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");

		DefaultKaptcha kaptcha = new DefaultKaptcha();
		Config config = new Config(properties);
		// Config config = new Config(new Properties());
		kaptcha.setConfig(config);
		captchaProducer = kaptcha;
      
		//Convert the Text to Image and return Base64 encoded image
		try { 
			BufferedImage capImage = captchaProducer.createImage(text);
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			ImageIO.write(capImage, "jpeg", outStream);
			String value =  new String(Base64.getEncoder().encode(outStream.toByteArray()));
			return value;
		} catch (Exception e) {
			ApplicationException applicationException = new ApplicationException(AuthValCheckEnum.API_AUTH_ERR_010.getErrroCode(), AuthValCheckEnum.API_AUTH_ERR_007.getErrorMessage());
			throw applicationException;
		}
	}
	
	public Boolean validateCaptcha(ValidateCaptchaReqDTO validateCaptchaReqDTO) throws ApplicationException {
		String identifier = validateCaptchaReqDTO.getIdentifier();
		CaptchaHistory captchaHistory = captchaHistoryRepo.findTopByCaptachHashValueOrderByCreatedOnDesc(identifier);
		String captchaText = validateCaptchaReqDTO.getValue();
		Date currentDateTime = new Date(Instant.now().toEpochMilli());
	
		// Check if identifier exists
		if(captchaHistory == null) {
			ApplicationException applicationException = new ApplicationException(AuthValCheckEnum.API_AUTH_ERROR_011.getErrroCode(), AuthValCheckEnum.API_AUTH_ERROR_011.getErrorMessage());
			throw applicationException;
		}
		// Check if provided captcha matches the saved value
		else if(!ObjectUtils.isEmpty(captchaText) && !captchaText.equals(captchaHistory.getCaptachValue())) {
			ApplicationException applicationException = new ApplicationException(AuthValCheckEnum.API_AUTH_ERROR_011.getErrroCode(), AuthValCheckEnum.API_AUTH_ERROR_011.getErrorMessage());
			throw applicationException;
		}
		// Check if Captcha has expired
		else if(captchaHistory.getExpiryOn().compareTo(currentDateTime) < 0) {
			ApplicationException applicationException = new ApplicationException(AuthValCheckEnum.API_AUTH_ERROR_012.getErrroCode(), AuthValCheckEnum.API_AUTH_ERROR_012.getErrorMessage());
			throw applicationException;
		} else {
			return Boolean.TRUE;
		}
	}
}
