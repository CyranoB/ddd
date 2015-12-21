package be.domaindrivendesign.ecole.module.laitecole.domain.model;

import be.domaindrivendesign.kernel.domain.model.Aggregate;
import be.domaindrivendesign.kernel.rule.interfaces.RuleObject;
import be.domaindrivendesign.kernel.rule.model.RuleGuard;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;
import be.domaindrivendesign.kernel.rule.type.RuleType;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

//TODO mappings
@Entity
public class ImplantationParticipante extends Aggregate implements RuleObject {

    protected String implantationNumeroReference;
    @OneToMany
    protected List<ClasseParticipante> classeParticipantes;

    protected ImplantationParticipante() {
    }
    protected ImplantationParticipante(UUID id) {
        super(id);
    }

    public static ImplantationParticipante Creer(String implantationNumeroReference, List<ClasseParticipante> classeParticipantes) {
        ImplantationParticipante implantationParticipante = new ImplantationParticipante(UUID.randomUUID());

        implantationParticipante.setImplantationNumeroReference(implantationNumeroReference);
        implantationParticipante.setClasseParticipantes(classeParticipantes);

        RuleGuard.mandatory(implantationParticipante, implantationParticipante::getImplantationNumeroReference);
        if (RuleGuard.nbrOfElements(implantationParticipante, implantationParticipante::getClasseParticipantes, 1, 7, false)) {
            // Une seule classe participante doit avoir son classe unique par ImplantationParticipante
            for (int i = 0; i < classeParticipantes.size(); i++) {
                for (int j = i + 1; j < classeParticipantes.size(); j++) {
                    if (classeParticipantes.get(i).getClasse() == classeParticipantes.get(j).getClasse()) {
                        RuleGuard.raiseViolation(implantationParticipante, implantationParticipante::getClasseParticipantes, classeParticipantes.get(i).getClasse().toString(), RuleType.Overlap.typeValue, RuleSeverityType.Error);
                    }
                }
            }
        }

        implantationParticipante.implantationNumeroReference = implantationNumeroReference;
        implantationParticipante.classeParticipantes = classeParticipantes;

        return implantationParticipante;
    }


    public String getImplantationNumeroReference() {
        return implantationNumeroReference;
    }

    protected void setImplantationNumeroReference(String implantationNumeroReference) {
        this.implantationNumeroReference = implantationNumeroReference;
    }

    public List<ClasseParticipante> getClasseParticipantes() {
        return classeParticipantes;
    }

    protected void setClasseParticipantes(List<ClasseParticipante> classeParticipantes) {
        this.classeParticipantes = classeParticipantes;
    }
}
