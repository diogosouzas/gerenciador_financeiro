package com.sdiogo.gerenciadorfinanceiro.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.sdiogo.gerenciadorfinanceiro.R
import com.sdiogo.gerenciadorfinanceiro.delegate.TransacaoDelegate
import com.sdiogo.gerenciadorfinanceiro.extension.converteParaCalendar
import com.sdiogo.gerenciadorfinanceiro.extension.formataParaBrasileiro
import com.sdiogo.gerenciadorfinanceiro.model.Tipo
import com.sdiogo.gerenciadorfinanceiro.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

class AlteraTransacaoDialog(
    private val viewGroup: ViewGroup,
    private val context: Context
) {

    private val viewCriada = criaLayout()
    private val campoValor = viewCriada.form_transacao_valor
    private val campoCategoria: Spinner = viewCriada.form_transacao_categoria
    private val campoData = viewCriada.form_transacao_data

    fun chama(transacao: Transacao, transacaoDelegate: TransacaoDelegate) {
        val tipo = transacao.tipo

        configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraFormulario(tipo, transacaoDelegate)

        campoValor.setText(transacao.valor.toString())
        campoData.setText(transacao.data.formataParaBrasileiro())
        val categoriasRetornadas = context.resources.getStringArray(categoriasPor(tipo))
        val transacaoCategoria = categoriasRetornadas.indexOf(transacao.categoria)
        campoCategoria.setSelection(transacaoCategoria, true)
    }

    private fun configuraFormulario(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {

        val titulo = tituloPor(tipo)

        AlertDialog.Builder(context)
            .setTitle(titulo)
            .setView(viewCriada)
            .setPositiveButton(
                "Alterar"
            ) { _, _ ->
                val valorEmTexto = campoValor.text.toString()
                val dataEmTexto = campoData.text.toString()
                val categoriaEmTexto = campoCategoria.selectedItem.toString()
                val valor = converteCampoValor(valorEmTexto)
                val data = dataEmTexto.converteParaCalendar()
                val transacaoCriada = Transacao(
                    tipo = tipo,
                    valor = valor,
                    data = data,
                    categoria = categoriaEmTexto
                )

                transacaoDelegate.delegate(transacaoCriada)

            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun tituloPor(tipo: Tipo): Int {
        return if (tipo == Tipo.RECEITA) {
            R.string.altera_receita
        } else {
            R.string.altera_despesa
        }
    }

    private fun configuraCampoCategoria(tipo: Tipo) {

        val categorias = categoriasPor(tipo)

        val adapter = ArrayAdapter
            .createFromResource(
                context,
                categorias,
                android.R.layout.simple_spinner_dropdown_item
            )

        campoCategoria
            .adapter = adapter
    }

    private fun categoriasPor(tipo: Tipo): Int {
        return if (tipo == Tipo.RECEITA) {
            R.array.categorias_de_receita
        } else {
            R.array.categorias_de_despesa

        }
    }

    private fun configuraCampoData() {
        val hoje = Calendar.getInstance()

        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)

        campoData
            .setText(
                hoje
                    .formataParaBrasileiro()
            )
        campoData
            .setOnClickListener {
                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        val dataSelecionada = Calendar.getInstance()
                        dataSelecionada.set(year, month, dayOfMonth)
                        campoData.setText(
                            dataSelecionada
                                .formataParaBrasileiro()
                        )
                    }, ano, mes, dia
                ).show()
            }
    }

    private fun criaLayout(): View {
        return LayoutInflater.from(context)
            .inflate(
                R.layout
                    .form_transacao, viewGroup,
                false
            )
    }

    private fun converteCampoValor(valorEmTexto: String): BigDecimal {
        return try {
            BigDecimal(valorEmTexto)
        } catch (exception: NumberFormatException) {
            Toast.makeText(
                context,
                "Falha na conversão do valor",
                Toast.LENGTH_LONG
            )
                .show()
            BigDecimal.ZERO
        }
    }

}

