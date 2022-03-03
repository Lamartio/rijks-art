package io.lamart.aholdart.network.types

data class ArtDetails(
    val artObject: ArtObject,
    val artObjectPage: ArtObjectPage,
    val elapsedMilliseconds: Int
) {

    data class ArtObject(
        val acquisition: Acquisition,
        val artistRole: Any,
        val associations: List<Any>,
        val catRefRPK: List<Any>,
        val classification: Classification,
        val colors: List<Color>,
        val colorsWithNormalization: List<ColorsWithNormalization>,
        val copyrightHolder: Any,
        val dating: Dating,
        val description: String,
        val dimensions: List<Dimension>,
        val documentation: List<Any>,
        val exhibitions: List<Any>,
        val hasImage: Boolean,
        val historicalPersons: List<String>,
        val id: String,
        val inscriptions: List<Any>,
        val label: Label,
        val labelText: Any,
        val language: String,
        val links: Links,
        val location: String,
        val longTitle: String,
        val makers: List<Any>,
        val materials: List<String>,
        val normalized32Colors: List<Normalized32Color>,
        val normalizedColors: List<NormalizedColor>,
        val objectCollection: List<String>,
        val objectNumber: String,
        val objectTypes: List<String>,
        val physicalMedium: String,
        val physicalProperties: List<Any>,
        val plaqueDescriptionDutch: String,
        val plaqueDescriptionEnglish: String,
        val principalMaker: String,
        val principalMakers: List<PrincipalMaker>,
        val principalOrFirstMaker: String,
        val priref: String,
        val productionPlaces: List<String>,
        val scLabelLine: String,
        val showImage: Boolean,
        val subTitle: String,
        val techniques: List<Any>,
        val title: String,
        val titles: List<String>,
        val webImage: WebImage
    )

    data class ArtObjectPage(
        val adlibOverrides: AdlibOverrides,
        val audioFile1: Any,
        val audioFileLabel1: Any,
        val audioFileLabel2: Any,
        val createdOn: String,
        val id: String,
        val lang: String,
        val objectNumber: String,
        val plaqueDescription: String,
        val similarPages: List<Any>,
        val tags: List<Any>,
        val updatedOn: String
    )

    data class Acquisition(
        val creditLine: String,
        val date: String,
        val method: String
    )

    data class Classification(
        val events: List<Any>,
        val iconClassDescription: List<String>,
        val iconClassIdentifier: List<String>,
        val motifs: List<Any>,
        val objectNumbers: List<String>,
        val people: List<String>,
        val periods: List<Any>,
        val places: List<Any>
    )

    data class Color(
        val hex: String,
        val percentage: Int
    )

    data class ColorsWithNormalization(
        val normalizedHex: String,
        val originalHex: String
    )

    data class Dating(
        val period: Int,
        val presentingDate: String,
        val sortingDate: Int,
        val yearEarly: Int,
        val yearLate: Int
    )

    data class Dimension(
        val part: Any,
        val type: String,
        val unit: String,
        val value: String
    )

    data class Label(
        val date: String,
        val description: String,
        val makerLine: String,
        val notes: String,
        val title: String
    )

    data class Links(
        val search: String
    )

    data class Normalized32Color(
        val hex: String,
        val percentage: Int
    )

    data class NormalizedColor(
        val hex: String,
        val percentage: Int
    )

    data class PrincipalMaker(
        val biography: Any,
        val dateOfBirth: String,
        val dateOfBirthPrecision: Any,
        val dateOfDeath: String,
        val dateOfDeathPrecision: Any,
        val labelDesc: String,
        val name: String,
        val nationality: Any,
        val occupation: List<String>,
        val placeOfBirth: String,
        val placeOfDeath: String,
        val productionPlaces: List<String>,
        val qualification: String,
        val roles: List<String>,
        val unFixedName: String
    )

    data class WebImage(
        val guid: String,
        val height: Int,
        val offsetPercentageX: Int,
        val offsetPercentageY: Int,
        val url: String,
        val width: Int
    )

    data class AdlibOverrides(
        val etiketText: Any,
        val maker: Any,
        val titel: Any
    )
}