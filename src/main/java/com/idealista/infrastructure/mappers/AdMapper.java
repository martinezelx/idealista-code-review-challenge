package com.idealista.infrastructure.mappers;

import com.idealista.domain.Ad;
import com.idealista.domain.Picture;
import com.idealista.infrastructure.persistence.entities.AdVO;
import com.idealista.infrastructure.persistence.entities.PictureVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface AdMapper {

    @Mapping(source = "pictures", target = "pictures")
    Ad toDomain(AdVO adVO);

    @Mapping(source = "pictures", target = "pictures")
    AdVO toPersistence(Ad ad);

    List<Picture> toDomain(List<PictureVO> pictureVOs);

    List<PictureVO> toPersistence(List<Picture> pictures);

    Picture toDomain(PictureVO pictureVO);

    PictureVO toPersistence(Picture picture);
}
