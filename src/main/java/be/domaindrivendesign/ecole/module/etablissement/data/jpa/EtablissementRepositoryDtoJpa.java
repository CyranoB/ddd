package be.domaindrivendesign.ecole.module.etablissement.data.jpa;


import be.domaindrivendesign.ecole.application.dto.etablissement.*;
import be.domaindrivendesign.ecole.module.etablissement.data.interfaces.EtablissementRepositoryDto;
import be.domaindrivendesign.ecole.module.etablissement.domain.model.Etablissement;
import be.domaindrivendesign.ecole.module.etablissement.domain.model.Implantation;
import be.domaindrivendesign.ecole.module.etablissement.domain.model.QEtablissement;
import be.domaindrivendesign.ecole.module.etablissement.domain.model.QImplantation;
import be.domaindrivendesign.kernel.data.jpa.RepositoryJpa;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EtablissementRepositoryDtoJpa extends RepositoryJpa<Etablissement> implements EtablissementRepositoryDto {


    @Override
    public EtablissementDto getEtablissementByNumeroReference(EtablissementDtoSearch search) {
        if (search == null || StringUtils.isNotBlank(search.getNumeroReference()))
            return null;
        JPAQuery<Etablissement> query = new JPAQuery<>(getUnitOfWork().getEntityManager());
        QEtablissement etablissements = QEtablissement.etablissement;
        Etablissement etablissement = query.from(etablissements).where(etablissements.numeroReference.eq(search.numeroReference)).fetchFirst();
        return EtablissementDto.convertir(etablissement, null);
    }

    @Override
    public List<EtablissementDtoList> listEtablissement(EtablissementDtoSearch search) {
        JPAQuery<Etablissement> query = new JPAQuery<>(getUnitOfWork().getEntityManager());
        QEtablissement etablissements = QEtablissement.etablissement;
        query = query.from(etablissements);
        int take = 100;
        int skip = 0;

        if (search != null) {
            if (search.id != null && StringUtils.isNotBlank(search.id.toString()))
                query = query.where(etablissements.id.eq(search.id));

            if (StringUtils.isNotBlank(search.denomination))
                query = query.where(etablissements.denomination.eq(search.denomination));

            if (search.codePostal > 0)
                query = query.where(etablissements.adresse.codePostal.eq(search.codePostal));

            if (StringUtils.isNotBlank(search.numeroReference))
                query = query.where(etablissements.numeroReference.toUpperCase().eq(search.numeroReference.toUpperCase()));
            take = search.take;
            skip = search.skip;
        }
        query = query.orderBy(etablissements.id.asc()).offset(skip).limit(take);
        List<Etablissement> results = query.fetch();
        return EtablissementDtoList.convertir(results);
    }

    @Override
    public List<ImplantationDtoList> listImplantation(ImplantationDtoSearch search) {
        List<Implantation> selected = (search != null && search.etablissementId != null) ? listByEtablissementId(search) : listByImplentationId(search);
        return ImplantationDtoList.convertir(selected);
    }


    private List<Implantation> listByImplentationId(ImplantationDtoSearch search) {
        JPAQuery<Implantation> query = new JPAQuery<>(getUnitOfWork().getEntityManager());
        QImplantation implantations = QImplantation.implantation;
        query = query.from(implantations);

        int take = 100;
        int skip = 0;
        if (search != null) {
            if (search.id != null)
                query = query.where(implantations.id.eq(search.id));
            if (StringUtils.isNotBlank(search.denomination))
                query = query.where(implantations.denomination.contains(search.denomination));
            if (StringUtils.isNotBlank(search.numeroReference))
                query = query.where(implantations.numeroReference.contains(search.numeroReference));
            if (search.codePostal > 0)
                query = query.where(implantations.adresse.codePostal.eq(search.codePostal));
            take = search.take;
            skip = search.skip;
        }
        query = query.orderBy(implantations.id.asc()).offset(skip).limit(take);
        return query.fetch();
    }


    private List<Implantation> listByEtablissementId(ImplantationDtoSearch search) {
        // TODO On doit pouvoir faire plus simple...
        JPAQuery<Etablissement> query = new JPAQuery<>(getUnitOfWork().getEntityManager());
        QEtablissement etablissements = QEtablissement.etablissement;
        query = query.from(etablissements);

        int take = 100;
        int skip = 0;

        if (search != null && search.etablissementId != null) {
            query = query.where(etablissements.id.eq(search.etablissementId));
            take = search.take;
            skip = search.skip;
        }

        List<Etablissement> el = query.orderBy(etablissements.id.asc()).offset(skip).limit(take).fetch();
        ArrayList<Implantation> implantations = new ArrayList<>();
        for (Etablissement e : el) {
            implantations.addAll(e.getImplantations());
        }
        return implantations;
    }
}
