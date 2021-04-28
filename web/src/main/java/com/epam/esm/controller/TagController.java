package com.epam.esm.controller;

import com.epam.esm.dao.pagination.Pageable;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.hateoas.HateoasResolver;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

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

  private final HateoasResolver hateoasResolver;
  /**
   * Constructor
   *
   * @param tagService {@link com.epam.esm.service.EntityService}
   */
  @Autowired
  public TagController(TagService tagService, HateoasResolver hateoasResolver) {
    this.tagService = tagService;
    this.hateoasResolver = hateoasResolver;
  }

  /**
   * Find all tags method
   *
   * <p>//* @param certificateId certificate id
   *
   * @return the response entity
   */
  @GetMapping()
  public CollectionModel<TagDTO> findAll(
      @RequestParam(name = "page", defaultValue = "1", required = false) int page,
      @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
    Pageable pageable = new Pageable(size, page);
    Collection<TagDTO> tags = tagService.findAll(pageable);
    final Long count = tagService.count();
    tags.forEach(hateoasResolver::addLinksForTag);
    return hateoasResolver.getModelForTags(tags,pageable,count);
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
    hateoasResolver.addLinksForTag(tag);
    return new ResponseEntity<>(tag, HttpStatus.OK);
  }

  /**
   * Create tag
   *
   * @param tag tag for create
   * @return response entity
   * @throws ValidatorException if entity is invalid
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> create(@RequestBody TagDTO tag) throws ValidatorException {
    tag = tagService.add(tag);
    hateoasResolver.addLinksForTag(tag);
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

  @GetMapping("/most-popular")
  public ResponseEntity<?> findMostPopularTag() {
    final TagDTO mostPopularTag = tagService.findMostPopularTag();
    hateoasResolver.addLinksForTag(mostPopularTag);
    return new ResponseEntity<>(mostPopularTag, HttpStatus.OK);
  }

}
