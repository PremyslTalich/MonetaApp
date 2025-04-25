package cz.talich.ma.feature.players.system

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import cz.talich.ma.feature.players.R
import cz.talich.ma.feature.players.presentation.PlayersViewModel
import cz.talich.ma.feature.players.presentation.PlayersViewModel.State
import cz.talich.ma.feature.players.presentation.name
import cz.talich.ma.library.datanba.model.Player
import cz.talich.ma.library.datanba.model.PlayerId
import cz.talich.ma.library.datanba.model.Team
import cz.talich.ma.library.datanba.model.TeamId
import cz.talich.ma.library.design.system.MyPreviewTheme
import cz.talich.ma.library.design.system.MyTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

private const val NBA_LOGO_URL = "https://cdn.brandfetch.io/idFBraEt77/w/800/h/455/theme/dark/logo.png?c=1dxbfHSJFAPEGdCLU4o5B"

@Composable
fun PlayersScreen() {
    val viewModel = koinViewModel<PlayersViewModel>()
    val state by viewModel.states.collectAsState()

    // for previews, so that we don't have to mock the viewModel.
    PlayersScreenImpl(
        state = state,
        onLoadMorePlayers = viewModel::onLoadMorePlayers,
        onPlayerClick = viewModel::onPlayerClick,
        onPlayerDetailClosed = viewModel::onPlayerDetailClosed,
        onErrorConsumed = viewModel::onErrorConsumed,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayersScreenImpl(
    state: State,
    onLoadMorePlayers: () -> Unit = {},
    onPlayerClick: (Player) -> Unit = {},
    onPlayerDetailClosed: () -> Unit = {},
    onErrorConsumed: () -> Unit = {},
) = MyTheme {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val isScrollToTopVisible = remember { mutableStateOf(false) }
    val playersListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { TopBar() },
                scrollBehavior = scrollBehavior,
                actions = {
                    TopBarActions(
                        isScrollToTopVisible = isScrollToTopVisible,
                        playersListState = playersListState,
                        coroutineScope = coroutineScope,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent,
                ),
            )
        }
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Players(
                playersListState = playersListState,
                players = state.players,
                isLoading = state.isLoading,
                isScrollToTopVisible = isScrollToTopVisible,
                onLoadMorePlayers = onLoadMorePlayers,
                onPlayerClick = onPlayerClick,
            )
            if (state.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }
    }

    state.selectedPlayer?.let {
        BottomSheet(
            selectedPlayer = it,
            onPlayerDetailClosed = onPlayerDetailClosed
        )
    }

    state.error?.let {
        ShowError(
            error = it,
            onErrorConsumed = onErrorConsumed
        )
    }
}

@Composable
fun TopBar() {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .fillMaxWidth()
            .height(100.dp)
    ) {
        // In my experience Coil is the better choice for jetpack compose
        // (but I used GlideImage in TeamDetail composable)
        AsyncImage(
            model = NBA_LOGO_URL,
            contentDescription = "nba logo",
            contentScale = ContentScale.FillHeight,
            alignment = Alignment.CenterStart,
            placeholder = painterResource(R.drawable.logo_placeholder),
            modifier = Modifier.fillMaxHeight()
        )
    }
}

@Composable
fun TopBarActions(
    isScrollToTopVisible: MutableState<Boolean>,
    playersListState: LazyListState,
    coroutineScope: CoroutineScope,
) {
    if (isScrollToTopVisible.value) {
        IconButton(
            onClick = { playersListState.scrollToTop(coroutineScope) }
        ) {
            Icon(
                painter = painterResource(R.drawable.go_up),
                tint = MyTheme.colorScheme.primary,
                contentDescription = "scroll to the top",
            )
        }
    }
}

private fun LazyListState.scrollToTop(scope: CoroutineScope) {
    scope.launch { animateScrollToItem(0) }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    selectedPlayer: Player,
    onPlayerDetailClosed: () -> Unit,
) {
    val isTeamDetailOpen = remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = { onPlayerDetailClosed() },
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
                .animateContentSize()
        ) {
            if (isTeamDetailOpen.value) {
                TeamDetail(
                    team = selectedPlayer.team,
                    onTeamDetailClosed = { isTeamDetailOpen.value = false },
                )
            } else {
                PlayerDetail(
                    player = selectedPlayer,
                    onTeamDetailOpened = { isTeamDetailOpen.value = true },
                )
            }
        }
    }
}

@Composable
fun ShowError(
    error: String,
    onErrorConsumed: () -> Unit,
) {
    Toast.makeText(LocalContext.current, error, Toast.LENGTH_LONG).show()

    onErrorConsumed()
}

@Composable
fun Players(
    playersListState: LazyListState,
    players: List<Player>,
    isLoading: Boolean,
    isScrollToTopVisible: MutableState<Boolean>,
    onLoadMorePlayers: () -> Unit,
    onPlayerClick: (Player) -> Unit,
) {
    val isLastIndexVisible = remember { mutableStateOf(false) }

    LaunchedEffect(playersListState) {
        snapshotFlow { playersListState.layoutInfo.visibleItemsInfo }.collectLatest { visibleItems ->
            val lastVisibleIndex = playersListState.firstVisibleItemIndex + visibleItems.lastIndex
            isLastIndexVisible.value = lastVisibleIndex >= playersListState.layoutInfo.totalItemsCount - 1

            isScrollToTopVisible.value = playersListState.firstVisibleItemIndex > 10
        }
    }

    LaunchedEffect(isLastIndexVisible.value) {
        if (isLastIndexVisible.value && players.isNotEmpty() && !isLoading) {
            onLoadMorePlayers()
        }
    }

    LazyColumn(
        state = playersListState,
        modifier = Modifier
            .padding(horizontal = 20.dp)
    ) {
        itemsIndexed(players) { index, player ->
            Spacer(modifier = Modifier.height(20.dp))

            Player(
                player = player,
                onPlayerClick = onPlayerClick
            )

            if (index == players.lastIndex) {
                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}

@Composable
fun Player(
    player: Player,
    onPlayerClick: (Player) -> Unit,
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(7.dp))
            .background(color = MyTheme.colorScheme.surfaceContainer)
            .border(
                width = 1.dp,
                color = MyTheme.colorScheme.onSurfaceVariant,
                shape = RoundedCornerShape(7.dp),
            )
            .clickable { onPlayerClick(player) }
            .fillMaxWidth()
            .padding(
                horizontal = 7.dp,
                vertical = 12.dp,
            )
    ) {
        Box(
            contentAlignment = Alignment.TopStart,
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
        }
        Spacer(modifier = Modifier.height(5.dp))
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = player.team.name,
                style = MyTheme.typography.labelMedium,
                color = MyTheme.colorScheme.secondary,
            )
        }
    }
}

@Preview
@Composable
private fun PlayersScreenPreview() = MyPreviewTheme {
    PlayersScreenImpl(
        state = mockState,
    )
}

internal val mockTeam = Team(
    id = TeamId(26),
    conference = "West",
    division = "Pacific",
    city = "Sacramento",
    name = "Kings",
    fullName = "Sacramento Kings",
    abbreviation = "SAC"
)

internal val mockPlayer = Player(
    id = PlayerId(36),
    firstName = "Kent",
    lastName = "Bazemore",
    position = "G",
    height = "6-4",
    weight = "195",
    jerseyNumber = "9",
    college = "Old Dominion",
    country = "USA",
    draftYear = 2025,
    draftRound = 20,
    draftNumber = 25,
    team = mockTeam
)

private val mockState = State(
    players = listOf(
        mockPlayer.copy(
            firstName = "LeBron",
            lastName = "James",
            position = "G",
            team = mockTeam.copy(
                fullName = "Los Angeles Lakers",
            ),
        ),
        mockPlayer.copy(
            firstName = "Stephen",
            lastName = "Curry",
            position = "G",
            team = mockTeam.copy(
                fullName = "Golden State Warriors",
            ),
        ),
        mockPlayer.copy(
            firstName = "Kevin",
            lastName = "Durant",
            position = "G",
            team = mockTeam.copy(
                fullName = "Phoenix Suns",
            ),
        ),
        mockPlayer.copy(
            firstName = "Giannis",
            lastName = "Antetokounmpo",
            position = "G",
            team = mockTeam.copy(
                fullName = "Milwaukee Bucks",
            ),
        )
    ),
    isLoading = false,
    selectedPlayer = null,
)
