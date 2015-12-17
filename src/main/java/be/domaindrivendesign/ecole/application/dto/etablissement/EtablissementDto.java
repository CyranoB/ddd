package be.domaindrivendesign.ecole.application.dto.etablissement;

import be.domaindrivendesign.ecole.application.dto.beneficiaire.AgrementDtoList;
import be.domaindrivendesign.ecole.module.etablissement.domain.model.Etablissement;
import be.domaindrivendesign.ecole.module.etablissement.domain.type.EcoleType;
import be.domaindrivendesign.ecole.module.etablissement.domain.type.EnseignementReseauType;
import be.domaindrivendesign.kernel.module.dto.Dto;
import be.domaindrivendesign.shared.dto.AdresseDto;
import be.domaindrivendesign.shared.dto.ContactDto;

import java.util.List;
import java.util.UUID;


public class EtablissementDto extends Dto {
    public UUID id;
    public String numeroReference;
    public String denomination;
    public AdresseDto adresse;
    public List<ImplantationDtoList> implantations;
    public EnseignementReseauType enseignementReseau;
    public EcoleType ecole;
    public ContactDto contact;
    public List<AgrementDtoList> agrements;

    public static EtablissementDto convertir(Etablissement etablissement, List<AgrementDtoList> agrementDtoLists) {
        if (etablissement == null)
            return null;

        EtablissementDto e = new EtablissementDto();

        e.adresse = AdresseDto.convertir(etablissement.getAdresse());
        e.agrements = agrementDtoLists;
        e.contact = ContactDto.convertir(etablissement.getContact());
        e.denomination = etablissement.getDenomination();
        e.ecole = etablissement.getEcole();
        e.enseignementReseau = etablissement.getEnseignementReseau();
        e.id = etablissement.getId();
        e.implantations = ImplantationDtoList.convertir(etablissement.getImplantations());
        e.numeroReference = etablissement.getNumeroReference();
        return e;
    }


}
