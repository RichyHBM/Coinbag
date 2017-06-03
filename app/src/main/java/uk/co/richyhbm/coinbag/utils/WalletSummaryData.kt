package uk.co.richyhbm.coinbag.utils

import android.graphics.Color
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import uk.co.richyhbm.coinbag.enums.Cryptocoins
import uk.co.richyhbm.coinbag.view_model.WalletSummaryViewModel
import java.util.concurrent.atomic.AtomicLong
import java.lang.Double.*

class WalletSummaryData {
    val summaryVM = WalletSummaryViewModel()
    val totalValue = AtomicLong(0)
    val typeValues = MutableList(0, { Pair(Cryptocoins.Other, 0.0) })

    val lock = Any()

    fun reset() {
        summaryVM.totalValue.set("Fetching")
        totalValue.set(0)
        synchronized(lock, {
            typeValues.clear()
        })
    }

    fun addToTotalValue(value:Double) {
        totalValue.set(
                doubleToLongBits(
                        longBitsToDouble(totalValue.get()) + value
                )
        )
    }

    fun addNewTypeValuePair(pair: Pair<Cryptocoins, Double>) {
        synchronized(lock, {
            typeValues.add(Pair(pair.first, pair.second))
        })
    }

    fun getTotalValue(): Double {
        return longBitsToDouble(totalValue.get())
    }

    fun buildChart(pieChart: PieChart):PieChart {
        synchronized(lock, {
            val entriesValue = typeValues.map { p ->
                p.first to typeValues.filter { q -> q.first == p.first }.sumByDouble { it.second }
            }.distinctBy { it.first }.map { PieEntry(it.second.toFloat(), it.first.symbol) }

            val dataSet = PieDataSet(entriesValue.toMutableList(), "")
            dataSet.sliceSpace = 3f
            dataSet.selectionShift = 5f
            dataSet.colors = ColorTemplate.MATERIAL_COLORS.toMutableList()
            dataSet.valueFormatter = PercentFormatter()
            dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
            dataSet.valueTextColor = Color.BLACK

            val data = PieData(dataSet)
            pieChart.data = data
            //pieChart.legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            //pieChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            //pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
            pieChart.legend.setDrawInside(false)
            pieChart.legend.isEnabled = true
            pieChart.description.isEnabled = false
            pieChart.setUsePercentValues(true)
            pieChart.isRotationEnabled = false
            pieChart.animateY(400, Easing.EasingOption.Linear)
            pieChart.setDrawEntryLabels(false)
            pieChart.setEntryLabelColor(Color.BLACK)
            return pieChart
        })
    }

}