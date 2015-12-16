package be.domaindrivendesign.ecole.module.etablissement.data.jpa;

import be.domaindrivendesign.ecole.module.common.type.ClasseType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ClasseTypeConverter implements AttributeConverter<ClasseType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ClasseType attribute) {
        switch (attribute) {
            case Maternelle:
                return ClasseType.Maternelle.typeValue;
            case Primaire:
                return ClasseType.Primaire.typeValue;
            case PremierePrimaire:
                return ClasseType.PremierePrimaire.typeValue;
            case DeuxiemePrimaire:
                return ClasseType.DeuxiemePrimaire.typeValue;
            case TroisièmePrimaire:
                return ClasseType.TroisièmePrimaire.typeValue;
            case QuatriemePrimaire:
                return ClasseType.QuatriemePrimaire.typeValue;
            case CinquiemePrimaire:
                return ClasseType.CinquiemePrimaire.typeValue;
            case SixiemePrimaire:
                return ClasseType.SixiemePrimaire.typeValue;
            case Secondaire:
                return ClasseType.Secondaire.typeValue;
            case PremiereSecondaire:
                return ClasseType.PremiereSecondaire.typeValue;
            case DeuxiemeSecondaire:
                return ClasseType.DeuxiemeSecondaire.typeValue;
            case TroisièmeSecondaire:
                return ClasseType.TroisièmeSecondaire.typeValue;
            case QuatriemeSecondaire:
                return ClasseType.QuatriemeSecondaire.typeValue;
            case CinquiemeSecondaire:
                return ClasseType.CinquiemeSecondaire.typeValue;
            case SixiemeSecondaire:
                return ClasseType.SixiemeSecondaire.typeValue;
            default:
                return ClasseType.Inconnu.typeValue;
        }
    }

    @Override
    public ClasseType convertToEntityAttribute(Integer dbData) {
        switch (dbData) {
            case 1:
                return ClasseType.Maternelle;
            case 10:
                return ClasseType.Primaire;
            case 11:
                return ClasseType.PremierePrimaire;
            case 12:
                return ClasseType.DeuxiemePrimaire;
            case 13:
                return ClasseType.TroisièmePrimaire;
            case 14:
                return ClasseType.QuatriemePrimaire;
            case 15:
                return ClasseType.CinquiemePrimaire;
            case 16:
                return ClasseType.SixiemePrimaire;

            case 20:
                return ClasseType.Secondaire;
            case 21:
                return ClasseType.PremiereSecondaire;
            case 22:
                return ClasseType.DeuxiemeSecondaire;
            case 23:
                return ClasseType.TroisièmeSecondaire;
            case 24:
                return ClasseType.QuatriemeSecondaire;
            case 25:
                return ClasseType.CinquiemeSecondaire;
            case 26:
                return ClasseType.SixiemeSecondaire;

            default:
                return ClasseType.Inconnu;
        }
    }
}
