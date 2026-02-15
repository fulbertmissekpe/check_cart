package com.example.checkcard.utils.mappers;

import com.example.checkcard.data.entities.Historical;
import com.example.checkcard.web.dto.responses.HistoricalDto;

public class HistoricalMapper {
    public static HistoricalDto toDto(Historical historical) {
        return HistoricalDto.builder()
                .id(historical.getId())
                .date(historical.getDate())
                .fileName(historical.getFileName())
                .type(historical.getType())
                .build();
    }
}
