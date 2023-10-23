package com.drawproject.dev.service;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.Mail;
import com.drawproject.dev.dto.report.ReportStudentDTO;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.course.ResponsePagingDTO;
import com.drawproject.dev.map.MapReport;
import com.drawproject.dev.model.ReportStudent;
import com.drawproject.dev.model.ReportStudentId;
import com.drawproject.dev.model.User;
import com.drawproject.dev.repository.CourseRepository;
import com.drawproject.dev.repository.EnrollRepository;
import com.drawproject.dev.repository.ReportStudentRepository;
import com.drawproject.dev.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@Service
public class ReportStudentService {

    @Autowired
    ReportStudentRepository reportStudentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    FileService fileService;

    @Autowired
    EnrollService enrollService;

    @Autowired
    MailService mailService;

    public ResponseDTO createReport(ReportStudentDTO reportStudentDTO) {
        ReportStudent reportStudent = new ReportStudent();
        reportStudent.setStudent(userRepository.findById(reportStudentDTO.getStudentId()).orElseThrow());
        reportStudent.setCourse(courseRepository.findById(reportStudentDTO.getCourseId()).orElseThrow());
        ReportStudentId reportStudentId = new ReportStudentId(reportStudentDTO.getStudentId(), reportStudentDTO.getCourseId());
        reportStudent.setId(reportStudentId);
        reportStudent.setMessage(reportStudentDTO.getMessage());
        reportStudent.setStatus(DrawProjectConstaints.PENDING);
        if(reportStudentDTO.getImage() != null) {
            int id = reportStudentDTO.getStudentId() * 10 + reportStudentDTO.getCourseId();
            reportStudent.setImage(fileService.uploadFile(reportStudentDTO.getImage(), id, DrawProjectConstaints.IMAGE, "reportstudent"));
        }
        reportStudentRepository.save(reportStudent);
        return new ResponseDTO(HttpStatus.CREATED, "Report created successfully", "Your report will be reviewed by the our!");
    }

    public ResponsePagingDTO getReports(int page, int eachPage, int studentId, int courseId) {
        Pageable pageable = PageRequest.of(page - 1, eachPage);
        ResponsePagingDTO responsePagingDTO;
        Page<ReportStudent> reportStudent;
        if(studentId != 0) {
            reportStudent = reportStudentRepository.findById_StudentId(studentId, pageable);
        } else if(courseId != 0) {
            reportStudent = reportStudentRepository.findById_CourseId(courseId, pageable);
        } else {
            reportStudent = reportStudentRepository.findAll(pageable);
        }
        if(reportStudent.isEmpty()) {
            responsePagingDTO = new ResponsePagingDTO(HttpStatus.NO_CONTENT, "Course not found",
                    reportStudent.getTotalElements(), page, reportStudent.getTotalPages(), eachPage, MapReport.mapReportsToDTOs(reportStudent.getContent()));
        } else {
            responsePagingDTO = new ResponsePagingDTO(HttpStatus.FOUND, "Course not found",
                    reportStudent.getTotalElements(), page, reportStudent.getTotalPages(), eachPage, MapReport.mapReportsToDTOs(reportStudent.getContent()));
        }

        return responsePagingDTO;
    }

    public ResponseDTO acceptReport(int studentId, int courseId) {
        ReportStudent reportStudent = reportStudentRepository.findById_StudentIdAndId_CourseId(studentId, courseId);
        if(reportStudent == null) {
            return new ResponseDTO(HttpStatus.NOT_FOUND, "Report not found", null);
        }
        enrollService.deleteEnroll(studentId, courseId);
        reportStudent.setStatus(DrawProjectConstaints.COMPELETED);
        reportStudentRepository.save(reportStudent);
        //send an email notification
        Mail mail = new Mail(reportStudent.getStudent().getEmail(), DrawProjectConstaints.TEMPLATE_REPORT_STUDENT);
        mailService.sendMessage(mail, new HashMap<String, Object>() {
            {
                put("fullName", reportStudent.getStudent().getFullName());
                put("nameCourse", reportStudent.getCourse().getCourseTitle());
                put("message", reportStudent.getMessage());
                put("courseId", reportStudent.getCourse().getCourseId());
            }
        });

        return new ResponseDTO(HttpStatus.OK, "Report accepted", "Report accepted");
    }

    @Transactional
    public ResponseDTO rejectReport(int studentId, int courseId, String message) {
        ReportStudent reportStudent = reportStudentRepository.findById_StudentIdAndId_CourseId(studentId, courseId);
        if(reportStudent == null) {
            return new ResponseDTO(HttpStatus.NOT_FOUND, "Report not found", null);
        }
        reportStudentRepository.deleteByIdStudentIdAndIdCourseId(studentId, courseId);
        return new ResponseDTO(HttpStatus.OK, "Report rejected", message);
    }

}
