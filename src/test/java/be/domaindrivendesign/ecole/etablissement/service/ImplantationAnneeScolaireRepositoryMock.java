package be.domaindrivendesign.ecole.etablissement.service;

import be.domaindrivendesign.ecole.common.type.ClasseType;
import be.domaindrivendesign.ecole.common.valueobject.AnneeScolaire;
import be.domaindrivendesign.ecole.etablissement.domain.model.ClasseComptage;
import be.domaindrivendesign.ecole.etablissement.domain.model.ImplantationAnneeScolaire;

import java.util.Arrays;
import java.util.LinkedHashSet;

public class ImplantationAnneeScolaireRepositoryMock {
    public static String INPLANTATION_REF = "implantation reference 1";

    public static ImplantationAnneeScolaire getImplantationAnneeScolaire01(int nbrElevesClasse01, int nbrElevesClasse02) {
        return ImplantationAnneeScolaire.creer(
                INPLANTATION_REF,
                new AnneeScolaire(2014, 2015),
                new LinkedHashSet<>(Arrays.asList(getClasseComptage01(nbrElevesClasse01), getClasseComptage02(nbrElevesClasse02))));
    }

    public static ClasseComptage getClasseComptage01(int nbrEleves) {
        return ClasseComptage.creer(ClasseType.Maternelle, nbrEleves);
    }

    public static ClasseComptage getClasseComptage02(int nbrEleves) {
        return ClasseComptage.creer(ClasseType.Primaire, nbrEleves);
    }


}
