package com.example.simplestockapp.network.response

data class StockResponse(
    val chart: Chart,
)

data class Chart(
    val result: List<Result>?,
    val error: Error?,
)

data class Result(
    val meta: Meta,
    val timestamp: List<Long?>?,
    val indicators: Indicators,
)

data class Meta(
    val currency: String,
    val symbol: String,
    val exchangeName: String,
    val fullExchangeName: String,
    val instrumentType: String,
    val firstTradeDate: Long,
    val regularMarketTime: Long,
    val hasPrePostMarketData: Boolean,
    val gmtoffset: Long,
    val timezone: String,
    val exchangeTimezoneName: String,
    val regularMarketPrice: Double,
    val fiftyTwoWeekHigh: Long,
    val fiftyTwoWeekLow: Double,
    val regularMarketDayHigh: Long,
    val regularMarketDayLow: Double,
    val regularMarketVolume: Long,
    val longName: String,
    val shortName: String,
    val chartPreviousClose: Double,
    val previousClose: Double,
    val scale: Long,
    val priceHint: Long,
    val currentTradingPeriod: CurrentTradingPeriod,
    val tradingPeriods: List<List<TradingPeriod>>,
    val dataGranularity: String,
    val range: String,
    val validRanges: List<String>,
) {
    val firstTradeDateMillis: Long
        get() = firstTradeDate * 1000

    val regularMarketTimeMillis: Long
        get() = regularMarketTime * 1000
}

data class TradingPeriod(
    val timezone: String,
    val start: Long,
    val end: Long,
    val gmtoffset: Long,
)

data class CurrentTradingPeriod(
    val pre: Pre,
    val regular: Regular,
    val post: Post,
)

data class Pre(
    val timezone: String,
    val start: Long,
    val end: Long,
    val gmtoffset: Long,
)

data class Regular(
    val timezone: String,
    val start: Long,
    val end: Long,
    val gmtoffset: Long,
)

data class Post(
    val timezone: String,
    val start: Long,
    val end: Long,
    val gmtoffset: Long,
)

data class Indicators(
    val quote: List<Quote>?,
    val adjclose: List<Map<String, Any>>?,
)

data class Quote(
    val low: List<Double?>,
    val close: List<Double?>,
    val open: List<Double?>,
    val volume: List<Long?>,
    val high: List<Double?>,
)

data class Error(
    val code: String,
    val description: String,
)