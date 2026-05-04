package com.sysbank.service;

import com.sysbank.exception.ContaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ContaService - Testes Unitarios")
class ContaServiceTest {

	private ContaService service;

	@BeforeEach
	void setUp() {
		service = new ContaService();
	}

	// Issue #2 - Cadastrar Conta
	@Test
	@DisplayName("#2 Deve cadastrar conta com saldo zero")
	void deveCadastrarContaComSaldoZero() throws ContaException {
		service.cadastrarConta(1001);
		assertEquals(0.0, service.buscarConta(1001).getSaldo());
	}

	@Test
	@DisplayName("#2 Deve lancar excecao ao cadastrar conta duplicada")
	void deveLancarExcecaoContaDuplicada() throws ContaException {
		service.cadastrarConta(1001);
		assertThrows(ContaException.class, () -> service.cadastrarConta(1001));
	}

	// Issue #3 - Consultar Saldo
	@Test
	@DisplayName("#3 Deve consultar saldo de conta existente")
	void deveConsultarSaldo() throws ContaException {
		service.cadastrarConta(2001);
		assertEquals(0.0, service.consultarSaldo(2001));
	}

	@Test
	@DisplayName("#3 Deve lancar excecao ao consultar conta inexistente")
	void deveLancarExcecaoContaInexistenteNaConsulta() {
		assertThrows(ContaException.class, () -> service.consultarSaldo(9999));
	}

	// Issue #4 - Crédito
	@Test
	@DisplayName("#4 Deve creditar valor corretamente")
	void deveCreditarValor() throws ContaException {
		service.cadastrarConta(3001);
		service.credito(3001, 200.0);
		assertEquals(200.0, service.consultarSaldo(3001));
	}

	@Test
	@DisplayName("#4 Credito acumulado deve somar corretamente")
	void deveCreditarAcumulado() throws ContaException {
		service.cadastrarConta(3002);
		service.credito(3002, 100.0);
		service.credito(3002, 50.0);
		assertEquals(150.0, service.consultarSaldo(3002));
	}

	@Test
	@DisplayName("#4 Deve lancar excecao ao creditar valor negativo")
	void deveLancarExcecaoValorNegativoNoCredito() throws ContaException {
		service.cadastrarConta(3003);
		assertThrows(ContaException.class, () -> service.credito(3003, -50.0));
	}

	@Test
	@DisplayName("#4 Deve lancar excecao ao creditar em conta inexistente")
	void deveLancarExcecaoCreditoContaInexistente() {
		assertThrows(ContaException.class, () -> service.credito(9999, 100.0));
	}
	
	// Issue #5 - Débito
    @Test
    @DisplayName("#5 Deve debitar valor corretamente")
    void deveDebitarValor() throws ContaException {
        service.cadastrarConta(4001);
        service.credito(4001, 300.0);
        service.debito(4001, 100.0);
        assertEquals(200.0, service.consultarSaldo(4001));
    }

    @Test
    @DisplayName("#5 Deve permitir saldo negativo apos debito")
    void devePermitirSaldoNegativo() throws ContaException {
        service.cadastrarConta(4002);
        service.debito(4002, 50.0);
        assertEquals(-50.0, service.consultarSaldo(4002));
    }

    @Test
    @DisplayName("#5 Deve lancar excecao ao debitar valor negativo")
    void deveLancarExcecaoValorNegativoNoDebito() throws ContaException {
        service.cadastrarConta(4003);
        assertThrows(ContaException.class, () -> service.debito(4003, -10.0));
    }

    @Test
    @DisplayName("#5 Deve lancar excecao ao debitar em conta inexistente")
    void deveLancarExcecaoDebitoContaInexistente() {
        assertThrows(ContaException.class, () -> service.debito(9999, 100.0));
    }
    
 // Issue #6 - Transferência
    @Test
    @DisplayName("#6 Deve transferir valor entre contas corretamente")
    void deveTransferirValor() throws ContaException {
        service.cadastrarConta(5001);
        service.cadastrarConta(5002);
        service.credito(5001, 500.0);
        service.transferencia(5001, 5002, 200.0);
        assertEquals(300.0, service.consultarSaldo(5001));
        assertEquals(200.0, service.consultarSaldo(5002));
    }

    @Test
    @DisplayName("#6 Deve permitir saldo negativo na origem apos transferencia")
    void devePermitirSaldoNegativoNaOrigem() throws ContaException {
        service.cadastrarConta(5003);
        service.cadastrarConta(5004);
        service.transferencia(5003, 5004, 100.0);
        assertEquals(-100.0, service.consultarSaldo(5003));
        assertEquals(100.0,  service.consultarSaldo(5004));
    }

    @Test
    @DisplayName("#6 Deve lancar excecao quando conta de origem nao existe")
    void deveLancarExcecaoOrigemInexistente() throws ContaException {
        service.cadastrarConta(5005);
        assertThrows(ContaException.class, () -> service.transferencia(9999, 5005, 50.0));
    }

    @Test
    @DisplayName("#6 Deve lancar excecao quando conta de destino nao existe")
    void deveLancarExcecaoDestinoInexistente() throws ContaException {
        service.cadastrarConta(5006);
        assertThrows(ContaException.class, () -> service.transferencia(5006, 9999, 50.0));
    }

    @Test
    @DisplayName("#6 Deve lancar excecao quando origem e destino sao iguais")
    void deveLancarExcecaoOrigemIgualDestino() throws ContaException {
        service.cadastrarConta(5007);
        assertThrows(ContaException.class, () -> service.transferencia(5007, 5007, 50.0));
    }

    @Test
    @DisplayName("#6 Deve lancar excecao ao transferir valor negativo")
    void deveLancarExcecaoValorNegativoNaTransferencia() throws ContaException {
        service.cadastrarConta(5008);
        service.cadastrarConta(5009);
        assertThrows(ContaException.class, () -> service.transferencia(5008, 5009, -10.0));
    }
}