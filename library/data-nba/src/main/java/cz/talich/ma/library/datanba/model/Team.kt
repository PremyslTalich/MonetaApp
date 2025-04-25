package cz.talich.ma.library.datanba.model

data class Team(
    val id: TeamId,
    val conference: String,
    val division: String,
    val city: String,
    val name: String,
    val fullName: String,
    val abbreviation: String
)
