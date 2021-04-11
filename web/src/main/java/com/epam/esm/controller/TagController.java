package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  private final TagService tagService;

  /**
   * Constructor
   *
   * @param tagService {@link com.epam.esm.service.EntityService}
   */
  @Autowired
  public TagController(TagService tagService) {
    this.tagService = tagService;
  }

  /**
   * Find all tags method
   *
   * <p>//* @param certificateId certificate id
   *
   * @return the response entity
   */
  @GetMapping()
  public ResponseEntity<Collection<TagDTO>> findAll() {
    Collection<TagDTO> tags = tagService.findAll();
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
    final TagDTO tag = tagService.findById(tagId);
    return new ResponseEntity<>(tag, HttpStatus.OK);
  }

  /**
   * Create tag
   *
   * @param tag tag for create
   * @return response entity
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> create(@RequestBody TagDTO tag) throws ValidatorException {
    tag = tagService.add(tag);
    return new ResponseEntity<>(tag, HttpStatus.OK);
  }

  /**
   * Delete tag by id
   *
   * @param id tag id
   * @return response entity
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") BigInteger id) {
    tagService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
