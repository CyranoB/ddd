package be.domaindrivendesign.ecole.beneficiaire.domain.type;


public enum BeneficiaireCategorieType {

    Fournisseur(1),
    EtablissementScolaire(2),
    Organisme(3),
    PouvoirOrganisateur(4),
    Creche(5),
    Accueillante(6);

    public final int typeValue;

    BeneficiaireCategorieType(int value) {
        this.typeValue = value;
    }

}
