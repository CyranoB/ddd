package be.domaindrivendesign.ecole.etablissement.data.jpa;

import be.domaindrivendesign.ecole.common.valueobject.AnneeScolaire;
import be.domaindrivendesign.ecole.etablissement.data.interfaces.ImplantationAnneeScolaireRepository;
import be.domaindrivendesign.ecole.etablissement.domain.model.ImplantationAnneeScolaire;
import be.domaindrivendesign.ecole.etablissement.domain.model.QImplantationAnneeScolaire;
import be.domaindrivendesign.kernel.data.jpa.RepositoryJpa;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaImplantationAnneeScolaireRepository extends RepositoryJpa<ImplantationAnneeScolaire>
        implements ImplantationAnneeScolaireRepository {

    @Override
    public List<ImplantationAnneeScolaire> listImplantationAnneeScolaireForImplantationNumeroReference(String implantationNumeroReference) {
        JPAQuery<ImplantationAnneeScolaire> query = new JPAQuery<>(getJpaUnitOfWork().getEntityManager());
        QImplantationAnneeScolaire implantationsAnneeScolaire = QImplantationAnneeScolaire.implantationAnneeScolaire;
        return query.from(implantationsAnneeScolaire).where(implantationsAnneeScolaire.implantationNumeroReference.eq(implantationNumeroReference)).fetch();
        // TODO Aspect pour mettre les entités à unodified
    }

    @Override
    public ImplantationAnneeScolaire getImplantationAnneeScolaireForAnneeScolaireAndImplantationNumeroReference(AnneeScolaire anneeScolaire, String implantationNumeroReference) {
        JPAQuery<ImplantationAnneeScolaire> query = new JPAQuery<>(getJpaUnitOfWork().getEntityManager());
        QImplantationAnneeScolaire implantationsAnneeScolaire = QImplantationAnneeScolaire.implantationAnneeScolaire;
        return query.from(implantationsAnneeScolaire).where(implantationsAnneeScolaire.implantationNumeroReference.eq(implantationNumeroReference), implantationsAnneeScolaire.anneeScolaire.eq(anneeScolaire)).fetchFirst();
        // TODO Aspect pour mettre les entités à unodified
    }
}
