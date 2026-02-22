package com.example.rincon_verde2.domain.model

data class SearchHistory(
    val id: String,
    val query: String,
    val category: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val resultCount: Int = 0
) {
    fun isRecent(): Boolean {
        val oneDayMs = 24 * 60 * 60 * 1000
        return System.currentTimeMillis() - timestamp < oneDayMs
    }
}
