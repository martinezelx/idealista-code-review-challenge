package com.idealista.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "picture")
@Entity
public class PictureVO {
    @Id
    private UUID id;
    private String url;
    private String quality;
    @ManyToOne
    @JoinColumn(name = "ad_id", insertable = false, updatable = false)
    private AdVO ad;
}
