package com.sdiogo.gerenciadorfinanceiro.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.sdiogo.gerenciadorfinanceiro.R
import com.sdiogo.gerenciadorfinanceiro.dao.TransacaoDAO
import com.sdiogo.gerenciadorfinanceiro.model.Tipo
import com.sdiogo.gerenciadorfinanceiro.model.Transacao
import com.sdiogo.gerenciadorfinanceiro.ui.ResumoView
import com.sdiogo.gerenciadorfinanceiro.ui.adapter.ListaTransacoesAdapter
import com.sdiogo.gerenciadorfinanceiro.ui.dialog.AdicionaTransacaoDialog
import com.sdiogo.gerenciadorfinanceiro.ui.dialog.AlteraTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesActivity : AppCompatActivity() {

    private val dao = TransacaoDAO()
    private val transacoes = dao.transacoes
    private val viewDaActivity by lazy {
        window.decorView
    }

    private val viewGroupDaActivity by lazy {
        viewDaActivity as ViewGroup
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configuraResumo()
        configuraLista()
        configuraFab()

    }

    private fun configuraFab() {
        lista_transacoes_adiciona_receita
            .setOnClickListener {
                chamaDialogAdicao(Tipo.RECEITA)
            }
        lista_transacoes_adiciona_despesa
            .setOnClickListener {
                chamaDialogAdicao(Tipo.DESPESA)
            }
    }

    private fun chamaDialogAdicao(tipo: Tipo) {
        AdicionaTransacaoDialog(viewGroupDaActivity as ViewGroup, this)
            .chama(tipo) {
                adiciona(it)
                lista_transacoes_adiciona_menu.close(true)
            }
    }

    private fun adiciona(transacao: Transacao) {
        dao.adiciona(transacao)
        atualizaTransacoes()
    }

    private fun atualizaTransacoes() {
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val resumoView = ResumoView(this, viewDaActivity, transacoes)
        resumoView.atualiza()
    }

    private fun configuraLista() {
        with(lista_transacoes_listview) {
            adapter = ListaTransacoesAdapter(transacoes, this@ListaTransacoesActivity)
            setOnItemClickListener { _, _, position, _ ->
                val transacao = transacoes[position]
                chamaDialogAlteracao(transacao, position)
            }
            setOnCreateContextMenuListener { menu, _, _ ->
                menu.add(Menu.NONE, 1, Menu.NONE, "Remover")
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val idDoMenu = item?.itemId
        if (idDoMenu == 1) {
            val adapterMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val posicaoDaTransacao = adapterMenuInfo.position
            remove(posicaoDaTransacao)
        }
        return super.onContextItemSelected(item)
    }

    private fun remove(posicao: Int) {
        dao.remove(posicao)
        atualizaTransacoes()
    }

    private fun chamaDialogAlteracao(transacao: Transacao, position: Int) {
        AlteraTransacaoDialog(viewGroupDaActivity as ViewGroup, this)
            .chama(transacao) {
                alterar(it, position)
            }
    }

    private fun alterar(transacao: Transacao, position: Int) {
        dao.altera(transacao, position)
        atualizaTransacoes()
    }
}