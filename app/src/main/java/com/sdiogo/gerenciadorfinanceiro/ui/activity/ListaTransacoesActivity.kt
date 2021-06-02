package com.sdiogo.gerenciadorfinanceiro.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sdiogo.gerenciadorfinanceiro.R
import com.sdiogo.gerenciadorfinanceiro.model.Tipo
import com.sdiogo.gerenciadorfinanceiro.model.Transacao
import com.sdiogo.gerenciadorfinanceiro.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes: List<Transacao> = transacoesDeExemplo()

        configuraLista(transacoes)

    }

    private fun configuraLista(transacoes: List<Transacao>) {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }

    private fun transacoesDeExemplo(): List<Transacao> {
        return listOf(
            Transacao(
                valor = BigDecimal(20.5),
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
                valor = BigDecimal(200),
                tipo = Tipo.DESPESA
            ),
            Transacao(
                valor = BigDecimal(500),
                tipo = Tipo.RECEITA,
                categoria = "Prêmio"
            )
        )
    }
}