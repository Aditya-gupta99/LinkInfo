package com.sparklead.linkinfo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.sparklead.linkinfo.R
import com.sparklead.linkinfo.ui.home.DashboardScreen
import com.sparklead.linkinfo.ui.theme.LinkInfoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LinkInfoTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    LineGraphNew(
//                        xData = List(10) { it.toFloat() },
//                        yData = List(10) { (0..100).random().toFloat() },
//                        dataLabel = "Sample Data",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
                DashboardScreen()
            }
        }
    }
}

@Composable
fun LineGraphNew(
    xData: List<Float>,
    yData: List<Float>,
    dataLabel: String,
    modifier: Modifier
) {
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(White),
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
            lineDataset.color = context.getColor(R.color.black)
            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(lineDataset)

            chart.description.isEnabled = false
            chart.setTouchEnabled(true)
            chart.setDrawGridBackground(false)
            chart.setScaleEnabled(true)
            chart.isDragEnabled = true
            chart.axisRight.isEnabled = false
            chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            chart.setGridBackgroundColor(White.toArgb())
            chart.legend.isEnabled = false
            chart.data = LineData(dataSets)
            lineDataset.notifyDataSetChanged()
            chart.notifyDataSetChanged()
            chart.invalidate()
            chart
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LinkInfoTheme {
    }
}