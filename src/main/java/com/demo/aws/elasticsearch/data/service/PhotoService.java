package com.demo.aws.elasticsearch.data.service;

import com.demo.aws.elasticsearch.data.document.PhotoDocument;
import com.demo.aws.elasticsearch.data.exception.ApplicationException;
import com.demo.aws.elasticsearch.data.model.PhotoDto;
import com.demo.aws.elasticsearch.data.repository.PhotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository = null;

    public PhotoDto createPhoto(PhotoDto document) {
        PhotoDocument photoDocumentEntity = getPhotoDocument(document);
        PhotoDocument photoDocument = photoRepository.save(photoDocumentEntity);
        return getPhotoDto(photoDocument);
    }

    public PhotoDto findById(String id) {
        Optional<PhotoDocument> photoDocument = photoRepository.findById(id);
        if (photoDocument.isPresent()) {
            return getPhotoDto(photoDocument.get());
        }
        throw new ApplicationException(HttpStatus.NOT_FOUND);
    }

    private PhotoDto getPhotoDto(PhotoDocument photoDocument) {
        PhotoDto photoDto = new PhotoDto();
        BeanUtils.copyProperties(photoDocument, photoDto);
        return photoDto;
    }

    private PhotoDocument getPhotoDocument(PhotoDto photoDto) {
        PhotoDocument photoDocument = new PhotoDocument();
        BeanUtils.copyProperties(photoDto, photoDocument);
        photoDocument.setId(UUID.randomUUID().toString());
        return photoDocument;
    }

    public void updatePhoto(String id, PhotoDto document) {
        findById(id);
        PhotoDocument photoDocumentEntity = getPhotoDocument(document);
        photoRepository.save(photoDocumentEntity);
    }

    public List<PhotoDto> findAll() {
        Iterable<PhotoDocument> photoDocuments = photoRepository.findAll();
        return getPhotoDtos(photoDocuments);
    }

    public List<PhotoDto> search(String key, String value) {
        List<PhotoDocument> photoDocuments = photoRepository.findByUsingQuery(key, value);
        if (photoDocuments.isEmpty()) {
            throw new ApplicationException(HttpStatus.NOT_FOUND);
        }
        return getPhotoDtos(photoDocuments);
    }

    private List<PhotoDto> getPhotoDtos(Iterable<PhotoDocument> photoDocuments) {
        List<PhotoDto> photoDtoList = new ArrayList<>();
        photoDocuments.forEach(e -> photoDtoList.add(getPhotoDto(e)));
        return photoDtoList;
    }

    public void deletePhoto(String id) {
        findById(id);
        photoRepository.deleteById(id);
    }

}
