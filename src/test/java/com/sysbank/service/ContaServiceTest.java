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
}