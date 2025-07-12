package com.pm.patientService.controller;

import com.pm.patientService.dto.PatientRequestDTO;
import com.pm.patientService.dto.PatientResponseDTO;
import com.pm.patientService.service.PatientService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    PatientController(PatientService patientService){
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> patients(){
        return ResponseEntity.status(HttpStatus.OK).body(patientService.getAllPatients());
    }
    @PostMapping
    public ResponseEntity<PatientResponseDTO> postMethodName(@Valid @RequestBody PatientRequestDTO patientRequestDTO) {
       return ResponseEntity.ok().body(patientService.createPatient(patientRequestDTO)); 
    }
    
}
