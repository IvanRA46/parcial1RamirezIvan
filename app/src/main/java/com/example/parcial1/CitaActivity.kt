package com.example.parcial1

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class CitaActivity : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var btnSelectTime: Button
    private lateinit var txtSelectedDateTime: TextView

    private val calendar: Calendar = Calendar.getInstance()
    private var selectedDate: String? = null
    private var selectedTime: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cita)

        // Inicializar los componentes
        calendarView = findViewById(R.id.calendarView)
        btnSelectTime = findViewById(R.id.btnSelectTime)
        txtSelectedDateTime = findViewById(R.id.txtSelectedDateTime)

        // Configurar el CalendarView
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            selectedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
            showDateTimeToast()
        }

        // Evento para seleccionar la hora
        btnSelectTime.setOnClickListener {
            val timePicker = TimePickerDialog(this, { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                selectedTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)
                showDateTimeToast()
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)

            timePicker.show()
        }
    }

    // Mostrar la fecha y la hora seleccionadas en un Toast
    private fun showDateTimeToast() {
        val dateTime = "$selectedDate $selectedTime"
        txtSelectedDateTime.text = "Fecha y Hora: $dateTime"
        Toast.makeText(this, "Fecha y Hora: $dateTime", Toast.LENGTH_LONG).show()
    }
}
