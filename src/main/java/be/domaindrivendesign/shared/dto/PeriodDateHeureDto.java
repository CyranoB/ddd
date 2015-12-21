package be.domaindrivendesign.shared.dto;


import be.domaindrivendesign.kernel.module.dto.Dto;
import be.domaindrivendesign.shared.valueobject.PeriodDateHeure;

import java.time.LocalDateTime;

public class PeriodDateHeureDto extends Dto {

    public LocalDateTime debut;
    public LocalDateTime fin;

    public static PeriodDateHeureDto convertir(PeriodDateHeure periodDateHeure) {
        if (periodDateHeure == null)
            return null;
        PeriodDateHeureDto periodDateHeureDto = new PeriodDateHeureDto();
        periodDateHeureDto.debut = periodDateHeure.getDebut();
        periodDateHeureDto.fin = periodDateHeure.getFin();
        return periodDateHeureDto;
    }

    public static PeriodDateHeure convertir(PeriodDateHeureDto periodDateHeureDto) {
        if (periodDateHeureDto == null)
            return null;
        return new PeriodDateHeure(periodDateHeureDto.debut, periodDateHeureDto.fin);
    }

    public LocalDateTime getDebut() {
        return debut;
    }

    public void setDebut(LocalDateTime debut) {
        this.debut = debut;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public void setFin(LocalDateTime fin) {
        this.fin = fin;
    }
}
