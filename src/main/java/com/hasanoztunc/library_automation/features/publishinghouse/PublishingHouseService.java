package com.hasanoztunc.library_automation.features.publishinghouse;

import com.hasanoztunc.library_automation.common.payload.GenericResponse;

import java.util.List;

public interface PublishingHouseService {

    GenericResponse<PublishingHouseDTO> createPublishingHouse(PublishingHouseDTO request);

    GenericResponse<List<PublishingHouseDTO>> getAllPublishingHouses();

    GenericResponse<PublishingHouseDTO> getPublishingHouseById(Long id);

    GenericResponse<List<PublishingHouseDTO>> searchPublishingHousesByName(String name);

    GenericResponse<PublishingHouseDTO> updatePublishingHouse(Long id, PublishingHouseDTO request);

    GenericResponse<Void> deletePublishingHouse(Long id);
}