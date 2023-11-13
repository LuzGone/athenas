package br.edu.ifpb.pdm.pentagon

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnLongClickListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.get
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var listinhaDeSenha: ListView;
    private lateinit var botaoNovaSenha: FloatingActionButton;
    private lateinit var senhas : List<Senha>;
    private lateinit var senhaDAO: SenhaDAO;

    private val gerenciadorResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            atualizarLista()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.senhaDAO = SenhaDAO(this);
        this.listinhaDeSenha = findViewById(R.id.listinhaDeSenhas);
        this.botaoNovaSenha = findViewById(R.id.botaoNovaSenha)
        this.senhas = this.senhaDAO.select();

        this.listinhaDeSenha.adapter = SenhaAdapter(this,this.senhas);
        this.listinhaDeSenha.setOnItemLongClickListener(PegarSenha())
        this.listinhaDeSenha.setOnItemClickListener (EditarSenha())

        this.botaoNovaSenha.setOnClickListener({irParaGerenciador()})
    }

    fun irParaGerenciador(){
        val intent = Intent(this, GerenciadorActivity::class.java)
        this.gerenciadorResult.launch(intent)
    }


    fun atualizarLista(){
        (this.listinhaDeSenha.adapter as SenhaAdapter).atualizar(this.senhaDAO.select())
    }

    inner class EditarSenha: AdapterView.OnItemClickListener {
        override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            val senha = this@MainActivity.senhas.get(p2)
            Log.i("Id da senha", "${senha.getId()}")
            editarSenha(senha.getId())
        }
    }



    fun editarSenha(id:Int){
        val intent = Intent(this,GerenciadorActivity::class.java).apply {
            putExtra("id",id)
        }

        this.gerenciadorResult.launch(intent)
    }

    inner class PegarSenha: AdapterView.OnItemLongClickListener {
        override fun onItemLongClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long):Boolean{
            val senha = this@MainActivity.senhas.get(p2)
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("simple text", senha.getSenha())
            clipboard.setPrimaryClip(clip)
            return true
        }
    }

}