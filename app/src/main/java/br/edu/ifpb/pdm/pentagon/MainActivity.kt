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
    private var senhas = mutableListOf<Senha>()
    private var caracteres = mutableListOf<String>()
    private var posicaoDaSenhaAlterada = 0;

    private val gerenciadorResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            if(it.data != null){
                val descricao = it.data?.getStringExtra("descricao")
                val temLM = it.data?.getBooleanExtra("temLM",false)
                val temN = it.data?.getBooleanExtra("temN",false)
                val temCS = it.data?.getBooleanExtra("temCS",false)
                val tamanho = it.data?.getIntExtra("tamanho",4)

                Log.i("VALORES","${temCS} - ${temLM} - ${temN}")
                val senha = Senha(descricao,temLM,temCS,temN,tamanho)

//                this.senhas.add(senha)
                Log.i("SENHA",senha.getSenha())
                Log.i("TAMANHO","${this.senhas.size}")
                if(it.data?.getBooleanExtra("temSenha",false) == true){
                    alterarSenha(senha,this.posicaoDaSenhaAlterada)
                }else if(it.data?.getBooleanExtra("excluir",false)==true){
                    excluirSenha(this.posicaoDaSenhaAlterada)
                }else{
                    atualizarLista(senha)
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.listinhaDeSenha = findViewById(R.id.listinhaDeSenhas);
        this.botaoNovaSenha = findViewById(R.id.botaoNovaSenha)

//        this.listinhaDeSenha.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,this.caracteres)
        this.listinhaDeSenha.adapter = SenhaAdapter(this,this.senhas);
        this.listinhaDeSenha.setOnItemLongClickListener(PegarSenha())
        this.listinhaDeSenha.setOnItemClickListener (EditarSenha())

        this.botaoNovaSenha.setOnClickListener({irParaGerenciador()})
    }

    fun irParaGerenciador(){
        val intent = Intent(this, GerenciadorActivity::class.java)
        this.gerenciadorResult.launch(intent)
    }


    fun atualizarLista(senha:Senha){
        (this.listinhaDeSenha.adapter as SenhaAdapter).adicionar(senha)
    }

    fun alterarSenha(novaSenha:Senha,posicao:Int){
        (this.listinhaDeSenha.adapter as SenhaAdapter).alterarSenha(novaSenha,posicao)
    }

    fun excluirSenha(posicao:Int){
        (this.listinhaDeSenha.adapter as SenhaAdapter).excluirSenha(posicao)
    }

    inner class EditarSenha: AdapterView.OnItemClickListener {
        override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            val senha = this@MainActivity.senhas.get(p2)
            posicaoDaSenhaAlterada = p2
            editarSenha(senha)
        }
    }



    fun editarSenha(senha:Senha){
        val descricao = senha.getDescricao()
        val temLM = senha.getTemLM()
        val temN = senha.getTemN()
        val temCS = senha.getTemCS()
        val tamanho = senha.getTamanho()

        val intent = Intent(this,GerenciadorActivity::class.java).apply {
            putExtra("descricao",descricao)
            putExtra("temLM",temLM)
            putExtra("temN",temN)
            putExtra("temCS",temCS)
            putExtra("tamanho",tamanho)
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