package com.pm.patientService.exceptions;

public class PatientWithTheIdDoesNotExistsException extends RuntimeException {
    public PatientWithTheIdDoesNotExistsException(String message){
        super(message);
    }
}
