package com.example.parcial1

class Solicitud(
    var curp: String,
    var nombre: String,
    var apellidos: String,
    var domicilio: String,
    var cantidadIngreso: Double,
    var tipoPrestamo: String
) {

    // Método para validar el tipo de préstamo y si es apto o no
    fun validarIngreso(): Boolean {
        return when (tipoPrestamo.lowercase()) {
            "personal" -> cantidadIngreso in 20000.0..40000.0
            "negocio" -> cantidadIngreso in 40001.0..60000.0
            "vivienda" -> cantidadIngreso in 15000.0..35000.0
            else -> false // En caso de que no coincida con ningún tipo de préstamo
        }
    }
}
