fun main() {
    //OBJETOS
    val joao = Analista(
        nome = "Luiz Pedro", 
        cad = "45.997.418/0018-00", 
        salario = 3500.0 , 
        nasc = null, 
        pf = false)
    println(joao)
    
    println("\n================================");
    
    val maria = Cliente(
        nome = "Maria Jose", 
        cad = "123.456.987-99", 
        nasc = "12/06/1996", 
        pf = true,
        bandeira = ClienteBandeira.MC,
        senha = "1234")
    println(maria)
    
    println("\n================================");
    
    val contaMaria = Conta(
        agencia = "1234",
        numero = "12345678-9"
        )
    println(contaMaria)
    
    try {contaMaria.sacar(120.0)}
    catch(m: Exception){
        println(m.message)
        contaMaria.depositar(240.00)
    }
    
    println("\n================================");
    
    val banco1 = Banco(nome = "Santander", cod = "033") 

    println(banco1.dados())
    println(banco1.copy(nome = "Nubank!", cod = "260!").dados()) // usando copy para alterar uma instancia de data class
    
}
abstract class Pessoa(
    val nome: String,
    val cad: String,
    val pf: Boolean?,
    val nasc: String?
    )

abstract class Funcionario(
    nome: String, 
    cad: String, 
    pf: Boolean?, 
    nasc: String?, 
    val salario: Double): 
Pessoa(nome, cad, pf, nasc){
    protected abstract fun calculoAuxilio(): Double
    
    override fun toString(): String = """
        Nome: $nome
        CPF/CNPJ: $cad
        Nascimento: $nasc
        Salario: $salario
        Auxilio: ${calculoAuxilio()}
        Pessoa Fisica: $pf
    """.trimIndent()
}

class Analista(
    nome: String,
    cad: String,
    pf: Boolean?,
    nasc: String?,
    salario: Double
    ): Funcionario(nome, cad, pf, nasc, salario){
        
    override fun calculoAuxilio() = salario * 0.1
}

enum class ClienteBandeira(val descricao: String){
    VS("Visa"),
    MC("MasterCard"),
    EL("Elo"),
    AM("American Express"),
    HC("Hipercard");
}
interface Log{
    fun logIn(): Boolean
}

class Cliente(
    nome: String, 
    cad: String, 
    pf: Boolean?, 
    nasc:String?, 
    val bandeira: ClienteBandeira,
    val senha: String
    ): Pessoa(nome, cad, pf, nasc), Log{
        override fun logIn():Boolean = "1234" == senha
        
        override fun toString(): String = """
        Nome:  $nome
        CPF/CNPJ: $cad
        Nascimento: $nasc
        Bandeira: ${bandeira.descricao}
    """.trimIndent()
    }
    
    
interface Transacao{
    fun sacar(saque: Double)
    fun depositar(deposito: Double)
}
class Conta(
    val agencia: String,
    val numero: String): Transacao{
        var saldo: Double = 100.0; private set
        
        override fun depositar(deposito: Double) {
            this.saldo += deposito
            println("\n Deposito no valor de $deposito feito com sucesso!")
        }
        
        override fun sacar(saque: Double) {
            if(this.saldo < saque) throw IllegalArgumentException("\n Valor de $saque para saque indisponivel no momento!")
            this.saldo -= saque
        }
        
        override fun toString(): String {
            return """
                Ag: $agencia
                C/C: $numero
                Saldo Disponivel: $saldo
            """.trimIndent()
        }
    }

data class Banco(val nome: String, val cod: String){
    fun dados() = "Banco: $nome - Numero: $cod"
}