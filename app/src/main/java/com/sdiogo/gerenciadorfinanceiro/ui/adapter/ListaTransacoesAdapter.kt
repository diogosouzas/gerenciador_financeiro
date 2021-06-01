package com.sdiogo.gerenciadorfinanceiro.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.sdiogo.gerenciadorfinanceiro.R

class ListaTransacoesAdapter(
    transacoes: List<String>,
    context: Context
) : BaseAdapter() {

    private val transacoes = transacoes
    private val context = context

    override fun getCount(): Int {
        return transacoes.size
    }

    override fun getItem(position: Int): String {
        return transacoes[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return LayoutInflater.from(context).inflate(R.layout.transacao_item, parent, false)
    }

}