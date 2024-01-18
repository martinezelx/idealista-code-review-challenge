package com.idealista.infrastructure.api;

import com.idealista.application.AdsService;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdsController {

    @Autowired
    private AdsService adsService;

    @GetMapping("/ads/quality")
    @Operation(summary = "Get quality ads list", description = "Retrieves a list of ads based on quality criteria")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of ads")
    @Timed(value = "ads.quality", description = "Time taken to retrieve the list of quality ads")
    public ResponseEntity<List<QualityAd>> qualityListing() {
        return ResponseEntity.ok(adsService.findQualityAds());
    }

    @GetMapping("/ads/public")
    @Operation(summary = "Get public ads list", description = "Retrieves a list of ads based on public criteria")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of ads")
    @Timed(value = "ads.public", description = "Time taken to retrieve the list of public ads")
    public ResponseEntity<List<PublicAd>> publicListing() {
        return ResponseEntity.ok(adsService.findPublicAds());
    }

    @GetMapping("/ads/score")
    @Operation(summary = "Calculate Scores", description = "Calculates the score of all the ads")
    @ApiResponse(responseCode = "202", description = "Successfully calculated the score of all the ads")
    @Timed(value = "ads.score", description = "Time taken to calculate the score of all the ads")
    public ResponseEntity<Void> calculateScore() {
        adsService.calculateScores();
        return ResponseEntity.accepted().build();
    }
}
