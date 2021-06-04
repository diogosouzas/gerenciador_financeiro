package com.sdiogo.gerenciadorfinanceiro.delegate

import com.sdiogo.gerenciadorfinanceiro.model.Transacao

interface TransacaoDelegate {

    fun delegate(transacao: Transacao)
}