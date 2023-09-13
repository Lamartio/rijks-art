package io.lamart.rijksart.network

import kotlinx.serialization.Serializable

@Serializable
data class ArtDetails(
    val artObject: ArtObject,
    val artObjectPage: ArtObjectPage,
    val elapsedMilliseconds: Int
) {
    @Serializable
    data class ArtObject(
        val acquisition: Acquisition,
        val classification: Classification,
        val colors: List<Color>,
        val colorsWithNormalization: List<ColorsWithNormalization>,
        val dating: Dating,
        val description: String,
        val dimensions: List<Dimension>,
        val documentation: List<String>,
        val hasImage: Boolean,
        val historicalPersons: List<String>,
        val id: String,
        val label: Label,
        val language: String,
        val links: Links,
        val location: String?,
        val longTitle: String,
        val makers: List<Maker>,
        val materials: List<String>,
        val normalized32Colors: List<Normalized32Color>,
        val normalizedColors: List<NormalizedColor>,
        val objectCollection: List<String>,
        val objectNumber: String,
        val objectTypes: List<String>,
        val physicalMedium: String,
        val plaqueDescriptionDutch: String?,
        val plaqueDescriptionEnglish: String,
        val principalMaker: String,
        val principalMakers: List<PrincipalMaker>,
        val principalOrFirstMaker: String,
        val priref: String,
        val productionPlaces: List<String>,
        val scLabelLine: String,
        val showImage: Boolean,
        val subTitle: String,
        val techniques: List<String>,
        val title: String,
        val titles: List<String>,
        val webImage: WebImage
    ) {
        @Serializable
        data class Acquisition(
            val creditLine: String?,
            val date: String,
            val method: String
        )

        @Serializable
        data class Classification(
            val iconClassDescription: List<String>,
            val iconClassIdentifier: List<String>,
            val objectNumbers: List<String>,
            val people: List<String>,
            val periods: List<String>,
        )

        @Serializable
        data class Color(
            val hex: String,
            val percentage: Int
        )

        @Serializable
        data class ColorsWithNormalization(
            val normalizedHex: String,
            val originalHex: String
        )

        @Serializable
        data class Dating(
            val period: Int,
            val presentingDate: String,
            val sortingDate: Int,
            val yearEarly: Int,
            val yearLate: Int
        )

        @Serializable
        data class Dimension(
            val part: String?,
            val type: String,
            val unit: String,
            val value: String
        )

        @Serializable
        data class Label(
            val date: String,
            val description: String,
            val makerLine: String,
            val notes: String,
            val title: String
        )

        @Serializable
        data class Links(
            val search: String
        )

        @Serializable
        data class Maker(
            val labelDesc: String,
            val name: String,
            val roles: List<String>,
            val unFixedName: String
        )

        @Serializable
        data class Normalized32Color(
            val hex: String,
            val percentage: Int
        )

        @Serializable
        data class NormalizedColor(
            val hex: String,
            val percentage: Int
        )

        @Serializable
        data class PrincipalMaker(
            val dateOfBirth: String?,
            val dateOfBirthPrecision: String?,
            val dateOfDeath: String?,
            val dateOfDeathPrecision: String?,
            val labelDesc: String,
            val name: String,
            val nationality: String?,
            val occupation: List<String>,
            val placeOfBirth: String?,
            val placeOfDeath: String?,
            val productionPlaces: List<String>,
            val qualification: String?,
            val roles: List<String>,
            val unFixedName: String
        )

        @Serializable
        data class WebImage(
            val guid: String,
            val height: Int,
            val offsetPercentageX: Int,
            val offsetPercentageY: Int,
            val url: String,
            val width: Int
        )
    }

    @Serializable
    data class ArtObjectPage(
        val createdOn: String,
        val id: String,
        val lang: String,
        val objectNumber: String,
        val plaqueDescription: String,
        val updatedOn: String
    )
}