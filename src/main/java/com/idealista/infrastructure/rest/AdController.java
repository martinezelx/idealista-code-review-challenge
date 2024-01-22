package com.idealista.infrastructure.rest;

import com.idealista.application.AdService;
import com.idealista.infrastructure.rest.dto.PublicAdDTO;
import com.idealista.infrastructure.rest.dto.QualityAdDTO;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdController {

    @Autowired
    private AdService adService;

    @GetMapping("/quality")
    @Operation(summary = "Get irrelevant ads list", description = "Retrieves a list of irrelevant ads that has < 40 score")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of irrelevant ads"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Timed(value = "ads.quality", description = "Time taken to retrieve the list of quality ads")
    public ResponseEntity<List<QualityAdDTO>> qualityListing() {
        return ResponseEntity.ok(adService.findQualityAds());
    }

    @GetMapping("/public")
    @Operation(summary = "Get relevant ads list", description = "Retrieves a list of relevant ads that has >= 40 score ordered by score descending")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of relevant ads"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Timed(value = "ads.public", description = "Time taken to retrieve the list of public ads")
    public ResponseEntity<List<PublicAdDTO>> publicListing() {
        return ResponseEntity.ok(adService.findPublicAds());
    }

    @PutMapping("/score")
    @Operation(summary = "Calculate Scores", description = "Calculates the score of all ads and updates the score field in the database")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successfully calculated the score of all the ads"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Timed(value = "ads.score", description = "Time taken to calculate the score of all the ads")
    public ResponseEntity<Void> calculateScore() {
        adService.calculateScores();
        return ResponseEntity.noContent().build();
    }
}
