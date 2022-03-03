package io.lamart.aholdart.rijksmuseum.types

data class ArtCollection(
    val artObjects: List<ArtObject>,
) {

    data class ArtObject(
        val hasImage: Boolean,
        val headerImage: HeaderImage,
        val id: String,
        val links: Links,
        val longTitle: String,
        val objectNumber: String,
        val permitDownload: Boolean,
        val principalOrFirstMaker: String,
        val productionPlaces: List<String>,
        val showImage: Boolean,
        val title: String,
        val webImage: WebImage
    )

    data class HeaderImage(
        val guid: String,
        val height: Int,
        val offsetPercentageX: Int,
        val offsetPercentageY: Int,
        val url: String,
        val width: Int
    )

    data class Links(
        val self: String,
        val web: String
    )

    data class WebImage(
        val guid: String,
        val height: Int,
        val offsetPercentageX: Int,
        val offsetPercentageY: Int,
        val url: String,
        val width: Int
    )
}