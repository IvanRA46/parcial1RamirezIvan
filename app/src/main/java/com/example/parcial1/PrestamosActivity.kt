package com.example.parcial1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PrestamosActivity : AppCompatActivity() {

    private lateinit var textViewPersonal: TextView
    private lateinit var textViewNegocio: TextView
    private lateinit var textViewVivienda: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prestamos)

        // Inicializar los componentes
        textViewPersonal = findViewById(R.id.textViewPersonal)
        textViewNegocio = findViewById(R.id.textViewNegocio)
        textViewVivienda = findViewById(R.id.textViewVivienda)

        // Configurar los textos de los préstamos
        textViewPersonal.text = "Préstamo Personal: Ingreso de 20,000 a 40,000"
        textViewNegocio.text = "Préstamo de Negocio: Ingreso de 40,000 a 60,000"
        textViewVivienda.text = "Préstamo de Vivienda: Ingreso de 15,000 a 35,000"
    }
}
