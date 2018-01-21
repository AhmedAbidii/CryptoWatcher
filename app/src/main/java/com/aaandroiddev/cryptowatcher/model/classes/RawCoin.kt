package com.aaandroiddev.cryptowatcher.model.classes

data class RawCoin(
        val TYPE: String,
        val MARKET: String,
        val FROMSYMBOL: String,
        val TOSYMBOL: String,
        val FLAGS: String,
        val PRICE: Float,
        val LASTUPDATE: Float,
        val LASTVOLUME: Float,
        val LASTVOLUMETO: Float,
        val LASTTRADEID: Float,
        val VOLUME24HOUR: Float,
        val VOLUME24HOURTO: Float,
        val OPEN24HOUR: Float,
        val HIGH24HOUR: Float,
        val LOW24HOUR: Float,
        val LASTMARKET: String,
        val CHANGE24HOUR: Float,
        val CHANGEPCT24HOUR: Float,
        val SUPPLY: Float,
        val MKTCAP: Float)