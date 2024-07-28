package com.example.spacex.AllScreens

import android.content.Intent
import android.net.Uri
import android.text.style.ClickableSpan
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spacex.Data.MissionDetailsItem
import com.example.spacex.MissionViewModel

@Composable
fun DetailScreen(itemJson: Int?) {
    val missionViewModel: MissionViewModel = viewModel()
    val viewState by missionViewModel.missionState
    Box(modifier = Modifier.fillMaxSize()) {
        if (itemJson != null) {
            val missionDetails = viewState.list.find { it.flight_number == itemJson }
            missionDetails?.let { details ->
                DetailView(missions = details)
            }
        } else {
            Text(text = "Invalid flight number")
        }
    }
}

@Composable
fun DetailView(missions: MissionDetailsItem) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                SimpleTextView(
                    text = missions.mission_name,
                    fontWeight = FontWeight.Bold,
                    size = 20
                )
                SimpleTextView(
                    text = "Launched in Year : ${missions.launch_year}",
                    fontWeight = FontWeight.Bold,
                    size = 14
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                SimpleTextView(text = "Rocket Details :: ", fontWeight = FontWeight.Bold, size = 17)
                TextView(question = "Rocket Id : ", answer = "${missions.rocket.rocket_id}")
                TextView(question = "Rocket Name : ", answer = "${missions.rocket.rocket_name}")
                TextView(question = "Rocket Type : ", answer = "${missions.rocket.rocket_type}")

                Spacer(modifier = Modifier.height(16.dp))

                SimpleTextView(
                    text = "Launch Site Details :: ",
                    fontWeight = FontWeight.Bold,
                    size = 17
                )
                TextView(question = "Launch Site Id : ", answer = "${missions.launch_site.site_id}")
                TextView(
                    question = "Launch Site Name : ",
                    answer = "${missions.launch_site.site_name_long}"
                )

                Spacer(modifier = Modifier.height(16.dp))

                SimpleTextView(text = "Other Details :: ", fontWeight = FontWeight.Bold, size = 17)
                TextView(question = "Details : ", answer = "${missions.details}")

                Spacer(modifier = Modifier.height(16.dp))

                SimpleTextView(
                    text = "Links to Media and Article :: ",
                    fontWeight = FontWeight.Bold,
                    size = 17
                )
                TextView(question = "Video Link : ", answer = "${missions.links.video_link}")
                TextView(question = "Article Link : ", answer = "${missions.links.article_link}")
                TextView(question = "Wikipedia Link : ", answer = "${missions.links.wikipedia}")
                TextView(question = "Reddit Link : ", answer = "${missions.links.reddit_media}")

            }
        }
    }
}

@Composable
fun TextView(question: String, answer: String) {
    Row(
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = question,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = Color.Black
        )
        SelectionContainer {
            Text(
                text = answer,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 15.sp,
                color = Color.Black
            )
        }

    }
}

@Composable
fun SimpleTextView(text: String, fontWeight: FontWeight, size: Int) {
    Text(
        text = text,
        fontWeight = fontWeight,
        fontSize = size.sp,
        color = Color.Black,
        modifier = Modifier.padding(4.dp)
    )
}
