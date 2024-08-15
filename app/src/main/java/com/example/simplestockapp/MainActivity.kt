package com.example.simplestockapp

import android.os.Bundle
import androidx.core.view.isVisible
import com.example.simplestockapp.base.BaseActivity
import com.example.simplestockapp.databinding.ActivityMainBinding
import com.example.simplestockapp.network.response.StockResponse
import com.example.simplestockapp.utility.Constant
import com.example.simplestockapp.utility.toDateString
import com.example.simplestockapp.utility.toMillis
import com.github.AAChartModel.AAChartCore.AAChartCreator.AAChartModel
import com.github.AAChartModel.AAChartCore.AAChartCreator.AASeriesElement
import com.github.AAChartModel.AAChartCore.AAChartEnum.AAChartType
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding>() {
    val viewModel : MainActivityVM by viewModel()

    override fun getUiBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onFirstLaunch(savedInstanceState: Bundle?) {
        viewModel.getLoadingEvent().observe(this) { showLoad ->
            when (showLoad) {
                true -> showProgress()
                else -> hideProgress()
            }
        }

        viewModel.getErrorEvent().observe(this) {
            showSearchNotFound()
            showToast(it)
        }

        viewModel.getStockEvent().observe(this) { response ->
            showSearchFound(response)
        }
    }

    override fun initUiListener() {
        viewBinding?.btnSearch?.setOnClickListener {
            viewModel.getStockData(this@MainActivity, viewBinding?.etSearch?.query.toString())
        }
    }

    private fun showSearchNotFound() {
        viewBinding?.layoutEmpty?.root?.isVisible = true
        viewBinding?.layoutFind?.root?.isVisible = false
    }

    private fun showSearchFound(response: StockResponse) {
        viewBinding?.layoutEmpty?.root?.isVisible = false
        viewBinding?.layoutFind?.root?.isVisible = true

        val meta = response.chart.result?.firstOrNull()?.meta
        val timeStamp = response.chart.result?.firstOrNull()?.timestamp
        val chartIndicators = response.chart.result?.firstOrNull()?.indicators

        viewBinding?.layoutFind?.tvTitleHeadline?.text = meta?.longName
        viewBinding?.layoutFind?.tvContentHeadline?.text = meta?.shortName
        viewBinding?.layoutFind?.tvTitle1?.text = getString(R.string.stock_symbol)
        viewBinding?.layoutFind?.tvContent1?.text = getString(R.string.common_string, meta?.symbol.toString(), "(${meta?.exchangeName.toString()})")
        viewBinding?.layoutFind?.tvTitle2?.text = getString(R.string.instrument_type)
        viewBinding?.layoutFind?.tvContent2?.text = meta?.instrumentType
        viewBinding?.layoutFind?.tvTitle3?.text = getString(R.string.regular_market_low)
        viewBinding?.layoutFind?.tvContent3?.text = getString(R.string.common_string, meta?.regularMarketDayLow.toString(), meta?.currency.toString())
        viewBinding?.layoutFind?.tvTitle4?.text = getString(R.string.regular_market_normal)
        viewBinding?.layoutFind?.tvContent4?.text = getString(R.string.common_string, meta?.regularMarketPrice.toString(), meta?.currency.toString())
        viewBinding?.layoutFind?.tvTitle5?.text = getString(R.string.regular_market_high)
        viewBinding?.layoutFind?.tvContent5?.text = getString(R.string.common_string, meta?.regularMarketDayHigh.toString(), meta?.currency.toString())
        viewBinding?.layoutFind?.tvTitle6?.text = getString(R.string.date_first_trade)
        viewBinding?.layoutFind?.tvContent6?.text = getString(R.string.common_string, meta?.firstTradeDateMillis?.toDateString(Constant.DATE_FORMAT), meta?.timezone)
        viewBinding?.layoutFind?.tvTitle7?.text = getString(R.string.date_regular_market)
        viewBinding?.layoutFind?.tvContent7?.text = getString(R.string.common_string, meta?.regularMarketTimeMillis?.toDateString(Constant.DATE_FORMAT), meta?.timezone)
        viewBinding?.layoutFind?.tvFooterRight?.text = getString(R.string.datetime_vary, timeStamp?.firstOrNull()?.toMillis().toDateString(Constant.DATE_FORMAT_2), timeStamp?.lastOrNull()?.toMillis().toDateString(Constant.DATE_FORMAT_2))

        val aaChartModel = AAChartModel()
            .chartType(AAChartType.Line)
            .title(getString(R.string.chart_of, "${meta?.longName} (${meta?.shortName})"))
            .subtitle(getString(R.string.price_on_market, "${meta?.regularMarketPrice} ${meta?.exchangeName.toString()}"))
            .backgroundColor(getColor(R.color.gray_8c8c8c))
            .gradientColorEnable(true)
            .categories(arrayOf("Open", "Close", "High", "Low"))
            .dataLabelsEnabled(true)
            .yAxisGridLineWidth(1f)
            .xAxisGridLineWidth(1f)
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("Open")
                        .data(
                            chartIndicators?.quote?.firstOrNull()?.open?.toTypedArray()
                        ),
                    AASeriesElement()
                        .name("Close")
                        .data(
                            chartIndicators?.quote?.firstOrNull()?.close?.toTypedArray()
                        ),
                    AASeriesElement()
                        .name("High")
                        .data(
                            chartIndicators?.quote?.firstOrNull()?.high?.toTypedArray()
                        ),
                    AASeriesElement()
                        .name("Low")
                        .data(
                            chartIndicators?.quote?.firstOrNull()?.low?.toTypedArray()
                        )
                )
            )

        viewBinding?.layoutFind?.stockGraph?.aa_drawChartWithChartModel(aaChartModel)
    }
}