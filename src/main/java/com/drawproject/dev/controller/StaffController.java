package com.drawproject.dev.controller;


import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.course.ResponsePagingDTO;
import com.drawproject.dev.service.ArtWorkService;
import com.drawproject.dev.service.CertificateService;
import com.drawproject.dev.service.ReportStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/staff")
public class StaffController {

    @Autowired
    ArtWorkService artworkService;

    @Autowired
    CertificateService certificateService;

    @Autowired
    ReportStudentService reportStudentService;

    @GetMapping("/artworks")
    public ResponseEntity<ResponsePagingDTO> getArtworks(@RequestParam(value = "page", defaultValue = "1") int page,
                                                         @RequestParam(value = "eachPage", defaultValue = "4") int eachPage,
                                                         @RequestParam(value = "status", required = false) String status) {

        page = Math.max(page, 1);
        eachPage = Math.max(eachPage, 1);

        return ResponseEntity.ok().body(artworkService.viewArtWork(page, eachPage, status));
    }

    @GetMapping("/certificates")
    public ResponseEntity<ResponsePagingDTO> getCertificates(@RequestParam(value = "page", defaultValue = "1") int page,
                                                             @RequestParam(value = "eachPage", defaultValue = "4") int eachPage,
                                                             @RequestParam(value = "status", required = false) String status) {

        page = Math.max(page, 1);
        eachPage = Math.max(eachPage, 1);

        return ResponseEntity.ok().body(certificateService.viewCertificate(page, eachPage, status));
    }

    @GetMapping("/reportstudent")
    public ResponseEntity<ResponsePagingDTO> getReportStudents(@RequestParam(value = "page", defaultValue = "1") int page,
                                                             @RequestParam(value = "eachPage", defaultValue = "4") int eachPage,
                                                             @RequestParam(value = "studentId", defaultValue = "0") int studentId,
                                                               @RequestParam(value = "courseId", defaultValue = "0") int courseId) {

        page = Math.max(page, 1);
        eachPage = Math.max(eachPage, 1);

        return ResponseEntity.ok().body(reportStudentService.getReports(page, eachPage, studentId, courseId));
    }

    @PutMapping("/reportstudent/accept")
    public ResponseEntity<ResponseDTO> acceptReport(@RequestParam(value = "studentId") int studentId,
                                                    @RequestParam(value = "courseId") int courseId) {
        return ResponseEntity.ok().body(reportStudentService.acceptReport(studentId, courseId));

    }

    @PutMapping("/reportstudent/reject")
    public ResponseEntity<ResponseDTO> rejectReport(@RequestParam(value = "studentId") int studentId,
                                                    @RequestParam(value = "courseId") int courseId,
                                                    @RequestParam(value = "message") String message) {
        return ResponseEntity.ok().body(reportStudentService.rejectReport(studentId, courseId, message));

    }

}
