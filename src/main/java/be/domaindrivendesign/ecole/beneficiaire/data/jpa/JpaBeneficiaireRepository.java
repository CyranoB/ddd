package be.domaindrivendesign.ecole.beneficiaire.data.jpa;


import be.domaindrivendesign.ecole.beneficiaire.data.interfaces.BeneficiaireRepository;
import be.domaindrivendesign.ecole.beneficiaire.domain.model.Beneficiaire;
import be.domaindrivendesign.ecole.beneficiaire.domain.model.QBeneficiaire;
import be.domaindrivendesign.kernel.data.jpa.RepositoryJpa;
import be.domaindrivendesign.shared.valueobject.NumeroEntreprise;
import be.domaindrivendesign.shared.valueobject.NumeroIdentificationRegistreNational;
import com.querydsl.jpa.impl.JPAQuery;

import java.util.UUID;

public class JpaBeneficiaireRepository extends RepositoryJpa<Beneficiaire, UUID> implements BeneficiaireRepository {

    @Override
    public Beneficiaire getBeneficiaireForNumeroEntreprise(NumeroEntreprise numeroEntreprise) {
        JPAQuery<Beneficiaire> query = new JPAQuery<>(getJpaUnitOfWork().getEntityManager());
        QBeneficiaire beneficiaires = QBeneficiaire.beneficiaire;
        return query.from(beneficiaires).where(beneficiaires.numeroEntreprise.eq(numeroEntreprise)).fetchOne();
    }

    @Override
    public Beneficiaire getBeneficiaireForNumeroIdentificationRegistreNational(NumeroIdentificationRegistreNational numeroIdentificationRegistreNational) {
        JPAQuery<Beneficiaire> query = new JPAQuery<>(getJpaUnitOfWork().getEntityManager());
        QBeneficiaire beneficiaires = QBeneficiaire.beneficiaire;
        return query.from(beneficiaires).where(beneficiaires.numeroIdentificationRegistreNational.eq(numeroIdentificationRegistreNational)).fetchOne();
    }
}
