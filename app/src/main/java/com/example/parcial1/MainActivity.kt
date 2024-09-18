package com.example.parcial1

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    // Declarar los componentes de la interfaz
    private lateinit var txtCurp: EditText
    private lateinit var txtNombre: EditText
    private lateinit var txtApellidos: EditText
    private lateinit var txtDomicilio: EditText
    private lateinit var txtIngreso: EditText
    private lateinit var spinnerTipoPrestamo: Spinner
    private lateinit var btnSubmit: Button
    private lateinit var btnLimpiar: Button

    private val CHANNEL_ID = "SolicitudChannel"
    private val NOTIFICATION_ID = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar los componentes
        txtCurp = findViewById(R.id.txtCurp)
        txtNombre = findViewById(R.id.txtNombre)
        txtApellidos = findViewById(R.id.txtApellidos)
        txtDomicilio = findViewById(R.id.txtDomicilio)
        txtIngreso = findViewById(R.id.txtIngreso)
        spinnerTipoPrestamo = findViewById(R.id.spinnerTipoPrestamo)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnLimpiar = findViewById(R.id.btnLimpiar)

        // Configurar las opciones del Spinner
        val tiposPrestamo = arrayOf("personal", "negocio", "vivienda")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tiposPrestamo)
        spinnerTipoPrestamo.adapter = adapter

        // Crear el canal de notificación si es necesario
        createNotificationChannel()

        // Evento para el botón Submit
        btnSubmit.setOnClickListener {
            // Capturar los datos de la interfaz
            val curp = txtCurp.text.toString()
            val nombre = txtNombre.text.toString()
            val apellidos = txtApellidos.text.toString()
            val domicilio = txtDomicilio.text.toString()
            val cantidadIngreso = txtIngreso.text.toString().toDoubleOrNull() ?: 0.0
            val tipoPrestamo = spinnerTipoPrestamo.selectedItem.toString()

            // Crear el objeto Solicitud con los datos capturados
            val solicitud = Solicitud(curp, nombre, apellidos, domicilio, cantidadIngreso, tipoPrestamo)

            // Validar la solicitud y mostrar la notificación
            if (solicitud.validarIngreso()) {
                showValidSolicitudNotification()
            } else {
                showInvalidSolicitudNotification()
            }
        }

        // Evento para el botón Limpiar
        btnLimpiar.setOnClickListener {
            // Limpiar los campos de entrada
            txtCurp.text.clear()
            txtNombre.text.clear()
            txtApellidos.text.clear()
            txtDomicilio.text.clear()
            txtIngreso.text.clear()
            spinnerTipoPrestamo.setSelection(0)
        }
    }

    // Método para crear el canal de notificación
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Solicitud Notification"
            val descriptionText = "Channel for Solicitud validation"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    // Método para mostrar una notificación de solicitud válida
    private fun showValidSolicitudNotification() {
        // Intent para 'Cita' action
        val citaIntent = Intent(this, CitaActivity::class.java)
        val citaPendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, citaIntent, PendingIntent.FLAG_IMMUTABLE)

        // Intent para 'Préstamos' action
        val prestamosIntent = Intent(this, PrestamosActivity::class.java)
        val prestamosPendingIntent: PendingIntent = PendingIntent.getActivity(this, 1, prestamosIntent, PendingIntent.FLAG_IMMUTABLE)

        // Notificación con acciones
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Asegúrate de tener este ícono en tu carpeta drawable
            .setContentTitle("Solicitud Aceptada")
            .setContentText("La solicitud es válida para el préstamo")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(R.drawable.ic_launcher_foreground, "Cita", citaPendingIntent)
            .addAction(R.drawable.ic_launcher_foreground, "Préstamos", prestamosPendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // No se puede omitir este permiso para Android 13 y superior
                return
            }
            notify(NOTIFICATION_ID, builder.build())
        }

    }

    // Método para mostrar una notificación de solicitud inválida
    private fun showInvalidSolicitudNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Asegúrate de tener este ícono en tu carpeta drawable
            .setContentTitle("Solicitud Rechazada")
            .setContentText("La solicitud no es válida para el préstamo")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // No se puede omitir este permiso para Android 13 y superior
                return
            }
            notify(NOTIFICATION_ID, builder.build())
        }
    }
}

