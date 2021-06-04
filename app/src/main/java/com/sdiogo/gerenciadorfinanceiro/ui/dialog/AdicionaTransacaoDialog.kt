package com.sdiogo.gerenciadorfinanceiro.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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

class AdicionaTransacaoDialog(
    private val viewGroup: ViewGroup,
    private val context: Context
) {

    private val viewCriada = criaLayout()

    fun configuraDialog(transacaoDelegate: TransacaoDelegate) {
        configuraCampoData()
        configuraCampoCategoria()
        configuraFormulario(transacaoDelegate)
    }

    private fun configuraFormulario(transacaoDelegate: TransacaoDelegate) {
        AlertDialog.Builder(context)
            .setTitle(R.string.adiciona_receita)
            .setView(viewCriada)
            .setPositiveButton(
                "Adicionar"
            ) { _, _ ->
                val valorEmTexto = viewCriada
                    .form_transacao_valor.text.toString()
                val dataEmTexto = viewCriada
                    .form_transacao_data.text.toString()
                val categoriaEmTexto = viewCriada
                    .form_transacao_categoria.selectedItem.toString()

                val valor = converteCampoValor(valorEmTexto)

                val data = dataEmTexto.converteParaCalendar()

                val transacaoCriada = Transacao(
                    tipo = Tipo.RECEITA,
                    valor = valor,
                    data = data,
                    categoria = categoriaEmTexto
                )

                transacaoDelegate.delegate(transacaoCriada)

            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun configuraCampoCategoria() {
        val adapter = ArrayAdapter
            .createFromResource(
                context,
                R.array.categorias_de_receita,
                android.R.layout.simple_spinner_dropdown_item
            )

        viewCriada.form_transacao_categoria
            .adapter = adapter
    }

    private fun configuraCampoData() {
        val hoje = Calendar.getInstance()

        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)

        viewCriada.form_transacao_data
            .setText(
                hoje
                    .formataParaBrasileiro()
            )
        viewCriada.form_transacao_data
            .setOnClickListener {
                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        val dataSelecionada = Calendar.getInstance()
                        dataSelecionada.set(year, month, dayOfMonth)
                        viewCriada.form_transacao_data.setText(
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