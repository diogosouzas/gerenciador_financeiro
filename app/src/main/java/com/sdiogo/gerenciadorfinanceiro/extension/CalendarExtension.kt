package com.sdiogo.gerenciadorfinanceiro.extension

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.formataParaBrasileiro(): String {
    val formatoBrasileiro = "dd/MM/yyyy"
    val simpleDateFormat = SimpleDateFormat(formatoBrasileiro)
    return simpleDateFormat.format(this.time)
}
