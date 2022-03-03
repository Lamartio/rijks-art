package io.lamart.aholdart.domain

import kotlinx.serialization.Serializable

@Serializable
data class ArtDetails(
    val artObject: ArtObject,
//    val artObjectPage: ArtObjectPage,
//    val elapsedMilliseconds: Int
) {

    @Serializable
    data class ArtObject(
        val id: String,
        val labelText: String?,
        val language: String,
        val links: Links,
        val location: String,
    )

    @Serializable
    data class ArtObjectPage(
        val createdOn: String,
        val id: String,
        val lang: String,
        val objectNumber: String,
        val plaqueDescription: String,
        val updatedOn: String
    )

    @Serializable
    data class Acquisition(
        val creditLine: String,
        val date: String,
        val method: String
    )

    @Serializable
    data class Classification(
        val iconClassDescription: List<String>,
        val iconClassIdentifier: List<String>,
        val objectNumbers: List<String>,
        val people: List<String>,
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
        val dateOfBirth: String,
        val dateOfDeath: String,
        val labelDesc: String,
        val name: String,
        val occupation: List<String>,
        val placeOfBirth: String,
        val placeOfDeath: String,
        val productionPlaces: List<String>,
        val qualification: String,
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