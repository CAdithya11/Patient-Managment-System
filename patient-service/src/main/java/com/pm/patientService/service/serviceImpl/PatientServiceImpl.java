package com.pm.patientService.service.serviceImpl;

import com.google.api.Billing;
import com.pm.patientService.dto.PatientRequestDTO;
import com.pm.patientService.dto.PatientResponseDTO;
import com.pm.patientService.exceptions.EmailAlreadyExistsException;
import com.pm.patientService.exceptions.PatientWithTheIdDoesNotExistsException;
import com.pm.patientService.grpc.BillingServiceGrpcClient;
import com.pm.patientService.mapper.PatientMapper;
import com.pm.patientService.model.Patient;
import com.pm.patientService.repository.PatientRepository;
import com.pm.patientService.service.PatientService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;

    PatientServiceImpl(PatientRepository patientRepository,BillingServiceGrpcClient billingServiceGrpcClient) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
    }

    @Override
    public List<PatientResponseDTO> getAllPatients() {
        List<Patient> allPatients = patientRepository.findAll();
        return allPatients.stream().map(PatientMapper::toDTO).toList();
    }

    @Override
    public PatientResponseDTO createPatient(PatientRequestDTO patient) {
        if (patientRepository.alreadyExists(patient.getEmail()) != null) {
            throw new EmailAlreadyExistsException("A patient with the email already exists " + patient.getEmail());
        }
        Patient newPatient = patientRepository.save(PatientMapper.toModel(patient));
        // Call the gRPC service to create a billing account for the new patient
        billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(),newPatient.getName(), newPatient.getEmail());
        return PatientMapper.toDTO(newPatient);
    }

    @Override
    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientWithTheIdDoesNotExistsException("A Patient with the id '" + id + "' Does not exists"));
        if (!patient.getEmail().equals(patientRequestDTO.getEmail())
                && patientRepository.alreadyExists(patientRequestDTO.getEmail()) != null) {
            throw new EmailAlreadyExistsException("A patient with the email already exists " + patient.getEmail());
        }
        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(updatedPatient);
    }

    @Override
    public void deletePatientById(UUID id) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientWithTheIdDoesNotExistsException("A Patient with the id '" + id + "' Does not exists"));
        patientRepository.deleteById(id);
    }
}
