package be.domaindrivendesign.kernel.common.translation;

/**
 * Created by eddie on 18/11/15.
 */
public enum LanguageType {
    French(1),
    Dutch(2),
    Flemish(3);

    public final int typeValue;

    LanguageType(int typeValue) {
        this.typeValue = typeValue;
    }
}
