package com.sdiogo.gerenciadorfinanceiro.extension

fun String.limitaEmAte(caracteres: Int): String {
    if (this.length > caracteres) {
        val primeiroCaracter = 0
        return "${substring(primeiroCaracter, caracteres)}..."
    }
    return this
}