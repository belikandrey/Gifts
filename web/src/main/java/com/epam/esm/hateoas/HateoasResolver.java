package com.epam.esm.hateoas;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.TagController;
import com.epam.esm.controller.UserController;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HateoasResolver {
    public void addLinksForTag(TagDTO tagDTO){
        Link linkForSelf = linkTo(TagController.class).slash(tagDTO.getId()).withSelfRel();
        Link deleteLink =
                linkTo(methodOn(TagController.class).delete(tagDTO.getId())).withRel("delete");
        tagDTO.add(linkForSelf, deleteLink);
    }

    public CollectionModel<TagDTO> getModelForTags(Collection<TagDTO> tags){
        Link linkForSelf = linkTo(TagController.class).withSelfRel();
        Link createLink = linkTo(TagController.class).withRel("create");
        return CollectionModel.of(tags, linkForSelf, createLink);
    }

    public void addLinksForCertificate(CertificateDTO certificateDTO){
        certificateDTO.getTags().forEach(this::addLinksForTag);
        Link linkForSelf = linkTo(CertificateController.class).slash(certificateDTO.getId()).withSelfRel();
        Link deleteLink = linkTo(methodOn(CertificateController.class).delete(certificateDTO.getId())).withRel("delete");
        Link updateLink = linkTo(methodOn(CertificateController.class).update(certificateDTO.getId(), certificateDTO)).withRel("update");
        certificateDTO.add(linkForSelf, deleteLink, updateLink);
    }

    public CollectionModel<CertificateDTO> getModelForCertificates(Collection<CertificateDTO> certificates){
        Link linkForSelf = linkTo(CertificateController.class).withSelfRel();
        Link createLink = linkTo(CertificateController.class).withRel("create");
        return CollectionModel.of(certificates, linkForSelf, createLink);
    }

    public void addLinksForUser(UserDTO userDTO){
        userDTO.getOrders().forEach(p->addLinksForOrder(p, userDTO.getId()));
        Link linkForSelf = linkTo(methodOn(UserController.class).findUserById(userDTO.getId())).withSelfRel();
        Link linkForOrders = linkTo(methodOn(UserController.class).findAllUserOrders(userDTO.getId(), 0, 0)).withRel("orders");
        userDTO.add(linkForSelf, linkForOrders);
    }

    public void addLinksForOrder(OrderDTO orderDTO, BigInteger userId){
        orderDTO.getCertificates().forEach(this::addLinksForCertificate);
        Link linkForSelf = linkTo(methodOn(UserController.class).findUserOrderById(userId, orderDTO.getId())).withSelfRel();
        orderDTO.add(linkForSelf);
    }

    public CollectionModel<UserDTO> getModelForUsers(Collection<UserDTO> users){
        Link linkForSelf = linkTo(UserController.class).withSelfRel();
        return CollectionModel.of(users, linkForSelf);
    }
}