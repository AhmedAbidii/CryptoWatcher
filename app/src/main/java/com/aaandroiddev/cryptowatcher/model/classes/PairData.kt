package com.aaandroiddev.cryptowatcher.model.classes

data class PairData (
        val exchange: String,
        val fromSymbol: String,
        val toSymbol: String,
        val volume24h: Float,
        val volume24hTo: Float)