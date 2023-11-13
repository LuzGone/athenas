package br.edu.ifpb.pdm.pentagon

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.text.SimpleDateFormat

class SenhaAdapter (var contexto : Context, var listaDeSenhas: List<Senha>): BaseAdapter() {
    override fun getCount(): Int {
        return this.listaDeSenhas.size
    }

    override fun getItem(posicao: Int): Any {
        return this.listaDeSenhas.get(posicao)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(posicao: Int, view: View?, viewGroup: ViewGroup?): View {
        var novaView = if (view == null){
            LayoutInflater.from(this.contexto).inflate(R.layout.senha_list, viewGroup, false)
        }else{
            view
        }

        val senhaTexto = novaView.findViewById<TextView>(R.id.senhaTexto)
        val senhaCriacao = novaView.findViewById<TextView>(R.id.senhaCriacao)
        val senhaAtualizacao = novaView.findViewById<TextView>(R.id.senhaAtualizacao)

        val senha = this.listaDeSenhas[posicao]
        val texto = "${senha.getDescricao()} (${senha.getTamanho()})"
        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        val dataCriacao = "Criado em: ${sdf.format(senha.getDataCriacao())}"
        val dataAtualizacao = "Atualizado em: ${sdf.format(senha.getDataAtualizacao())}"
        senhaTexto.text = texto
        senhaCriacao.text = dataCriacao
        senhaAtualizacao.text = dataAtualizacao

        return novaView
    }

    fun atualizar(novaListaSenha:List<Senha>){
        this.listaDeSenhas = novaListaSenha
        notifyDataSetChanged()
    }

}