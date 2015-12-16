package be.domaindrivendesign.ecole.module.etablissement.service;

import be.domaindrivendesign.ecole.module.etablissement.data.interfaces.EtablissementRepository;
import be.domaindrivendesign.ecole.module.etablissement.data.interfaces.ImplantationAnneeScolaireRepository;
import be.domaindrivendesign.ecole.module.etablissement.domain.model.Etablissement;
import be.domaindrivendesign.ecole.module.etablissement.domain.model.ImplantationAnneeScolaire;
import be.domaindrivendesign.kernel.data.interfaces.UnitOfWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class EcoleDomainServiceImpl implements EcoleDomainService {

    @Autowired
    EtablissementRepository etablissementRepository;

    @Autowired
    ImplantationAnneeScolaireRepository implantationAnneeScolaireRepository;

    @Autowired
    UnitOfWork unitOfWork;

    @Override
    public void importerEtablissement(Etablissement etablissement, LocalDateTime dateApplication) {
        Etablissement existant = etablissementRepository.getEtablissementForNumeroDeReference(etablissement.getNumeroReference());
        if (existant == null) {
            etablissementRepository.insert(etablissement);
        } else {
            existant.modifierContact(etablissement.getContact());
            existant.modifierImplantations(new ArrayList<>(etablissement.getImplantations()), dateApplication);
            etablissementRepository.update(existant);
        }
        unitOfWork.commit();
    }

    @Override
    public void importerImplantationAnneeScolaire(ImplantationAnneeScolaire implantationAnneeScolaire, LocalDateTime dateApplication) {
        ImplantationAnneeScolaire existant = implantationAnneeScolaireRepository.getImplantationAnneeScolaireForAnneeScolaireAndImplantationNumeroReference(implantationAnneeScolaire.getAnneeScolaire(), implantationAnneeScolaire.getImplantationNumeroReference());
        if (existant == null) {
            implantationAnneeScolaireRepository.insert(implantationAnneeScolaire);
        } else {
            existant.modifier(implantationAnneeScolaire, dateApplication);
            implantationAnneeScolaireRepository.update(existant);
        }
        unitOfWork.commit();
    }
}
