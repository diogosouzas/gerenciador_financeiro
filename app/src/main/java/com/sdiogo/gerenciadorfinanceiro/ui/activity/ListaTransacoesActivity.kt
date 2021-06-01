package com.sdiogo.gerenciadorfinanceiro.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sdiogo.gerenciadorfinanceiro.R
import com.sdiogo.gerenciadorfinanceiro.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes = listOf("Comida - R$20,50", "Economia - R$10,50")

        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)

    }
}