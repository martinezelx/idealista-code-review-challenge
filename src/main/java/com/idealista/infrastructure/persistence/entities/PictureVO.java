package com.idealista.infrastructure.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PictureVO {

    @Id
    private Integer id;
    private String url;
    private String quality;

    @ManyToOne
    @JoinColumn(name = "ad_id", insertable = false, updatable = false)
    private AdVO ad;
}