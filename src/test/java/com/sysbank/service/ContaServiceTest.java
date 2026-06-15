package com.sysbank.service;

import com.sysbank.exception.ContaException;
import com.sysbank.model.ContaBonus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ContaService - Testes Unitarios (Parte 2)")
class ContaServiceTest {

	private ContaService service;

	@BeforeEach
	void setUp() {
		service = new ContaService();
	}

	// ===================================================================
	// #49 - Cadastrar Conta (um teste por tipo)
	// ===================================================================
	@Test
	@DisplayName("#49 Cadastrar conta simples")
	void cadastrarContaSimples() throws ContaException {
		service.cadastrarConta(1001, 100.0);
		assertEquals(100.0, service.consultarSaldo(1001));
	}

	@Test
	@DisplayName("#49 Cadastrar conta bonus")
	void cadastrarContaBonus() throws ContaException {
		service.cadastrarContaBonus(1002);
		ContaBonus cb = (ContaBonus) service.buscarConta(1002);
		assertEquals(10, cb.getPontuacao());
		assertEquals(0.0, cb.getSaldo());
	}

	@Test
	@DisplayName("#49 Cadastrar conta poupanca")
	void cadastrarContaPoupanca() throws ContaException {
		service.cadastrarContaPoupanca(1003, 500.0);
		assertEquals(500.0, service.consultarSaldo(1003));
	}

	// ===================================================================
	// #50 - Consultar Conta (um teste por tipo)
	// ===================================================================
	@Test
	@DisplayName("#50 Consultar conta simples retorna tipo correto")
	void consultarContaSimples() throws ContaException {
		service.cadastrarConta(2001, 50.0);
		String info = service.consultarInfoConta(2001);
		assertTrue(info.contains("Simples"));
		assertTrue(info.contains("2001"));
	}

	@Test
	@DisplayName("#50 Consultar conta bonus retorna pontuacao")
	void consultarContaBonus() throws ContaException {
		service.cadastrarContaBonus(2002);
		String info = service.consultarInfoConta(2002);
		assertTrue(info.contains("Bonus"));
		assertTrue(info.contains("10"));
	}

	@Test
	@DisplayName("#50 Consultar conta poupanca retorna tipo correto")
	void consultarContaPoupanca() throws ContaException {
		service.cadastrarContaPoupanca(2003, 300.0);
		String info = service.consultarInfoConta(2003);
		assertTrue(info.contains("Poupanca"));
	}

	@Test
	@DisplayName("#50 Consultar conta inexistente lanca excecao")
	void consultarContaInexistente() {
		assertThrows(ContaException.class, () -> service.consultarInfoConta(9999));
	}

	// ===================================================================
	// #51 - Consultar Saldo
	// ===================================================================
	@Test
	@DisplayName("#51 Consultar saldo retorna valor correto")
	void consultarSaldo() throws ContaException {
		service.cadastrarConta(3001, 250.0);
		assertEquals(250.0, service.consultarSaldo(3001));
	}

	@Test
	@DisplayName("#51 Consultar saldo de conta inexistente lanca excecao")
	void consultarSaldoInexistente() {
		assertThrows(ContaException.class, () -> service.consultarSaldo(9999));
	}

	// ===================================================================
	// #52 - Crédito
	// ===================================================================
	@Test
	@DisplayName("#52 Credito caso normal")
	void creditoNormal() throws ContaException {
		service.cadastrarConta(4001, 0.0);
		service.credito(4001, 200.0);
		assertEquals(200.0, service.consultarSaldo(4001));
	}

	@Test
	@DisplayName("#52 Credito nao permite valor negativo")
	void creditoValorNegativo() throws ContaException {
		service.cadastrarConta(4002, 0.0);
		assertThrows(ContaException.class, () -> service.credito(4002, -50.0));
	}

	@Test
	@DisplayName("#52 Credito gera bonificacao em conta bonus")
	void creditoBonificacao() throws ContaException {
		service.cadastrarContaBonus(4003);
		service.credito(4003, 540.0); // 540/100 = 5 pontos
		ContaBonus cb = (ContaBonus) service.buscarConta(4003);
		assertEquals(15, cb.getPontuacao()); // 10 iniciais + 5
	}

	// ===================================================================
	// #53 - Débito
	// ===================================================================
	@Test
	@DisplayName("#53 Debito caso normal")
	void debitoNormal() throws ContaException {
		service.cadastrarConta(5001, 300.0);
		service.debito(5001, 100.0);
		assertEquals(200.0, service.consultarSaldo(5001));
	}

	@Test
	@DisplayName("#53 Debito nao permite valor negativo")
	void debitoValorNegativo() throws ContaException {
		service.cadastrarConta(5002, 100.0);
		assertThrows(ContaException.class, () -> service.debito(5002, -10.0));
	}

	@Test
	@DisplayName("#53 Debito nao permite poupanca ficar negativa")
	void debitoPoupancaSaldoNegativo() throws ContaException {
		service.cadastrarContaPoupanca(5003, 100.0);
		assertThrows(ContaException.class, () -> service.debito(5003, 200.0));
	}

	// ===================================================================
	// #54 - Transferência
	// ===================================================================
	@Test
	@DisplayName("#54 Transferencia nao permite valor negativo")
	void transferenciaValorNegativo() throws ContaException {
		service.cadastrarConta(6001, 100.0);
		service.cadastrarConta(6002, 0.0);
		assertThrows(ContaException.class, () -> service.transferencia(6001, 6002, -50.0));
	}

	@Test
	@DisplayName("#54 Transferencia nao permite poupanca origem ficar negativa")
	void transferenciaPoupancaSaldoNegativo() throws ContaException {
		service.cadastrarContaPoupanca(6003, 100.0);
		service.cadastrarConta(6004, 0.0);
		assertThrows(ContaException.class, () -> service.transferencia(6003, 6004, 200.0));
	}

	@Test
	@DisplayName("#54 Transferencia gera bonificacao em conta bonus destino")
	void transferenciaBonificacao() throws ContaException {
		service.cadastrarConta(6005, 1000.0);
		service.cadastrarContaBonus(6006);
		service.transferencia(6005, 6006, 540.0); // 540/150 = 3 pontos
		ContaBonus cb = (ContaBonus) service.buscarConta(6006);
		assertEquals(13, cb.getPontuacao()); // 10 iniciais + 3
	}

	// ===================================================================
	// #55 - Render Juros
	// ===================================================================
	@Test
	@DisplayName("#55 Render juros aplica em todas as contas poupanca")
	void renderJurosTodasPoupancas() throws ContaException {
		service.cadastrarContaPoupanca(7001, 200.0);
		service.cadastrarContaPoupanca(7002, 100.0);
		service.cadastrarConta(7003, 200.0); // simples — nao deve render
		service.renderJurosEmTodasPoupancas(10.0);
		assertEquals(220.0, service.consultarSaldo(7001), 0.01);
		assertEquals(110.0, service.consultarSaldo(7002), 0.01);
		assertEquals(200.0, service.consultarSaldo(7003), 0.01); // inalterada
	}
}