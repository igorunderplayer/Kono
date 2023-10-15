package me.igorunderplayer.kono.utils

fun formatNumber(number: Int): String {
    val billion = 1_000_000_000L
    val million = 1_000_000L
    val thousand = 1_000L

    return when {
        number >= billion -> {
            val value = number / billion.toDouble()
            String.format("%.1fB", value)
        }
        number >= million -> {
            val value = number / million.toDouble()
            String.format("%.1fM", value)
        }
        number >= thousand -> {
            val value = number / thousand.toDouble()
            String.format("%.1fK", value)
        }
        else -> number.toString()
    }
}