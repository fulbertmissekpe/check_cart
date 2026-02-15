package com.example.checkcard.services;

import com.example.checkcard.web.dto.responses.SoldeDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface SoldeService {
    Map<String, Object> getSolde(String id);
    Map<String, Object> getByMatricule(String matricule);
    Map<String, Object> saveSolde(MultipartFile soldesFile, String ecole);
    Map<String, Object> getAllSoldes();
    Page<SoldeDto> getAllWithPagination(int page, int size, String search, String school, String year, String classe);
    Map<String, List<String>> getFilterOptions();
}
