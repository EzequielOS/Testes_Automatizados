package br.edu.iftm.Testes_Automatizados;

public class FuncionarioTerceirizado extends Funcionario{

    private double despesasAdicionais;

    public FuncionarioTerceirizado(String nomeFuncionario, int horasTrabalhadas, double valorHora, double despesasAdicionais) {
        super(nomeFuncionario, horasTrabalhadas, valorHora);
        this.despesasAdicionais = despesasAdicionais;
    }

    @Override
    public double calcularPagamento() {
        if (despesasAdicionais >= 1000){
            throw new IllegalArgumentException("Valor de despesas não pode exceder 1000");
        }
        return super.calcularPagamento() + (despesasAdicionais * 1.1);
    }
}
