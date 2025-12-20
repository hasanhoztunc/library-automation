package com.hasanoztunc.library_automation.features.publishinghouse;

import com.hasanoztunc.library_automation.common.payload.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishing-houses")
public class PublishingHouseController {

    private final PublishingHouseService publishingHouseService;

    public PublishingHouseController(PublishingHouseService publishingHouseService) {
        this.publishingHouseService = publishingHouseService;
    }

    @PostMapping("/")
    public ResponseEntity<GenericResponse<PublishingHouseDTO>> createPublishingHouse(@RequestBody PublishingHouseDTO request) {
        var response = publishingHouseService.createPublishingHouse(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<GenericResponse<List<PublishingHouseDTO>>> getAllPublishingHouses() {
        var response = publishingHouseService.getAllPublishingHouses();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<PublishingHouseDTO>> getPublishingHouseById(@PathVariable Long id) {
        var response = publishingHouseService.getPublishingHouseById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<GenericResponse<List<PublishingHouseDTO>>> searchPublishingHousesByName(@RequestParam String name) {
        var response = publishingHouseService.searchPublishingHousesByName(name);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<PublishingHouseDTO>> updatePublishingHouse(
            @PathVariable Long id,
            @RequestBody PublishingHouseDTO request) {
        var response = publishingHouseService.updatePublishingHouse(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Void>> deletePublishingHouse(@PathVariable Long id) {
        var response = publishingHouseService.deletePublishingHouse(id);

        return ResponseEntity.ok(response);
    }
}