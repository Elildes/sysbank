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
}