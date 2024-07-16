@file:OptIn(ExperimentalMaterial3Api::class)

package com.sparklead.linkinfo.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.sparklead.linkinfo.R
import com.sparklead.linkinfo.data.dto.DashboardDto
import com.sparklead.linkinfo.ui.theme.BackgroundLight
import com.sparklead.linkinfo.ui.theme.BlueLight
import com.sparklead.linkinfo.ui.theme.CircularProgress
import com.sparklead.linkinfo.ui.theme.Green
import com.sparklead.linkinfo.ui.theme.GreenLight
import com.sparklead.linkinfo.ui.theme.PrimaryBlue
import com.sparklead.linkinfo.ui.theme.SweetError

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state by viewModel.dashboardUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getDashboardDetails()
    }

    DashboardScreen(
        state = state,
        onRetry = {
            viewModel.getDashboardDetails()
        }
    )

}

@Composable
fun DashboardScreen(
    state: DashboardUiState,
    onRetry: () -> Unit
) {

    Scaffold(
        modifier = Modifier.padding(),
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
                        color = Color.White,
                        textAlign = TextAlign.Start
                    )
                },
                actions = {
                    IconButton(onClick = {

                    }, colors = IconButtonDefaults.iconButtonColors(Color(0xFF2b81ff))) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_settings),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            )
        },
        containerColor = PrimaryBlue
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                color = BackgroundLight
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = "Good Morning",
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal,
                            fontFamily = FontFamily(Font(R.font.outfit_medium))
                        ),
                        color = Color.Gray,
                        textAlign = TextAlign.Start
                    )
                    Row {
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
                        Icon(
                            modifier = Modifier.height(24.dp),
                            painter = painterResource(id = R.drawable.ic_greetings),
                            contentDescription = null
                        )
                    }

                    when (state) {
                        is DashboardUiState.DashboardDetails -> {
                            DashboardContent(details = state.details)
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
    details: DashboardDto
) {
    Spacer(modifier = Modifier.height(16.dp))

    Card(
        colors = CardDefaults.cardColors(Color.White)
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
                colors = CardDefaults.cardColors(Color.White),
                shape = RoundedCornerShape(4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "22 Aug - 23 Sept",
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
            xData = List(10) { it.toFloat() },
            yData = List(10) { (0..100).random().toFloat() },
            modifier = Modifier.padding(8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
    }

    LazyRow {
//        items() {
//
//        }
    }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp),
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

    Spacer(modifier = Modifier.width(8.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            TextButton(onClick = { }, colors = ButtonDefaults.textButtonColors(PrimaryBlue)) {
                Text(
                    text = "Top Links",
                    modifier = Modifier,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily(Font(R.font.outfit_medium))
                    ),
                    color = Color.White,
                    textAlign = TextAlign.Start
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            TextButton(onClick = { }, colors = ButtonDefaults.textButtonColors(BackgroundLight)) {
                Text(
                    text = "Recent Links",
                    modifier = Modifier,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily(Font(R.font.outfit_medium))
                    ),
                    color = Color.Gray,
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

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp),
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
            .padding(top = 8.dp, bottom = 8.dp),
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
            .padding(top = 8.dp, bottom = 8.dp),
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
}

@Composable
fun RowCardItem() {

    Card(
        colors = CardDefaults.cardColors(Color.White)
    ) {
    }
}


@Composable
fun LineGraphNew(
    xData: List<Float>,
    yData: List<Float>,
    modifier: Modifier
) {
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
            .background(Color.White),
        factory = { context ->
            val chart = LineChart(context)
            val chartData: List<Entry> = xData.zip(yData) { x, y -> Entry(x, y) }
            val lineDataset = LineDataSet(chartData, "Overview")
            lineDataset.apply {
                setDrawIcons(false)
                setDrawCircles(false)
                setDrawCircleHole(false)
                setDrawValues(false)
                lineWidth = 3f
                setDrawFilled(true)
                fillDrawable = context.getDrawable(R.drawable.backgroud_blue)
            }
            lineDataset.color = PrimaryBlue.toArgb()
            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(lineDataset)

            chart.description.isEnabled = false
            chart.setTouchEnabled(true)
            chart.setDrawGridBackground(false)
            chart.setScaleEnabled(true)
            chart.isDragEnabled = true
            chart.axisRight.isEnabled = false
            chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            chart.setGridBackgroundColor(Color.White.toArgb())
            chart.legend.isEnabled = false
            chart.data = LineData(dataSets)
            lineDataset.notifyDataSetChanged()
            chart.notifyDataSetChanged()
            chart.invalidate()
            chart
        }
    )
}


@Preview
@Composable
private fun DashboardScreenPreview() {

}