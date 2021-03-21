package com.alyndroid.decadeofmovies.domain.model

import com.google.gson.annotations.SerializedName


data class ImageResponse(
    @SerializedName("photo")
    val photo: List<Image>
)

data class ImageWrapperResponse(
    @SerializedName("photos")
    val photos: ImageResponse,
    @SerializedName("stat")
val stat: String
)

data class Image(
    @SerializedName("owner")
    val owner: String = "",
    @SerializedName("server")
    val server: String = "",
    @SerializedName("ispublic")
    val ispublic: Int = 0,
    @SerializedName("isfriend")
    val isfriend: Int = 0,
    @SerializedName("farm")
    val farm: Int = 0,
    @SerializedName("id")
    val id: String = "",
    @SerializedName("secret")
    val secret: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("isfamily")
    val isfamily: Int = 0
) {
    fun toUrl(): String {
        return "https://farm$farm.static.flickr.com/$server/${id}_$secret.jpg"
    }
}