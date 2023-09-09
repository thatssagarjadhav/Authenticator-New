/**
 * 
 */
package com.authenticator.authenticate.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hibernate.model.UserSecurityQuest;

/**
 * @author sagar.jadhav
 *
 */
@Repository
public interface UserSecurityQuestionRepo extends JpaRepository<UserSecurityQuest, Long> {

}
