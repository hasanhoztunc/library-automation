package com.hasanoztunc.library_automation.features.publishinghouse;

import com.hasanoztunc.library_automation.common.payload.GenericResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PublishingHouseServiceImpl implements PublishingHouseService {

    private final PublishingHouseRepository publishingHouseRepository;
    private final ModelMapper modelMapper;

    public PublishingHouseServiceImpl(
            PublishingHouseRepository publishingHouseRepository,
            ModelMapper modelMapper
    ) {
        this.publishingHouseRepository = publishingHouseRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public GenericResponse<PublishingHouseDTO> createPublishingHouse(PublishingHouseDTO request) {
        var publishingHouse = modelMapper.map(request, PublishingHouse.class);

        var savedPublishingHouse = publishingHouseRepository.save(publishingHouse);
        var responseDTO = modelMapper.map(savedPublishingHouse, PublishingHouseDTO.class);

        return GenericResponse.success(responseDTO);
    }

    @Override
    public GenericResponse<List<PublishingHouseDTO>> getAllPublishingHouses() {
        var publishingHouses = publishingHouseRepository.findAll();

        var publishingHouseDTOs = publishingHouses.stream()
                .map(ph -> modelMapper.map(ph, PublishingHouseDTO.class))
                .toList();

        return GenericResponse.success(publishingHouseDTOs);
    }

    @Override
    public GenericResponse<PublishingHouseDTO> getPublishingHouseById(Long id) {
        var publishingHouseOptional = publishingHouseRepository.findById(id);

        if (!publishingHouseOptional.isPresent()) {
            return GenericResponse.fail("Publishing house not found with id: " + id);
        }

        var publishingHouse = publishingHouseOptional.get();
        var responseDTO = modelMapper.map(publishingHouse, PublishingHouseDTO.class);

        return GenericResponse.success(responseDTO);
    }

    @Override
    public GenericResponse<List<PublishingHouseDTO>> searchPublishingHousesByName(String name) {
        var publishingHouses = publishingHouseRepository.findByNameContainingIgnoreCase(name);

        var publishingHouseDTOs = publishingHouses.stream()
                .map(ph -> modelMapper.map(ph, PublishingHouseDTO.class))
                .toList();

        return GenericResponse.success(publishingHouseDTOs);
    }

    @Transactional
    @Override
    public GenericResponse<PublishingHouseDTO> updatePublishingHouse(Long id, PublishingHouseDTO request) {
        var publishingHouseOptional = publishingHouseRepository.findById(id);

        if (!publishingHouseOptional.isPresent()) {
            return GenericResponse.fail("Publishing house not found with id: " + id);
        }

        var publishingHouse = publishingHouseOptional.get();
        publishingHouse.setName(request.getName());

        var updatedPublishingHouse = publishingHouseRepository.save(publishingHouse);
        var responseDTO = modelMapper.map(updatedPublishingHouse, PublishingHouseDTO.class);

        return GenericResponse.success(responseDTO);
    }

    @Transactional
    @Override
    public GenericResponse<Void> deletePublishingHouse(Long id) {
        var publishingHouseOptional = publishingHouseRepository.findById(id);

        if (!publishingHouseOptional.isPresent()) {
            return GenericResponse.fail("Publishing house not found with id: " + id);
        }

        publishingHouseRepository.deleteById(id);

        return GenericResponse.success(null);
    }
}