package be.domaindrivendesign.shared.valueobject;

/**
 * Created by eddie on 14/12/15.
 */
public class CompteBancaire01 extends CompteBancaire {


    public CompteBancaire01() {
        super(new BusinessIdentifierCode("1111", "1111", "111"), new InternationalBankAccountNumber("11", "1111", "1111", "1111"), "titulaire 01");
    }

}
