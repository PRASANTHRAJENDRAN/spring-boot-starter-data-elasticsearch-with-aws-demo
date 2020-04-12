package com.demo.aws.elasticsearch.data.controller;

import com.demo.aws.elasticsearch.data.model.PhotoDto;
import com.demo.aws.elasticsearch.data.service.PhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    private PhotoService service = null;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PhotoDto> createPhoto(@RequestBody PhotoDto document) {
        return new ResponseEntity<>(service.createPhoto(document), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updatePhoto(@PathVariable String id, @RequestBody PhotoDto document) {
        service.updatePhoto(id, document);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PhotoDto> findById(@PathVariable String id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PhotoDto>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PhotoDto>> search(@RequestParam(value = "key") String key, @RequestParam(value = "value") String value) {
        return new ResponseEntity<>(service.search(key, value), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable String id) {
        service.deletePhoto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
