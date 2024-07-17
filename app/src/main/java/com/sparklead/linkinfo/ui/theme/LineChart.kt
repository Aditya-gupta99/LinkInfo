package com.sparklead.linkinfo.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.sparklead.linkinfo.R

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