package be.domaindrivendesign.ecole.etablissement.service;

import be.domaindrivendesign.ecole.etablissement.data.interfaces.EtablissementRepository;
import be.domaindrivendesign.ecole.etablissement.data.interfaces.ImplantationAnneeScolaireRepository;
import be.domaindrivendesign.ecole.etablissement.domain.model.Etablissement;
import be.domaindrivendesign.ecole.etablissement.domain.model.ImplantationAnneeScolaire;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class EcoleDomainServiceImpl implements EcoleDomainService {

    @Autowired
    EtablissementRepository etablissementRepository;

    @Autowired
    ImplantationAnneeScolaireRepository implantationAnneeScolaireRepository;

    @Override
    public void importerEtablissement(Etablissement etablissement, LocalDateTime dateApplication) {
        Etablissement existant = etablissementRepository.getEtablissementForNumeroDeReference(etablissement.getNumeroReference());
        if (existant == null) {
            etablissementRepository.insert(etablissement);
            return;
        }
        existant.modifierContact(etablissement.getContact());
        existant.modifierImplantations(new ArrayList<>(etablissement.getImplantations()), dateApplication);
        etablissementRepository.update(existant);
    }

    @Override
    public void importerImplantationAnneeScolaire(ImplantationAnneeScolaire implantationAnneeScolaire, LocalDateTime dateApplication) {
        ImplantationAnneeScolaire existant = implantationAnneeScolaireRepository.getImplantationAnneeScolaireForAnneeScolaireAndImplantationNumeroReference(implantationAnneeScolaire.getAnneeScolaire(), implantationAnneeScolaire.getImplantationNumeroReference());
        if (existant == null) {
            implantationAnneeScolaireRepository.insert(implantationAnneeScolaire);
            return;
        }
        existant.modifier(implantationAnneeScolaire, dateApplication);
        implantationAnneeScolaireRepository.update(existant);
    }
}
