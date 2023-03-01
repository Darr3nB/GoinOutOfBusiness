package com.ZSoos_Darren.GoingOutOfBusiness.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
    @Column(unique = true)
    private String eMail;
    private String userName;
    @JsonIgnore
    private String password;
    @Column(columnDefinition = "timestamp default current_timestamp")
    @CreationTimestamp
    @JsonFormat(pattern = "YY.MM.dd HH:mm")
    private LocalDateTime dateOfRegistration;
    @JsonFormat(pattern = "YY.MM.dd")
    private Date dateOfBirth;
    @Column(columnDefinition = "VARCHAR")
    private String profilePicture;
    @Enumerated(EnumType.STRING)
    private Role role;
}
