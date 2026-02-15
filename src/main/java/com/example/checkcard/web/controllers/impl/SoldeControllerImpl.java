package com.example.checkcard.web.controllers.impl;

import com.example.checkcard.services.SoldeService;
import com.example.checkcard.web.controllers.SoldeController;
import com.example.checkcard.web.dto.responses.RestResponse;
import com.example.checkcard.web.dto.responses.SoldeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static com.example.checkcard.utils.mappers.Tools.getMapResponseEntity;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/soldes")
public class SoldeControllerImpl implements SoldeController {
    private final SoldeService soldeService;

    @Override
    public ResponseEntity<Map<String, Object>> getAllSoldes() {
        Map<String, Object> response = soldeService.getAllSoldes();
        if (response.get("message").equals("Solde not found")) {
            return new ResponseEntity<>(RestResponse.response(HttpStatus.NOT_FOUND, response.get("message"), null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(RestResponse.response(HttpStatus.OK, response.get("message"), response.get("soldes")), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllWithPagination(int page, int size, String search, String school, String year, String classe) {
        Page<SoldeDto> response = soldeService.getAllWithPagination(page, size, search, school, year, classe);
        Object results = response.getContent();
        Object currentPage = response.getNumber();
        Integer totalPages = response.getTotalPages();
        Object totalItems = response.getTotalElements();
        Boolean isFirst = response.isFirst();
        Boolean isLast = response.isLast();
        String type = "Soldes";
        return new ResponseEntity<>(RestResponse.responsePaginate(HttpStatus.OK, results, currentPage, totalPages, totalItems, isFirst, isLast, type), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> saveSoldes(MultipartFile soldesFile, String ecole) {
        Map<String, Object> response = soldeService.saveSolde(soldesFile, ecole);
        return getMapResponseEntity(response);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getFilterOptions() {
        Map<String, java.util.List<String>> filters = soldeService.getFilterOptions();
        return new ResponseEntity<>(RestResponse.response(HttpStatus.OK, filters, "Successfully"), HttpStatus.OK);
    }

}
