package com.idealista.infrastructure.rest.mappers;

import com.idealista.domain.Ad;
import com.idealista.domain.Picture;
import com.idealista.infrastructure.rest.dto.QualityAdDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface AdToQualityAdDTOMapper {

    @Mapping(source = "pictures", target = "pictureUrls")
    QualityAdDTO adToQualityAd(Ad ad);

    @IterableMapping(qualifiedByName = "pictureToUrl")
    List<String> map(List<Picture> pictures);

    @Named("pictureToUrl")
    default String pictureToUrl(Picture picture) {
        return picture.getUrl();
    }
}
