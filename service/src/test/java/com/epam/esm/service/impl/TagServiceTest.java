package com.epam.esm.service.impl;

import com.epam.esm.config.TestConfig;
import com.epam.esm.dao.impl.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.converter.impl.TagConverter;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.validator.impl.TagValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class TagServiceTest {
    @Autowired
    private TagService tagService;
    @Autowired
    private TagDAO tagDAO;
    @Autowired
    private TagConverter converter;
    @Autowired
    private TagValidator validator;

    private final static String FIRST_NAME = "#cool";
    private final static BigInteger FIRST_ID = BigInteger.ONE;
    private final static String SECOND_NAME = "#beautiful";
    private final static BigInteger SECOND_ID = BigInteger.TWO;
    private final static String WRONG_NAME = "A";
    private final static BigInteger CERTIFICATE_ID = BigInteger.TEN;
    private final static int DAO_UPDATE_RETURN_VALUE = 3;
    private final static int DAO_UPDATE_BAD_RETURN_VALUE = 0;

    @Test
    public void findAllTest() {
        final List<Tag> tags = List.of(new Tag(FIRST_ID, FIRST_NAME), new Tag(SECOND_ID, SECOND_NAME));
        when(tagDAO.findAll()).thenReturn(tags);
        when(converter.convert(any(Tag.class))).thenReturn(new TagDTO(FIRST_ID, FIRST_NAME));
        final Collection<TagDTO> tagsDTO = tagService.findAll();
        assertNotNull(tagsDTO);
        assertFalse(tagsDTO.isEmpty());
        final long count = tagsDTO.stream().filter((p) ->
                p.getName().equals(FIRST_NAME) || p.getName().equals(SECOND_NAME)).count();
        assertEquals(count, 2);
    }

    @Test
    public void findAllByCertificateIdTest() {
        final List<Tag> tags = List.of(new Tag(FIRST_NAME), new Tag(SECOND_NAME));
        when(tagDAO.findAll(CERTIFICATE_ID)).thenReturn(tags);
        when(converter.convert(any(Tag.class))).thenReturn(new TagDTO(FIRST_ID, FIRST_NAME));
        final Collection<TagDTO> tagsDTO = tagService.findAll(CERTIFICATE_ID);
        assertNotNull(tagsDTO);
        assertFalse(tagsDTO.isEmpty());
        final long count = tagsDTO.stream().
                filter((p) -> p.getName().equals(FIRST_NAME) || p.getName().equals(SECOND_NAME)).count();
        assertEquals(count, 2);
    }

    @Test
    public void findTest() {
        final Tag tag = new Tag(FIRST_ID, FIRST_NAME);
        when(tagDAO.find(FIRST_ID)).thenReturn(tag);
        when(converter.convert(tag)).thenReturn(new TagDTO(FIRST_ID, FIRST_NAME));
        final TagDTO tagDTO = tagService.find(FIRST_ID);
        assertNotNull(tagDTO);
        assertEquals(tagDTO.getName(), tag.getName());
    }

    @Test
    public void nullFindTest() {
        when(tagDAO.find(any(BigInteger.class))).thenReturn(null);
        when(converter.convert(any(Tag.class))).thenReturn(null);
        final TagDTO tagDTO = tagService.find(FIRST_ID);
        assertNull(tagDTO);
    }

    @Test
    public void addTest() throws ValidatorException {
        when(tagDAO.add(any(Tag.class))).thenReturn(DAO_UPDATE_RETURN_VALUE);
        when(converter.convert(any(TagDTO.class))).thenReturn(new Tag(FIRST_ID, FIRST_NAME));
        doNothing().when(validator).validate(any(Tag.class));
        final TagDTO tagDTO = assertDoesNotThrow(() -> tagService.add(new TagDTO(FIRST_ID, FIRST_NAME)));
        assertNotNull(tagDTO);
    }

    @Test
    public void addNotAddedTag() throws ValidatorException {
        final Tag tag = new Tag(FIRST_ID, FIRST_NAME);
        when(tagDAO.add(tag)).thenReturn(DAO_UPDATE_BAD_RETURN_VALUE);
        when(converter.convert(any(TagDTO.class))).thenReturn(new Tag(FIRST_ID, FIRST_NAME));
        doNothing().when(validator).validate(any(Tag.class));
        assertDoesNotThrow(() -> tagService.add(new TagDTO(FIRST_ID, FIRST_NAME)));
    }

    @Test
    public void addTagWithBadName() throws ValidatorException {
        doThrow(ValidatorException.class).when(validator).validate(any(Tag.class));
        when(converter.convert(any(Tag.class))).thenReturn(new TagDTO(FIRST_ID, FIRST_NAME));
        assertThrows(ValidatorException.class, () -> tagService.add(new TagDTO(FIRST_ID, WRONG_NAME)));
    }

    @Test
    public void updateTag() throws ValidatorException {
        when(tagDAO.update(any(), any(Tag.class))).thenReturn(DAO_UPDATE_RETURN_VALUE);
        when(converter.convert(any(TagDTO.class))).thenReturn(new Tag(FIRST_ID, FIRST_NAME));
        assertEquals(tagService.update(FIRST_ID, new TagDTO(FIRST_ID, FIRST_NAME)), DAO_UPDATE_RETURN_VALUE);
    }

    @Test
    public void deleteTest(){
        when(tagDAO.delete(any())).thenReturn(DAO_UPDATE_RETURN_VALUE);
        assertTrue(tagService.delete(FIRST_ID));
    }

}