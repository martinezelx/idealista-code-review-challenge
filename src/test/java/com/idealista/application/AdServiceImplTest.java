package com.idealista.application;

import com.idealista.domain.Ad;
import com.idealista.domain.AdRepository;
import com.idealista.infrastructure.rest.dto.PublicAdDTO;
import com.idealista.infrastructure.rest.dto.QualityAdDTO;
import com.idealista.infrastructure.rest.mappers.AdToPublicAdDTOMapper;
import com.idealista.infrastructure.rest.mappers.AdToQualityAdDTOMapper;
import com.idealista.utils.TestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AdServiceImplTest {

    @Mock
    private AdRepository adRepository;

    @Mock
    private AdToPublicAdDTOMapper adToPublicAdDTOMapper;

    @Mock
    private AdToQualityAdDTOMapper adToQualityAdDTOMapper;

    @InjectMocks
    private AdServiceImpl adService;

    @Test
    public void findPublicAdsTest() {
        PublicAdDTO publicAdDTO = TestBuilder.buildPublicAd();
        Ad ad = TestBuilder.buildAd();
        when(adRepository.findRelevantAds()).thenReturn(Collections.singletonList(ad));
        when(adToPublicAdDTOMapper.adToPublicAd(ad)).thenReturn(publicAdDTO);

        List<PublicAdDTO> result = adService.findPublicAds();

        assertEquals(1, result.size());
        assertEquals(publicAdDTO, result.get(0));
        verify(adRepository).findRelevantAds();
    }

    @Test
    public void findQualityAdsTest() {
        QualityAdDTO qualityAdDTO = TestBuilder.buildQualityAd();
        Ad ad = TestBuilder.buildAd();
        when(adRepository.findIrrelevantAds()).thenReturn(Collections.singletonList(ad));
        when(adToQualityAdDTOMapper.adToQualityAd(ad)).thenReturn(qualityAdDTO);

        List<QualityAdDTO> result = adService.findQualityAds();

        assertEquals(1, result.size());
        assertEquals(qualityAdDTO, result.get(0));
        verify(adRepository).findIrrelevantAds();
    }

    @Test
    public void calculateScoresTest() {
        when(adRepository.findAllAds()).thenReturn(Arrays.asList(TestBuilder.buildAd(), TestBuilder.buildAd()));
        adService.calculateScores();
        verify(adRepository).findAllAds();
        verify(adRepository, times(2)).save(any());
    }
}