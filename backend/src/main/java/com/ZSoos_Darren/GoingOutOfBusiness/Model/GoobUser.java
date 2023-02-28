package com.ZSoos_Darren.GoingOutOfBusiness.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class GoobUser {

    @Id
    @GeneratedValue
    private Long id;
    private String eMail;
    private String userName;
    private String password;
    @Column(columnDefinition = "timestamp default current_timestamp")
    @CreationTimestamp
    @JsonFormat(pattern = "YY.MM.dd HH:mm")
    private LocalDateTime dateOfRegistration;
    @JsonFormat(pattern = "YY.MM.dd")
    private Date dateOfBirth;
    @Column(columnDefinition = "VARCHAR")
    private String profilePicture;
    private Role role;
}
