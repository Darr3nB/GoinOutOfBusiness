package com.ZSoos_Darren.GoingOutOfBusiness.dao;

import com.ZSoos_Darren.GoingOutOfBusiness.model.GoobUser;
import com.ZSoos_Darren.GoingOutOfBusiness.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GoobUserDao extends JpaRepository<GoobUser, Long> {
    GoobUser findUserByEmail(String eMail);

    Boolean existsByEmail(String email);

    @Query("SELECT gb from GoobUser gb WHERE (:id is null OR gb.id = :id) " +
            "AND (:email is null OR gb.email LIKE concat('%', :email, '%')) " +
            "AND (:role is null OR gb.role = :role)")
    Page<GoobUser> completeSearchWithoutDate(Long id, String email, Role role, Pageable pageable);
}
