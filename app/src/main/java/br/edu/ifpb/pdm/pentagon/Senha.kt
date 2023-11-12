package br.edu.ifpb.pdm.pentagon

class Senha {
    private var descricao : String;
    private lateinit var senha: String;
    private val intervaloDeLetrasMinusculas : CharRange = ('a'..'z')
    private val intervaloDeLetrasMaiusculas : CharRange = ('A'..'Z')
    private val intervaloDeNumeros : CharRange = ('0'..'9')
    private val caracteresEspeciais : CharArray = "!@#$%*-+=(){}[]".toCharArray()

    private val temLM:Boolean;
    private val temCS:Boolean;
    private val temN:Boolean;
    private val tamanho:Int;

    constructor(descricao:String?,temLM:Boolean? = false,temCS:Boolean?,temN:Boolean?,tamanho: Int?){
        this.descricao = if (descricao!=null) descricao else "";
        this.temLM = if (temLM!=null) temLM else false;
        this.temCS = if (temCS!=null) temCS else false;
        this.temN = if (temN!=null) temN else false;
        this.tamanho = if (tamanho!=null) tamanho else 4;
        gerarSenha()
    }

    fun gerarSenha(){
        var listaDeCaracteres = mutableListOf<Char>()

        for(caractere in intervaloDeLetrasMinusculas){
            listaDeCaracteres.add(caractere)
        }

        if(this.temLM){
            for(caractere in intervaloDeLetrasMaiusculas){
                listaDeCaracteres.add(caractere)
            }
        }

        if (this.temN){
            for(caractere in intervaloDeNumeros){
                listaDeCaracteres.add(caractere)
            }
        }

        if(this.temCS){
            for(caractere in caracteresEspeciais){
                listaDeCaracteres.add(caractere)
            }
        }

        this.senha = List(tamanho) { listaDeCaracteres.random() }.joinToString("")
    }

    fun getDescricao():String{
        return this.descricao
    }

    fun setDescricao(novaDescricao:String){
        this.descricao = novaDescricao
    }

    fun getSenha():String{
        return this.senha;
    }

    fun getTamanho():Int{
        return this.tamanho
    }

    fun getTemLM():Boolean{
        return this.temLM
    };
    fun getTemCS():Boolean{
        return this.temCS
    };
    fun getTemN():Boolean{
        return this.temN
    };
}