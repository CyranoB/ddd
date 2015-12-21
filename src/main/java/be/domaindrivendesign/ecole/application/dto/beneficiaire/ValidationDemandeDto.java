package be.domaindrivendesign.ecole.application.dto.beneficiaire;

import be.domaindrivendesign.ecole.module.common.valueobject.ValidationDemande;
import be.domaindrivendesign.kernel.module.dto.Dto;

import java.time.LocalDateTime;
import java.util.UUID;


public class ValidationDemandeDto extends Dto {

    // Membres
    public UUID id;
    public LocalDateTime enValidationLe;
    public LocalDateTime accepterLe;
    public LocalDateTime rejeterLe;
    public String raisonDeRejet;

    // NORMAL VERS DTO
    public static ValidationDemandeDto convertir(ValidationDemande validationDemande) {
        if (validationDemande == null)
            return null;

        ValidationDemandeDto vd = new ValidationDemandeDto();
        vd.enValidationLe = validationDemande.getEnValidationLe();
        vd.accepterLe = validationDemande.getAccepterLe();
        vd.rejeterLe = validationDemande.getRejeterLe();
        vd.raisonDeRejet = validationDemande.getRaisonDeRejet();
        return vd;
    }

    // DTO VERS NORMAL
    public static ValidationDemande convertir(ValidationDemandeDto validationDemandeDto) {
        if (validationDemandeDto == null)
            return null;

        return new ValidationDemande(
                validationDemandeDto.enValidationLe,
                validationDemandeDto.accepterLe,
                validationDemandeDto.rejeterLe,
                validationDemandeDto.raisonDeRejet);
    }

}
