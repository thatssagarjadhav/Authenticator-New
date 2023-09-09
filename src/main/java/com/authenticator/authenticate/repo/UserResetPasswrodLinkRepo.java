/**
 * 
 */
package com.authenticator.authenticate.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hibernate.model.UserResetPasswordLink;

/**
 * @author sagar.jadhav
 *
 */
@Repository
public interface UserResetPasswrodLinkRepo extends JpaRepository<UserResetPasswordLink, Long> {

	UserResetPasswordLink findFirstByUserIdFkUserIdOrderByCreatedOnDesc(Long userId);
}
