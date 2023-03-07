package com.ZSoos_Darren.GoingOutOfBusiness.dao;

import com.ZSoos_Darren.GoingOutOfBusiness.model.GoobUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoobUserDao extends JpaRepository<GoobUser, Long> {
    GoobUser findUserByEmail(String eMail);

    Boolean existsByEmail(String email);
}
