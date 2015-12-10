package be.domaindrivendesign.kernel.common.translation;

/**
 * Created by eddie on 18/11/15.
 */
public class TranslationManagerImpl implements TranslationManager {
    public String GetTranslationForRuleId(int ruleId, LanguageType language) {
        return Integer.toString(ruleId);
    }

    public String GetTranslationForDomain(String property, LanguageType language) {
        return property;
    }
}