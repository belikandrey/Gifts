package com.epam.esm.hateoas;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.TagController;
import com.epam.esm.controller.UserController;
import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.UserDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Collection;

import static org.springframework.hateoas.PagedModel.PageMetadata;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HateoasResolver {
  public void addLinksForTag(TagDTO tagDTO) {
    Link linkForSelf = linkTo(TagController.class).slash(tagDTO.getId()).withSelfRel();
    Link deleteLink =
        linkTo(methodOn(TagController.class).delete(tagDTO.getId())).withRel("delete");
    tagDTO.add(linkForSelf, deleteLink);
  }

  public CollectionModel<TagDTO> getModelForTags(
          Collection<TagDTO> tags, PaginationSetting paginationSetting, Long count) {
    Link linkForSelf = linkTo(TagController.class).withSelfRel();
    Link createLink = linkTo(TagController.class).withRel("create");
    final PageMetadata pageMetadata = getPageMetadata(paginationSetting, count);
    return PagedModel.of(tags, pageMetadata, linkForSelf, createLink);
  }

  private PageMetadata getPageMetadata(PaginationSetting paginationSetting, Long count) {
    return new PageMetadata(
        paginationSetting.getSize(),
        paginationSetting.getPage(),
        count,
        (long) Math.ceil(count.doubleValue() / paginationSetting.getSize()));
  }

  public void addLinksForCertificate(CertificateDTO certificateDTO) {
    certificateDTO.getTags().forEach(this::addLinksForTag);
    Link linkForSelf =
        linkTo(CertificateController.class).slash(certificateDTO.getId()).withSelfRel();
    Link deleteLink =
        linkTo(methodOn(CertificateController.class).delete(certificateDTO.getId()))
            .withRel("delete");
    Link updateLink =
        linkTo(methodOn(CertificateController.class).update(certificateDTO.getId(), certificateDTO))
            .withRel("update");
    certificateDTO.add(linkForSelf, deleteLink, updateLink);
  }

  public CollectionModel<CertificateDTO> getModelForCertificates(
          Collection<CertificateDTO> certificates, PaginationSetting paginationSetting, Long count) {
    Link linkForSelf = linkTo(CertificateController.class).withSelfRel();
    Link createLink = linkTo(CertificateController.class).withRel("create");
    final PageMetadata pageMetadata = getPageMetadata(paginationSetting, count);
    return PagedModel.of(certificates, pageMetadata, linkForSelf, createLink);
  }

  public void addLinksForUser(UserDTO userDTO) {
    Link linkForSelf =
        linkTo(methodOn(UserController.class).findUserById(userDTO.getId())).withSelfRel();
    Link linkForOrders =
        linkTo(methodOn(UserController.class).findAllUserOrders(userDTO.getId(), null, null))
            .withRel("orders");
    userDTO.add(linkForSelf, linkForOrders);
  }

  public void addLinksForOrder(OrderDTO orderDTO, BigInteger userId) {
    orderDTO.getCertificates().forEach(this::addLinksForCertificate);
    Link linkForSelf =
        linkTo(methodOn(UserController.class).findUserOrderById(userId, orderDTO.getId()))
            .withSelfRel();
    orderDTO.add(linkForSelf);
  }

  public CollectionModel<UserDTO> getModelForUsers(Collection<UserDTO> users) {
    Link linkForSelf = linkTo(UserController.class).withSelfRel();
    return CollectionModel.of(users, linkForSelf);
  }
}
