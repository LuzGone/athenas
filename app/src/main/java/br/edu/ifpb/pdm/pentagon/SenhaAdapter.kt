package br.edu.ifpb.pdm.pentagon

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class SenhaAdapter (var contexto : Context, var listaDeSenhas: MutableList<Senha>): BaseAdapter() {
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

        val textView = novaView.findViewById<TextView>(R.id.senhaList)
        val senha = this.listaDeSenhas[posicao]
        val texto = "${senha.getDescricao()} (${senha.getTamanho()})"
        textView.text = texto

        return novaView
    }

    fun adicionar(novaSenha: Senha){
        this.listaDeSenhas.add(novaSenha)
        notifyDataSetChanged()
    }

    fun alterarSenha(novaSenha:Senha, posicao:Int){
        this.listaDeSenhas[posicao] = novaSenha
        notifyDataSetChanged()
    }

    fun excluirSenha(posicao:Int){
        this.listaDeSenhas.removeAt(posicao)
        notifyDataSetChanged()
    }

}