package be.domaindrivendesign.ecole.module.etablissement.data.jpa;

import be.domaindrivendesign.ecole.module.common.valueobject.AnneeScolaire;
import be.domaindrivendesign.ecole.module.etablissement.data.interfaces.ImplantationAnneeScolaireRepository;
import be.domaindrivendesign.ecole.module.etablissement.domain.model.ImplantationAnneeScolaire;
import be.domaindrivendesign.ecole.module.etablissement.domain.model.QImplantationAnneeScolaire;
import be.domaindrivendesign.kernel.data.jpa.RepositoryJpa;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ImplantationAnneeScolaireRepositoryJpa extends RepositoryJpa<ImplantationAnneeScolaire>
        implements ImplantationAnneeScolaireRepository {

    @Override
    public List<ImplantationAnneeScolaire> listImplantationAnneeScolaireForImplantationNumeroReference(String implantationNumeroReference) {
        JPAQuery<ImplantationAnneeScolaire> query = new JPAQuery<>(getUnitOfWorkJpa().getEntityManager());
        QImplantationAnneeScolaire implantationsAnneeScolaire = QImplantationAnneeScolaire.implantationAnneeScolaire;
        return query.from(implantationsAnneeScolaire).where(implantationsAnneeScolaire.implantationNumeroReference.eq(implantationNumeroReference)).fetch();
    }

    @Override
    public ImplantationAnneeScolaire getImplantationAnneeScolaireForAnneeScolaireAndImplantationNumeroReference(AnneeScolaire anneeScolaire, String implantationNumeroReference) {
        JPAQuery<ImplantationAnneeScolaire> query = new JPAQuery<>(getUnitOfWorkJpa().getEntityManager());
        QImplantationAnneeScolaire implantationsAnneeScolaire = QImplantationAnneeScolaire.implantationAnneeScolaire;
        return query.from(implantationsAnneeScolaire).where(implantationsAnneeScolaire.implantationNumeroReference.eq(implantationNumeroReference), implantationsAnneeScolaire.anneeScolaire.eq(anneeScolaire)).fetchFirst();
    }
}
