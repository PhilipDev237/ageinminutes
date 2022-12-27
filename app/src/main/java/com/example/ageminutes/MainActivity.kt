package com.example.ageminutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.fms.ageminutes.R
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    // the selected date textview
    private var tvSelectedDate : TextView? = null
    private var tvAgeInMins : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // display the datepicker object
        val datePickerBtn : Button = findViewById<Button>(R.id.datePickerBtn)
        datePickerBtn.setOnClickListener {
            clickDatePicker()
        }

        // init the selectedate textview
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMins = findViewById(R.id.tvTimeInMins) // init age in mins textview
    }

    private fun clickDatePicker(){
        val calendar  = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dialogPickerVal = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{
            view, selectedYear, selectedMonth, selectedDayOfMonth ->

            // display the selected date value
            val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
            tvSelectedDate?.text = selectedDate

            // calculate how much time has passed since
            // the selected date
            var sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val theDate = sdf.parse(selectedDate)
            theDate.let {
                val dateInMinutes = theDate.time / 60000 // time from 1970 to selected date
                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis())) // time from 1970 to current date
                currentDate.let {
                    val currentDateTimeInMins = currentDate.time / 60000
                    val timeDiffInMins = currentDateTimeInMins - dateInMinutes
                    tvAgeInMins?.text = timeDiffInMins.toString()
                }
            }
        }, year, month, day)
        dialogPickerVal.datePicker.maxDate = System.currentTimeMillis() - 864000000
        dialogPickerVal.show()
    }
}