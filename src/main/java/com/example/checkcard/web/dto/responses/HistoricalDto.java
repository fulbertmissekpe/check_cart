package com.example.checkcard.web.dto.responses;

import com.example.checkcard.data.enums.TypeHistorical;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class HistoricalDto {
    private String id;
    private TypeHistorical type;
    private String fileName;
    private Date date;
}
