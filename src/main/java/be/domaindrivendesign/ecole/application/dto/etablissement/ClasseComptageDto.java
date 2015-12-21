package be.domaindrivendesign.ecole.application.dto.etablissement;

import be.domaindrivendesign.ecole.module.common.type.ClasseType;
import be.domaindrivendesign.ecole.module.etablissement.domain.model.ClasseComptage;
import be.domaindrivendesign.kernel.module.dto.Dto;

import java.util.function.Function;

public class ClasseComptageDto extends Dto {

    public static Function<ClasseComptage, ClasseComptageDto> aggregateToDto
            = new Function<ClasseComptage, ClasseComptageDto>() {
        @Override
        public ClasseComptageDto apply(ClasseComptage classeComptage) {
            return ClasseComptageDto.convertir(classeComptage);
        }
    };
    public ClasseType classe;
    public int nombreEleves;

    public static ClasseComptageDto convertir(ClasseComptage classeComptage) {
        if (classeComptage == null)
            return null;

        ClasseComptageDto classeComptageDto = new ClasseComptageDto();
        classeComptageDto.classe = classeComptage.getClasse();
        classeComptageDto.nombreEleves = classeComptage.getNombreEleves();
        return classeComptageDto;
    }

    public ClasseType getClasse() {
        return classe;
    }

    public void setClasse(ClasseType classe) {
        this.classe = classe;
    }

    public int getNombreEleves() {
        return nombreEleves;
    }

    public void setNombreEleves(int nombreEleves) {
        this.nombreEleves = nombreEleves;
    }
}
