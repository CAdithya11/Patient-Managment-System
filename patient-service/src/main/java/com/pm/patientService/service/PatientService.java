package com.pm.patientService.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pm.patientService.dto.PatientRequestDTO;
import com.pm.patientService.dto.PatientResponseDTO;
@Service
public interface PatientService {
    public List<PatientResponseDTO> getAllPatients();
    public PatientResponseDTO createPatient(PatientRequestDTO patient);
}
