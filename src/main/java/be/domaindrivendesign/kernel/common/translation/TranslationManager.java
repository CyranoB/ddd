package be.domaindrivendesign.kernel.common.translation;

/**
 * Created by eddie on 18/11/15.
 */
public interface TranslationManager {
    String getTranslationForDomain(String property, LanguageType language);

    String getTranslationForRuleId(int ruleId, LanguageType language);
}
