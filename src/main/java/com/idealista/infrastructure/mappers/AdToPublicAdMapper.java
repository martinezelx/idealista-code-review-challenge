package com.idealista.infrastructure.mappers;

import com.idealista.domain.Ad;
import com.idealista.domain.Picture;
import com.idealista.infrastructure.api.PublicAd;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface AdToPublicAdMapper {

    @Mapping(source = "pictures", target = "pictureUrls")
    PublicAd adToPublicAd(Ad ad);

    @IterableMapping(qualifiedByName = "pictureToUrl")
    List<String> map(List<Picture> pictures);

    @Named("pictureToUrl")
    default String pictureToUrl(Picture picture) {
        return picture.getUrl();
    }
}
