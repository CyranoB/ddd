package be.domaindrivendesign.kernel.common.translation;

/**
 * Created by eddie on 18/11/15.
 */
public interface TranslationManager {
    String GetTranslationForDomain(String property, LanguageType language);

    String GetTranslationForRuleId(int ruleId, LanguageType language);
}
