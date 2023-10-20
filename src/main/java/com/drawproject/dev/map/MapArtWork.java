package com.drawproject.dev.map;

import com.drawproject.dev.dto.ArtWorkDTO;
import com.drawproject.dev.model.Artwork;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class MapArtWork {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static ArtWorkDTO mapArtWorkToDTO(Artwork artwork) {
        return modelMapper.map(artwork, ArtWorkDTO.class);
    }

    public static List<ArtWorkDTO> mapArtWorkToDTOs(List<Artwork> artworks) {

        List<ArtWorkDTO> list = new ArrayList<>();

        artworks.forEach(artwork -> list.add(mapArtWorkToDTO(artwork)));

        return list;
    }

}
