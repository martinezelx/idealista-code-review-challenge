package com.idealista.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ad {

    private UUID id;
    private Typology typology;
    private String description;
    private List<Picture> pictures;
    private Integer houseSize;
    private Integer gardenSize;
    private Integer score;
    private Date irrelevantSince;

    public boolean isComplete() {
        return (Typology.GARAGE.equals(typology) && !pictures.isEmpty())
                || (Typology.FLAT.equals(typology) && !pictures.isEmpty() && description != null && !description.isEmpty() && houseSize != null)
                || (Typology.CHALET.equals(typology) && !pictures.isEmpty() && description != null && !description.isEmpty() && houseSize != null && gardenSize != null);
    }
}
