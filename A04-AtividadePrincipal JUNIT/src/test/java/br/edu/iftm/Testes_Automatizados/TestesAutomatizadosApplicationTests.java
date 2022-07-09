package br.edu.iftm.Testes_Automatizados;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class TestesAutomatizadosApplicationTests {

    @Test
    void testaConstrutorPagamentoInvalido() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Funcionario("Mary", 45, 5.5));
    }

    @Test
    void testarConstrutorEntradaValorHoraInvalida() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Funcionario("Mary", 45, 50));
    }

    @Test
    void testarConstrutoresValidos() {
        Funcionario funcionario1 = new Funcionario("Julian", 40, 50);
        String textoEsperado = "Julian";
        int valorEsperado = 40;
        double valorEsperado2 = 50.0;

        Assertions.assertEquals(textoEsperado, funcionario1.getNomeFuncionario());
        Assertions.assertEquals(valorEsperado, funcionario1.getHorasTrabalhadas());
        Assertions.assertEquals(valorEsperado2, funcionario1.getValorHora());
    }

    @Test
    void testarValoresComSetters(){
        Funcionario funcionario1 = new Funcionario();
        String textoEsperado = "Ambrosio";
        int valorEsperado = 42;
        double valorEsperado2 = 50.3;

        funcionario1.setNomeFuncionario("Ambrosio");
        funcionario1.setValorHora(50.3);
        funcionario1.setHorasTrabalhadas(42);

        Assertions.assertEquals(valorEsperado, funcionario1.getHorasTrabalhadas());
        Assertions.assertEquals(valorEsperado2, funcionario1.getValorHora());
        Assertions.assertEquals(textoEsperado, funcionario1.getNomeFuncionario());
    }

    @Test
    void testarValidaHoraTrabalhada(){
        Funcionario funcionario1 = new Funcionario();
        Assertions.assertThrows(IllegalArgumentException.class, () -> funcionario1.validaHorasTrabalhadas(50));
    }

    @Test
    void testarConstrutorEntradaDespesasInvalida(){
        FuncionarioTerceirizado funcionarioTerceirizado1 = new FuncionarioTerceirizado("Joelson Santos", 40, 50, 5000);
        Assertions.assertThrows(IllegalArgumentException.class, () -> funcionarioTerceirizado1.calcularPagamento());

    }
    void testarConstrutorEntradaDespesasValida(){
        FuncionarioTerceirizado funcionarioTerceirizado1 = new FuncionarioTerceirizado("Joelson Santos", 40, 50, 1000);


    }

}
