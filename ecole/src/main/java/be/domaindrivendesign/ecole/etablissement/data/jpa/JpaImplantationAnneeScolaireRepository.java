package be.domaindrivendesign.ecole.etablissement.data.jpa;

import be.domaindrivendesign.ecole.etablissement.data.interfaces.ImplantationAnneeScolaireRepository;
import be.domaindrivendesign.ecole.etablissement.model.ImplantationAnneeScolaire;
import be.domaindrivendesign.ecole.etablissement.model.QImplantationAnneeScolaire;
import be.domaindrivendesign.kernel.data.jpa.RepositoryJpa;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class JpaImplantationAnneeScolaireRepository extends RepositoryJpa<ImplantationAnneeScolaire, UUID>
        implements ImplantationAnneeScolaireRepository {

    @Override
    public List<ImplantationAnneeScolaire> getByImplantationNumeroReference(String numeroDeReference) {
        JPAQuery<ImplantationAnneeScolaire> query = new JPAQuery<>(getJpaUnitOfWork().getEntityManager());
        QImplantationAnneeScolaire implantationsAnneeScolaire = QImplantationAnneeScolaire.implantationAnneeScolaire;
        return query.from(implantationsAnneeScolaire).where(implantationsAnneeScolaire.implantationNumeroReference.eq(numeroDeReference)).fetch();
    }
}
