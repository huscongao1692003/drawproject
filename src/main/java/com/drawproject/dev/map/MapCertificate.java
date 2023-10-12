package com.drawproject.dev.map;

import com.drawproject.dev.dto.CertificateDTO;
import com.drawproject.dev.model.Certificate;
import com.drawproject.dev.ultils.ImageUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class MapCertificate {
    private static final ModelMapper modelMapper = new ModelMapper();


    public static CertificateDTO mapCertificateToDTO(Certificate certificate) {
        return modelMapper.map(certificate, CertificateDTO.class);
    }

    public static List<CertificateDTO> mapCertificateToDTOs(List<Certificate> certificates) {
        List<CertificateDTO> list = new ArrayList<>();

        certificates.forEach(certificate -> list.add(mapCertificateToDTO(certificate)));

        return list;
    }

}
