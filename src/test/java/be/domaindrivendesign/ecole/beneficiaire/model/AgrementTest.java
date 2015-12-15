package be.domaindrivendesign.ecole.beneficiaire.model;

import be.domaindrivendesign.ecole.beneficiaire.domain.event.DemandeAgrementValidee;
import be.domaindrivendesign.ecole.beneficiaire.domain.model.Agrement;
import be.domaindrivendesign.ecole.common.valueobject.AnneeScolaire;
import be.domaindrivendesign.kernel.common.model.EntityStateType;
import be.domaindrivendesign.kernel.domain.control.DefaultDomainEventManager;
import be.domaindrivendesign.kernel.domain.interfaces.DomainEventListener;
import be.domaindrivendesign.kernel.rule.model.UnitOfWorkRule;
import be.domaindrivendesign.kernel.rule.type.RuleSeverityType;
import be.domaindrivendesign.kernel.rule.type.RuleType;
import org.junit.After;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.Assert.*;

public class AgrementTest {

    @After
    public void cleanCallContext() {
        UnitOfWorkRule.getInstance().clear();
        DefaultDomainEventManager.getInstance().destroy();
    }


    @Test
    public void testCreer() {
        UUID beneficiaireId = UUID.randomUUID();
        Agrement demandeAgrement = Agrement.creer("DGRANE01",                       // numeroDgarne
                LocalDateTime.of(2010, 01, 01, 0, 0, 0),       // dateDepot
                LocalDateTime.of(2010, 01, 15, 0, 0, 0),       // dateDemande
                1,                                // numeroDossier
                new AnneeScolaire(2010, 2011),    // premiereAnneeScolaire
                beneficiaireId);                  // beneficiaireId            

        assertNotNull(demandeAgrement.getId());
        assertEquals(demandeAgrement.getDateDepot(), LocalDateTime.of(2010, 01, 01, 0, 0, 0));
        assertEquals(demandeAgrement.getDateDemande(), LocalDateTime.of(2010, 01, 15, 0, 0, 0));
        assertEquals(demandeAgrement.getNumeroDossier(), 1);
        assertEquals(demandeAgrement.getPremiereAnneeScolaire(), new AnneeScolaire(2010, 2011));
        assertNull(demandeAgrement.getDerniereAnneeScolaire());
        assertEquals(demandeAgrement.getBeneficiaireId(), beneficiaireId);
        assertNotNull(demandeAgrement.getValidationDemande());
        assertEquals(demandeAgrement.getNumeroDgarne(), "DGRANE01");
    }

    @Test
    public void testCreerNullNumeroDgarne() {
        UUID beneficiaireId = UUID.randomUUID();
        Agrement demandeAgrement = Agrement.creer(null, LocalDateTime.of(2010, 01, 01, 0, 0, 0), LocalDateTime.of(2010, 01, 15, 0, 0, 0), 1,
                new AnneeScolaire(2010, 2011), beneficiaireId);

        // Violation
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(Arrays.asList("be.domaindrivendesign.ecole.beneficiaire.domain.model|Agrement.getNumeroDgarne"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(demandeAgrement, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        assertEquals(0, UnitOfWorkRule.getInstance().getViolations().get(0).getValues().size());
    }

    @Test
    public void testCreerNullDateDepot() {
        UUID beneficiaireId = UUID.randomUUID();
        Agrement demandeAgrement = Agrement.creer("DGRANE01", null, LocalDateTime.of(2010, 01, 15, 0, 0, 0), 1,
                new AnneeScolaire(2010, 2011), beneficiaireId);

        // Violation
        assertEquals(UnitOfWorkRule.getInstance().getViolations().size(), 1);
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths(), Arrays.asList("be.domaindrivendesign.ecole.beneficiaire.domain.model|Agrement.getDateDepot"));
        assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.Mandatory.typeValue);
        assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), demandeAgrement);
        assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues(), Arrays.asList());
    }

    @Test
    public void testCreerNullDateDemande() {
        UUID beneficiaireId = UUID.randomUUID();
        Agrement demandeAgrement = Agrement.creer("DGRANE01", LocalDateTime.of(2010, 01, 01, 0, 0, 0), null, 1,
                new AnneeScolaire(2010, 2011), beneficiaireId);

        // Violation
        assertEquals(UnitOfWorkRule.getInstance().getViolations().size(), 1);
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths(), Arrays.asList("be.domaindrivendesign.ecole.beneficiaire.domain.model|Agrement.getDateDemande"));
        assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.Mandatory.typeValue);
        assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), demandeAgrement);
        assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues(), Arrays.asList());
    }

    @Test
    public void testCreerNullPremiereAnneeScolaire() {
        UUID beneficiaireId = UUID.randomUUID();
        Agrement demandeAgrement = Agrement.creer("DGRANE01", LocalDateTime.of(2010, 01, 01, 0, 0, 0), LocalDateTime.of(2010, 01, 15, 0, 0, 0), 1,
                null, beneficiaireId);

        // Violation
        assertEquals(UnitOfWorkRule.getInstance().getViolations().size(), 1);
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths(), Arrays.asList("be.domaindrivendesign.ecole.beneficiaire.domain.model|Agrement.getPremiereAnneeScolaire"));
        assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.Mandatory.typeValue);
        assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), demandeAgrement);
        assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues(), Arrays.asList());
    }

    @Test
    public void testCreerNullBeneficiaireId() {
        UUID beneficiaireId = UUID.randomUUID();
        Agrement demandeAgrement = Agrement.creer("DGRANE01", LocalDateTime.of(2010, 01, 01, 0, 0, 0), LocalDateTime.of(2010, 01, 15, 0, 0, 0), 1,
                new AnneeScolaire(2010, 2011), null);

        // Violation
        assertEquals(UnitOfWorkRule.getInstance().getViolations().size(), 1);
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths(), Arrays.asList("be.domaindrivendesign.ecole.beneficiaire.domain.model|Agrement.getBeneficiaireId"));
        assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId(), RuleType.Mandatory.typeValue);
        assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject(), demandeAgrement);
        assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType(), RuleSeverityType.Error);
        assertEquals(UnitOfWorkRule.getInstance().getViolations().get(0).getValues(), Arrays.asList());

    }

    @Test
    public void testDemanderValidationDeLaDemande() {
        UUID beneficiaireId = UUID.randomUUID();
        Agrement demandeAgrement = Agrement.creer("DGRANE01", LocalDateTime.of(2010, 01, 01, 0, 0, 0), LocalDateTime.of(2010, 01, 15, 0, 0, 0), 1,
                new AnneeScolaire(2010, 2011), beneficiaireId);
        demandeAgrement.forceState(EntityStateType.Unchanged);

        demandeAgrement.demanderValidation(null);

        assertEquals(UnitOfWorkRule.getInstance().getViolations().size(), 0);
        assertNull(demandeAgrement.getValidationDemande().getAccepterLe());
        assertNotNull(demandeAgrement.getValidationDemande().getEnValidationLe());
        assertNull(demandeAgrement.getValidationDemande().getRaisonDeRejet());
        assertNull(demandeAgrement.getValidationDemande().getRejeterLe());
        assertEquals(demandeAgrement.getState(), EntityStateType.Modified);
    }

    @Test
    public void testAccepterDemande() {
        UUID beneficiaireId = UUID.randomUUID();
        Agrement demandeAgrement = Agrement.creer("DGRANE01", LocalDateTime.of(2010, 01, 01, 0, 0, 0), LocalDateTime.of(2010, 01, 15, 0, 0, 0), 1,
                new AnneeScolaire(2010, 2011), beneficiaireId);
        demandeAgrement.forceState(EntityStateType.Unchanged);

        demandeAgrement.demanderValidation(null);
        AgrementTestEventRegister eventRegister = new AgrementTestEventRegister();
        demandeAgrement.accepter(null);

        assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());
        assertNotNull(demandeAgrement.getValidationDemande().getAccepterLe());
        assertNotNull(demandeAgrement.getValidationDemande().getEnValidationLe());
        assertNull(demandeAgrement.getValidationDemande().getRaisonDeRejet());
        assertNull(demandeAgrement.getValidationDemande().getRejeterLe());
        assertEquals(demandeAgrement, eventRegister.getDemandeAgrement());
        assertEquals(EntityStateType.Modified, demandeAgrement.getState());
    }

    @Test
    public void testRefuserDemande() {
        UUID beneficiaireId = UUID.randomUUID();
        Agrement demandeAgrement = Agrement.creer("DGRANE01", LocalDateTime.of(2010, 01, 01, 0, 0, 0), LocalDateTime.of(2010, 01, 15, 0, 0, 0), 1,
                new AnneeScolaire(2010, 2011), beneficiaireId);
        demandeAgrement.forceState(EntityStateType.Unchanged);

        demandeAgrement.demanderValidation(null);
        AgrementTestEventRegister eventRegister = new AgrementTestEventRegister();
        demandeAgrement.refuser("Raison de refus", null);

        assertEquals(0, UnitOfWorkRule.getInstance().getViolations().size());
        assertNull(demandeAgrement.getValidationDemande().getAccepterLe());
        assertNotNull(demandeAgrement.getValidationDemande().getEnValidationLe());
        assertEquals(demandeAgrement.getValidationDemande().getRaisonDeRejet(), "Raison de refus");
        assertNotNull(demandeAgrement.getValidationDemande().getRejeterLe());
        assertEquals(demandeAgrement, eventRegister.getDemandeAgrement());
        assertEquals(EntityStateType.Modified, demandeAgrement.getState());
    }

    @Test
    public void testSupprimerAgrement() {
        UUID beneficiaireId = UUID.randomUUID();
        Agrement demandeAgrement = Agrement.creer("DGRANE01", LocalDateTime.of(2010, 01, 01, 0, 0, 0), LocalDateTime.of(2010, 01, 15, 0, 0, 0), 1,
                new AnneeScolaire(2000, 2001), beneficiaireId);
        demandeAgrement.forceState(EntityStateType.Unchanged);

        demandeAgrement.cloturerAgrement(new AnneeScolaire(2010, 2011));

        assertEquals(new AnneeScolaire(2010, 2011), demandeAgrement.getDerniereAnneeScolaire());
        assertEquals(EntityStateType.Modified, demandeAgrement.getState());
    }

    //@Test
    // TODO check lambda
    public void testSupprimerAgrementDerniereAnneeScolaireAvantPremiereAnneeScolaire() {
        UUID beneficiaireId = UUID.randomUUID();
        Agrement demandeAgrement = Agrement.creer("DGRANE01", LocalDateTime.of(2010, 01, 01, 0, 0, 0), LocalDateTime.of(2010, 01, 15, 0, 0, 0), 1,
                new AnneeScolaire(2005, 2006), beneficiaireId);
        demandeAgrement.forceState(EntityStateType.Unchanged);

        demandeAgrement.cloturerAgrement(new AnneeScolaire(2004, 2005));

        // Violation
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(Arrays.asList("BeneficiaireDomain.Domain.Model|Agrement.DerniereAnneeScolaire", "BeneficiaireDomain.Domain.Model|Agrement.PremiereAnneeScolaire"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.AfterOrEqual.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(demandeAgrement, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Arrays.asList(LocalDateTime.of(2004, 01, 01, 0, 0, 0).toString(), LocalDateTime.of(2005, 01, 01, 0, 0, 0).toString()), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }

    @Test
    public void testSupprimerAgrementDerniereAnneeScolaireNull() {
        UUID beneficiaireId = UUID.randomUUID();
        Agrement demandeAgrement = Agrement.creer("DGRANE01", LocalDateTime.of(2010, 01, 01, 0, 0, 0), LocalDateTime.of(2010, 01, 15, 0, 0, 0), 1,
                new AnneeScolaire(2000, 2001), beneficiaireId);
        demandeAgrement.forceState(EntityStateType.Unchanged);

        demandeAgrement.cloturerAgrement(null);

        // Violation
        assertEquals(1, UnitOfWorkRule.getInstance().getViolations().size());
        assertNull(UnitOfWorkRule.getInstance().getViolations().get(0).getMessage());
        assertEquals(Arrays.asList("be.domaindrivendesign.ecole.beneficiaire.domain.model|Agrement.getDerniereAnneeScolaire"), UnitOfWorkRule.getInstance().getViolations().get(0).getPropertyPaths());
        assertEquals(RuleType.Mandatory.typeValue, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleId());
        assertEquals(demandeAgrement, UnitOfWorkRule.getInstance().getViolations().get(0).getRuleObject());
        assertEquals(RuleSeverityType.Error, UnitOfWorkRule.getInstance().getViolations().get(0).getSeverityType());
        assertEquals(Arrays.asList(), UnitOfWorkRule.getInstance().getViolations().get(0).getValues());
    }


    public class AgrementTestEventRegister implements DomainEventListener<DemandeAgrementValidee> {

        public Agrement demandeAgrement;

        public AgrementTestEventRegister() {
            DefaultDomainEventManager.getInstance().registerObserver(DemandeAgrementValidee.class, this);
        }

        @Override
        public void onEvent(DemandeAgrementValidee event) {
            this.demandeAgrement = event.getDemandeAgrement();
        }

        public Agrement getDemandeAgrement() {
            return demandeAgrement;
        }
    }
}
