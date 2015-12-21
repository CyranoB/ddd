package be.domaindrivendesign.ecole.module.etablissement.data.mock;

import be.domaindrivendesign.ecole.application.dto.etablissement.*;
import be.domaindrivendesign.ecole.module.etablissement.data.interfaces.EtablissementRepository;
import be.domaindrivendesign.ecole.module.etablissement.data.interfaces.EtablissementRepositoryDto;
import be.domaindrivendesign.ecole.module.etablissement.domain.model.Adresse01;
import be.domaindrivendesign.ecole.module.etablissement.domain.model.Contact01;
import be.domaindrivendesign.ecole.module.etablissement.domain.model.Etablissement;
import be.domaindrivendesign.ecole.module.etablissement.domain.model.Implantation;
import be.domaindrivendesign.ecole.module.etablissement.domain.type.EcoleType;
import be.domaindrivendesign.ecole.module.etablissement.domain.type.EnseignementReseauType;
import be.domaindrivendesign.ecole.module.etablissement.domain.type.NiveauType;
import be.domaindrivendesign.kernel.data.mock.RepositoryMock;
import be.domaindrivendesign.shared.valueobject.PeriodDateHeure;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EtablissementRepositoryMock extends RepositoryMock<Etablissement>
        implements EtablissementRepository, EtablissementRepositoryDto {

    public static final String EtablissementNumeroRefence01 = "etablissement reference 1";
    public static final String ImplantatioNumeroRefence01 = "implantation reference 1";

    public static ImplantationDtoList convertir(Implantation implantation) {
        if (implantation == null)
            return null;

        ImplantationDtoList idl = new ImplantationDtoList();
        idl.adresseCodePostal = implantation.getAdresse().getCodePostal();
        idl.denomination = implantation.getDenomination();
        idl.id = implantation.getId();
        idl.numeroReference = implantation.getNumeroReference();
        return idl;
    }

    public static Etablissement getEtablissement01() {
        return Etablissement.creer(EtablissementNumeroRefence01,
                "denomination 1",
                EnseignementReseauType.OfficielProvincial,
                new Adresse01(),
                EcoleType.EtablissementScolaire,
                new Contact01(),
                Arrays.asList(getImplantation01()));
    }

    public static Implantation getImplantation01() {
        return Implantation.creer(ImplantatioNumeroRefence01,
                "denomination 1",
                new Adresse01(),
                Arrays.asList(NiveauType.Primaire),
                new Contact01(),
                new PeriodDateHeure(LocalDateTime.parse("2014-01-01T00:00:00"), LocalDateTime.parse("2015-01-01T00:00:00"))
        );
    }

    public Etablissement getEtablissementForNumeroDeReference(String numeroDeReference) {
        Optional<Etablissement> e = mocked.stream().filter(x -> x.getNumeroReference().equals(numeroDeReference)).findAny();
        return e.get();
    }

    public EtablissementDto getEtablissementByNumeroReference(EtablissementDtoSearch search) {
        if (search == null)
            return new EtablissementDto();

        Stream<Etablissement> etablissements = mocked.stream();

        if (search.getId() != null)
            etablissements = etablissements.filter(e -> e.getId().equals(search.getId()));

        if (StringUtils.isNotBlank(search.denomination))
            etablissements = etablissements.filter(e -> e.getDenomination().equals(search.getDenomination()));

        Optional<Etablissement> e = etablissements.findFirst();
        return EtablissementDto.convertir(e.get(), null);
    }

    public List<EtablissementDtoList> listEtablissement(EtablissementDtoSearch search) {
        return mocked.stream().map(EtablissementDtoList.aggregateToDto).collect(Collectors.toList());
    }

    public List<ImplantationDtoList> listImplantation(ImplantationDtoSearch search) {
        List<ImplantationDtoList> results = new ArrayList<ImplantationDtoList>();
        for (Etablissement et : mocked) {
            List<ImplantationDtoList> l = mocked.stream().map(Etablissement::getImplantations).map(ImplantationDtoList.aggregatesToDtos).findFirst().get();
            results.addAll(l);
        }
        return results;
    }

    @Override
    public List<Etablissement> list() {
        return null;
    }
}
