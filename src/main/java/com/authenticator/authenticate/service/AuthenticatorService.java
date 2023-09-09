/**
 * 
 */
package com.authenticator.authenticate.service;

import java.math.BigDecimal;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.authenticator.authenticate.dto.custom.UserRolePlatformRepoDTO;
import com.authenticator.authenticate.dto.request.AuthUserReqDTO;
import com.authenticator.authenticate.dto.request.UserMenuRoleDetailsReqDTO;
import com.authenticator.authenticate.dto.response.AuthUserResDTO;
import com.authenticator.authenticate.dto.response.DepartmentResDTO;
import com.authenticator.authenticate.dto.response.EntityResDTO;
import com.authenticator.authenticate.dto.response.PlatformMenuResponseDto;
import com.authenticator.authenticate.dto.response.PlatformResDTO;
import com.authenticator.authenticate.dto.response.RoleResDTO;
import com.authenticator.authenticate.dto.response.UserMenuRoleDetailsResDTO;
import com.authenticator.authenticate.repo.UserMasterRepo;
import com.authenticator.authenticate.util.AuthValCheckEnum;
import com.authenticator.authenticate.util.RoleTypeEnum;
import com.authenticator.captcha.service.CaptchaHistoryService;
import com.authenticator.config.UserRegulatorConf;
import com.authenticator.cryptology.AESV2;
import com.authenticator.cryptology.JWTTokenUtitlity;
import com.authenticator.cryptology.PasswordHashing;
import com.authenticator.cryptology.SHAHashingUtil;
import com.authenticator.exception.ApplicationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hibernate.model.UserMaster;
import com.iris.dateutility.enums.DateConstants;
import com.iris.dateutility.util.DateManip;

/**
 * @author sagar.jadhav
 *
 */
@Service
public class AuthenticatorService {
	
	private static final Logger LOGGER = LogManager.getLogger(AuthenticatorService.class);


	@Autowired
	private UserMasterRepo userMasterRepo;

	//	@Autowired
	//	private UserRegulatorRepo userRegulatorRepo;

	@Autowired
	private UserRegulatorConf userRegulatorConf;

	@Autowired
	private AESV2 aesV2;

	@Autowired
	private JWTTokenUtitlity jwtTokenUtitlity;

	@Autowired
	private EntityManager em;

	@Autowired
	private CaptchaHistoryService capHistoryService;
	
	
	public AuthUserResDTO authenticateUserAndSendPlatformDetails(AuthUserReqDTO authUserReqDTO) throws Exception {
		
		// Captcha validation
		capHistoryService.validateCaptcha(authUserReqDTO.getCaptchaDetails());
		
		String encryptedUserName = null;
		try {
			encryptedUserName = aesV2.encrypt(authUserReqDTO.getUserName());
		} catch (Exception e) {
			ApplicationException applicationException = new ApplicationException(AuthValCheckEnum.API_AUTH_ERR_007.getErrroCode(), AuthValCheckEnum.API_AUTH_ERR_007.getErrorMessage());
			throw applicationException;
		}
		
		UserMaster userMaster = userMasterRepo.findByUserName(encryptedUserName);

		if (ObjectUtils.isEmpty(userMaster)) {
			//User Not present
			ApplicationException applicationException = new ApplicationException(AuthValCheckEnum.API_AUTH_ERR_007.getErrroCode(), AuthValCheckEnum.API_AUTH_ERR_007.getErrorMessage());
			throw applicationException;
		}

		if (!userMaster.getIsActive()) {
			//User Not active
			ApplicationException applicationException = new ApplicationException(AuthValCheckEnum.API_AUTH_ERR_007.getErrroCode(), AuthValCheckEnum.API_AUTH_ERR_007.getErrorMessage());
			throw applicationException;
		}

		if (ObjectUtils.isEmpty(userMaster.getSalt()) && ObjectUtils.isEmpty(userMaster.getPassword()) && (!ObjectUtils.isEmpty(userMaster.getIsPasswordReset()) && userMaster.getIsPasswordReset().equals(Boolean.TRUE))) {
			//First time user 
			ApplicationException applicationException = new ApplicationException(AuthValCheckEnum.API_AUTH_ERR_007.getErrroCode(), AuthValCheckEnum.API_AUTH_ERR_007.getErrorMessage());
			throw applicationException;
		}

//		String userPlainPwdData = null;
//		try {
//			userPlainPwdData = aesV2.decrypt(authUserReqDTO.getPassword());
//		} catch (Exception e) {
//			ApplicationException applicationException = new ApplicationException(AuthValCheckEnum.API_AUTH_ERR_007.getErrroCode(), AuthValCheckEnum.API_AUTH_ERR_007.getErrorMessage());
//			throw applicationException;
//		}

		String pwdHashValue = PasswordHashing.getSHA256HashValue(authUserReqDTO.getPassword());

		boolean isAuthenticated = PasswordHashing.validatePassword(userMaster.getSalt(), pwdHashValue, userMaster.getPassword());

		if (isAuthenticated) {

			//			UserRegulator userRegulator = userRegulatorRepo.findTopByOrderByUrIdDesc();

			Long currentLoggedInUserCount = userMasterRepo.findCurrentLoggedInUserCount();

			if (currentLoggedInUserCount >= userRegulatorConf.getMaxLoginAllowed()) {
				// max login user count exceeded
				ApplicationException applicationException = new ApplicationException("", "");
				throw applicationException;
			}

			if (!userMaster.getRoleType().getRoleTypeId().equals(Long.valueOf(1L))) {
				// Other than regulator user 

				if (checkLastLogin(userMaster.getLastLogedOn(), userRegulatorConf.getInActUsrExprDays())) {
					userMaster.setIsActive(false);
					userMasterRepo.save(userMaster);
					//1. check for last login date and expired if days exceeded
					ApplicationException applicationException = new ApplicationException(AuthValCheckEnum.API_AUTH_ERR_007.getErrroCode(), AuthValCheckEnum.API_AUTH_ERR_007.getErrorMessage());
					throw applicationException;
				}

				boolean isPasswordExpired = checkPwdExpired(userMaster.getPwdChangeOn(), userRegulatorConf.getPswdExpiryDays());

				if (userMaster.getIsPasswordReset() || isPasswordExpired || (userMaster.getIsPasswordReset() && userMaster.getLastLogedOn() == null && userMaster.getPwdChangeOn() == null)) {
					userMaster.setIsPasswordReset(true);
					userMasterRepo.save(userMaster);
					//2. check for passwrod expired policy 
					ApplicationException applicationException = new ApplicationException(AuthValCheckEnum.API_AUTH_ERR_007.getErrroCode(), AuthValCheckEnum.API_AUTH_ERR_007.getErrorMessage());
					throw applicationException;
				}
			}

			List<UserRolePlatformRepoDTO> userRolePlatFormDtoList = userMasterRepo.getUserRolePlatformDetails(authUserReqDTO.getLangCode(), userMaster.getUserId());

			AuthUserResDTO autUserResDTO = convertQueryDataToAutUserRes(userRolePlatFormDtoList);

			return autUserResDTO;
		} else {
			//Credemtial not valid
			ApplicationException applicationException = new ApplicationException(AuthValCheckEnum.API_AUTH_ERR_007.getErrroCode(), AuthValCheckEnum.API_AUTH_ERR_007.getErrorMessage());
			throw applicationException;
		}
	}

	private AuthUserResDTO convertQueryDataToAutUserRes(List<UserRolePlatformRepoDTO> userRolePlatFormDtoList) throws Exception {
		AuthUserResDTO authUserResDTO = new AuthUserResDTO();

		authUserResDTO.setUserId(userRolePlatFormDtoList.get(0).getUserId());
		authUserResDTO.setUserName(userRolePlatFormDtoList.get(0).getUserName());
		authUserResDTO.setRoleType(userRolePlatFormDtoList.get(0).getRoleType());
		authUserResDTO.setPlatformSelectMessage("Welcome to SARB CoDI application");

		List<PlatformResDTO> platFormResDTOList = new ArrayList<>();

		Set<String> roleCodeList = new HashSet<>();

		for (UserRolePlatformRepoDTO userRolePlatformRepoDTO : userRolePlatFormDtoList) {
			PlatformResDTO platformResDTO = new PlatformResDTO();
			platformResDTO.setPlatformCode(userRolePlatformRepoDTO.getPlatFormCode());
			platformResDTO.setPlatformName(userRolePlatformRepoDTO.getPlatformName());
			platformResDTO.setPlatformActionUrl(userRolePlatformRepoDTO.getPlatFormActionUrl());
			platformResDTO.setPlatformMessage(userRolePlatformRepoDTO.getPlatFormMessage());
			roleCodeList.add(userRolePlatformRepoDTO.getUserRoleId().toString());
			platFormResDTOList.add(platformResDTO);
		}

		if (!CollectionUtils.isEmpty(platFormResDTOList)) {
			authUserResDTO.setPlatformList(platFormResDTOList);
		}

		ObjectMapper obj = new ObjectMapper();

		Map<String, Object> tokenDetailsMap = new HashMap<>();
		tokenDetailsMap.put("userName", userRolePlatFormDtoList.get(0).getUserName());
		tokenDetailsMap.put("tokenIssueOn", new Date().getTime());
		tokenDetailsMap.put("issuer", "codiifile");
		tokenDetailsMap.put("tokenExpiryInSecond", 60);
		tokenDetailsMap.put("roleCodes", obj.writeValueAsString(roleCodeList));
		String accessToken = jwtTokenUtitlity.generateJwtToken(tokenDetailsMap);
		authUserResDTO.setAccessToken(SHAHashingUtil.hashUsingSHA256(accessToken));
		return authUserResDTO;
	}

	public boolean checkLastLogin(Date lastLogedOn, Integer inActiveUserExpiryDaysCount) {
		long dayDiff = 0;
		if (lastLogedOn != null) {
			String currentDateStr = DateManip.getCurrentDate(DateConstants.DD_MM_YYYY.getDateConstants());
			try {
				dayDiff = DateManip.getDayDiff(DateManip.convertDateToString(lastLogedOn, DateConstants.DD_MM_YYYY.getDateConstants()), currentDateStr, DateConstants.DD_MM_YYYY.getDateConstants());
			} catch (ParseException e) {
				return false;
			}
		}
		if (dayDiff > inActiveUserExpiryDaysCount) {
			return true;
		}
		return false;
	}

	public boolean checkPwdExpired(Date passwordChangeDate, Integer pwdExpDaysCount) {
		long dayDiff = 0;
		if (passwordChangeDate != null) {
			try {
				String currentDateStr = DateManip.getCurrentDate(DateConstants.DD_MM_YYYY.getDateConstants());
				dayDiff = DateManip.getDayDiff(DateManip.convertDateToString(passwordChangeDate, DateConstants.DD_MM_YYYY.getDateConstants()), currentDateStr, DateConstants.DD_MM_YYYY.getDateConstants());
			} catch (Exception e) {
				return false;
			}
		}
		if (dayDiff > pwdExpDaysCount) {
			return true;
		}
		return false;
	}

	public UserMenuRoleDetailsResDTO getUserMenuRoleDetails(UserMenuRoleDetailsReqDTO userMenuRoleDetailsReqDTO) throws ApplicationException {

		UserMaster userMaster = userMasterRepo.findByUserName(userMenuRoleDetailsReqDTO.getUserName());

		if (ObjectUtils.isEmpty(userMaster)) {
			//User Not present
			ApplicationException applicationException = new ApplicationException(AuthValCheckEnum.API_AUTH_ERR_007.getErrroCode(), AuthValCheckEnum.API_AUTH_ERR_007.getErrorMessage());
			throw applicationException;
		}

		if (!userMaster.getIsActive()) {
			//User Not active
			ApplicationException applicationException = new ApplicationException(AuthValCheckEnum.API_AUTH_ERR_007.getErrroCode(), AuthValCheckEnum.API_AUTH_ERR_007.getErrorMessage());
			throw applicationException;
		}

		List<UserRolePlatformRepoDTO> userRolePlatFormDtoList = userMasterRepo.getUserRolePlatformDetails(userMenuRoleDetailsReqDTO.getLangCode(), userMaster.getUserId());

		UserMenuRoleDetailsResDTO userMenuRoleDetailsResDTO = convertQueryDataToUserMenuRoleDetailsRes(userRolePlatFormDtoList, userMenuRoleDetailsReqDTO);

		return userMenuRoleDetailsResDTO;
	}

	private UserMenuRoleDetailsResDTO convertQueryDataToUserMenuRoleDetailsRes(List<UserRolePlatformRepoDTO> userRolePlatFormDtoList, UserMenuRoleDetailsReqDTO userMenuRoleDetailsReqDTO) {

		UserMenuRoleDetailsResDTO userMenuRoleDetailsResDTO = new UserMenuRoleDetailsResDTO();

		userMenuRoleDetailsResDTO.setAccessToken(userMenuRoleDetailsReqDTO.getAccessToken());
		userMenuRoleDetailsResDTO.setTokenExpiryTimeInSeconds(10000);
		userMenuRoleDetailsResDTO.setRoleType(userRolePlatFormDtoList.get(0).getRoleType());

		if (userRolePlatFormDtoList.get(0).getRoleType().equals(RoleTypeEnum.ENTITY_ROLE_TYPE.getRoleTypeId())) {
			userMenuRoleDetailsResDTO.setUserEmailAdd(userRolePlatFormDtoList.get(0).getEntityUserEmail());
		} else {
			userMenuRoleDetailsResDTO.setUserEmailAdd(userRolePlatFormDtoList.get(0).getDeptUserEmail());
		}

		try {
			userMenuRoleDetailsResDTO.setUserName(aesV2.decrypt(userRolePlatFormDtoList.get(0).getUserName()));
		} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			LOGGER.error("Exception :", e);
		}
		
		try {
			userMenuRoleDetailsResDTO.setFirstName(aesV2.decrypt(userRolePlatFormDtoList.get(0).getFirstName()));
		} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			LOGGER.error("Exception :", e);
		}
		
		try {
			userMenuRoleDetailsResDTO.setLastName(aesV2.decrypt(userRolePlatFormDtoList.get(0).getLastName()));
		} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			LOGGER.error("Exception :", e);
		}
		
		try {
			userMenuRoleDetailsResDTO.setPhoneNumber(aesV2.decrypt(userRolePlatFormDtoList.get(0).getPhoneNumber()));
		} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			LOGGER.error("Exception :", e);
		}

		EntityResDTO entityResDTO = null;
		DepartmentResDTO departmentResDTO = null;
		List<PlatformResDTO> platFormResList = new ArrayList<>();
		List<RoleResDTO> roleResList = new ArrayList<>();

		for (UserRolePlatformRepoDTO userRolePlatformRepoDTO : userRolePlatFormDtoList) {
			if (entityResDTO == null && userRolePlatformRepoDTO.getRoleType().equals(RoleTypeEnum.ENTITY_ROLE_TYPE.getRoleTypeId())) {
				entityResDTO = new EntityResDTO();
				entityResDTO.setEntityCode(userRolePlatformRepoDTO.getEntityCode());
				entityResDTO.setEntityName(userRolePlatformRepoDTO.getEntityName());
			} else {
				if (departmentResDTO == null) {
					departmentResDTO = new DepartmentResDTO();
					departmentResDTO.setDeptCode(userRolePlatformRepoDTO.getRegulatorCode());
					departmentResDTO.setDeptName(userRolePlatformRepoDTO.getRegulatorName());
					departmentResDTO.setIsMainDept(userRolePlatformRepoDTO.getIsMainDept());
				}
			}
			PlatformResDTO platformResDTO = new PlatformResDTO();
			platformResDTO.setPlatformActionUrl(userRolePlatformRepoDTO.getPlatFormActionUrl());
			platformResDTO.setPlatformCode(userRolePlatformRepoDTO.getPlatFormCode());
			platformResDTO.setPlatformName(userRolePlatformRepoDTO.getPlatformName());
			platFormResList.add(platformResDTO);

			RoleResDTO roleResDTO = new RoleResDTO();
			roleResDTO.setIsAdmin(userRolePlatformRepoDTO.getIsAdminRole());
			roleResDTO.setRoleCode(userRolePlatformRepoDTO.getUserRoleCode());
			roleResDTO.setRoleId(userRolePlatformRepoDTO.getUserRoleId());
			roleResDTO.setRoleName(userRolePlatformRepoDTO.getUserRole());
			roleResList.add(roleResDTO);
		}

		userMenuRoleDetailsResDTO.setRoleList(roleResList);
		userMenuRoleDetailsResDTO.setPlatformList(platFormResList);
		userMenuRoleDetailsResDTO.setEntity(entityResDTO);
		userMenuRoleDetailsResDTO.setDepartment(departmentResDTO);

		List<PlatformMenuResponseDto> menuList = getMenuListBasedUponPlatform(userMenuRoleDetailsReqDTO.getLangCode(), userMenuRoleDetailsReqDTO.getPlatformCode(), userRolePlatFormDtoList.get(0).getUserId().longValue());
		
		if(!CollectionUtils.isEmpty(menuList)) {
			userMenuRoleDetailsResDTO.setMenuList(menuList);
		}

		return userMenuRoleDetailsResDTO;
	}

	public List<PlatformMenuResponseDto> getMenuListBasedUponPlatform(String langCode, String platformCode, Long userId) {
		StringBuilder str = new StringBuilder("SELECT distinct lbl1.MENU_LABEL AS lev1, lbl2.MENU_LABEL as lev2, lbl3.MENU_LABEL as lev3," + " lbl4.MENU_LABEL as lev4, t1.ORDER_NO as lev1_orderNo, t2.ORDER_NO as lev2_orderNo, t3.ORDER_NO as lev3_orderNo, " + "t4.ORDER_NO as lev4_orderNo,t1.GLYPHICONS_CLASS as lev1_menuIcon, t2.GLYPHICONS_CLASS as lev2_menuIcon, " + "t3.GLYPHICONS_CLASS as lev3_menuIcon, t4.GLYPHICONS_CLASS as lev4_menuIcon, t1.REDIRECTURL as lev1_action, " + "t2.REDIRECTURL as lev2_action, t3.REDIRECTURL as lev3_action, t4.REDIRECTURL as lev4_action,  t4.APPLICABLE_FOR_DEPT, " + "t4.APPLICABLE_FOR_ENTITY, t4.APPLICABLE_FOR_AUDITOR FROM TBL_USER_MASTER mas, TBL_USER_ROLE_MASTER roleMas, " + "TBL_MENU_ROLE_MAP roleMap, TBL_USER_ROLE_PLATFORM_MAPPING rolePlatForm, TBL_PLATFORM_MASTER plat, " + "TBL_MENU t4 left join TBL_MENU_LABEL lbl4 on lbl4.MENU_ID_FK = t4.MENU_ID " + "left join TBL_LANGUAGE_MASTER lmas on lmas.LANGUAGE_ID = lbl4.LANGUAGE_ID_FK " + "left join TBL_MENU t3 on t4.PARENT_MENU_ID = t3.MENU_ID " + "left join TBL_MENU_LABEL lbl3 on lbl3.MENU_ID_FK = t3.MENU_ID " + "and  lmas.LANGUAGE_ID = lbl3.LANGUAGE_ID_FK " + "left join TBL_MENU t2 on t3.PARENT_MENU_ID = t2.MENU_ID " + "left join TBL_MENU_LABEL lbl2 on lbl2.MENU_ID_FK = t2.MENU_ID " + "and  lmas.LANGUAGE_ID = lbl2.LANGUAGE_ID_FK" + " left join TBL_MENU t1 on t2.PARENT_MENU_ID = t1.MENU_ID" + " left join TBL_MENU_LABEL lbl1 on lbl1.MENU_ID_FK = t1.MENU_ID " + "and  lmas.LANGUAGE_ID = lbl1.LANGUAGE_ID_FK where" + "" + " mas.USER_ID =:userId and mas.IS_ACTIVE = 1 " + "and lmas.LANGUAGE_CODE =:langCode and lmas.IS_ACTIVE = 1" + " and roleMas.IS_ACTIVE = 1" + " and roleMap.IS_ACTIVE = 1 " + "and t1.MENU_DESC = 'root' and t4.REDIRECTURL!='#' and t4.ISACTIVE='1' " + " and plat.PLATFORM_CODE =:platFormCode  and plat.PLATFORM_ID = rolePlatForm.PLATFORM_ID_FK and rolePlatForm.USER_ROLE_ID_FK = roleMas.USER_ROLE_ID_FK " + " and mas.USER_ID = roleMas.USER_MASTER_ID_FK " + " and roleMap.USER_ROLE_ID_FK = roleMas.USER_ROLE_ID_FK and t4.MENU_ID = roleMap.MENU_ID_FK and t4.ISACTIVE='1' " + " order by t1.ORDER_NO, t2.ORDER_NO, t3.ORDER_NO, t4.ORDER_NO");

		Query query = em.createNativeQuery(str.toString(), Tuple.class);
		query.setParameter("userId", userId);
		query.setParameter("platFormCode", platformCode);
		query.setParameter("langCode", langCode);

		List<Tuple> resultList = query.getResultList();

		List<PlatformMenuResponseDto> platformMenuResponseDtos = new ArrayList<>();
		for (Tuple tuple : resultList) {
			PlatformMenuResponseDto level1Menu = new PlatformMenuResponseDto();
			level1Menu.setActionName(tuple.get("lev1_action", String.class));
			level1Menu.setMenuName(tuple.get("lev1", String.class));
			level1Menu.setOrderNo(tuple.get("lev1_orderNo", BigDecimal.class).floatValue());
			level1Menu.setMenuIcon(tuple.get("lev1_menuIcon", String.class));

			int level1MenuIndex = platformMenuResponseDtos.indexOf(level1Menu);

			if (level1MenuIndex >= 0) {
				level1Menu = platformMenuResponseDtos.get(level1MenuIndex);

				List<PlatformMenuResponseDto> leve2MenuList = level1Menu.getChildMenus();

				PlatformMenuResponseDto level2Menu = new PlatformMenuResponseDto();
				level2Menu.setActionName(tuple.get("lev2_action", String.class));
				level2Menu.setMenuName(tuple.get("lev2", String.class));
				level2Menu.setOrderNo(tuple.get("lev2_orderNo", BigDecimal.class).floatValue());
				level2Menu.setMenuIcon(tuple.get("lev2_menuIcon", String.class));

				int level2MenuIndex = leve2MenuList.indexOf(level2Menu);

				if (level2MenuIndex >= 0) {
					level2Menu = leve2MenuList.get(level2MenuIndex);

					List<PlatformMenuResponseDto> leve3MenuList = level2Menu.getChildMenus();
					PlatformMenuResponseDto level3Menu = new PlatformMenuResponseDto();
					level3Menu.setActionName(tuple.get("lev3_action", String.class));
					level3Menu.setMenuName(tuple.get("lev3", String.class));
					level3Menu.setOrderNo(tuple.get("lev3_orderNo", BigDecimal.class).floatValue());
					level3Menu.setMenuIcon(tuple.get("lev3_menuIcon", String.class));

					int level3MenuIndex = leve3MenuList.indexOf(level3Menu);
					if (level3MenuIndex >= 0) {
						level3Menu = leve3MenuList.get(level3MenuIndex);

						List<PlatformMenuResponseDto> leve4MenuList = level3Menu.getChildMenus();

						PlatformMenuResponseDto level4Menu = new PlatformMenuResponseDto();
						level4Menu.setActionName(tuple.get("lev4_action", String.class));
						level4Menu.setMenuName(tuple.get("lev4", String.class));
						level4Menu.setOrderNo(tuple.get("lev4_orderNo", BigDecimal.class).floatValue());
						level4Menu.setMenuIcon(tuple.get("lev4_menuIcon", String.class));
						if (tuple.get("APPLICABLE_FOR_DEPT") != null && tuple.get("APPLICABLE_FOR_DEPT").equals('1')) {
							level4Menu.setIsApplicableForDept(true);
						}
						if (tuple.get("APPLICABLE_FOR_ENTITY") != null && tuple.get("APPLICABLE_FOR_ENTITY").equals('1')) {
							level4Menu.setIsApplicableForEnt(true);
						}
						if (tuple.get("APPLICABLE_FOR_AUDITOR") != null && tuple.get("APPLICABLE_FOR_AUDITOR").equals('1')) {
							level4Menu.setIsApplicableForAudt(true);
						}

						leve4MenuList.add(level4Menu);
					} else {
						List<PlatformMenuResponseDto> leve4MenuList = new ArrayList<>();
						PlatformMenuResponseDto level4Menu = new PlatformMenuResponseDto();
						level4Menu.setActionName(tuple.get("lev4_action", String.class));
						level4Menu.setMenuName(tuple.get("lev4", String.class));
						level4Menu.setOrderNo(tuple.get("lev4_orderNo", BigDecimal.class).floatValue());
						level4Menu.setMenuIcon(tuple.get("lev4_menuIcon", String.class));
						if (tuple.get("APPLICABLE_FOR_DEPT") != null && tuple.get("APPLICABLE_FOR_DEPT").equals('1')) {
							level4Menu.setIsApplicableForDept(true);
						}
						if (tuple.get("APPLICABLE_FOR_ENTITY") != null && tuple.get("APPLICABLE_FOR_ENTITY").equals('1')) {
							level4Menu.setIsApplicableForEnt(true);
						}
						if (tuple.get("APPLICABLE_FOR_AUDITOR") != null && tuple.get("APPLICABLE_FOR_AUDITOR").equals('1')) {
							level4Menu.setIsApplicableForAudt(true);
						}
						leve4MenuList.add(level4Menu);

						level3Menu.setChildMenus(leve4MenuList);

						leve3MenuList.add(level3Menu);
					}
				} else {
					List<PlatformMenuResponseDto> level3MenuList = new ArrayList<>();
					PlatformMenuResponseDto level3Menu = new PlatformMenuResponseDto();
					level3Menu.setActionName(tuple.get("lev3_action", String.class));
					level3Menu.setMenuName(tuple.get("lev3", String.class));
					level3Menu.setOrderNo(tuple.get("lev3_orderNo", BigDecimal.class).floatValue());
					level3Menu.setMenuIcon(tuple.get("lev3_menuIcon", String.class));
					level3MenuList.add(level3Menu);

					List<PlatformMenuResponseDto> leve4MenuList = new ArrayList<>();
					PlatformMenuResponseDto level4Menu = new PlatformMenuResponseDto();
					level4Menu.setActionName(tuple.get("lev4_action", String.class));
					level4Menu.setMenuName(tuple.get("lev4", String.class));
					level4Menu.setOrderNo(tuple.get("lev4_orderNo", BigDecimal.class).floatValue());
					level4Menu.setMenuIcon(tuple.get("lev4_menuIcon", String.class));
					if (tuple.get("APPLICABLE_FOR_DEPT") != null && tuple.get("APPLICABLE_FOR_DEPT").equals('1')) {
						level4Menu.setIsApplicableForDept(true);
					}
					if (tuple.get("APPLICABLE_FOR_ENTITY") != null && tuple.get("APPLICABLE_FOR_ENTITY").equals('1')) {
						level4Menu.setIsApplicableForEnt(true);
					}
					if (tuple.get("APPLICABLE_FOR_AUDITOR") != null && tuple.get("APPLICABLE_FOR_AUDITOR").equals('1')) {
						level4Menu.setIsApplicableForAudt(true);
					}
					leve4MenuList.add(level4Menu);

					level2Menu.setChildMenus(level3MenuList);

					level3Menu.setChildMenus(leve4MenuList);

					leve2MenuList.add(level2Menu);
				}
			} else {
				List<PlatformMenuResponseDto> level2MenuList = new ArrayList<>();

				PlatformMenuResponseDto level2Menu = new PlatformMenuResponseDto();
				level2Menu.setActionName(tuple.get("lev2_action", String.class));
				level2Menu.setMenuName(tuple.get("lev2", String.class));
				level2Menu.setOrderNo(tuple.get("lev2_orderNo", BigDecimal.class).floatValue());
				level2Menu.setMenuIcon(tuple.get("lev2_menuIcon", String.class));
				level2MenuList.add(level2Menu);

				List<PlatformMenuResponseDto> level3MenuList = new ArrayList<>();
				PlatformMenuResponseDto level3Menu = new PlatformMenuResponseDto();
				level3Menu.setActionName(tuple.get("lev3_action", String.class));
				level3Menu.setMenuName(tuple.get("lev3", String.class));
				level3Menu.setOrderNo(tuple.get("lev3_orderNo", BigDecimal.class).floatValue());
				level3Menu.setMenuIcon(tuple.get("lev3_menuIcon", String.class));
				level3MenuList.add(level3Menu);

				List<PlatformMenuResponseDto> leve4MenuList = new ArrayList<>();
				PlatformMenuResponseDto level4Menu = new PlatformMenuResponseDto();
				level4Menu.setActionName(tuple.get("lev4_action", String.class));
				level4Menu.setMenuName(tuple.get("lev4", String.class));
				level4Menu.setOrderNo(tuple.get("lev4_orderNo", BigDecimal.class).floatValue());
				level4Menu.setMenuIcon(tuple.get("lev4_menuIcon", String.class));
				if (tuple.get("APPLICABLE_FOR_DEPT") != null && tuple.get("APPLICABLE_FOR_DEPT").equals('1')) {
					level4Menu.setIsApplicableForDept(true);
				}
				if (tuple.get("APPLICABLE_FOR_ENTITY") != null && tuple.get("APPLICABLE_FOR_ENTITY").equals('1')) {
					level4Menu.setIsApplicableForEnt(true);
				}
				if (tuple.get("APPLICABLE_FOR_AUDITOR") != null && tuple.get("APPLICABLE_FOR_AUDITOR").equals('1')) {
					level4Menu.setIsApplicableForAudt(true);
				}
				leve4MenuList.add(level4Menu);

				level1Menu.setChildMenus(level2MenuList);
				level2Menu.setChildMenus(level3MenuList);
				level3Menu.setChildMenus(leve4MenuList);
				platformMenuResponseDtos.add(level1Menu);
			}
		}

		return platformMenuResponseDtos;
	}

}
