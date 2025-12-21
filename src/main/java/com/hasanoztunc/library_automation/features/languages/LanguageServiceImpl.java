package com.hasanoztunc.library_automation.features.languages;

import com.hasanoztunc.library_automation.common.payload.GenericResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;
    private final ModelMapper modelMapper;

    public LanguageServiceImpl(
            LanguageRepository languageRepository,
            ModelMapper modelMapper
    ) {
        this.languageRepository = languageRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public GenericResponse<LanguageDTO> createLanguage(LanguageDTO request) {
        var language = modelMapper.map(request, Language.class);

        var savedLanguage = languageRepository.save(language);
        var savedLanguageDTO = modelMapper.map(savedLanguage, LanguageDTO.class);

        return GenericResponse.success(savedLanguageDTO);
    }

    @Override
    public GenericResponse<List<LanguageDTO>> getAllLanguages() {
        var languages = languageRepository.findAll();
        var languageDTOs = languages.stream()
                .map(language -> modelMapper.map(language, LanguageDTO.class))
                .toList();

        return GenericResponse.success(languageDTOs);
    }

    @Override
    public GenericResponse<LanguageDTO> getLanguageById(Long id) {
        var languageOptional = languageRepository.findById(id);

        if (!languageOptional.isPresent()) {
            return GenericResponse.fail("Language not found with id: " + id);
        }

        var language = languageOptional.get();
        var languageDTO = modelMapper.map(language, LanguageDTO.class);

        return GenericResponse.success(languageDTO);
    }

    @Override
    public GenericResponse<List<LanguageDTO>> searchLanguagesByName(String name) {
        var languages = languageRepository.findByNameContainingIgnoreCase(name);

        var languageDTOs = languages.stream()
                .map(language -> modelMapper.map(language, LanguageDTO.class))
                .toList();

        return GenericResponse.success(languageDTOs);
    }

    @Transactional
    @Override
    public GenericResponse<LanguageDTO> updateLanguage(Long id, LanguageDTO request) {
        var languageOptional = languageRepository.findById(id);

        if (!languageOptional.isPresent()) {
            return GenericResponse.fail("Language not found with id: " + id);
        }

        var language = languageOptional.get();
        language.setName(request.getName());

        var updatedLanguage = languageRepository.save(language);
        var updatedLanguageDTO = modelMapper.map(updatedLanguage, LanguageDTO.class);

        return GenericResponse.success(updatedLanguageDTO);
    }

    @Transactional
    @Override
    public GenericResponse<Void> deleteLanguage(Long id) {
        var languageOptional = languageRepository.findById(id);

        if (!languageOptional.isPresent()) {
            return GenericResponse.fail("Language not found with id: " + id);
        }

        languageRepository.deleteById(id);
        return GenericResponse.empty();
    }
}