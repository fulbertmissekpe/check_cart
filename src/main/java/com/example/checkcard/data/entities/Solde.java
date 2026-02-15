package com.example.checkcard.data.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(collection = "soldes")
public class Solde extends AbstractEntity {
    @Field("amount")
    private double amount;
}
