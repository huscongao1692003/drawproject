package com.drawproject.dev.service;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import com.drawproject.dev.dto.ArtWorkDTO;
import com.drawproject.dev.dto.ResponseDTO;
import com.drawproject.dev.dto.course.ResponsePagingDTO;
import com.drawproject.dev.map.MapArtWork;
import com.drawproject.dev.map.MapCourse;
import com.drawproject.dev.model.Artwork;
import com.drawproject.dev.model.User;
import com.drawproject.dev.repository.ArtworkRepository;
import com.drawproject.dev.repository.CategoryRepository;
import com.drawproject.dev.repository.InstructorRepository;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ArtWorkService {

    @Autowired
    ArtworkRepository artworkRepository;

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    FileService fileService;

    @Autowired
    CategoryRepository categoryRepository;

    public ResponsePagingDTO getArtworks(int page, int eachPage, int instructorId, int categoryId) {

        Pageable pageable = PageRequest.of(page - 1, eachPage);
        Page<Artwork> artworks;

        if(categoryId != 0) {
            artworks = artworkRepository.findByInstructorInstructorIdAndCategoryCategoryIdAndStatus(instructorId, categoryId, DrawProjectConstaints.COMPELETED, pageable);
        } else {
            artworks = artworkRepository.findByInstructorInstructorIdAndStatus(instructorId, DrawProjectConstaints.COMPELETED, pageable);
        }
        ResponsePagingDTO responsePagingDTO = new ResponsePagingDTO(HttpStatus.FOUND, "Artwork found",
                artworks.getTotalElements(), page, artworks.getTotalPages(), eachPage, MapArtWork.mapArtWorkToDTOs(artworks.getContent()));

        if(artworks.isEmpty()) {
            responsePagingDTO.setStatus(HttpStatus.NOT_FOUND);
            responsePagingDTO.setMessage("Instruction might not uploaded any artwork yet");
        }

        return responsePagingDTO;
    }

    public ResponseDTO createArtwork(MultipartFile requestImage, ArtWorkDTO artWorkDTO, HttpSession session) {
        User user = (User) session.getAttribute("loggedInPerson");
        if(!user.getRoles().getName().equals(DrawProjectConstaints.INSTRUCTOR)) {
            return new ResponseDTO(HttpStatus.METHOD_NOT_ALLOWED, "You are not an instructor", null);
        }
        Artwork artwork = new Artwork();
        artwork.setInstructor(instructorRepository.findById(user.getUserId()).orElseThrow());
        artwork.setImage(fileService.uploadFile(requestImage, user.getUserId(), "image", "artworks"));
        artwork.setCategory(categoryRepository.findById(artWorkDTO.getCategoryId()).orElseThrow());
        artworkRepository.save(artwork);

        return new ResponseDTO(HttpStatus.CREATED, "Artwork created", "Your artwork will be reviewed by our");
    }

    public ResponseDTO openArtWork(String message, int artWorkId) {
        Artwork artwork = artworkRepository.findById(artWorkId).orElseThrow();
        artwork.setStatus(DrawProjectConstaints.COMPELETED);
        artworkRepository.save(artwork);

        return new ResponseDTO(HttpStatus.OK, "Artwork checked", message);
    }

    public ResponseDTO deleteArtWork(String message, int artWorkId) {
        Artwork artwork = artworkRepository.findById(artWorkId).orElseThrow();
        artwork.setStatus(DrawProjectConstaints.CLOSE);
        artworkRepository.save(artwork);

        return new ResponseDTO(HttpStatus.OK, "Artwork checked", message);
    }

    public ResponsePagingDTO viewArtWork(int page, int eachPage, String status) {
        Pageable pageable = PageRequest.of(page - 1, eachPage);
        Page<Artwork> artworks;
        if(status == null) {
            artworks = artworkRepository.findAll(pageable);
        } else {
            artworks = artworkRepository.findByStatus(status, pageable);
        }

        ResponsePagingDTO responsePagingDTO = new ResponsePagingDTO(HttpStatus.FOUND, "Artwork found",
                artworks.getTotalElements(), page, artworks.getTotalPages(), eachPage, MapArtWork.mapArtWorkToDTOs(artworks.getContent()));

        if(artworks.isEmpty()) {
            responsePagingDTO.setStatus(HttpStatus.NOT_FOUND);
            responsePagingDTO.setMessage("ArtWorks is empty!");
        }

        return responsePagingDTO;
    }

}
