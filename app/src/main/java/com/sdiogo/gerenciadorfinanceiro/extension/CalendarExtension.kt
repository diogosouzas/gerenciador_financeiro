package com.sdiogo.gerenciadorfinanceiro.extension

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.formataParaBrasileiro(): String {
    val formatoBrasileiro = "dd/MM/yyyy"
    val simpleDateFormat = SimpleDateFormat(formatoBrasileiro)
    return simpleDateFormat.format(this.time)
}

fun String.converteParaCalendar(): Calendar {
    val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyy")
    val dataConvertida: Date = formatoBrasileiro.parse(this)
    val data = Calendar.getInstance()
    data.time = dataConvertida
    return data
}
