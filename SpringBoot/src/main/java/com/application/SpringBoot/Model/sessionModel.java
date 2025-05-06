package com.application.SpringBoot.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table
@Data
@Entity
public class sessionModel {

    @Id
    @GeneratedValue (strategy =GenerationType.IDENTITY)
    @Column
    private int ID;
    @Column
    private String SessionID;
    @Column
    private String sessionStartTime=LocalDateTime.now().toString();
}
