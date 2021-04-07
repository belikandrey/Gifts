package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Collection;

/**
 * Rest controller for tags
 *
 * @author Andrey Belik
 * @version 1.0
 */
@RestController
@RequestMapping("/tags")
public class TagController {
    private final EntityService<TagDTO, BigInteger> tagService;

    /**
     * Constructor
     *
     * @param tagService {@link com.epam.esm.service.EntityService}
     */
    @Autowired
    public TagController(EntityService<TagDTO, BigInteger> tagService) {
        this.tagService = tagService;
    }

    /**
     * Find all tags method
     *
     * @param certificateId certificate id
     * @return the response entity
     */
    @GetMapping()
    public ResponseEntity<Collection<TagDTO>> findAll(@RequestParam(value = "certificateId", required = false) BigInteger certificateId) {
        Collection<TagDTO> tags = certificateId == null ? tagService.findAll() : tagService.findAll(certificateId);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    /**
     * Find tag by id
     *
     * @param tagId tag id
     * @return response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable("id") BigInteger tagId) {
        final TagDTO tag = tagService.find(tagId);
        return tag != null ?
                new ResponseEntity<>(tag, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Create tag
     *
     * @param tag tag for create
     * @return response entity
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody TagDTO tag) {
        try {
            tagService.add(tag);
            return new ResponseEntity<>(tag, HttpStatus.OK);
        } catch (ValidatorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Delete tag by id
     *
     * @param id tag id
     * @return response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") BigInteger id) {
        return tagService.delete(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
