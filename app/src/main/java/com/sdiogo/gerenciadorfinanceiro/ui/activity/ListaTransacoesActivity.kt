package com.sdiogo.gerenciadorfinanceiro.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sdiogo.gerenciadorfinanceiro.R
import com.sdiogo.gerenciadorfinanceiro.extension.formataParaBrasileiro
import com.sdiogo.gerenciadorfinanceiro.model.Tipo
import com.sdiogo.gerenciadorfinanceiro.model.Transacao
import com.sdiogo.gerenciadorfinanceiro.ui.ResumoView
import com.sdiogo.gerenciadorfinanceiro.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes: List<Transacao> = transacoesDeExemplo()

        configuraResumo(transacoes)

        configuraLista(transacoes)

        lista_transacoes_adiciona_receita
            .setOnClickListener {
                val view: View = window.decorView
                val viewCriada = LayoutInflater.from(this)
                    .inflate(
                        R.layout
                            .form_transacao, view as ViewGroup,
                        false
                    )

                val ano = 2021
                val mes = 5
                val dia = 3

                val hoje = Calendar.getInstance()
                viewCriada.form_transacao_data
                    .setText(hoje.formataParaBrasileiro())
                viewCriada.form_transacao_data
                    .setOnClickListener {
                        DatePickerDialog(
                            this,
                            { view, year, month, dayOfMonth ->
                                val dataSelecionada = Calendar.getInstance()
                                dataSelecionada.set(year, month, dayOfMonth)
                                viewCriada.form_transacao_data.setText(
                                    dataSelecionada
                                        .formataParaBrasileiro()
                                )
                            }, ano, mes, dia
                        ).show()
                    }

                AlertDialog.Builder(this)
                    .setTitle(R.string.adiciona_receita)
                    .setView(viewCriada)
                    .show()
            }
    }

    private fun configuraResumo(transacoes: List<Transacao>) {
        val view: View = window.decorView
        val resumoView = ResumoView(this, view, transacoes)
        resumoView.atualiza()
    }

    private fun configuraLista(transacoes: List<Transacao>) {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }

    private fun transacoesDeExemplo(): List<Transacao> {
        return listOf(
            Transacao(
                valor = BigDecimal(100),
                tipo = Tipo.DESPESA,
                categoria = "Almoço final de semana",
                data = Calendar.getInstance()
            ),
            Transacao(
                valor = BigDecimal(100),
                tipo = Tipo.RECEITA,
                categoria = "Economia"
            ),
            Transacao(
                valor = BigDecimal(100),
                tipo = Tipo.DESPESA
            ),
            Transacao(
                valor = BigDecimal(200),
                tipo = Tipo.RECEITA,
                categoria = "Prêmio"
            )
        )
    }
}