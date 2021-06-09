package com.sdiogo.gerenciadorfinanceiro.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.sdiogo.gerenciadorfinanceiro.R
import com.sdiogo.gerenciadorfinanceiro.extension.formataParaBrasileiro
import com.sdiogo.gerenciadorfinanceiro.model.Tipo
import com.sdiogo.gerenciadorfinanceiro.model.Transacao

class AlteraTransacaoDialog(
    viewGroup: ViewGroup,
    private val context: Context,
) : FormularioTransacaoDialog(context, viewGroup) {

    override val tituloBotaoPositivo: String
        get() = "Alterar"

    fun chama(transacao: Transacao, delegate: (transacao: Transacao) -> Unit) {
        val tipo = transacao.tipo
        super.chama(tipo, delegate)
        inicializaCampos(transacao)
    }

    private fun inicializaCampos(transacao: Transacao) {
        val tipo = transacao.tipo
        inicializaCampoValor(transacao)
        inicializaCampoData(transacao)
        inicializaCampoCategoria(tipo, transacao)
    }

    private fun inicializaCampoCategoria(
        tipo: Tipo, transacao: Transacao
    ) {
        val categoriasRetornadas = context.resources.getStringArray(categoriasPor(tipo))
        val transacaoCategoria = categoriasRetornadas.indexOf(transacao.categoria)
        inicializaCampoCategoria(transacaoCategoria)
    }

    private fun inicializaCampoCategoria(transacaoCategoria: Int) {
        campoCategoria.setSelection(transacaoCategoria, true)
    }

    private fun inicializaCampoData(transacao: Transacao) {
        campoData.setText(transacao.data.formataParaBrasileiro())
    }

    private fun inicializaCampoValor(transacao: Transacao) {
        campoValor.setText(transacao.valor.toString())
    }

    override fun tituloPor(tipo: Tipo): Int {
        return if (tipo == Tipo.RECEITA) {
            R.string.altera_receita
        } else {
            R.string.altera_despesa
        }
    }
}

