package com.idealista.infrastructure.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Details about the quality ad")
public class QualityAd {
    @Schema(description = "Unique identifier of the ad", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Typology of the ad", example = "flat")
    private String typology;

    @Schema(description = "Description of the ad", example = "Beautiful flat in the city center")
    private String description;

    @Schema(description = "List of picture URLs of the ad")
    private List<String> pictureUrls;

    @Schema(description = "Size of the house", example = "120")
    private Integer houseSize;

    @Schema(description = "Size of the garden", example = "30")
    private Integer gardenSize;

    @Schema(description = "Score of the ad", example = "80")
    private Integer score;

    @Schema(description = "Date when the ad became irrelevant")
    private Date irrelevantSince;
}