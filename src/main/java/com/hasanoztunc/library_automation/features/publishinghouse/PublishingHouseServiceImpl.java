package com.hasanoztunc.library_automation.features.publishinghouse;

import com.hasanoztunc.library_automation.common.payload.GenericResponse;
import com.hasanoztunc.library_automation.features.book.BookResponse;
import com.hasanoztunc.library_automation.features.book.BookResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public GenericResponse<BookResponse> getBooksByPublishingHouseId(
            Long publishingHouseId,
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortOrder
    ) {
        var sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        var pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        var pageBooks = publishingHouseRepository.findBooksByPublishingHouseId(publishingHouseId, pageDetails);

        var books = pageBooks.getContent();

        var bookDTOs = books.stream()
                .map(book -> modelMapper.map(book, BookResponseDTO.class))
                .toList();

        var bookResponse = new BookResponse();
        bookResponse.setBooks(bookDTOs);
        bookResponse.setPageNumber(pageBooks.getNumber());
        bookResponse.setPageSize(pageBooks.getSize());
        bookResponse.setTotalElements(pageBooks.getTotalElements());
        bookResponse.setTotalPages(pageBooks.getTotalPages());
        bookResponse.setLastPage(pageBooks.isLast());

        return GenericResponse.success(bookResponse);
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

        return GenericResponse.empty();
    }
}