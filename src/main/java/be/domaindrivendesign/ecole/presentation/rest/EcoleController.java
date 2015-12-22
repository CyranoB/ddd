package be.domaindrivendesign.ecole.presentation.rest;

import be.domaindrivendesign.ecole.application.dto.etablissement.EtablissementDto;
import be.domaindrivendesign.ecole.application.dto.etablissement.EtablissementDtoList;
import be.domaindrivendesign.ecole.application.interfaces.EcoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/ecole")
public class EcoleController {

    @Autowired
    EcoleService ecoleService;

    @RequestMapping(method = GET, value = "/etablissement")
    @ResponseStatus(HttpStatus.OK)
    List<EtablissementDtoList> listEtablissements() {
        return ecoleService.listEtablissement(null);
    }

    @RequestMapping(method = GET, value = "/etablissement/{id}")
    @ResponseStatus(HttpStatus.OK)
    Resource<?> getEtablissementsById(@PathVariable UUID id) {
        EtablissementDto e = ecoleService.getEtablissement(id);
        Link selfLink = linkTo(EcoleController.class).slash(e.id).withSelfRel();
        return new Resource<>(e, selfLink);
    }
}
