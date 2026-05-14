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

	// Issue #2
	@Test
	@DisplayName("#2 Deve cadastrar conta simples com saldo zero")
	void deveCadastrarContaComSaldoZero() throws ContaException {
		service.cadastrarConta(1001, 0.0);
		assertEquals(0.0, service.buscarConta(1001).getSaldo());
	}

	@Test
	@DisplayName("#2 Deve lancar excecao ao cadastrar conta duplicada")
	void deveLancarExcecaoContaDuplicada() throws ContaException {
		service.cadastrarConta(1001, 0.0);
		assertThrows(ContaException.class, () -> service.cadastrarConta(1001, 0.0));
	}

	// Issue #3
	@Test
	@DisplayName("#3 Deve consultar saldo de conta existente")
	void deveConsultarSaldo() throws ContaException {
		service.cadastrarConta(2001, 0.0);
		assertEquals(0.0, service.consultarSaldo(2001));
	}

	@Test
	@DisplayName("#3 Deve lancar excecao ao consultar conta inexistente")
	void deveLancarExcecaoContaInexistenteNaConsulta() {
		assertThrows(ContaException.class, () -> service.consultarSaldo(9999));
	}

	// Issue #4
	@Test
	@DisplayName("#4 Deve creditar valor corretamente")
	void deveCreditarValor() throws ContaException {
		service.cadastrarConta(3001, 0.0);
		service.credito(3001, 200.0);
		assertEquals(200.0, service.consultarSaldo(3001));
	}

	@Test
	@DisplayName("#4 Deve lancar excecao ao creditar valor negativo")
	void deveLancarExcecaoValorNegativoNoCredito() throws ContaException {
		service.cadastrarConta(3002, 0.0);
		assertThrows(ContaException.class, () -> service.credito(3002, -50.0));
	}

	@Test
	@DisplayName("#4 Deve lancar excecao ao creditar em conta inexistente")
	void deveLancarExcecaoCreditoContaInexistente() {
		assertThrows(ContaException.class, () -> service.credito(9999, 100.0));
	}

	// Issue #5
	@Test
	@DisplayName("#5 Deve debitar valor corretamente")
	void deveDebitarValor() throws ContaException {
		service.cadastrarConta(4001, 0.0);
		service.credito(4001, 300.0);
		service.debito(4001, 100.0);
		assertEquals(200.0, service.consultarSaldo(4001));
	}

	@Test
	@DisplayName("#5 Deve lancar excecao ao debitar valor negativo")
	void deveLancarExcecaoValorNegativoNoDebito() throws ContaException {
		service.cadastrarConta(4003, 0.0);
		assertThrows(ContaException.class, () -> service.debito(4003, -10.0));
	}

	@Test
	@DisplayName("#5 Deve lancar excecao ao debitar em conta inexistente")
	void deveLancarExcecaoDebitoContaInexistente() {
		assertThrows(ContaException.class, () -> service.debito(9999, 100.0));
	}

	// Bug #15
	@Test
	@DisplayName("#15 Deve lancar excecao ao debitar com saldo insuficiente")
	void deveLancarExcecaoSaldoInsuficienteNoDebito() throws ContaException {
		service.cadastrarConta(4002, 0.0);
		service.credito(4002, 100.0);
		assertThrows(ContaException.class, () -> service.debito(4002, 200.0));
	}

	// Issue #6
	@Test
	@DisplayName("#6 Deve transferir valor entre contas corretamente")
	void deveTransferirValor() throws ContaException {
		service.cadastrarConta(5001, 0.0);
		service.cadastrarConta(5002, 0.0);
		service.credito(5001, 500.0);
		service.transferencia(5001, 5002, 200.0);
		assertEquals(300.0, service.consultarSaldo(5001));
		assertEquals(200.0, service.consultarSaldo(5002));
	}

	@Test
	@DisplayName("#6 Deve lancar excecao quando conta de origem nao existe")
	void deveLancarExcecaoOrigemInexistente() throws ContaException {
		service.cadastrarConta(5005, 0.0);
		assertThrows(ContaException.class, () -> service.transferencia(9999, 5005, 50.0));
	}

	@Test
	@DisplayName("#6 Deve lancar excecao quando conta de destino nao existe")
	void deveLancarExcecaoDestinoInexistente() throws ContaException {
		service.cadastrarConta(5006, 0.0);
		assertThrows(ContaException.class, () -> service.transferencia(5006, 9999, 50.0));
	}

	@Test
	@DisplayName("#6 Deve lancar excecao quando origem e destino sao iguais")
	void deveLancarExcecaoOrigemIgualDestino() throws ContaException {
		service.cadastrarConta(5007, 0.0);
		assertThrows(ContaException.class, () -> service.transferencia(5007, 5007, 50.0));
	}

	@Test
	@DisplayName("#6 Deve lancar excecao ao transferir valor negativo")
	void deveLancarExcecaoValorNegativoNaTransferencia() throws ContaException {
		service.cadastrarConta(5008, 0.0);
		service.cadastrarConta(5009, 0.0);
		assertThrows(ContaException.class, () -> service.transferencia(5008, 5009, -10.0));
	}

	// Bug #15
	@Test
	@DisplayName("#15 Deve lancar excecao ao transferir com saldo insuficiente")
	void deveLancarExcecaoSaldoInsuficienteNaTransferencia() throws ContaException {
		service.cadastrarConta(5003, 0.0);
		service.cadastrarConta(5004, 0.0);
		service.credito(5003, 100.0);
		assertThrows(ContaException.class, () -> service.transferencia(5003, 5004, 500.0));
	}

	// Bug #18 - Validação de valores negativos e zero
	@Test
	@DisplayName("#18 Deve lancar excecao especifica para credito com valor negativo")
	void deveLancarExcecaoEspecificaParaValorNegativoNoCredito() throws ContaException {
		service.cadastrarConta(8001, 0.0);
		ContaException ex = assertThrows(ContaException.class, () -> service.credito(8001, -100.0));
		assertTrue(ex.getMessage().contains("negativo"));
	}

	@Test
	@DisplayName("#18 Deve lancar excecao especifica para debito com valor zero")
	void deveLancarExcecaoEspecificaParaValorZeroNoDebito() throws ContaException {
		service.cadastrarConta(8002, 0.0);
		ContaException ex = assertThrows(ContaException.class, () -> service.debito(8002, 0.0));
		assertTrue(ex.getMessage().contains("zero"));
	}

	@Test
	@DisplayName("#18 Deve lancar excecao especifica para transferencia com valor negativo")
	void deveLancarExcecaoEspecificaParaValorNegativoNaTransferencia() throws ContaException {
		service.cadastrarConta(8003, 0.0);
		service.cadastrarConta(8004, 0.0);
		ContaException ex = assertThrows(ContaException.class, () -> service.transferencia(8003, 8004, -50.0));
		assertTrue(ex.getMessage().contains("negativo"));
	}

	// Hotfix #30
	@Test
	@DisplayName("#30 Deve cadastrar conta simples com saldo inicial informado")
	void deveCadastrarContaComSaldoInicial() throws ContaException {
		service.cadastrarConta(1010, 250.0);
		assertEquals(250.0, service.buscarConta(1010).getSaldo());
	}

	@Test
	@DisplayName("#30 Deve cadastrar conta simples com saldo inicial zero")
	void deveCadastrarContaComSaldoInicialZero() throws ContaException {
		service.cadastrarConta(1011, 0.0);
		assertEquals(0.0, service.buscarConta(1011).getSaldo());
	}

	@Test
	@DisplayName("#30 Deve lancar excecao ao cadastrar conta duplicada apos hotfix")
	void deveLancarExcecaoContaDuplicadaAposHotfix() throws ContaException {
		service.cadastrarConta(1012, 100.0);
		assertThrows(ContaException.class, () -> service.cadastrarConta(1012, 50.0));
	}
}