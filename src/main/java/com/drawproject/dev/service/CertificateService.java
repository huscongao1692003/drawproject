package com.drawproject.dev.service;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.CertificateDTO;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.map.MapCertificate;
import com.drawproject.dev.model.Certificate;
import com.drawproject.dev.model.Instructor;
import com.drawproject.dev.repository.CertificateRepository;
import com.drawproject.dev.repository.InstructorRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class CertificateService {

    @Autowired
    CertificateRepository certificateRepository;

    @Autowired
    InstructorRepository instructorRepository;

    public ResponseDTO getCertificates(int instructorId) {
        List<Certificate> certificates = certificateRepository.findByInstructorInstructorId(instructorId);
        if(certificates.isEmpty()) {
            return new ResponseDTO(HttpStatus.NO_CONTENT, "No certificates found", certificates);
        }
        return new ResponseDTO(HttpStatus.FOUND, "Certificates found", MapCertificate.mapCertificateToDTOs(certificates));
    }


    @SneakyThrows
    public ResponseDTO createCertificate(int instructorId, List<MultipartFile> listImages) {
        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow();

        for(MultipartFile image : listImages) {
            Certificate certificate = new Certificate();
            //How to save image MultipartFile to database
            certificate.setImage(image.getBytes());
            certificate.setInstructor(instructor);
            certificate.setStatus(DrawProjectConstaints.CLOSE);
            certificateRepository.save(certificate);
        }
        return new ResponseDTO(HttpStatus.CREATED, "Certificate created", null);
    }

    public ResponseDTO deleteCertificate(int certificateId) {
        Certificate certificate = certificateRepository.findById(certificateId).orElseThrow();
        certificate.setStatus(DrawProjectConstaints.CLOSE);
        certificateRepository.save(certificate);
        return new ResponseDTO(HttpStatus.OK, "Certificate deleted", null);
    }

    @SneakyThrows
    public ResponseDTO updateCertificate(int certificateId, MultipartFile image, int instructorId) {
        Certificate certificate = certificateRepository.findByCertificateIdAndInstructorInstructorId(certificateId, instructorId);
        if(certificate == null) {
            return new ResponseDTO(HttpStatus.NOT_FOUND, "Not existed", null);
        }
         certificate.setImage(image.getBytes());
        certificateRepository.save(certificate);
        return new ResponseDTO(HttpStatus.OK, "Certificate updated", null);
    }

}
