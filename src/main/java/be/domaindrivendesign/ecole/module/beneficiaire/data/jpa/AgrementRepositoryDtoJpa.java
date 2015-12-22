package be.domaindrivendesign.ecole.module.beneficiaire.data.jpa;

import be.domaindrivendesign.ecole.application.dto.DemandeValidationDashBoardDto;
import be.domaindrivendesign.ecole.application.dto.beneficiaire.AgrementDtoList;
import be.domaindrivendesign.ecole.application.dto.beneficiaire.AgrementDtoSearch;
import be.domaindrivendesign.ecole.application.dto.common.AnneeScolaireDto;
import be.domaindrivendesign.ecole.module.beneficiaire.data.interfaces.AgrementRepositoryDto;
import be.domaindrivendesign.ecole.module.beneficiaire.domain.model.Agrement;
import be.domaindrivendesign.ecole.module.beneficiaire.domain.model.Beneficiaire;
import be.domaindrivendesign.ecole.module.beneficiaire.domain.model.QAgrement;
import be.domaindrivendesign.ecole.module.beneficiaire.domain.model.QBeneficiaire;
import be.domaindrivendesign.kernel.data.jpa.RepositoryJpa;
import com.querydsl.jpa.impl.JPAQuery;

import java.util.ArrayList;
import java.util.List;

public class AgrementRepositoryDtoJpa extends RepositoryJpa<Agrement> implements AgrementRepositoryDto {

    public List<AgrementDtoList> listAgrement(AgrementDtoSearch search) {
        // Déclaration
        List<Agrement> results;

        // La quantité d'information renvoyée peut être très conséquente
        // => on utilise les éventuelles valeurs de Skip et Take par défaut
        // si elles ne sont pas précisées dans le search
        AgrementDtoSearch searchDefault = new AgrementDtoSearch();

        // Charger les bénéficiaires
        JPAQuery<Agrement> agrementQuery = new JPAQuery<>(getUnitOfWork().getEntityManager());
        QAgrement agrements = QAgrement.agrement;
        agrementQuery = agrementQuery.from(agrements);

        // A-t-on reçu un search différent de NULL ?
        if (search != null) {
            // A-t-on un BeneficiaireId ?
            if (search.beneficiaireId != null)
                // On a un BeneficiaireId => appliquer la requête par BeneficiaireId
                agrementQuery = agrementQuery.where(agrements.beneficiaireId.eq(search.beneficiaireId));

            // ?
            if (search.enConstitution || search.aValider) {
                agrementQuery = agrementQuery.where(agrements.validationDemande.accepterLe.isNull());

                agrementQuery = agrementQuery.where(agrements.validationDemande.rejeterLe.isNull());

                if (search.enConstitution)
                    agrementQuery = agrementQuery.where(agrements.validationDemande.enValidationLe.isNull());
                else
                    agrementQuery = agrementQuery.where(agrements.validationDemande.enValidationLe.isNotNull());
            }

            // A t'on une liste de NumeroDgarnEs ?
            if (search.numeroDgarnes != null && search.numeroDgarnes.size() > 0)
                // TODO On a une liste de NumeroDgarnEs => appliquer la requête par NumeroDgarnEs
                // agrementQuery = agrementQuery.where(search.numeroDgarnes.contains(agrements.numeroDgarne));

                // Trier par Id (Guid), prendre Skip et Take
                agrementQuery = agrementQuery.orderBy(agrements.id.asc()).offset(search.skip).limit(search.take);

            // Mettre les agréments en liste
            results = agrementQuery.fetch();

        } else {
            // On reçu un search égal à NULL
            // Il n'y a pas de Skip ni de Take => mettre ceux par défaut
            agrementQuery = agrementQuery.orderBy(agrements.id.asc()).offset(searchDefault.skip).limit(searchDefault.take);

            // Récupérer tous les agréments
            results = agrementQuery.fetch();
        }

        // Convertir en DTO et ajouter les informations manquantes (dénomination du bénéficiaire)

        JPAQuery<Beneficiaire> beneficiaireQuery = new JPAQuery<>(getUnitOfWork().getEntityManager());
        QBeneficiaire qb = QBeneficiaire.beneficiaire;

        List<AgrementDtoList> list = new ArrayList<>();
        for (Agrement a : results) {
            Beneficiaire b = beneficiaireQuery.from(qb).where(qb.id.eq(a.getBeneficiaireId())).fetchFirst();
            if (b != null) {
                list.add(AgrementDtoList.convertir(a, b.denomination));
            } else {
                list.add(AgrementDtoList.convertir(a, null));
            }
        }

        // Renvoyer la liste DTO
        return list;
    }

    /// <summary>
    /// Récupère le tableau de bord des demandes de validation pour une année donnée.
    /// </summary>
    /// <param name="anneeScolaireDto">L'année scolaire a considérer</param>
    /// <returns>
    /// Le tableau de bord des demandes de validation
    /// </returns>
    /// <exception cref="System.NotImplementedException"></exception>
    public DemandeValidationDashBoardDto getDemandeValidationDashBoard(AnneeScolaireDto anneeScolaireDto) {
        //TODO: implémenter
        return null;
    }

}
