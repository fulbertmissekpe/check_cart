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
@Document(collection = "students")
public class Student extends AbstractEntity {
    @Field("email")
    private String email;
    @Field("card")
    private byte[] card;
}
