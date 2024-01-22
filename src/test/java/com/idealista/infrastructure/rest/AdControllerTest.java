package com.idealista.infrastructure.rest;

import com.idealista.application.AdService;
import com.idealista.infrastructure.rest.dto.PublicAdDTO;
import com.idealista.infrastructure.rest.dto.QualityAdDTO;
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
        QualityAdDTO qualityAdDTO = new QualityAdDTO();
        when(adService.findQualityAds()).thenReturn(Collections.singletonList(qualityAdDTO));

        mockMvc = MockMvcBuilders.standaloneSetup(adController).build();
        mockMvc.perform(get("/ads/quality")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void publicListingTest() throws Exception {
        PublicAdDTO publicAdDTO = new PublicAdDTO();
        when(adService.findPublicAds()).thenReturn(Collections.singletonList(publicAdDTO));

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