package cz.talich.ma.library.datanba.data

import com.google.gson.annotations.SerializedName

data class PlayersResponseDto(
    val data: List<PlayerDto>,
    val meta: PlayersResponseMetaDto
)

data class PlayersResponseMetaDto(
    @SerializedName("next_cursor") val nextCursor: Int,
    @SerializedName("per_page")val pageSize: Int
)

data class PlayerDto(
    val id: Int,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    val position: String,
    val height: String?,
    val weight: String?,
    @SerializedName("jersey_number") val jerseyNumber: String?,
    val college: String?,
    val country: String?,
    @SerializedName("draft_year") val draftYear: Int?,
    @SerializedName("draft_round") val draftRound: Int?,
    @SerializedName("draft_number") val draftNumber: Int?,
    val team: TeamDto
)

data class TeamDto(
    val id: Int,
    val conference: String,
    val division: String,
    val city: String,
    val name: String,
    @SerializedName("full_name") val fullName: String,
    val abbreviation: String
)
