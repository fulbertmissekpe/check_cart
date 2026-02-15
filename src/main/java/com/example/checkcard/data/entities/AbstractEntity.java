package com.example.checkcard.data.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AbstractEntity {
    @Id
    protected String id;
    @Field("matricule")
    protected String matricule;
    @Field("nomComplet")
    protected String nomComplet;
    @Field("classe")
    protected String classe;
    @Field("ecole")
    protected String ecole;
}
