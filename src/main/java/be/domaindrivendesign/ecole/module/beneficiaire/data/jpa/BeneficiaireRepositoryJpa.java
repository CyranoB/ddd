package be.domaindrivendesign.ecole.module.beneficiaire.data.jpa;


import be.domaindrivendesign.ecole.module.beneficiaire.data.interfaces.BeneficiaireRepository;
import be.domaindrivendesign.ecole.module.beneficiaire.domain.model.Beneficiaire;
import be.domaindrivendesign.ecole.module.beneficiaire.domain.model.QBeneficiaire;
import be.domaindrivendesign.kernel.data.jpa.RepositoryJpa;
import be.domaindrivendesign.shared.valueobject.NumeroEntreprise;
import be.domaindrivendesign.shared.valueobject.NumeroIdentificationRegistreNational;
import com.querydsl.jpa.impl.JPAQuery;

public class BeneficiaireRepositoryJpa extends RepositoryJpa<Beneficiaire> implements BeneficiaireRepository {

    @Override
    public Beneficiaire getBeneficiaireForNumeroEntreprise(NumeroEntreprise numeroEntreprise) {
        JPAQuery<Beneficiaire> query = new JPAQuery<>(getUnitOfWorkJpa().getEntityManager());
        QBeneficiaire beneficiaires = QBeneficiaire.beneficiaire;
        return query.from(beneficiaires).where(beneficiaires.numeroEntreprise.eq(numeroEntreprise)).fetchOne();
    }

    @Override
    public Beneficiaire getBeneficiaireForNumeroIdentificationRegistreNational(NumeroIdentificationRegistreNational numeroIdentificationRegistreNational) {
        JPAQuery<Beneficiaire> query = new JPAQuery<>(getUnitOfWorkJpa().getEntityManager());
        QBeneficiaire beneficiaires = QBeneficiaire.beneficiaire;
        return query.from(beneficiaires).where(beneficiaires.numeroIdentificationRegistreNational.eq(numeroIdentificationRegistreNational)).fetchOne();
    }
}
