package be.domaindrivendesign.ecole.presentation.rest.v1;

import be.domaindrivendesign.ecole.application.dto.etablissement.EtablissementDto;
import be.domaindrivendesign.ecole.application.dto.etablissement.EtablissementDtoList;
import be.domaindrivendesign.ecole.application.interfaces.EcoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/v1/Etablissement")
@CrossOrigin(origins = "http://localhost:9000")
public class EcoleController {

    @Autowired
    EcoleService ecoleService;

    @RequestMapping(method = GET, value = "/")
    @ResponseStatus(HttpStatus.OK)
    List<EtablissementDtoList> listEtablissements() {
        return ecoleService.listEtablissement(null);
    }

    @RequestMapping(method = GET, value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    Resource<?> getEtablissementsById(@PathVariable UUID id) {
        EtablissementDto e = ecoleService.getEtablissement(id);
        // A voir si on veux les links ou pas
        // Link selfLink = linkTo(EcoleControllerRest.class).slash(e.id).withSelfRel();
        // return new Resource<>(e , selfLink);
        return new Resource<>(e);
    }
}
