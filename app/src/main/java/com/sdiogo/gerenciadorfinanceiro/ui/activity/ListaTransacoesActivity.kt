package com.sdiogo.gerenciadorfinanceiro.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sdiogo.gerenciadorfinanceiro.R
import com.sdiogo.gerenciadorfinanceiro.model.Transacao
import com.sdiogo.gerenciadorfinanceiro.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes = listOf(
            Transacao(
                BigDecimal(20.5),
                "Comida", Calendar.getInstance()
            ),
            Transacao(
                BigDecimal(100),
                "Economia", Calendar.getInstance())
        )

        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)

    }
}