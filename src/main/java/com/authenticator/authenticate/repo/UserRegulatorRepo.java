/**
 * 
 */
package com.authenticator.authenticate.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hibernate.model.UserRegulator;

/**
 * @author sagar.jadhav
 *
 */
@Repository
public interface UserRegulatorRepo extends JpaRepository<UserRegulator, Long> {

	UserRegulator findTopByOrderByUrIdDesc();

}
