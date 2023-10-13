package com.drawproject.dev.service;

import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.map.MapCertificate;
import com.drawproject.dev.model.Certificate;
import com.drawproject.dev.model.Instructor;
import com.drawproject.dev.repository.CertificateRepository;
import com.drawproject.dev.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    CertificateRepository certificateRepository;

    public ResponseDTO getCertificates(int instructorId) {
        List<Certificate> certificates = certificateRepository.findByInstructorInstructorId(instructorId);
        if(certificates.isEmpty()) {
            return new ResponseDTO(HttpStatus.NO_CONTENT, "No certificates found", certificates);
        }
        return new ResponseDTO(HttpStatus.FOUND, "Certificates found", MapCertificate.mapCertificateToDTOs(certificates));
    }

    public Instructor saveInstructorRegister(Instructor instructor) {
         return  instructorRepository.save(instructor);
    }
}
