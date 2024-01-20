package com.idealista.infrastructure.api;

import com.idealista.application.AdService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AdControllerTest {

    @Mock
    private AdService adService;

    @InjectMocks
    private AdController adController;

    private MockMvc mockMvc;

    @Test
    public void qualityListingTest() throws Exception {
        QualityAd qualityAd = new QualityAd();
        when(adService.findQualityAds()).thenReturn(Collections.singletonList(qualityAd));

        mockMvc = MockMvcBuilders.standaloneSetup(adController).build();
        mockMvc.perform(get("/ads/quality")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void publicListingTest() throws Exception {
        PublicAd publicAd = new PublicAd();
        when(adService.findPublicAds()).thenReturn(Collections.singletonList(publicAd));

        mockMvc = MockMvcBuilders.standaloneSetup(adController).build();
        mockMvc.perform(get("/ads/public")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void calculateScoreTest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(adController).build();
        mockMvc.perform(put("/ads/score")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}