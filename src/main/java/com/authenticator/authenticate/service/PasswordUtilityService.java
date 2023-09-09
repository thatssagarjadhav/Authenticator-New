/**
 * 
 */
package com.authenticator.authenticate.service;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.authenticator.authenticate.dto.request.ResetPasswordReqDTO;
import com.authenticator.authenticate.repo.UserMasterRepo;
import com.authenticator.authenticate.repo.UserPreviousPasswordRepo;
import com.authenticator.authenticate.repo.UserResetPasswrodLinkRepo;
import com.authenticator.authenticate.repo.UserSecurityQuestionRepo;
import com.authenticator.config.UserRegulatorConf;
import com.authenticator.cryptology.AESV2;
import com.authenticator.cryptology.PasswordHashing;
import com.authenticator.cryptology.SHAHashingUtil;
import com.authenticator.exception.ApplicationException;
import com.hibernate.model.SecurityQues;
import com.hibernate.model.UserMaster;
import com.hibernate.model.UserPrevPassword;
import com.hibernate.model.UserResetPasswordLink;
import com.hibernate.model.UserSecurityQuest;
import com.iris.dateutility.enums.DateConstants;
import com.iris.dateutility.util.DateManip;

/**
 * @author sagar.jadhav
 *
 */
@Service
public class PasswordUtilityService {

	@Autowired
	private UserMasterRepo userMasterRepo;

	@Autowired
	private UserResetPasswrodLinkRepo userResetPasswrodLinkRepo;

	@Autowired
	private UserPreviousPasswordRepo userPreviousPasswordRepo;

	@Autowired
	private UserSecurityQuestionRepo userSecurityQuestionRepo;

	//	@Autowired
	//	private UserRegulatorRepo userRegulatorRepo;

	@Autowired
	private UserRegulatorConf userRegulatorConf;

	@Autowired
	private AESV2 aesV2;

	@Transactional(rollbackFor = Exception.class)
	public boolean resetUserPassword(ResetPasswordReqDTO resetPwdReqDTO) throws ApplicationException {
		UserMaster userMaster = userMasterRepo.findByUserNameAndIsActiveTrue(resetPwdReqDTO.getUserName());

		boolean isLdapEnabled = false;

		if (userMaster == null) {
			ApplicationException applicationException = new ApplicationException("", "");
			throw applicationException;
		}

		//		UserRegulator userRegulator = userRegulatorRepo.findTopByOrderByUrIdDesc();

		performResetPasswordLinkValidation(userMaster.getUserId(), resetPwdReqDTO.getResetPasswordLink(), userRegulatorConf.getPswdExpiryDays());

		if (userMaster.getRoleType().getRoleTypeId().equals(1L) && isLdapEnabled) {
			ApplicationException applicationException = new ApplicationException("", "");
			throw applicationException;
		}

		resetUserPasswordInternal(resetPwdReqDTO, userMaster);
		return true;
	}

	private void resetUserPasswordInternal(ResetPasswordReqDTO resetPwdReqDTO, UserMaster userMaster) throws ApplicationException {
		boolean isFreshUser = false;
		if (userMaster.getLastLogedOn() == null && userMaster.getPwdChangeOn() == null) {
			isFreshUser = true;
		}
		String decryptedPaswrod;

		try {
			decryptedPaswrod = aesV2.decrypt(resetPwdReqDTO.getNewPassword());
		} catch (Exception e) {
			e.printStackTrace();
			ApplicationException applicationException = new ApplicationException("", "");
			throw applicationException;
		}

		validateUserPasswordAgainstPasswrodPolicy(decryptedPaswrod);

		if (!isFreshUser) {
			validateSecurityQuestion(userMaster, resetPwdReqDTO);
			validateCurrentPasswordAgainstPreviousPassword(resetPwdReqDTO.getNewPassword(), userMaster.getUserId(), userRegulatorConf.getNumOfPrevPasswd());
			insertDataIntoPrevisouPassword(resetPwdReqDTO, userMaster);
		} else {
			insertSecurityQuestion(resetPwdReqDTO, userMaster);
		}

		String salt = PasswordHashing.getSalt();
		userMaster.setSalt(salt);
		try {
			userMaster.setPassword(PasswordHashing.createHash(decryptedPaswrod, salt, Boolean.TRUE));
		} catch (Exception e) {
			e.printStackTrace();
			ApplicationException applicationException = new ApplicationException("", "");
			throw applicationException;
		}

		userMaster.setPwdChangeOn(DateManip.getCurrentDateTime());
		userMaster.setIsPasswordReset(false);
		userMaster.setPwdResetDate(null);
		userMasterRepo.save(userMaster);
	}

	private void insertDataIntoPrevisouPassword(ResetPasswordReqDTO resetPwdReqDTO, UserMaster userMaster) {
		UserPrevPassword userPrevpwdObj = new UserPrevPassword();
		userPrevpwdObj.setPrevPassword(userMaster.getPassword());
		userPrevpwdObj.setPrevSalt(userMaster.getSalt());
		userPrevpwdObj.setUser(userMaster);
		userPreviousPasswordRepo.save(userPrevpwdObj);
	}

	private void insertSecurityQuestion(ResetPasswordReqDTO resetPwdReqDTO, UserMaster userMaster) {
		UserSecurityQuest userSecurityQuest = new UserSecurityQuest();
		userSecurityQuest.setIsUsed(true);
		userSecurityQuest.setQuesAnswer(resetPwdReqDTO.getSecurityQuesAns());
		userSecurityQuest.setUserIdFk(userMaster);

		SecurityQues securityQues = new SecurityQues();
		securityQues.setQuestionId(resetPwdReqDTO.getSecurityQuesId());
		userSecurityQuest.setSecurityQues(securityQues);

		userSecurityQuestionRepo.save(userSecurityQuest);
	}

	private void validateSecurityQuestion(UserMaster userMaster, ResetPasswordReqDTO resetPwdReqDTO) throws ApplicationException {

		UserSecurityQuest userSecurityQuest = userMaster.getUserSecurityQuest();
		if (ObjectUtils.isEmpty(userSecurityQuest)) {
			ApplicationException applicationException = new ApplicationException("", "");
			throw applicationException;
		} else {
			if (!(userSecurityQuest.getSecurityQues().getQuestionId().longValue() == resetPwdReqDTO.getSecurityQuesId() && userSecurityQuest.getQuesAnswer().equals(resetPwdReqDTO.getSecurityQuesAns()))) {
				ApplicationException applicationException = new ApplicationException("", "");
				throw applicationException;
			}
		}
	}

	private void validateUserPasswordAgainstPasswrodPolicy(String password) throws ApplicationException {

		int smallAlphabetCount = 0, capsAlphabetCount = 0, specialCharCount = 0, digitCount = 0;

		for (int i = 0; i < password.length(); i++) {
			char ch = password.charAt(i);
			if (ch >= 'a' && ch <= 'z') {
				smallAlphabetCount++;
			} else if (ch >= 'A' && ch <= 'Z') {
				capsAlphabetCount++;
			} else if (ch >= '0' && ch <= '9') {
				digitCount++;
			} else {
				specialCharCount++;
			}
		}

		if (password.length() < userRegulatorConf.getMinPswdLength() || password.length() > userRegulatorConf.getMaxPswdLength() || userRegulatorConf.getMinCapsChar() > capsAlphabetCount || userRegulatorConf.getMinSmallChar() > smallAlphabetCount || userRegulatorConf.getMinSplChar() > specialCharCount || userRegulatorConf.getMinNum() > digitCount) {
			ApplicationException applicationException = new ApplicationException("", "");
			throw applicationException;
		}
	}

	private void validateCurrentPasswordAgainstPreviousPassword(String newPassword, Long userId, Integer allowedPreviousPwdCount) {
		Page<UserPrevPassword> page = userPreviousPasswordRepo.findAll(PageRequest.of(0, allowedPreviousPwdCount, Sort.by(Sort.Direction.ASC, "prevPasswordId")));

		page.forEach(prevPasswrod -> {
			boolean prevPassValid;
			try {
				prevPassValid = PasswordHashing.validatePassword(prevPasswrod.getPrevSalt(), SHAHashingUtil.hashUsingSHA256(newPassword), prevPasswrod.getPrevPassword());
				if (prevPassValid) {
					ApplicationException applicationException = new ApplicationException("", "");
					throw applicationException;
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private void performResetPasswordLinkValidation(Long userId, String resetPasswordLink, Integer resetLinkExpiryCount) throws ApplicationException {
		UserResetPasswordLink userResetPasswrodLink = userResetPasswrodLinkRepo.findFirstByUserIdFkUserIdOrderByCreatedOnDesc(userId);
		if (userResetPasswrodLink == null) {
			ApplicationException applicationException = new ApplicationException("", "");
			throw applicationException;
		} else {
			if (!userResetPasswrodLink.getResetPassLink().equalsIgnoreCase(resetPasswordLink)) {
				ApplicationException applicationException = new ApplicationException("", "");
				throw applicationException;
			}

			//Here Check reset password link send time and current time does not be greater than reset password expired time(In Regulator Tab)
			//if pwdFlag true--- means diff greater than ResetPwdExpiryDays--- return response
			//if pwdFlag false --- means diff less than ResetPwdExpiryDays----set validate new password and send email to user
			boolean pwdFlag = isResetLinkExpired(userResetPasswrodLink.getCreatedOn(), resetLinkExpiryCount);
			if (pwdFlag) {
				ApplicationException applicationException = new ApplicationException("", "");
				throw applicationException;
			}
		}

	}

	private boolean isResetLinkExpired(Date resetLinkGeneratedDate, Integer resetLIngExpiryCount) {
		long dayDiff = 0;
		if (resetLinkGeneratedDate != null) {
			try {
				String currentDateStr = DateManip.getCurrentDate(DateConstants.DD_MM_YYYY.getDateConstants());
				dayDiff = DateManip.getDayDiff(DateManip.convertDateToString(resetLinkGeneratedDate, DateConstants.DD_MM_YYYY.getDateConstants()), currentDateStr, DateConstants.DD_MM_YYYY.getDateConstants());
			} catch (Exception e) {
				return false;
			}
		}
		if (dayDiff > resetLIngExpiryCount) {
			return true;
		}
		return false;

	}

}
