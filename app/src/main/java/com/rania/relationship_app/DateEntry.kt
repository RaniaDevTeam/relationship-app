package com.rania.relationship_app

data class DateEntry(val year: Int, val zeroBasedMonth: Int, val dayOfMonth: Int) {
    val oneBasedMonth = zeroBasedMonth + 1
}