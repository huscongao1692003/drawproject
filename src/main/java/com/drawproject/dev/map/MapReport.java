package com.drawproject.dev.map;

import com.drawproject.dev.dto.report.ReportStudentDTO;
import com.drawproject.dev.dto.report.ResponseReport;
import com.drawproject.dev.model.ReportStudent;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class MapReport {

    private static final ModelMapper modelMapper = new ModelMapper();


    public static ResponseReport mapReportToDTO(ReportStudent reportStudent) {

        return modelMapper.map(reportStudent, ResponseReport.class);
    }

    public static List<ResponseReport> mapReportsToDTOs(List<ReportStudent> reportStudents) {

        List<ResponseReport> list = new ArrayList<>();

        reportStudents.forEach(reportStudent -> list.add(mapReportToDTO(reportStudent)));

        return list;
    }

}
