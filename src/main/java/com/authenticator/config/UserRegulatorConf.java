/**
 * 
 */
package com.authenticator.config;

import org.springframework.stereotype.Component;

/**
 * @author sagar.jadhav
 *
 */
@Component
public class UserRegulatorConf {

	private Integer minPswdLength;

	private Integer pswdAttempts;

	private Integer pswdExpiryDays;

	private Integer inActUsrExprDays;

	private Integer maxPswdLength;

	private Integer minSmallChar;

	private Integer minNum;

	private Integer minSplChar;

	private Integer minCapsChar;

	private Integer numOfPrevPasswd;

	private String calendarFormat;

	private Integer maxLoginAllowed;

	private Integer hourFormat;

	private Boolean isUsersBlocked;

	private Integer pswdExpiryNotifiDays;

	private Integer sessionTimeout;

	private Boolean regulatorLoginInternet;

	private String noahUrl;

	private String sdmxUrl;

	private Boolean isItemsCanDragOnDashboard;

	private Integer audEntMapExpDays;

	private Boolean iscommonLogin;

	private String commonLoginUrl;
	
	private Integer tokenExpiry;
	
	private Integer captchaExpiry;

	/**
	 * @return the minPswdLength
	 */
	public Integer getMinPswdLength() {
		return minPswdLength;
	}

	/**
	 * @param minPswdLength the minPswdLength to set
	 */
	public void setMinPswdLength(Integer minPswdLength) {
		this.minPswdLength = minPswdLength;
	}

	/**
	 * @return the pswdAttempts
	 */
	public Integer getPswdAttempts() {
		return pswdAttempts;
	}

	/**
	 * @param pswdAttempts the pswdAttempts to set
	 */
	public void setPswdAttempts(Integer pswdAttempts) {
		this.pswdAttempts = pswdAttempts;
	}

	/**
	 * @return the pswdExpiryDays
	 */
	public Integer getPswdExpiryDays() {
		return pswdExpiryDays;
	}

	/**
	 * @param pswdExpiryDays the pswdExpiryDays to set
	 */
	public void setPswdExpiryDays(Integer pswdExpiryDays) {
		this.pswdExpiryDays = pswdExpiryDays;
	}

	/**
	 * @return the inActUsrExprDays
	 */
	public Integer getInActUsrExprDays() {
		return inActUsrExprDays;
	}

	/**
	 * @param inActUsrExprDays the inActUsrExprDays to set
	 */
	public void setInActUsrExprDays(Integer inActUsrExprDays) {
		this.inActUsrExprDays = inActUsrExprDays;
	}

	/**
	 * @return the maxPswdLength
	 */
	public Integer getMaxPswdLength() {
		return maxPswdLength;
	}

	/**
	 * @param maxPswdLength the maxPswdLength to set
	 */
	public void setMaxPswdLength(Integer maxPswdLength) {
		this.maxPswdLength = maxPswdLength;
	}

	/**
	 * @return the minSmallChar
	 */
	public Integer getMinSmallChar() {
		return minSmallChar;
	}

	/**
	 * @param minSmallChar the minSmallChar to set
	 */
	public void setMinSmallChar(Integer minSmallChar) {
		this.minSmallChar = minSmallChar;
	}

	/**
	 * @return the minNum
	 */
	public Integer getMinNum() {
		return minNum;
	}

	/**
	 * @param minNum the minNum to set
	 */
	public void setMinNum(Integer minNum) {
		this.minNum = minNum;
	}

	/**
	 * @return the minSplChar
	 */
	public Integer getMinSplChar() {
		return minSplChar;
	}

	/**
	 * @param minSplChar the minSplChar to set
	 */
	public void setMinSplChar(Integer minSplChar) {
		this.minSplChar = minSplChar;
	}

	/**
	 * @return the minCapsChar
	 */
	public Integer getMinCapsChar() {
		return minCapsChar;
	}

	/**
	 * @param minCapsChar the minCapsChar to set
	 */
	public void setMinCapsChar(Integer minCapsChar) {
		this.minCapsChar = minCapsChar;
	}

	/**
	 * @return the numOfPrevPasswd
	 */
	public Integer getNumOfPrevPasswd() {
		return numOfPrevPasswd;
	}

	/**
	 * @param numOfPrevPasswd the numOfPrevPasswd to set
	 */
	public void setNumOfPrevPasswd(Integer numOfPrevPasswd) {
		this.numOfPrevPasswd = numOfPrevPasswd;
	}

	/**
	 * @return the calendarFormat
	 */
	public String getCalendarFormat() {
		return calendarFormat;
	}

	/**
	 * @param calendarFormat the calendarFormat to set
	 */
	public void setCalendarFormat(String calendarFormat) {
		this.calendarFormat = calendarFormat;
	}

	/**
	 * @return the maxLoginAllowed
	 */
	public Integer getMaxLoginAllowed() {
		return maxLoginAllowed;
	}

	/**
	 * @param maxLoginAllowed the maxLoginAllowed to set
	 */
	public void setMaxLoginAllowed(Integer maxLoginAllowed) {
		this.maxLoginAllowed = maxLoginAllowed;
	}

	/**
	 * @return the hourFormat
	 */
	public Integer getHourFormat() {
		return hourFormat;
	}

	/**
	 * @param hourFormat the hourFormat to set
	 */
	public void setHourFormat(Integer hourFormat) {
		this.hourFormat = hourFormat;
	}

	/**
	 * @return the isUsersBlocked
	 */
	public Boolean getIsUsersBlocked() {
		return isUsersBlocked;
	}

	/**
	 * @param isUsersBlocked the isUsersBlocked to set
	 */
	public void setIsUsersBlocked(Boolean isUsersBlocked) {
		this.isUsersBlocked = isUsersBlocked;
	}

	/**
	 * @return the pswdExpiryNotifiDays
	 */
	public Integer getPswdExpiryNotifiDays() {
		return pswdExpiryNotifiDays;
	}

	/**
	 * @param pswdExpiryNotifiDays the pswdExpiryNotifiDays to set
	 */
	public void setPswdExpiryNotifiDays(Integer pswdExpiryNotifiDays) {
		this.pswdExpiryNotifiDays = pswdExpiryNotifiDays;
	}

	/**
	 * @return the sessionTimeout
	 */
	public Integer getSessionTimeout() {
		return sessionTimeout;
	}

	/**
	 * @param sessionTimeout the sessionTimeout to set
	 */
	public void setSessionTimeout(Integer sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

	/**
	 * @return the regulatorLoginInternet
	 */
	public Boolean getRegulatorLoginInternet() {
		return regulatorLoginInternet;
	}

	/**
	 * @param regulatorLoginInternet the regulatorLoginInternet to set
	 */
	public void setRegulatorLoginInternet(Boolean regulatorLoginInternet) {
		this.regulatorLoginInternet = regulatorLoginInternet;
	}

	/**
	 * @return the noahUrl
	 */
	public String getNoahUrl() {
		return noahUrl;
	}

	/**
	 * @param noahUrl the noahUrl to set
	 */
	public void setNoahUrl(String noahUrl) {
		this.noahUrl = noahUrl;
	}

	/**
	 * @return the sdmxUrl
	 */
	public String getSdmxUrl() {
		return sdmxUrl;
	}

	/**
	 * @param sdmxUrl the sdmxUrl to set
	 */
	public void setSdmxUrl(String sdmxUrl) {
		this.sdmxUrl = sdmxUrl;
	}

	/**
	 * @return the isItemsCanDragOnDashboard
	 */
	public Boolean getIsItemsCanDragOnDashboard() {
		return isItemsCanDragOnDashboard;
	}

	/**
	 * @param isItemsCanDragOnDashboard the isItemsCanDragOnDashboard to set
	 */
	public void setIsItemsCanDragOnDashboard(Boolean isItemsCanDragOnDashboard) {
		this.isItemsCanDragOnDashboard = isItemsCanDragOnDashboard;
	}

	/**
	 * @return the audEntMapExpDays
	 */
	public Integer getAudEntMapExpDays() {
		return audEntMapExpDays;
	}

	/**
	 * @param audEntMapExpDays the audEntMapExpDays to set
	 */
	public void setAudEntMapExpDays(Integer audEntMapExpDays) {
		this.audEntMapExpDays = audEntMapExpDays;
	}

	/**
	 * @return the iscommonLogin
	 */
	public Boolean getIscommonLogin() {
		return iscommonLogin;
	}

	/**
	 * @param iscommonLogin the iscommonLogin to set
	 */
	public void setIscommonLogin(Boolean iscommonLogin) {
		this.iscommonLogin = iscommonLogin;
	}

	/**
	 * @return the commonLoginUrl
	 */
	public String getCommonLoginUrl() {
		return commonLoginUrl;
	}

	/**
	 * @param commonLoginUrl the commonLoginUrl to set
	 */
	public void setCommonLoginUrl(String commonLoginUrl) {
		this.commonLoginUrl = commonLoginUrl;
	}
	
	/**
	 * 
	 * @return tokenExpiry
	 */
	public Integer getTokenExpiry() {
		return tokenExpiry;
	}

	/**
	 * 
	 * @param tokenExpiry to set
	 */
	public void setTokenExpiry(Integer tokenExpiry) {
		this.tokenExpiry = tokenExpiry;
	}

	/**
	 * 
	 * @return captchaExpiry
	 */
	public Integer getCaptchaExpiry() {
		return captchaExpiry;
	}

	/**
	 * 
	 * @param captchaExpiry to set
	 */
	public void setCaptchaExpiry(Integer captchaExpiry) {
		this.captchaExpiry = captchaExpiry;
	}

}
