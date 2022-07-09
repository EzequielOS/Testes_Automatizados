package br.edu.iftm.Testes_Automatizados;

public class Funcionario {
    private String nomeFuncionario;
    private int horasTrabalhadas;
    private double valorHora;

    public Funcionario(String nomeFuncionario, int horasTrabalhadas, double valorHora) {
        this.nomeFuncionario = nomeFuncionario;
        this.horasTrabalhadas = validaHorasTrabalhadas(horasTrabalhadas);
        this.valorHora = validaValorHora(valorHora);
    }

    public Funcionario() {
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public int getHorasTrabalhadas() {
        return horasTrabalhadas;
    }

    public void setHorasTrabalhadas(int horasTrabalhadas) {
        this.horasTrabalhadas = horasTrabalhadas;
    }

    public double getValorHora() {
        return valorHora;
    }

    public void setValorHora(double valorHora) {
        this.valorHora = valorHora;
    }

    public int validaHorasTrabalhadas(int horasTrabalhadas){
        this.horasTrabalhadas = horasTrabalhadas;
        if(horasTrabalhadas > 40){
            throw new IllegalArgumentException("Horas acima do limite esperado, espera-se menor ou igual 40 horas trabalhadas");
        }
        return horasTrabalhadas;
    }

    public double validaValorHora(double valorHora){
        this.valorHora = valorHora;
        if(valorHora <= (1212.0*0.04) || valorHora >= (1212.0*0.1)){
            throw new IllegalArgumentException("Valor hora fora do limite esperado, espera-se entre 4% e 10% de R$1212.00");
        }
        return valorHora;
    }


    public double calcularPagamento(){
        return getHorasTrabalhadas()*getValorHora();
    }

}
