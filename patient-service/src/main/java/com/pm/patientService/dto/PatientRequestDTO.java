package com.pm.patientService.dto;

import com.pm.patientService.dto.Validators.CreatePatientValidationGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PatientRequestDTO {
    @NotBlank(message = "Name cannot be null")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Date of Birth is required")
    private String dateOfBirth;
    @NotBlank(groups = CreatePatientValidationGroup.class,message = "Registered Date is required")
    private String registeredDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }
}
