package com.example.checkcard.services.impl;

import com.example.checkcard.data.entities.Historical;
import com.example.checkcard.data.entities.Solde;
import com.example.checkcard.data.enums.TypeHistorical;
import com.example.checkcard.data.repositories.HistoricalRepository;
import com.example.checkcard.data.repositories.SoldeRepository;
import com.example.checkcard.services.SoldeService;
import com.example.checkcard.utils.mappers.SoldeMapper;
import com.example.checkcard.web.dto.responses.SoldeDto;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

import static com.example.checkcard.utils.mappers.Tools.getCellValue;

@Service
@RequiredArgsConstructor
public class SoldeServiceImpl implements SoldeService {
    private final SoldeRepository soldeRepository;
    private final HistoricalRepository historicalRepository;
    @Override
    public Map<String, Object> getSolde(String id) {
        return Map.of();
    }

    @Override
    public Map<String, Object> getByMatricule(String matricule) {
        return Map.of();
    }

    @Override
    public Map<String, Object> saveSolde(MultipartFile soldesFile, String ecole) {
        List<Solde> soldes = new ArrayList<>();
        HashMap<String, Object> response = new HashMap<>();
        String fileName = soldesFile.getOriginalFilename();
        try (InputStream inputStream = soldesFile.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                Cell firstCell = row.getCell(0);
                if (firstCell == null || getCellValue(firstCell).isEmpty()) {
                    continue;
                }
                Solde solde = SoldeMapper.rowToEntity(row, ecole);
                soldes.add(solde);
            }
            Solde solde = soldeRepository.findByEcole(ecole);
            if (solde != null) soldeRepository.deleteAllByEcole(ecole);
            soldeRepository.saveAll(soldes);
            workbook.close();
            response.put("message", "Successfully");
            historicalRepository.save(Historical.builder()
                            .type(TypeHistorical.SOLDES)
                            .fileName(fileName)
                            .date(new Date())
                    .build());
        } catch (Exception e) {
            response.put("message", e.getMessage());
        }
        return response;
    }

    @Override
    public Map<String, Object> getAllSoldes() {
        List<Solde> soldes = soldeRepository.findAll();
        Map<String, Object> response = new HashMap<>();
        if (soldes.isEmpty()) {
            response.put("message", "Solde not found");
            return response;
        }
        List<SoldeDto> soldeDtos = soldes.stream().map(SoldeMapper::toDto).toList();
        response.put("soldes", soldeDtos);
        response.put("message", "Successfully");
        return response;
    }


    @Override
    public Page<SoldeDto> getAllWithPagination(int page, int size, String search, String school, String year, String classe) {
        List<Solde> allSoldes = soldeRepository.findAll();
        List<SoldeDto> filtered = allSoldes.stream()
                .map(SoldeMapper::toDto)
                .filter(solde -> {
                    if (search != null && !search.isEmpty()) {
                        String lowerSearch = search.toLowerCase();
                        if (!solde.getNomComplet().toLowerCase().contains(lowerSearch) &&
                            !solde.getMatricule().toLowerCase().contains(lowerSearch)) {
                            return false;
                        }
                    }
                    if (school != null && !school.isEmpty()) {
                        if (!solde.getEcole().equals(school)) {
                            return false;
                        }
                    }
                    return true;
                })
                .toList();
        int pageIndex = Math.max(0, page - 1);
        int start = pageIndex * size;
        int end = Math.min(start + size, filtered.size());
        List<SoldeDto> paginated = filtered.subList(start, end);
        Pageable pageable = PageRequest.of(pageIndex, size);
        return new PageImpl<>(paginated, pageable, filtered.size());
    }

    @Override
    public Map<String, List<String>> getFilterOptions() {
        Map<String, List<String>> options = new HashMap<>();
        List<Solde> allSoldes = soldeRepository.findAll();
        
        Set<String> schoolSet = new HashSet<>();
        
        for (Solde solde : allSoldes) {
            if (solde.getEcole() != null && !solde.getEcole().isEmpty()) {
                schoolSet.add(solde.getEcole());
            }
        }
        
        List<String> schools = new ArrayList<>(schoolSet);
        schools.sort(null);
        
        options.put("schools", schools);
        
        return options;
    }

}
