package com.authenticator.authenticate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hibernate.model.UserMaster;

import com.hibernate.model.ErrorKeyLabel;

public interface ErrorKeyLabelRepo extends JpaRepository<UserMaster, Long> {

	@Query(value = "SELECT new com.hibernate.model.ErrorKeyLabel(errorKey.errorKey, lable.errorKeyLable, " + "lable.languageIdFk.languageCode, errorKey.errorCode)  " + " FROM ErrorKeyLabel lable , ErrorKey errorKey where lable.languageIdFk.isActive = 1 " + " and lable.errorIdFk.errorId = errorKey.errorId and errorKey.errorCode IN :errorCodes order by errorKey.errorCode asc")
	List<ErrorKeyLabel> loadErrorKeyLables(@Param("errorCodes") List<String> errorCodes);

}
