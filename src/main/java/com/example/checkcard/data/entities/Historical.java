package com.example.checkcard.data.entities;

import com.example.checkcard.data.enums.TypeHistorical;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Getter
@Setter
@Builder
@Document(collection = "historical")
public class Historical {
    @Id
    private String id;
    @Field("type")
    private TypeHistorical type;
    @Field("filename")
    private String fileName;
    @Field("date")
    private Date date;
}
