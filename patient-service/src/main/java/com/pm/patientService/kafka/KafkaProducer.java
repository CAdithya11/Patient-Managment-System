package com.pm.patientService.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.pm.patientService.model.Patient;
import patient.events.PatientEvent;

@Service
public class KafkaProducer {
    private final KafkaTemplate<String,byte[]> kafkaTemplate;

    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);
    public KafkaProducer(KafkaTemplate<String,byte[]> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Patient patient){
        PatientEvent event = PatientEvent.newBuilder()
                .setEmail(patient.getEmail())
                .setName(patient.getName())
                .setPatientID(patient.getId().toString())
                .setEventType("PATIENT_CREATED")
                .build();
        try {
            kafkaTemplate.send("patient",event.toByteArray());
        }catch(Exception e){
            log.info("Failed to produce the event {}",event);
        }
    }
}
