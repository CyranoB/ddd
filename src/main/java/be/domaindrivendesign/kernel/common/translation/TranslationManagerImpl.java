package be.domaindrivendesign.kernel.common.translation;

/**
 * Created by eddie on 18/11/15.
 */
public class TranslationManagerImpl implements TranslationManager {
    @Override
    public String getTranslationForRuleId(int ruleId, LanguageType language) {
        return Integer.toString(ruleId);
    }

    @Override
    public String getTranslationForDomain(String property, LanguageType language) {
        return property;
    }
}