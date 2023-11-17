package com.drawproject.dev.controller;


import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.course.ResponsePagingDTO;
import com.drawproject.dev.model.User;
import com.drawproject.dev.service.ArtWorkService;
import com.drawproject.dev.service.CertificateService;
import com.drawproject.dev.service.CourseService;
import com.drawproject.dev.service.ReportStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/staff")
public class StaffController {

    @Autowired
    ArtWorkService artworkService;

    @Autowired
    CertificateService certificateService;

    @Autowired
    ReportStudentService reportStudentService;

    @Autowired
    ArtWorkService artWorkService;

    @Autowired
    CourseService courseService;

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

    @PutMapping("/artworks/complete-check")
    public ResponseEntity<ResponseDTO> completeCheckArtWork(@RequestParam(value = "message", defaultValue = "", required = false) String message,
                                                     @RequestParam(value = "instructorId") int instructorId) {
        return ResponseEntity.ok().body(artworkService.completeCheckArtWorks(instructorId, message));
    }

    @PutMapping("/certificates/complete-check")
    public ResponseEntity<ResponseDTO> completeCheckCertificate(@RequestParam(value = "message", defaultValue = "", required = false) String message,
                                                            @RequestParam(value = "instructorId") int instructorId) {
        return ResponseEntity.ok().body(certificateService.completeCheckCertificates(instructorId, message));
    }

    @PutMapping("/artworks/accept")
    public ResponseEntity<Object> acceptArtWork(@RequestParam(value = "artworkId") int artworkId,
                                              @RequestParam(value = "message", defaultValue = "") String message) {


        return ResponseEntity.ok(artWorkService.acceptArtWork(message, artworkId));
    }

    @PutMapping("/artworks/reject")
    public ResponseEntity<Object> rejectArtWork(@RequestParam(value = "artworkId") int artworkId,
                                              @RequestParam(value = "message", defaultValue = "") String message) {


        return ResponseEntity.ok(artWorkService.rejectArtWork(message, artworkId));
    }

    @PutMapping("/certificates/accept")
    public ResponseEntity<ResponseDTO> acceptCertificates(@RequestParam(value = "certificateId") int certificateId,
                                                          @RequestParam(value = "message", defaultValue = "") String message) {

        return ResponseEntity.ok().body(certificateService.acceptCertificate(certificateId, message));
    }

    @PutMapping("/certificates/reject")
    public ResponseEntity<ResponseDTO> rejectCertificates(@RequestParam(value = "certificateId") int certificateId,
                                                          @RequestParam(value = "message", defaultValue = "") String message) {

        return ResponseEntity.ok().body(certificateService.rejectCertificate(certificateId, message));
    }

    @PutMapping(value = "/courses/report")
    public ResponseEntity<ResponseDTO> reportCourse(@RequestParam("id") int id,
                                                    @RequestParam(required = false, defaultValue = "") String message) {
        return ResponseEntity.ok().body(courseService.reportCourse(id, message));
    }

}
