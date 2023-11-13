package br.edu.ifpb.pdm.pentagon

class Senha {
    private var descricao : String;
    private lateinit var senha: String;
    private var id : Int;
    private var dataCriacao : Long;
    private var dataAtualizacao : Long;


    private val intervaloDeLetrasMinusculas : CharRange = ('a'..'z')
    private val intervaloDeLetrasMaiusculas : CharRange = ('A'..'Z')
    private val intervaloDeNumeros : CharRange = ('0'..'9')
    private val caracteresEspeciais : CharArray = "!@#$%*-+=(){}[]".toCharArray()

    private var temLM:Boolean;
    private var temCS:Boolean;
    private var temN:Boolean;
    private var tamanho:Int;

    constructor(descricao:String?,temLM:Boolean? = false,temCS:Boolean?,temN:Boolean?,tamanho: Int?){
        this.descricao = if (descricao!=null) descricao else "";
        this.temLM = if (temLM!=null) temLM else false;
        this.temCS = if (temCS!=null) temCS else false;
        this.temN = if (temN!=null) temN else false;
        this.tamanho = if (tamanho!=null) tamanho else 4;
        this.id = -1;
        this.dataCriacao = -1;
        this.dataAtualizacao = -1;
        gerarSenha()
    }

    // banco
    constructor(id: Int, descricao: String, senha: String, created_at: Long, updated_at: Long){
        this.id = id
        this.descricao = descricao
        this.senha = senha
        this.dataCriacao = created_at
        this.dataAtualizacao = updated_at
        this.temLM = false;
        this.temCS = false;
        this.temN = false;
        this.tamanho = senha.length;
        analizarSenha(senha);
    }

    fun analizarSenha(senha: String){
        for(caractere in this.caracteresEspeciais){
            if(senha.contains(caractere)){
                this.temCS = true;
                break
            }
        }
        for(caractere in this.intervaloDeNumeros){
            if(senha.contains(caractere)){
                this.temN = true;
                break
            }
        }
        for(caractere in this.intervaloDeLetrasMaiusculas){
            if(senha.contains(caractere)){
                this.temLM = true;
                break
            }
        }
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

    fun getId():Int{
        return this.id
    }

    fun setId(novoId:Int){
        this.id = novoId
    }

    fun getDataCriacao():Long{
        return this.dataCriacao
    }

    fun setDataCriacao(novaData:Long){
        this.dataCriacao = novaData
    }

    fun getDataAtualizacao():Long{
        return this.dataAtualizacao
    }

    fun setDataAtualizacao(novaData:Long){
        this.dataAtualizacao = novaData
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

    fun setTamanho(tamanho:Int){
        this.tamanho = tamanho
    }

    fun setTemLM(temLM: Boolean){
        this.temLM = temLM
    };
    fun setTemCS(temCS: Boolean){
        this.temCS = temCS
    };
    fun setTemN(temN: Boolean){
        this.temN = temN
    };
}