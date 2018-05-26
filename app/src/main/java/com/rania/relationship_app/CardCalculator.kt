package com.rania.relationship_app

object CardCalculator {
    fun calculate(querentDOB: DateEntry, partnerDOB: DateEntry): Int {
        var sum = sumDateParts(querentDOB) + sumDateParts(partnerDOB)
        return theoReduce(sum)
    }

    private fun sumDateParts(entryModel: DateEntry) =
            entryModel.dayOfMonth + entryModel.oneBasedMonth + entryModel.year

    private fun theoReduce(num: Int): Int {

        var numToReduce = num
        do {
            var tot = 0
            while (numToReduce > 0) {
                tot += numToReduce % 10
                numToReduce /= 10
            }
            numToReduce = tot
        } while (numToReduce > 22)

        return numToReduce
    }
}