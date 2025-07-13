package com.pm.patientService.controller;

import com.pm.patientService.dto.PatientRequestDTO;
import com.pm.patientService.dto.PatientResponseDTO;
import com.pm.patientService.dto.Validators.CreatePatientValidationGroup;
import com.pm.patientService.service.PatientService;

import jakarta.validation.Valid;

import jakarta.validation.groups.Default;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> patients() {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.getAllPatients());
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> postMethodName(@Validated({ Default.class,
            CreatePatientValidationGroup.class }) @RequestBody PatientRequestDTO patientRequestDTO) {
        return ResponseEntity.ok().body(patientService.createPatient(patientRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatientWithId(
            @Validated({ Default.class }) @RequestBody PatientRequestDTO patientRequestDTO, @PathVariable UUID id) {
        return ResponseEntity.ok().body(patientService.updatePatient(id, patientRequestDTO));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatientById(@PathVariable UUID id){
        patientService.deletePatientById(id);
        return ResponseEntity.noContent().build();
    }

}
