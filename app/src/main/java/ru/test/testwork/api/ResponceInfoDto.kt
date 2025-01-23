package ru.test.testwork.api

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import ru.test.testwork.entity.ResponceInfo

@Parcelize
@JsonClass(generateAdapter = true)
data class ResponseInfoDto(
    @Json(name = "title") override val title: String?,
    @Json(name = "objectBeginDate") override val objectBeginDate: String?,
    @Json(name = "objectEndDate") override val objectEndDate: String?,
    @Json(name = "primaryImageSmall") override val primaryImageSmall: String?,
    @Json(name = "artistRole") override val artistRole: String?,
    @Json(name = "artistDisplayName") override val artistDisplayName: String?,
    @Json(name = "artistDisplayBio") override val artistDisplayBio: String?,
    @Json(name = "department") override val department: String?,
    @Json(name = "culture") override val culture: String?,
    @Json(name = "period") override val period: String?,
    @Json(name = "medium") override val medium: String?,
    @Json(name = "dimensions") override val dimensions: String?
) : ResponceInfo, Parcelable
