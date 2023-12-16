package com.hardus.trueagencyapp.main_content.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.PeopleAlt
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.auth.viewmodel.AuthViewModel
import com.hardus.trueagencyapp.util.Post
import com.hardus.trueagencyapp.util.SubTraining
import com.hardus.trueagencyapp.util.generateFakeData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController
) {
    val viewModel = hiltViewModel<AuthViewModel>()
    val fakePosts = generateFakeData()
    val nonEmptyPosts = fakePosts.filter { it.subTrainingList.isNotEmpty() }

    Scaffold(topBar = {
        TopAppBarHome()
    }, content = { paddingValue ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Surface {
                        LeaderStatus(
                            name = viewModel.currentUser?.displayName ?: "",
                            status = "Leader",
                            hierarchy = "AAD"
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Surface {
                        Menu()
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(
                        "Kegiatan di Minggu Ini",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 20.sp,
                        )
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }
            if (nonEmptyPosts.isNotEmpty()) {
                items(nonEmptyPosts) { post ->
                    TrainingScreen(post = post)
                }
            } else {
                // Handle case when all subTrainingLists are empty
                item {
                    Text("No training data available", color = Color.Gray)
                }
            }
        }
    })
}

@Composable
fun PostText(subTraining: SubTraining) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Text(text = subTraining.bodyTitle,color = Color.Black)
        Text(text = "(${subTraining.day})",color = Color.Black)
        Spacer(Modifier.padding(3.dp))
        Divider(
            color = Color.White, thickness = 2.dp, modifier = Modifier.background(
                shape = MaterialTheme.shapes.small, color = Color.Black
            )
        )
        Spacer(Modifier.padding(1.dp))
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PostImage(subTraining: SubTraining, modifier: Modifier = Modifier) {
    val urlImage = subTraining.image ?: ""
    val painter = if (urlImage.isNotEmpty()) {
        rememberImagePainter(data = urlImage)
    } else {
        painterResource(id = R.drawable.placeholder)
    }
    Image(
        painter = painter, contentDescription = subTraining.bodyTitle, // decorative
        modifier = modifier.fillMaxSize(), contentScale = ContentScale.FillWidth
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCard(
    subTraining: SubTraining
) {
    var openDialog by remember { mutableStateOf(false) }
    Card(modifier = Modifier
        .width(155.dp)
        .height(144.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
        onClick = { openDialog = true }) {
        Column {
            PostText(subTraining = subTraining)
            PostImage(subTraining = subTraining)
        }
    }
    if (openDialog) {
        AlertDialog(modifier = Modifier.height(500.dp),
            onDismissRequest = { openDialog = false },
            title = {
                Text(
                    text = subTraining.bodyTitle, style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Text(
                    text = subTraining.paragraph, style = MaterialTheme.typography.bodyLarge
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

@Composable
fun TrainingScreen(post: Post) {
    Column(modifier = Modifier.padding(10.dp)) {
        Text(
            text = post.headerTitle, fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.padding(bottom = 5.dp))
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .height(IntrinsicSize.Max)
                .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (post.subTrainingList.isNotEmpty()) {
                post.subTrainingList.forEach { subTraining ->
                    PostCard(subTraining = subTraining)
                }
            } else {
                // Handle case when data is empty
                Text("No training data available", color = Color.Gray)
            }
        }
    }
}

@Composable
fun Menu() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(
                color = MaterialTheme.colorScheme.inverseOnSurface,
                shape = MaterialTheme.shapes.medium
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        Surface(
            modifier = Modifier.size(30.dp),
            color = MaterialTheme.colorScheme.primary,
            shape = MaterialTheme.shapes.small,
        ) {
            Icon(
                imageVector = Icons.Outlined.NoteAlt,
                contentDescription = "note",
                modifier = Modifier
                    .size(30.dp)
                    .background(Color.Transparent),
                tint = Color.White,
            )
        }
        Divider(
            color = Color.White, modifier = Modifier
                .width(3.dp)
                .fillMaxHeight()
        )
        Surface(
            modifier = Modifier.size(30.dp),
            color = MaterialTheme.colorScheme.primary,
            shape = MaterialTheme.shapes.small,
        ) {
            Icon(
                imageVector = Icons.Outlined.QrCodeScanner,
                contentDescription = "note",
                modifier = Modifier.size(30.dp),
                tint = Color.White
            )
        }
        Divider(
            color = Color.White, modifier = Modifier
                .width(3.dp)
                .fillMaxHeight()
        )
        Surface(
            modifier = Modifier.size(30.dp),
            color = MaterialTheme.colorScheme.primary,
            shape = MaterialTheme.shapes.small,
        ) {
            Icon(
                imageVector = Icons.Outlined.PeopleAlt,
                contentDescription = "note",
                modifier = Modifier.size(30.dp),
                tint = Color.White
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarHome() {
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onSecondary,
        ), title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    painter = painterResource(R.drawable.vector),
                    contentDescription = stringResource(R.string.logo_app)
                )
            }
        }, scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    )
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun LeaderStatus(name: String, status: String, hierarchy: String) {
    val urlImage =
        "https://images.unsplash.com/photo-1554151228-14d9def656e4?q=80&w=986&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    val painter = rememberImagePainter(data = urlImage)
    Row {
        Card(
            modifier = Modifier.size(45.dp),
            shape = CircleShape,
            border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary)
        ) {
            Image(
                painter = painter, contentDescription = "Leader Image"
            )
        }
        Column(modifier = Modifier.padding(start = 20.dp)) {
            Text(text = name)
            Row {
                Text(text = status)
                Text(text = " | ")
                Text(text = hierarchy)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckImageLoader() {
    LeaderStatus(name = "Anna", status = "Leader", hierarchy = "AAD")
}

@Preview(showBackground = true)
@Composable
fun CheckMenu() {
    Menu()
}

@Preview(showBackground = true)
@Composable
fun TrainingScreenPreview() {
    val fakePost = generateFakeData().firstOrNull() ?: return
    TrainingScreen(post = fakePost)
}

@Preview(showBackground = true, showSystemUi = true, name = "Hardus")
@Composable
//CheckNewPasswordScreenPhone
fun CheckHomeScreenPhone() {
    HomeScreen(rememberNavController())
}

@Preview(showBackground = true, showSystemUi = true, name = "Hardus", device = Devices.TABLET)
@Composable
//CheckNewPasswordScreenPhone
fun CheckHomeScreenPhoneTablet() {
    HomeScreen(rememberNavController())
}

