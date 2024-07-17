@file:OptIn(ExperimentalMaterial3Api::class)

package com.sparklead.linkinfo.ui.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sparklead.linkinfo.R
import com.sparklead.linkinfo.common.utils.DateUtils
import com.sparklead.linkinfo.data.dto.AnalyticsData
import com.sparklead.linkinfo.data.dto.AnalyticsGraphData
import com.sparklead.linkinfo.data.dto.DashboardDto
import com.sparklead.linkinfo.data.dto.Link
import com.sparklead.linkinfo.ui.theme.BackgroundLight
import com.sparklead.linkinfo.ui.theme.BlueLight
import com.sparklead.linkinfo.ui.theme.CircularProgress
import com.sparklead.linkinfo.ui.theme.Green
import com.sparklead.linkinfo.ui.theme.GreenLight
import com.sparklead.linkinfo.ui.theme.LineGraphNew
import com.sparklead.linkinfo.ui.theme.LinkCard
import com.sparklead.linkinfo.ui.theme.PrimaryBlue
import com.sparklead.linkinfo.ui.theme.SweetError

@Composable
fun DashboardScreen(
    padding: PaddingValues,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state by viewModel.dashboardUiState.collectAsStateWithLifecycle()
    val analyticsDataList by viewModel.analyticsDataList.collectAsStateWithLifecycle()
    val analyticsGraphData by viewModel.analyticsGraphData.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.saveToken()
        viewModel.getDashboardDetails()
    }

    DashboardScreen(
        state = state,
        analyticsGraphData = analyticsGraphData,
        padding = padding,
        analyticsDataList = analyticsDataList,
        onRetry = {
            viewModel.getDashboardDetails()
        }
    )
}

@Composable
fun DashboardScreen(
    state: DashboardUiState,
    analyticsGraphData: AnalyticsGraphData,
    padding: PaddingValues,
    analyticsDataList: List<AnalyticsData>,
    onRetry: () -> Unit
) {

    Scaffold(
        modifier = Modifier.padding(padding),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = PrimaryBlue),
                title = {
                    Text(
                        text = "Dashboard",
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontStyle = FontStyle.Normal,
                            fontFamily = FontFamily(Font(R.font.outfit_medium))
                        ),
                        color = White,
                        textAlign = TextAlign.Start
                    )
                },
                actions = {
                    Card(
                        modifier = Modifier.padding(8.dp),
                        colors = CardDefaults.cardColors(Color(0xFF2b81ff))
                    ) {
                        IconButton(
                            onClick = {},
                            colors = IconButtonDefaults.iconButtonColors(Color(0xFF2b81ff))
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_settings),
                                contentDescription = null,
                                tint = White
                            )
                        }
                    }
                }
            )
        },
        containerColor = PrimaryBlue
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                color = BackgroundLight
            ) {
                Column {
                    Text(
                        text = DateUtils.getGreetingMessage(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 16.dp, bottom = 4.dp),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal,
                            fontFamily = FontFamily(Font(R.font.outfit_medium))
                        ),
                        color = Color.Gray,
                        textAlign = TextAlign.Start
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Aditya Gupta",
                            modifier = Modifier,
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontStyle = FontStyle.Normal,
                                fontFamily = FontFamily(Font(R.font.outfit_medium))
                            ),
                            color = Color.Black,
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            modifier = Modifier.height(24.dp),
                            painter = painterResource(id = R.drawable.ic_greetings),
                            contentDescription = null
                        )
                    }

                    when (state) {
                        is DashboardUiState.DashboardDetails -> {
                            DashboardContent(
                                details = state.details,
                                analyticsDataList = analyticsDataList,
                                analyticsGraphData = analyticsGraphData
                            )
                        }

                        is DashboardUiState.Error -> SweetError(message = stringResource(id = state.message)) {
                            onRetry()
                        }

                        is DashboardUiState.Loading -> CircularProgress()
                    }
                }
            }
        }
    }
}


@Composable
fun DashboardContent(
    details: DashboardDto,
    analyticsDataList: List<AnalyticsData>,
    analyticsGraphData: AnalyticsGraphData
) {
    val context = LocalContext.current
    var topLink by rememberSaveable { mutableStateOf(true) }

    var links by rememberSaveable {
        mutableStateOf(
            details.data.top_links.map {
                Link(
                    title = it.title,
                    totalClicks = it.total_clicks,
                    webLinks = it.web_link,
                    createdDate = it.created_at,
                    originalImage = it.original_image
                )
            }
        )
    }

    LaunchedEffect(topLink) {
        links =
            if (topLink) {
                details.data.top_links.map {
                    Link(
                        title = it.title,
                        totalClicks = it.total_clicks,
                        webLinks = it.web_link,
                        createdDate = it.created_at,
                        originalImage = it.original_image
                    )
                }
            } else {
                details.data.recent_links.map {
                    Link(
                        title = it.title,
                        totalClicks = it.total_clicks,
                        webLinks = it.web_link,
                        createdDate = it.created_at,
                        originalImage = it.original_image
                    )
                }
            }
    }

    Spacer(modifier = Modifier.height(12.dp))
    Card(
        modifier = Modifier.padding(16.dp),
        colors = CardDefaults.cardColors(White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Overview",
                modifier = Modifier,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily(Font(R.font.outfit_regular))
                ),
                color = Color.Gray,
                textAlign = TextAlign.Start
            )
            OutlinedCard(
                onClick = { },
                colors = CardDefaults.cardColors(White),
                shape = RoundedCornerShape(4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = DateUtils.overviewSpan(
                            analyticsGraphData.span.first,
                            analyticsGraphData.span.second
                        ),
                        modifier = Modifier,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal,
                            fontFamily = FontFamily(Font(R.font.outfit_regular))
                        ),
                        color = Color.Black,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        modifier = Modifier.height(16.dp),
                        imageVector = Icons.Rounded.AccessTime,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            }
        }

        LineGraphNew(
            xData = analyticsGraphData.xData,
            xValue = analyticsGraphData.xValue,
            yData = analyticsGraphData.yData,
            modifier = Modifier.padding(8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
    }

    LazyRow(modifier = Modifier.padding(start = 8.dp)) {
        items(analyticsDataList) { analyticsData ->
            AnalyticsCardItem(
                analyticsData = analyticsData
            )
        }
    }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
        onClick = { },
        colors = CardDefaults.cardColors(BackgroundLight),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.height(20.dp),
                painter = painterResource(id = R.drawable.ic_price_boost),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "View Analytics",
                modifier = Modifier,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily(Font(R.font.outfit_medium))
                ),
                color = Color.Black,
                textAlign = TextAlign.Start
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            TextButton(
                onClick = {
                    topLink = true
                }, colors = ButtonDefaults.textButtonColors(
                    if (topLink) PrimaryBlue else BackgroundLight
                )
            ) {
                Text(
                    text = "Top Links",
                    modifier = Modifier,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily(Font(R.font.outfit_medium))
                    ),
                    color = if (topLink) White else Color.Gray,
                    textAlign = TextAlign.Start
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            TextButton(
                onClick = {
                    topLink = false
                }, colors = ButtonDefaults.textButtonColors(
                    if (topLink) BackgroundLight else PrimaryBlue
                )
            ) {
                Text(
                    text = "Recent Links",
                    modifier = Modifier,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily(Font(R.font.outfit_medium))
                    ),
                    color = if (topLink) Color.Gray else White,
                    textAlign = TextAlign.Start
                )
            }
        }
        OutlinedCard(
            modifier = Modifier,
            onClick = { },
            colors = CardDefaults.cardColors(BackgroundLight),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(
                modifier = Modifier
                    .height(36.dp)
                    .padding(10.dp),
                painter = painterResource(id = R.drawable.ic_stroke),
                contentDescription = null,
                tint = Color.LightGray
            )
        }
    }

    LazyColumn(modifier = Modifier.height(links.size * 148.dp)) {
        items(links) { link ->
            LinkCard(link, onCopy = {
                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
            })
        }
    }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
        onClick = { },
        colors = CardDefaults.cardColors(BackgroundLight),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.height(20.dp),
                painter = painterResource(id = R.drawable.ic_link),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "View All Links",
                modifier = Modifier,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily(Font(R.font.outfit_medium))
                ),
                color = Color.Black,
                textAlign = TextAlign.Start
            )
        }
    }

    Spacer(modifier = Modifier.width(16.dp))

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
        onClick = { },
        colors = CardDefaults.cardColors(GreenLight),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.height(20.dp),
                painter = painterResource(id = R.drawable.ic_whatsapp),
                contentDescription = null,
                tint = Green
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Talk with us",
                modifier = Modifier,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily(Font(R.font.outfit_medium))
                ),
                color = Color.Black,
                textAlign = TextAlign.Start
            )
        }
    }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
        onClick = { },
        colors = CardDefaults.cardColors(BlueLight),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.height(20.dp),
                painter = painterResource(id = R.drawable.ic_faq),
                contentDescription = null,
                tint = PrimaryBlue
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Frequently Asked Questions",
                modifier = Modifier,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily(Font(R.font.outfit_medium))
                ),
                color = Color.Black,
                textAlign = TextAlign.Start
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun AnalyticsCardItem(
    analyticsData: AnalyticsData
) {
    Card(
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(White)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(42.dp)
                    .background(analyticsData.color, CircleShape),
            ) {
                Image(
                    painter = painterResource(id = analyticsData.icon),
                    contentDescription = null
                )
            }
            Text(
                text = analyticsData.value,
                modifier = Modifier.padding(start = 4.dp, top = 8.dp, end = 8.dp, bottom = 4.dp),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily(Font(R.font.outfit_medium))
                ),
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
            Text(
                text = analyticsData.title,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily(Font(R.font.outfit_light))
                ),
                color = Color.Gray,
                textAlign = TextAlign.Start
            )
        }
    }
}


@Preview
@Composable
private fun DashboardScreenPreview() {

}