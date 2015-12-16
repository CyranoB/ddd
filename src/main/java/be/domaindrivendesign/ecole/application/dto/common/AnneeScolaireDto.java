package be.domaindrivendesign.ecole.application.dto.common;

import be.domaindrivendesign.ecole.module.common.valueobject.AnneeScolaire;
import be.domaindrivendesign.kernel.module.dto.Dto;

public class AnneeScolaireDto extends Dto {

    protected int anneeDebut;
    protected int anneeFin;

    public static AnneeScolaireDto convertir(AnneeScolaire anneeScolaire) {
        if (anneeScolaire == null)
            return null;

        AnneeScolaireDto anneeScolaireDto = new AnneeScolaireDto();
        anneeScolaireDto.setAnneeDebut(anneeScolaire.getAnneeDebut());
        anneeScolaireDto.setAnneeFin(anneeScolaire.getAnneeFin());
        return anneeScolaireDto;
    }

    public static AnneeScolaire convertir(AnneeScolaireDto anneeScolaireDto) {
        if (anneeScolaireDto == null)
            return null;
        return new AnneeScolaire(anneeScolaireDto.getAnneeDebut(), anneeScolaireDto.getAnneeFin());
    }

    public int getAnneeFin() {
        return anneeFin;
    }

    public void setAnneeFin(int anneeFin) {
        this.anneeFin = anneeFin;
    }

    public int getAnneeDebut() {
        return anneeDebut;
    }

    public void setAnneeDebut(int anneeDebut) {
        this.anneeDebut = anneeDebut;
    }
}
