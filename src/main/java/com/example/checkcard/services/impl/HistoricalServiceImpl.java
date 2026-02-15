package com.example.checkcard.services.impl;

import com.example.checkcard.data.repositories.HistoricalRepository;
import com.example.checkcard.services.HistoricalService;
import com.example.checkcard.utils.mappers.HistoricalMapper;
import com.example.checkcard.web.dto.responses.HistoricalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HistoricalServiceImpl implements HistoricalService {
    private final HistoricalRepository historicalRepository;
    @Override
    public Map<String, Object> getAll() {
        HashMap<String, Object> response = new HashMap<>();
        if (historicalRepository.findAll().isEmpty()) {
            response.put("message", "Historical not found");
            return response;
        }
        List<HistoricalDto> historicalDtos = historicalRepository.findAll().stream().map(HistoricalMapper::toDto).toList();
        response.put("historicals", historicalDtos);
        response.put("message", "Successfully");
        return response;
    }
}
