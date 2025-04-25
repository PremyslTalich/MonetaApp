package cz.talich.ma.feature.players.system

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import cz.talich.ma.library.datanba.model.Team
import cz.talich.ma.library.datanba.model.logo
import cz.talich.ma.library.design.system.MyPreviewTheme
import cz.talich.ma.library.design.system.MyTheme
import cz.talich.ma.library.localization.R
import cz.talich.ma.feature.players.R.drawable as drawable

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TeamDetail(
    team: Team,
    onTeamDetailClosed: () -> Unit,
) = Column {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = team.fullName,
                style = MyTheme.typography.titleLarge,
            )
            Text(
                text = team.abbreviation,
                style = MyTheme.typography.labelSmall,
                modifier = Modifier
                    .padding(start = 5.dp)
            )
        }
        IconButton(
            onClick = onTeamDetailClosed,
        ) {
            Icon(
                painter = painterResource(drawable.close),
                tint = MyTheme.colorScheme.primary,
                contentDescription = "close team detail",
            )
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            KeyValueAttribute(
                text = stringResource(R.string.conference) + ":",
                value = team.conference,
            )
            KeyValueAttribute(
                text = stringResource(R.string.division) + ":",
                value = team.division,
            )
            KeyValueAttribute(
                text = stringResource(R.string.city) + ":",
                value = team.city,
            )
        }
        GlideImage(
            model = team.logo?.toUri() ?: drawable.unknown,
            contentDescription = "nba logo",
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.TopCenter,
            loading = placeholder(drawable.logo_placeholder),
            modifier = Modifier
                .size(100.dp)
        )
    }

    Spacer(modifier = Modifier.height(20.dp))
}

@Preview
@Composable
fun TeamDetailPreview() = MyPreviewTheme {
    TeamDetail(
        team = mockTeam,
        onTeamDetailClosed = {}
    )
}
