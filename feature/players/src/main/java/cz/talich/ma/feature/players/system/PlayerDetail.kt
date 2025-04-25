package cz.talich.ma.feature.players.system

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.talich.ma.feature.players.presentation.name
import cz.talich.ma.library.datanba.model.Player
import cz.talich.ma.library.design.system.MyPreviewTheme
import cz.talich.ma.library.design.system.MyTheme
import cz.talich.ma.library.localization.R

private val dataPadding = 20.dp

@Composable
fun PlayerDetail(
    player: Player,
    onTeamDetailOpened: () -> Unit,
) = Column {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = player.name,
                style = MyTheme.typography.titleLarge,
            )
            Text(
                text = player.position,
                style = MyTheme.typography.labelSmall,
                modifier = Modifier
                    .padding(start = 5.dp)
            )
        }

        val jerseyNumber = player.jerseyNumber?.let {
            "#$it"
        } ?: stringResource(R.string.unknown_value)
        
        Text(
            text = jerseyNumber,
            style = MyTheme.typography.titleLarge,
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column {
            KeyValueAttribute(
                text = stringResource(R.string.country) + ":",
                value = player.country ?: stringResource(R.string.unknown_value),
            )
            KeyValueAttribute(
                text = stringResource(R.string.college) + ":",
                value = player.college ?: stringResource(R.string.unknown_value),
            )
            KeyValueAttribute(
                text = stringResource(R.string.height) + ":",
                value = player.height ?: stringResource(R.string.unknown_value),
            )
            KeyValueAttribute(
                text = stringResource(R.string.weight) + ":",
                value = player.weight ?: stringResource(R.string.unknown_value),
            )
        }

        Column {
            Draft(
                year = player.draftYear,
                round = player.draftRound,
                number = player.draftNumber,
            )
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    Button(
        onClick = onTeamDetailOpened,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.open_team_detail, player.team.name),
            color = MyTheme.colorScheme.onPrimary,
        )
    }

    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun Draft(
    year: Int?,
    round: Int?,
    number: Int?,
) {
    if (year == null && round == null && number == null) {
        return
    }

    Column {
        Text(
            text = stringResource(R.string.draft),
            style = MyTheme.typography.titleMedium,
        )

        val modifier = Modifier.padding(start = dataPadding)

        year?.let {
            KeyValueAttribute(
                text = stringResource(R.string.year),
                value = it.toString(),
                modifier = modifier,
            )
        }
        round?.let {
            KeyValueAttribute(
                text = stringResource(R.string.round),
                value = it.toString(),
                modifier = modifier,
            )
        }
        number?.let {
            KeyValueAttribute(
                text = stringResource(R.string.number),
                value = it.toString(),
                modifier = modifier,
            )
        }
    }
}

@Preview
@Composable
fun PlayerDetailPreview() = MyPreviewTheme {
    PlayerDetail(
        player = mockPlayer,
        onTeamDetailOpened = {}
    )
}