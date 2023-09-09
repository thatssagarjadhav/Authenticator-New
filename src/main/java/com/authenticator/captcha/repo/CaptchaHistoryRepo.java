package com.authenticator.captcha.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hibernate.model.CaptchaHistory;
import com.hibernate.model.UserRegulator;


/**
 * @author vinay.rajagopalan
 */
@Repository
public interface CaptchaHistoryRepo extends JpaRepository<CaptchaHistory, Long> {
	CaptchaHistory findTopByCaptachHashValueOrderByCreatedOnDesc(String captchaHashValue);
}
