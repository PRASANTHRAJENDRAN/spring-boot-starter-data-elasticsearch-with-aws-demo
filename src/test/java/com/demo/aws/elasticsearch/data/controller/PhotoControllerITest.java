package com.demo.aws.elasticsearch.data.controller;

import com.demo.aws.elasticsearch.data.Application;
import com.demo.aws.elasticsearch.data.model.PhotoDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PhotoControllerITest {

    @Autowired
    private PhotoController photoController = null;

    @Test
    void createPhotoTest() {
        PhotoDto photoDto = createPhotoDtoResponseEntity();
        assertNotNull(photoDto);
    }

    @Test
    void getPhotoTest() {
        PhotoDto photoDto = createPhotoDtoResponseEntity();
        ResponseEntity<PhotoDto> responseEntity = photoController.findById(photoDto.getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        responseEntity = photoController.findById(UUID.randomUUID().toString());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void getAllPhotoTest() {
        createPhotoDtoResponseEntity();
        ResponseEntity<List<PhotoDto>> responseEntity = photoController.findAll();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertFalse(responseEntity.getBody().isEmpty());
    }

    @Test
    void updatePhotoTest() {
        PhotoDto photoDto = createPhotoDtoResponseEntity();
        String updatedTitle = "Title Updated";
        photoDto.setTitle(updatedTitle);
        ResponseEntity<Void> responseEntity = photoController.updatePhoto(photoDto.getId(), photoDto);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        ResponseEntity<PhotoDto> byId = photoController.findById(photoDto.getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(byId.getBody());
        assertEquals(updatedTitle, byId.getBody().getTitle());
        responseEntity = photoController.updatePhoto(UUID.randomUUID().toString(), photoDto);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void deletePhotoTest() {
        PhotoDto photoDto = createPhotoDtoResponseEntity();
        ResponseEntity<Void> responseEntity = photoController.deletePhoto(photoDto.getId());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        responseEntity = photoController.deletePhoto(UUID.randomUUID().toString());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void searchPhotoTest() {
        PhotoDto photoDto = createPhotoDtoResponseEntity();
        ResponseEntity<List<PhotoDto>> responseEntity = photoController.search("title", photoDto.getTitle());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        Optional<PhotoDto> any = responseEntity.getBody().stream().findAny();
        assertTrue(any.isPresent());
        assertEquals(photoDto.getId(), any.get().getId());
        responseEntity = photoController.search("title", UUID.randomUUID().toString());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }


    private PhotoDto createPhotoDtoResponseEntity() {
        PhotoDto photoDto = new PhotoDto();
        photoDto.setThumbnailUrl(UUID.randomUUID().toString());
        photoDto.setTitle(UUID.randomUUID().toString());
        photoDto.setUrl(UUID.randomUUID().toString());
        ResponseEntity<PhotoDto> photoDtoResponseEntity = photoController.createPhoto(photoDto);
        assertNotNull(photoDtoResponseEntity);
        assertNotNull(photoDtoResponseEntity.getBody());
        assertEquals(HttpStatus.CREATED, photoDtoResponseEntity.getStatusCode());
        return photoDtoResponseEntity.getBody();
    }
}
