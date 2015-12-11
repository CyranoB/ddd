package be.domaindrivendesign.ecole.etablissement.data.jpa;

import be.domaindrivendesign.ecole.etablissement.data.interfaces.EtablissementRepository;
import be.domaindrivendesign.ecole.etablissement.domain.model.Etablissement;
import be.domaindrivendesign.ecole.etablissement.domain.model.QEtablissement;
import be.domaindrivendesign.kernel.data.jpa.RepositoryJpa;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class JpaEtablissementRepository extends RepositoryJpa<Etablissement, UUID>
        implements EtablissementRepository {

    @Override
    public Etablissement getEtablissementForNumeroDeReference(String numeroDeReference) {
        JPAQuery<Etablissement> query = new JPAQuery<>(getJpaUnitOfWork().getEntityManager());
        QEtablissement implantationsAnneeScolaire = QEtablissement.etablissement;
        return query.from(implantationsAnneeScolaire).where(implantationsAnneeScolaire.numeroReference.eq(numeroDeReference)).fetchFirst();
    }
}