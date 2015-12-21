package be.domaindrivendesign.ecole.application.dto;

public class DemandeValidationDashBoardDto {
    public int enConstitutionNbr;
    public int enValidationNbr;
    public int accepterNbr;
    public int refuserNbr;

    public int getEnConstitutionNbr() {
        return enConstitutionNbr;
    }

    public void setEnConstitutionNbr(int enConstitutionNbr) {
        this.enConstitutionNbr = enConstitutionNbr;
    }

    public int getEnValidationNbr() {
        return enValidationNbr;
    }

    public void setEnValidationNbr(int enValidationNbr) {
        this.enValidationNbr = enValidationNbr;
    }

    public int getAccepterNbr() {
        return accepterNbr;
    }

    public void setAccepterNbr(int accepterNbr) {
        this.accepterNbr = accepterNbr;
    }

    public int getRefuserNbr() {
        return refuserNbr;
    }

    public void setRefuserNbr(int refuserNbr) {
        this.refuserNbr = refuserNbr;
    }
}
