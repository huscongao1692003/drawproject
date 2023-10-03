package com.drawproject.dev.map;

import com.drawproject.dev.dto.CollectionDTO;
import com.drawproject.dev.model.Collection;
import org.modelmapper.ModelMapper;

public class MapCollection {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static CollectionDTO mapCollectionToDTO(Collection collection) {
        return modelMapper.map(collection, CollectionDTO.class);
    }

}
