/**
 * 
 */
package com.authenticator.authenticate.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authenticator.authenticate.repo.ErrorKeyLabelRepo;
import com.authenticator.authenticate.repo.UserRegulatorRepo;
import com.authenticator.authenticate.util.ErrorConstant;
import com.authenticator.config.LabelConf;
import com.authenticator.config.UserRegulatorConf;
import com.hibernate.model.ErrorKeyLabel;
import com.hibernate.model.UserRegulator;

/**
 * @author sagar.jadhav
 *
 */
@Service
public class LoadStartupDataService {

	@Autowired
	private UserRegulatorRepo userRegulatorRepo;

	@Autowired
	private UserRegulatorConf userRegulatorConf;

	@Autowired
	private LabelConf LabelConf;

	@Autowired
	private ErrorKeyLabelRepo errorKeyLabelRepo;

	public void loadStartupData() throws InstantiationException, Exception {

		loadUserRegulatorData();

		loadErrorLables();
	}

	private void loadErrorLables() {

		List<String> errorCodeList = new ArrayList<String>();

		Field[] fields = ErrorConstant.class.getFields();

		for (Field field : fields) {
			try {
				Class<?> targetType = field.getType();
				Object objectValue = targetType.newInstance();
				String value = (String) field.get(objectValue);
				errorCodeList.add(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		List<ErrorKeyLabel> errorKeyLables = errorKeyLabelRepo.loadErrorKeyLables(errorCodeList);
		LabelConf.fillLabelMap(errorKeyLables);
	}

	private void loadUserRegulatorData() {

		UserRegulator userRegulator = userRegulatorRepo.findTopByOrderByUrIdDesc();
		userRegulatorConf.setAudEntMapExpDays(userRegulator.getAudEntMapExpDays());
		userRegulatorConf.setCalendarFormat(userRegulator.getCalendarFormat());
		userRegulatorConf.setCommonLoginUrl(userRegulator.getCommonLoginUrl());
		userRegulatorConf.setHourFormat(userRegulator.getHourFormat());
		userRegulatorConf.setInActUsrExprDays(userRegulator.getInActUsrExprDays());
		userRegulatorConf.setIscommonLogin(userRegulator.getIscommonLogin());
		userRegulatorConf.setIsItemsCanDragOnDashboard(userRegulator.getIsItemsCanDragOnDashboard());
		userRegulatorConf.setIsUsersBlocked(userRegulator.getIsUsersBlocked());
		userRegulatorConf.setMaxLoginAllowed(userRegulator.getMaxLoginAllowed());
		userRegulatorConf.setMaxPswdLength(userRegulator.getMaxPswdLength());
		userRegulatorConf.setMinCapsChar(userRegulator.getMinCapsChar());
		userRegulatorConf.setMinNum(userRegulator.getMinNum());
		userRegulatorConf.setMinPswdLength(userRegulator.getMinPswdLength());
		userRegulatorConf.setMinSmallChar(userRegulator.getMinSmallChar());
		userRegulatorConf.setMinSplChar(userRegulator.getMinSplChar());
		userRegulatorConf.setNoahUrl(userRegulator.getNoahUrl());
		userRegulatorConf.setNumOfPrevPasswd(userRegulator.getNumOfPrevPasswd());
		userRegulatorConf.setPswdAttempts(userRegulator.getPswdAttempts());
		userRegulatorConf.setPswdExpiryDays(userRegulator.getPswdExpiryDays());
		userRegulatorConf.setPswdExpiryNotifiDays(userRegulator.getPswdExpiryNotifiDays());
		userRegulatorConf.setRegulatorLoginInternet(userRegulator.getRegulatorLoginInternet());
		userRegulatorConf.setSdmxUrl(userRegulator.getSdmxUrl());
		userRegulatorConf.setSessionTimeout(userRegulator.getSessionTimeout());
		userRegulatorConf.setTokenExpiry(userRegulator.getSessionTimeout());
		userRegulatorConf.setCaptchaExpiry(userRegulator.getSessionTimeout());

	}
}
