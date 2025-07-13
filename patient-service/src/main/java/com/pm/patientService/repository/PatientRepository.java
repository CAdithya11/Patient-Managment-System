package com.pm.patientService.repository;

import com.pm.patientService.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {
    @Query(value = "SELECT * FROM patient WHERE email=:email;" ,nativeQuery = true)
    Patient alreadyExists(@Param(value = "email")String email);
}
