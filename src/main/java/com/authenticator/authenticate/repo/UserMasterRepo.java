/**
 * 
 */
package com.authenticator.authenticate.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.authenticator.authenticate.dto.custom.UserRolePlatformRepoDTO;
import com.hibernate.model.UserMaster;

/**
 * @author sagar.jadhav
 *
 */
@Repository
public interface UserMasterRepo extends JpaRepository<UserMaster, Long> {

	UserMaster findByUserNameAndIsActiveTrue(String userName);

	UserMaster findByUserName(String userName);

	@Query("select COUNT(*) FROM UserMaster WHERE isLoggedIn=1")
	Long findCurrentLoggedInUserCount();

	@Query("select new com.authenticator.authenticate.dto.custom.UserRolePlatformRepoDTO"
			+ " (mas.userId, mas.userName, mas.roleType.roleTypeId ,  "
			+ " mas.firstName, mas.lastName, roleLabel.userRoleLabel, " + " userRole.userRoleId , "
			+ " lbl.gridFormKeyLable , " + " platMas.platformId, entLabl.entityNameLabel , "
			+ "  ent.entityCode, regLbl.regulatorLabel , reg.regulatorCode,"
			+ " entRole.companyEmail ,  mas.primaryEmail ,"
			+ "  platMas.platformCode, platMas.platformUrl, lbl.gridFormKeyLable, mas.primaryPhoneNo,  "
			+ " reg.isMaster, userRole.deptAdmin, userRole.roleCode) "
			+ " FROM UserRolePlatformMapping plat,  "
			+ " PlatformMaster platMas, "
			+ " UserMaster mas,"
			+ " LanguageMaster lngMas "
			+ " left join UserRoleMaster roleMas on mas.userId = roleMas.userMaster.userId "
			+ " left join UserEntityRole entRole on entRole.userRoleMaster.userRoleMasterId =  roleMas.userRoleMasterId "
			+ " left join Regulator reg on reg.regulatorId =  mas.departmentIdFk.regulatorId "
			+ " left join EntityBean ent on ent.entityId = entRole.entityBean.entityId "
			+ " left join RegulatorLabel regLbl on regLbl.regulatorIdFk.regulatorId = reg.regulatorId "
			+ " and lngMas.languageId = regLbl.langIdFk.languageId and lngMas.languageCode =:langCode "
			+ " left join EntityLabel entLabl "
			+ " on ent.entityId = entLabl.entityBean.entityId "
			+ " and  entLabl.languageMaster.languageId = lngMas.languageId"
			+ " and lngMas.languageCode =:langCode "
			+ " left join GridFormKey gridFk on gridFk.gridFormIdKey = platMas.platformKey "
			+ " left join GridFormKeyLabel lbl on gridFk.gridFormId = lbl.gridFormIdFk.gridFormId "
			+ " and lbl.languageIdFk.languageId = lngMas.languageId"
			+ " and lngMas.languageCode =:langCode "
			+ " left join UserRole userRole on userRole.userRoleId = roleMas.userRole.userRoleId "
			+ " left join UserRoleLabel roleLabel on userRole.userRoleId = roleLabel.userRoleIdFk.userRoleId "
			+ " and roleLabel.langIdFk.languageId = lngMas.languageId and lngMas.languageCode =:langCode "
			+ " where mas.userId =:userId"
			+ " and plat.userRoleIdFk.userRoleId = userRole.userRoleId "
			+ " and plat.platformIdFk.platformId = platMas.platformId and lngMas.languageCode =:langCode")
	List<UserRolePlatformRepoDTO> getUserRolePlatformDetails(@Param("langCode") String langCode,
			@Param("userId") Long userId);
	

}
