package be.domaindrivendesign.ecole.module.etablissement.data.jpa;

import be.domaindrivendesign.ecole.module.etablissement.data.interfaces.EtablissementRepository;
import be.domaindrivendesign.ecole.module.etablissement.domain.model.Etablissement;
import be.domaindrivendesign.ecole.module.etablissement.domain.model.QEtablissement;
import be.domaindrivendesign.kernel.data.jpa.RepositoryJpa;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

@Repository
public class EtablissementRepositoryJpa extends RepositoryJpa<Etablissement>
        implements EtablissementRepository {

    @Override
    public Etablissement getEtablissementForNumeroDeReference(String numeroDeReference) {
        JPAQuery<Etablissement> query = new JPAQuery<>(getUnitOfWorkJpa().getEntityManager());
        QEtablissement implantationsAnneeScolaire = QEtablissement.etablissement;
        return query.from(implantationsAnneeScolaire).where(implantationsAnneeScolaire.numeroReference.eq(numeroDeReference)).fetchFirst();
    }
}