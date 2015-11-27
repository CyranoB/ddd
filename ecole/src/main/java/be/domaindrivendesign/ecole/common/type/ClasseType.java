package be.domaindrivendesign.ecole.common.type;

/**
 * Created by eddie on 27/11/15.
 */
public enum ClasseType {


    /// <summary>
    /// Maternelle
    /// </summary>
    Maternelle(1),

    /// <summary>
    /// Primaire
    /// </summary>
    Primaire(10),

    /// <summary>
    /// Première primaire
    /// </summary>
    PremierePrimaire(11),
    /// <summary>
    /// Deuxième primaire
    /// </summary>
    DeuxiemePrimaire(12),
    /// <summary>
    /// Troisième primaire
    /// </summary>
    TroisièmePrimaire(13),
    /// <summary>
    /// Quatrième primaire
    /// </summary>
    QuatriemePrimaire(14),
    /// <summary>
    /// Cinquième primaire
    /// </summary>
    CinquiemePrimaire(15),
    /// <summary>
    /// Sixième primaire
    /// </summary>
    SixiemePrimaire(16),

    /// <summary>
    /// Secondaire
    /// </summary>
    Secondaire(20),

    /// <summary>
    /// Première secondaire
    /// </summary>
    PremiereSecondaire(21),
    /// <summary>
    /// Deuxième secondaire
    /// </summary>
    DeuxiemeSecondaire(22),
    /// <summary>
    /// Troisième secondaire
    /// </summary>
    TroisièmeSecondaire(23),
    /// <summary>
    /// Quatrième secondaire
    /// </summary>
    QuatriemeSecondaire(24),
    /// <summary>
    /// Cinquième secondaire
    /// </summary>
    CinquiemeSecondaire(25),
    /// <summary>
    /// Sixième secondaire
    /// </summary>
    SixiemeSecondaire(26);

    public int typeValue;

    ClasseType(int typeValue) {
        this.typeValue = typeValue;
    }

}
