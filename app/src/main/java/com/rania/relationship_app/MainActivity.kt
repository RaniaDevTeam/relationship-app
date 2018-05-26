package com.rania.relationship_app

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.codetroopers.betterpickers.datepicker.DatePickerBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.text.SimpleDateFormat
import java.util.*
import android.content.DialogInterface
import android.content.Intent
import java.text.DateFormat


class MainActivity : AppCompatActivity() {
    private lateinit var df: DateFormat

    private var querentDOB: DateEntry? = null
    private var partnerDOB: DateEntry? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        df = SimpleDateFormat(resources.getString(R.string.date_format), Locale.getDefault())

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        input_first_partner.setOnClickListener { view ->
            runDatePicker { year, month, day ->
                val dateEntry = DateEntry(year, month, day)
                querentDOB = dateEntry
                input_first_partner.setText(
                        formatDate(dateEntry),
                        TextView.BufferType.EDITABLE);
            }
        }

        input_second_partner.setOnClickListener { view ->
            runDatePicker { year, month, day ->
                val dateEntry = DateEntry(year, month, day)
                partnerDOB = dateEntry
                input_second_partner.setText(
                        formatDate(dateEntry),
                        TextView.BufferType.EDITABLE);
            }
        }

        btn_calculate.setOnClickListener { view ->
            if (querentDOB == null || partnerDOB == null) {
                alertDatesNotSet()
            } else {
                showCardChapter()
            }
        }
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun formatDate(dateEntry: DateEntry): String {
        val date = GregorianCalendar(
                dateEntry.year,
                dateEntry.zeroBasedMonth,
                dateEntry.dayOfMonth).time
        return df.format(date)
    }

    private fun about() { // TODO: construct prettier dialog
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Copyright (c) Rania Enterprise Ltd.")
                .setCancelable(false)
                .setPositiveButton("OK") { _, _ ->
                    // just close it
                }
        val alert = builder.create()
        alert.show()
    }

    private fun alertDatesNotSet() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(resources.getString(R.string.error_enter_both_dates))
                .setCancelable(false)
                .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                    // just close it
                })
        val alert = builder.create()
        alert.show()
    }

    private fun showCardChapter() {
        val intent = Intent(this, ChapterActivity::class.java).apply {
            querentDOB?.let { q ->
                partnerDOB?.let { p ->
                    putExtra(Keys.CARD_NUM, CardCalculator.calculate(q, p))
                }
            }
        }
        startActivity(intent)
    }

    private fun runDatePicker(callback: (Int, Int, Int) -> Unit) {
        var dpb = DatePickerBuilder().setFragmentManager(supportFragmentManager)
                .setStyleResId(R.style.BetterPickersDialogFragment_Light)
                .addDatePickerDialogHandler { _, year, month, day ->
                    callback(year, month, day)
                }
        dpb.show()

    }
}
