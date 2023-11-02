package com.drawproject.dev.service;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.CertificateDTO;
import com.drawproject.dev.dto.Mail;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.course.ResponsePagingDTO;
import com.drawproject.dev.map.MapCertificate;
import com.drawproject.dev.map.MapCourse;
import com.drawproject.dev.model.Certificate;
import com.drawproject.dev.model.Instructor;
import com.drawproject.dev.model.User;
import com.drawproject.dev.repository.CertificateRepository;
import com.drawproject.dev.repository.InstructorRepository;
import com.drawproject.dev.repository.UserRepository;
import lombok.SneakyThrows;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

@Service
public class CertificateService {

    @Autowired
    CertificateRepository certificateRepository;

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    FileService fileService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MailService mailService;

    public ResponseDTO getCertificates(int instructorId, String status) {

        List<Certificate> certificates;
        if(status.equalsIgnoreCase("")) {
            certificates = certificateRepository.findByInstructorInstructorId(instructorId);
        } else {
            certificates = certificateRepository.findByInstructorInstructorIdAndStatus(instructorId, status);
        }

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
            certificate.setImage(fileService.uploadFile(image, instructorId, "image", "certificates"));
            certificate.setInstructor(instructor);
            certificate.setStatus(DrawProjectConstaints.OPEN);
            certificateRepository.save(certificate);
        }
        return new ResponseDTO(HttpStatus.CREATED, "Certificate created", "Your certificate will be reviewed before updating!");
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
         certificate.setImage(fileService.uploadFile(image, instructorId, "image", "certificates"));
        certificateRepository.save(certificate);
        return new ResponseDTO(HttpStatus.OK, "Certificate updated", "Your certificate will be reviewed before updating!");
    }

    public ResponsePagingDTO viewCertificate(int page, int eachPage, String status) {
        Pageable pageable = PageRequest.of(page - 1, eachPage);

        Page<Certificate> certificates;
        if(status == null) {
            certificates = certificateRepository.findAll(pageable);
        } else {
            certificates = certificateRepository.findByStatus(status, pageable);
        }

        ResponsePagingDTO responsePagingDTO = new ResponsePagingDTO(HttpStatus.FOUND, "Certificate found",
                certificates.getTotalElements(), page, certificates.getTotalPages(), eachPage, MapCertificate.mapCertificateToDTOs(certificates.getContent()));

        if(certificates.isEmpty()) {
            responsePagingDTO.setMessage("Certificate not found");
            responsePagingDTO.setStatus(HttpStatus.NO_CONTENT);
        }

        return responsePagingDTO;
    }

    public ResponseDTO completeCheckCertificates(int instructorId, String message) {
        User user = userRepository.findById(instructorId).orElseThrow();
        Mail mail = new Mail(user.getEmail(), DrawProjectConstaints.TEMPLATE_CHECK_COMPLETE);
        if(!user.getRoles().getName().equalsIgnoreCase(DrawProjectConstaints.INSTRUCTOR)) {
            return new ResponseDTO(HttpStatus.METHOD_NOT_ALLOWED, "You are not an instructor", "Email not sent");
        }
        mailService.sendMessage(mail, new HashMap<String, Object>() {
            {
                put("fullName", user.getFullName());
                put("typeNotification", "Certificates");
                put("message", message);
            }
        });
        return new ResponseDTO(HttpStatus.OK, "Artwork checked", "Send email to " + user.getEmail() + " Successfully");
    }

}
