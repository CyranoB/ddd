package be.domaindrivendesign.shared.valueobject;

/**
 * Created by eddie on 14/12/15.
 */
public class CompteBancaire02 extends CompteBancaire {


    public CompteBancaire02() {
        super(new BusinessIdentifierCode("2222", "2222", "222"), new InternationalBankAccountNumber("22", "2222", "2222", "2222"), "titulaire 02");
    }

}
