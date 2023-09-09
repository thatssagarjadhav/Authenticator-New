/**
 * 
 */
package com.authenticator.authenticate.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hibernate.model.UserPrevPassword;

/**
 * @author sagar.jadhav
 *
 */
@Repository
public interface UserPreviousPasswordRepo extends JpaRepository<UserPrevPassword, Long> {

}
