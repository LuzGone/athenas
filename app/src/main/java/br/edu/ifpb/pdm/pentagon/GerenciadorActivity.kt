package br.edu.ifpb.pdm.pentagon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.material.slider.Slider

class GerenciadorActivity : AppCompatActivity() {
    private lateinit var senha: Senha;
    private var temSenha = false


    private lateinit var titulo:TextView;
    private lateinit var descricao:EditText;

    private lateinit var slider: Slider;
    private lateinit var valorMinimoSlider:TextView;
    private lateinit var valorMaximoSlider:TextView;
    private lateinit var valorAtualSlider:TextView;

    private lateinit var letrasMaiusculas:CheckBox;
    private lateinit var numeros:CheckBox;
    private lateinit var caracteresEspeciais:CheckBox;

    private lateinit var botaoConfirmar:Button;
    private lateinit var botaoCancelar:Button;
    private lateinit var botaoExcluir:Button;

    private var senhaDescricao: String? = "";
    private var senhaTemLM:Boolean? = false;
    private var senhaTemN:Boolean? = false;
    private var senhaTemCS:Boolean? = false;
    private var senhaTamanho:Int? = 4;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gerenciador)

        if(intent.hasExtra("descricao")){
            this.senhaDescricao = intent.getStringExtra("descricao")
            this.temSenha = true;
        }
        if(intent.hasExtra("temLM")){
            this.senhaTemLM = intent.getBooleanExtra("temLM",false);
        }
        if(intent.hasExtra("temN")){
            this.senhaTemN = intent.getBooleanExtra("temN",false);
        }
        if(intent.hasExtra("temCS")){
            this.senhaTemCS = intent.getBooleanExtra("temCS",false);
        }
        if(intent.hasExtra("tamanho")){
            this.senhaTamanho = intent.getIntExtra("tamanho",4);
        }

        this.senha = Senha(
            this.senhaDescricao,
            this.senhaTemLM,
            this.senhaTemCS,
            this.senhaTemN,
            this.senhaTamanho
        )



        this.titulo = findViewById(R.id.titulo);
        if(this.temSenha){
            this.titulo.setText("Editar Senha")
        }

        this.descricao = findViewById(R.id.descricao);
        this.descricao.setText(this.senha.getDescricao())

        //Configuracoes do Slider
        this.slider = findViewById(R.id.slider);
        this.slider.value = this.senha.getTamanho().toFloat()
        this.slider.addOnChangeListener{view,value:Float,fromUser:Boolean ->
            this.valorAtualSlider.setText("${this.slider.value}")
        }

        this.valorMinimoSlider = findViewById(R.id.valorMinimoSlider);
        this.valorMinimoSlider.setText("${this.slider.valueFrom}")

        this.valorMaximoSlider = findViewById(R.id.valorMaximoSlider);
        this.valorMaximoSlider.setText("${this.slider.valueTo}")

        this.valorAtualSlider = findViewById(R.id.valorAtualSlider);
        this.valorAtualSlider.setText("${this.slider.value}")

        this.letrasMaiusculas = findViewById(R.id.letrasMaiusculas);
        this.letrasMaiusculas.isChecked = this.senha.getTemLM()

        this.numeros = findViewById(R.id.numeros);
        this.numeros.isChecked = this.senha.getTemN()

        this.caracteresEspeciais = findViewById(R.id.caracteresEspeciais);
        this.caracteresEspeciais.isChecked = this.senha.getTemCS()


        this.botaoConfirmar = findViewById(R.id.botaoConfirmar);
        this.botaoConfirmar.setOnClickListener{confirmar()}

        this.botaoCancelar = findViewById(R.id.botaoCancelar);
        this.botaoCancelar.setOnClickListener{cancelar()}

        this.botaoExcluir = findViewById(R.id.botaoExcluir);
        if(!this.temSenha){
            this.botaoExcluir.isVisible = false
        }
        this.botaoExcluir.setOnClickListener{ excluir() }

    }

    fun cancelar(){
        finish()
    }

    fun confirmar(){
        //A SENHA NÃO VAI SER CRIADA AQUI E SIM NO MAIN ACITIVITY ;-;
        val descricao = this.descricao.text.toString()
        val temLM = this.letrasMaiusculas.isChecked()
        val temN = this.numeros.isChecked()
        val temCS = this.caracteresEspeciais.isChecked()
        val tamanho = this.slider.value.toInt()

        val intent = Intent().apply {
            putExtra("descricao",descricao)
            putExtra("temLM",temLM)
            putExtra("temN",temN)
            putExtra("temCS",temCS)
            putExtra("tamanho",tamanho)
        }

        if(this.temSenha){
            intent.apply {
                putExtra("temSenha",true)
            }
        }
        setResult(RESULT_OK,intent)
        finish()
    }

    fun excluir(){
        val intent = Intent().apply {
            putExtra("excluir",true)
        }
        setResult(RESULT_OK,intent)
        finish()
    }
}
