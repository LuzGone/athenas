package br.edu.ifpb.pdm.pentagon

import android.content.ContentValues
import android.content.Context
import java.util.Calendar

class SenhaDAO {
    private val banco: BancoHelper

    constructor(context: Context){
        this.banco = BancoHelper(context)
    }

    fun insert(senha: Senha){
        val dataHora = Calendar.getInstance().timeInMillis
        val cv = ContentValues()
        cv.put("descricao", senha.getDescricao())
        cv.put("senha",senha.getSenha())
        cv.put("dataCriacao",dataHora)
        cv.put("dataAtualizacao",dataHora)
        this.banco.writableDatabase.insert("senhas", null, cv)
    }

    fun select(): List<Senha>{
        var lista = ArrayList<Senha>()
        val colunas = arrayOf("id", "descricao", "senha", "dataCriacao", "dataAtualizacao")
        val c = this.banco.readableDatabase.query("senhas", colunas, null, null, null,null, null)

        c.moveToFirst()
        for (i in 1..c.count){
            val id = c.getInt(0)
            val descricao = c.getString(1)
            val senha = c.getString(2)
            val dataCriacao = c.getLong(3)
            val dataAtualizacao = c.getLong(4)
            val password = Senha(id, descricao, senha, dataCriacao, dataAtualizacao)
            lista.add(password)
            c.moveToNext()
        }

        return lista
    }

    fun find(id: Int): Senha?{
        val colunas = arrayOf("id", "descricao", "senha", "dataCriacao", "dataAtualizacao")
        val where = "id = ?"
        val pWhere = arrayOf(id.toString())
        val c = this.banco.readableDatabase.query("senhas", colunas, where, pWhere, null,null, null)

        c.moveToFirst()
        if(c.count == 1){
            val id = c.getInt(0)
            val descricao = c.getString(1)
            val senha = c.getString(2)
            val dataCriacao = c.getLong(3)
            val dataAtualizacao = c.getLong(4)
            return Senha(id, descricao, senha, dataCriacao, dataAtualizacao)
        }
        return null
    }

    fun delete(id: Int){
        val where = "id = ?"
        val pWhere = arrayOf(id.toString())
        this.banco.writableDatabase.delete("senhas", where, pWhere)
    }

    fun update(senha: Senha){
        val where = "id = ?"
        val pWhere = arrayOf(senha.getId().toString())
        val cv = ContentValues()
        cv.put("descricao", senha.getDescricao())
        cv.put("senha", senha.getSenha())
        cv.put("dataAtualizacao", Calendar.getInstance().timeInMillis)
        this.banco.writableDatabase.update("senhas", cv, where, pWhere)
    }
}