package be.domaindrivendesign.ecole.beneficiaire.data.interfaces;

import be.domaindrivendesign.ecole.beneficiaire.domain.model.Beneficiaire;
import be.domaindrivendesign.shared.valueobject.NumeroEntreprise;
import be.domaindrivendesign.shared.valueobject.NumeroIdentificationRegistreNational;

public interface BeneficiaireRepository {

    Beneficiaire getBeneficiaireForNumeroEntreprise(NumeroEntreprise numeroEntreprise);
    Beneficiaire getBeneficiaireForNumeroIdentificationRegistreNational(NumeroIdentificationRegistreNational numeroIdentificationRegistreNational);

}
