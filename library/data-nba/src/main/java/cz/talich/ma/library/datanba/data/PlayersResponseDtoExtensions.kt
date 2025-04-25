package cz.talich.ma.library.datanba.data

import cz.talich.ma.library.datanba.model.Player
import cz.talich.ma.library.datanba.model.PlayerId
import cz.talich.ma.library.datanba.model.Team
import cz.talich.ma.library.datanba.model.TeamId

internal fun PlayersResponseDto.toModel() =
    data.map { it.toModel() }

private fun PlayerDto.toModel() = Player(
    id = PlayerId(id),
    firstName = firstName,
    lastName = lastName,
    position = position,
    height = height,
    weight = weight,
    jerseyNumber = jerseyNumber,
    college = college,
    country = country,
    draftYear = draftYear,
    draftRound = draftRound,
    draftNumber = draftNumber,
    team = team.toModel(),
)

private fun TeamDto.toModel() = Team(
    id = TeamId(id),
    conference = conference,
    division = division,
    city = city,
    name = name,
    fullName = fullName,
    abbreviation = abbreviation
)
