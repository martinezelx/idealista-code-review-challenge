package com.idealista.application;

import com.idealista.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdServiceImplTest {

    @Mock
    private AdRepository adRepository;

    @InjectMocks
    private AdServiceImpl scoreService;

    @Test
    public void calculateScoresTest() {
        when(adRepository.findAllAds()).thenReturn(Arrays.asList(irrelevantAd(), relevantAd()));
        scoreService.calculateScores();
        verify(adRepository).findAllAds();
        verify(adRepository, times(2)).save(any());
    }

    private Ad relevantAd() {
        return Ad.builder()
                .id(1)
                .typology(Typology.FLAT)
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras dictum felis elit, vitae cursus erat blandit vitae. Maecenas eget efficitur massa. Maecenas ut dolor eget enim consequat iaculis vitae nec elit. Maecenas eu urna nec massa feugiat pharetra. Sed eu quam imperdiet orci lobortis fermentum. Sed odio justo, congue eget iaculis.")
                .pictures(Arrays.asList(
                        Picture.builder().id(1).url("http://urldeprueba.com/1").quality(Quality.HD).build(),
                        Picture.builder().id(2).url("http://urldeprueba.com/2").quality(Quality.HD).build()))
                .houseSize(50)
                .gardenSize(null)
                .build();
    }

    private Ad irrelevantAd() {
        return Ad.builder()
                .id(1)
                .typology(Typology.FLAT)
                .description("")
                .pictures(Collections.emptyList())
                .houseSize(100)
                .gardenSize(null)
                .build();
    }

}