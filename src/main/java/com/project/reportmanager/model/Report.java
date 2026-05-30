package com.project.reportmanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity // Bu sınıfın bir veritabanı tablosu olduğunu belirtir
public class Report {

    @Id // Birincil anahtar (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID'yi veritabanı otomatik versin
    private Long id;

    @NotBlank(message = "Rapor adı boş olamaz")
    @Size(min = 2, max = 30, message = "Ad 2-30 karakter arası olmalı")
    private String name;
}