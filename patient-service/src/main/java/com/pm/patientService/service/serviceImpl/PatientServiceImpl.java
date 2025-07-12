package com.pm.patientService.service.serviceImpl;

import com.pm.patientService.dto.PatientRequestDTO;
import com.pm.patientService.dto.PatientResponseDTO;
import com.pm.patientService.mapper.PatientMapper;
import com.pm.patientService.model.Patient;
import com.pm.patientService.repository.PatientRepository;
import com.pm.patientService.service.PatientService;

import java.util.List;

import org.springframework.stereotype.Service;
@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    PatientServiceImpl(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }
    @Override
    public List<PatientResponseDTO> getAllPatients() {
        List<Patient> allPatients =  patientRepository.findAll();
        return allPatients.stream().map(PatientMapper::toDTO).toList();
    }
    @Override
    public PatientResponseDTO createPatient(PatientRequestDTO patient) {
        Patient newPatient = patientRepository.save(PatientMapper.toModel(patient));
        return PatientMapper.toDTO(newPatient);
    }
}
