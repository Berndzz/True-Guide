package com.hardus.trueagencyapp.main_content.program

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.main_content.home.data.Aktivitas
import com.hardus.trueagencyapp.main_content.home.data.Program
import com.hardus.trueagencyapp.main_content.home.data.ProgramWithActivities
import com.hardus.trueagencyapp.main_content.home.presentation.util.formatToString
import com.hardus.trueagencyapp.ui.theme.TrueAgencyAppTheme
import com.hardus.trueagencyapp.util.ProgramContentType
import kotlinx.coroutines.delay

@Composable
fun ProgramScreen(
    windowSize: WindowWidthSizeClass, onBackPressed: () -> Unit, viewModel: ProgramViewModel
) {

    val uiState by viewModel.uiState.collectAsState()
    val isLoading = viewModel.isLoading.value
    var refreshing by remember { mutableStateOf(false) }
    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(3000)
            refreshing = false
        }
    }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    val contentType = when (windowSize) {
        WindowWidthSizeClass.Compact, WindowWidthSizeClass.Medium -> ProgramContentType.ListOnly

        WindowWidthSizeClass.Expanded -> ProgramContentType.ListAndDetail
        else -> ProgramContentType.ListOnly
    }

    Scaffold(topBar = {
        TopAppBarProgram(
            onBackButtonClick = { viewModel.navigateToListPage() },
            isShowingListPage = uiState.isShowingListPage,
            windowSize = windowSize,
            viewModel = viewModel
        )
    }, content = { paddingValue ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            SwipeRefresh(state = swipeRefreshState, onRefresh = {
                //viewModel.refreshPrograms()
                refreshing = true
            }) {
                if (contentType == ProgramContentType.ListAndDetail) {
                    ProgramListAndDetail(
                        posts = uiState.programList,
                        selectedPost = uiState.currentProgram!!,
                        onClick = {
                            viewModel.updateCurrentProgram(it)
                        },
                        onBackPressed = onBackPressed,
                        contentPadding = paddingValue,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    if (uiState.isShowingListPage) {
                        ProgramList(
                            posts = uiState.programList,
                            onClick = {
                                viewModel.updateCurrentProgram(it)
                                viewModel.navigateToDetailPage()
                            },
                            contentPadding = paddingValue,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = dimensionResource(id = R.dimen.padding_medium),
                                    start = dimensionResource(id = R.dimen.padding_medium),
                                    end = dimensionResource(id = R.dimen.padding_medium)
                                )
                        )
                    } else {
                        if (uiState.isShowingListPage) {
                            ProgramList(
                                posts = uiState.programList,
                                onClick = {
                                    viewModel.updateCurrentProgram(it)
                                    viewModel.navigateToDetailPage()
                                },
                                contentPadding = paddingValue,
                                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
                            )
                        } else {
                            uiState.currentProgram?.let { currentProgram ->
                                ProgramDetail(
                                    selectedPost = currentProgram,
                                    onBackPressed = { viewModel.navigateToListPage() },
                                    contentPadding = paddingValue
                                )
                            } ?: run {
                                Text("No program selected")
                            }
                        }
                    }
                }
            }
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarProgram(
    onBackButtonClick: () -> Unit,
    isShowingListPage: Boolean,
    windowSize: WindowWidthSizeClass,
    viewModel: ProgramViewModel,
    modifier: Modifier = Modifier
) {
    val isShowingDetailPage = windowSize != WindowWidthSizeClass.Expanded && !isShowingListPage
    val uiState by viewModel.uiState.collectAsState()

    val displayedTitle = if (!isShowingDetailPage) {
        stringResource(id = R.string.list_fragment_label)
    } else {
        uiState.currentProgram?.program?.judul_program
    }

    TopAppBar(modifier = modifier, colors = topAppBarColors(
        MaterialTheme.colorScheme.primary
    ), title = {
        Text(
            text = displayedTitle
                ?: stringResource(id = R.string.default_title), // Default title jika null
            color = Color.White,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }, navigationIcon = if (!isShowingListPage) {
        {
            IconButton(onClick = onBackButtonClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }
    } else {
        { Box {} }
    })
}

@Composable
fun ProgramList(
    posts: List<ProgramWithActivities>,
    onClick: (ProgramWithActivities) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)),
        modifier = modifier
    ) {
        items(posts, key = { post -> post.program.id_program }) { post ->
            CardScreen(post = post, onItemClick = onClick)
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ProgramImage(post: Program, modifier: Modifier = Modifier) {
    val urlImage = post.photo_program
    val painter = if (urlImage.isNotEmpty()) {
        rememberImagePainter(data = urlImage)
    } else {
        painterResource(id = R.drawable.placeholder)
    }
    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = post.judul_program,
            alignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .size(600.dp, 600.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, MaterialTheme.colorScheme.scrim), 0f, 600f
                    )
                ),
            contentScale = ContentScale.FillHeight,
        )
        Text(
            text = "",
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardScreen(
    post: ProgramWithActivities,
    onItemClick: (ProgramWithActivities) -> Unit,
    modifier: Modifier = Modifier
) {
    val program = post.program
    Card(elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier,
        colors = CardDefaults.cardColors(Color.White),
        shape = MaterialTheme.shapes.small,
        onClick = { onItemClick(post) }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(128.dp)
        ) {
            ProgramImage(
                post = program, modifier = Modifier.size(128.dp)
            )
            Column(
                modifier = Modifier
                    .padding(
                        vertical = 8.dp,
                        horizontal = 16.dp,
                    )
                    .weight(1f)
            ) {
                Text(
                    text = program.deskripsi_program,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3
                )
                Spacer(Modifier.weight(1f))
                //Text(text = "${post.subTrainingList.length()} Kegiatan ")
                Text(
                    text = "${post.activities.size} Kegiatan",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ProgramDetail(
    selectedPost: ProgramWithActivities,
    onBackPressed: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    BackHandler {
        onBackPressed()
    }
    val program = selectedPost.program
    val urlImage = program.photo_program
    val painter = if (urlImage.isNotEmpty()) {
        rememberImagePainter(data = urlImage)
    } else {
        painterResource(id = R.drawable.placeholder)
    }
    val scrollState = rememberScrollState()
    val layoutDirection = LocalLayoutDirection.current

    Box(
        modifier = modifier
            .verticalScroll(state = scrollState)
            .padding(top = contentPadding.calculateTopPadding())
    ) {
        Column(
            modifier = Modifier.padding(
                    bottom = contentPadding.calculateTopPadding(),
                    start = contentPadding.calculateStartPadding(layoutDirection),
                    end = contentPadding.calculateEndPadding(layoutDirection)
                )
        ) {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(MaterialTheme.shapes.medium)
            ) {
                Box {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        alignment = Alignment.Center,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .align(Alignment.BottomStart)
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, MaterialTheme.colorScheme.scrim), 0f, 800f
                            )
                        )
                        .clip(MaterialTheme.shapes.medium)
                ) {
                    Text(
                        text = pluralStringResource(
                            R.plurals.activity_count_caption,
                            selectedPost.activities.size,
                            selectedPost.activities.size
                        ),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        modifier = Modifier.padding(top = 50.dp, start = 20.dp)
                    )
                }
            }
            Text(
                text = program.deskripsi_program,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(
                    vertical = dimensionResource(R.dimen.padding_medium),
                    horizontal = dimensionResource(R.dimen.padding_medium)
                )
            )
            Spacer(modifier = Modifier.padding(10.dp))
            ActivityScreen(post = selectedPost)
            Text(
                text = program.deskripsi_program2,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(
                    vertical = dimensionResource(R.dimen.padding_medium),
                    horizontal = dimensionResource(R.dimen.padding_medium)
                )
            )
        }
    }
}

@Composable
fun ActivityScreen(post: ProgramWithActivities) {
    Column {
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .height(IntrinsicSize.Max)
                .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (post.activities.isNotEmpty()) {
                post.activities.forEach { subTraining ->
                    ActivityCard(subTraining = subTraining)
                }
            } else {
                // Handle case when data is empty
                Text("No training data available", color = Color.Gray)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityCard(
    subTraining: Aktivitas
) {
    var openDialog by remember { mutableStateOf(false) }
    Card(modifier = Modifier
        .width(155.dp)
        .height(144.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
        onClick = { openDialog = true }) {
        Column {
            ActivityText(subTraining = subTraining)
            ActivityImage(subTraining = subTraining)
        }
    }
    if (openDialog) {
        AlertDialog(modifier = Modifier.height(500.dp),
            onDismissRequest = { openDialog = false },
            title = {
                Text(
                    text = subTraining.judul_aktivitas, style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Text(
                    text = subTraining.deskripsi_aktivitas,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog = false
                    },
                    modifier = Modifier.padding(15.dp),
                ) {
                    Text("Close")
                }
            })
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ActivityImage(subTraining: Aktivitas, modifier: Modifier = Modifier) {
    val urlImage = subTraining.gambar_aktivitas ?: ""
    val painter = if (urlImage.isNotEmpty()) {
        rememberImagePainter(data = urlImage)
    } else {
        painterResource(id = R.drawable.placeholder)
    }
    Image(
        painter = painter, contentDescription = subTraining.judul_aktivitas, // decorative
        modifier = modifier.fillMaxSize(), contentScale = ContentScale.FillWidth
    )
}

@Composable
fun ActivityText(subTraining: Aktivitas) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Text(
            text = subTraining.judul_aktivitas,
            color = Color.Black,
            maxLines = 1, //change with colorSchame
            overflow = TextOverflow.Ellipsis
        )
        //Text(text = "(${subTraining.hari_aktivitas.formatToString()})", maxLines = 1)
        Text(text = "(${subTraining.body_aktivitas})", maxLines = 1)
        Spacer(Modifier.padding(3.dp))
        Divider(
            color = Color.White, thickness = 2.dp, modifier = Modifier.background(
                shape = MaterialTheme.shapes.small, color = Color.Black
            )
        )
        Spacer(Modifier.padding(1.dp))
    }
}

@Composable
private fun ProgramListAndDetail(
    posts: List<ProgramWithActivities>, // Perbarui tipe data ini
    selectedPost: ProgramWithActivities, // Perbarui tipe data ini
    onClick: (ProgramWithActivities) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Row(
        modifier = modifier
    ) {
        ProgramList(
            posts = posts,
            onClick = onClick,
            contentPadding = contentPadding,
            modifier = Modifier
                .weight(2f)
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
        )
        ProgramDetail(
            selectedPost = selectedPost,
            onBackPressed = onBackPressed,
            contentPadding = contentPadding,
            modifier = Modifier.weight(3f)
        )
    }
}


@Preview
@Composable
fun CheckProgramScreen() {
    TrueAgencyAppTheme {
//        ProgramScreen(
//            windowSize = WindowWidthSizeClass.Compact,
//            onBackPressed = {}
//        )
    }
}

@Preview
@Composable
fun CheckProgramList() {
    TrueAgencyAppTheme {
        Surface {
//            ProgramList(
//                posts = LocalProgramDataProvider.generateFakeDataProgram(),
//                onClick = {}
//            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CheckProgramDetail() {
    TrueAgencyAppTheme {
//        ProgramDetail(
//            selectedPost = LocalProgramDataProvider.generateFakeDataProgram().getOrElse(0) {
//                LocalProgramDataProvider.defaultProgram
//            },
//            onBackPressed = {},
//            contentPadding = PaddingValues(0.dp)
//        )
    }
}

@Preview(device = Devices.TABLET)
@Composable
fun CheckProgramListAndDetail() {
    TrueAgencyAppTheme {
        Surface {
//            ProgramListAndDetail(
//                posts = LocalProgramDataProvider.generateFakeDataProgram(),
//                selectedPost = LocalProgramDataProvider.generateFakeDataProgram().getOrElse(0) {
//                    LocalProgramDataProvider.defaultProgram
//                },
//                onClick = {},
//                onBackPressed = { })
        }
    }
}