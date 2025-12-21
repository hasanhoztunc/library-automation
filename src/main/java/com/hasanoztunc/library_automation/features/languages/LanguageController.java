package com.hasanoztunc.library_automation.features.languages;

import com.hasanoztunc.library_automation.common.payload.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
public class LanguageController {

    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @PostMapping("/")
    public ResponseEntity<GenericResponse<LanguageDTO>> createLanguage(@RequestBody LanguageDTO request) {
        var response = languageService.createLanguage(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<GenericResponse<List<LanguageDTO>>> getAllLanguages() {
        var response = languageService.getAllLanguages();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<LanguageDTO>> getLanguageById(@PathVariable Long id) {
        var response = languageService.getLanguageById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<GenericResponse<List<LanguageDTO>>> searchLanguagesByName(@RequestParam String name) {
        var response = languageService.searchLanguagesByName(name);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<LanguageDTO>> updateLanguage(@PathVariable Long id, @RequestBody LanguageDTO request) {
        var response = languageService.updateLanguage(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Void>> deleteLanguage(@PathVariable Long id) {
        var response = languageService.deleteLanguage(id);

        return ResponseEntity.ok(response);
    }
}