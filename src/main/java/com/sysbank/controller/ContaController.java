package com.sysbank.controller;

import com.sysbank.exception.ContaException;
import com.sysbank.service.ContaService;

public class ContaController {

	private final ContaService contaService;

	public ContaController(ContaService contaService) {
		this.contaService = contaService;
	}

	// Issue #2 - Cadastrar Conta Simples
	public String cadastrarConta(int numero) {
		try {
			contaService.cadastrarConta(numero);
			return "✔ Conta simples " + numero + " cadastrada. Saldo inicial: R$ 0,00";
		} catch (ContaException e) {
			return "✘ Erro: " + e.getMessage();
		}
	}

	// Issue #16 - Cadastrar Conta Bônus
	public String cadastrarContaBonus(int numero) {
		try {
			contaService.cadastrarContaBonus(numero);
			return "✔ Conta bonus " + numero + " cadastrada. Saldo: R$ 0,00 | Pontuacao inicial: 10 pts";
		} catch (ContaException e) {
			return "✘ Erro: " + e.getMessage();
		}
	}

	// Issue #28 (v3 Req 1)
	public String cadastrarContaPoupanca(int numero, double saldoInicial) {
		try {
			contaService.cadastrarContaPoupanca(numero, saldoInicial);
			return String.format("✔ Conta poupanca %d cadastrada. Saldo inicial: R$ %.2f", numero, saldoInicial);
		} catch (ContaException e) {
			return "✘ Erro: " + e.getMessage();
		}
	}

	// Issue #3 - Consultar Saldo
	public String consultarSaldo(int numero) {
		try {
			return "✔ " + contaService.consultarInfoConta(numero);
		} catch (ContaException e) {
			return "✘ Erro: " + e.getMessage();
		}
	}

	// Issue #4 - Crédito
	public String credito(int numero, double valor) {
		try {
			contaService.credito(numero, valor);
			return "✔ " + contaService.consultarInfoConta(numero);
		} catch (ContaException e) {
			return "✘ Erro: " + e.getMessage();
		}
	}

	// Issue #5 - Débito
	public String debito(int numero, double valor) {
		try {
			contaService.debito(numero, valor);
			double novoSaldo = contaService.consultarSaldo(numero);
			return String.format("✔ Debito de R$ %.2f na conta %d | Novo saldo: R$ %.2f", valor, numero, novoSaldo);
		} catch (ContaException e) {
			return "✘ Erro: " + e.getMessage();
		}
	}

	// Issue #6 - Transferência
	public String transferencia(int numeroOrigem, int numeroDestino, double valor) {
		try {
			contaService.transferencia(numeroOrigem, numeroDestino, valor);
			double saldoOrigem = contaService.consultarSaldo(numeroOrigem);
			double saldoDestino = contaService.consultarSaldo(numeroDestino);
			return String.format(
					"✔ Transferencia de R$ %.2f da conta %d para conta %d realizada.%n"
							+ "   Conta %d | Novo saldo: R$ %.2f%n" + "   Conta %d | Novo saldo: R$ %.2f",
					valor, numeroOrigem, numeroDestino, numeroOrigem, saldoOrigem, numeroDestino, saldoDestino);
		} catch (ContaException e) {
			return "✘ Erro: " + e.getMessage();
		}
	}

	// Issue #17 - Render Juros
	public String renderJuros(double taxaPercentual) {
		try {
			contaService.renderJurosEmTodasPoupancas(taxaPercentual);
			return String.format("✔ Juros de %.2f%% aplicados em todas as contas poupanca.", taxaPercentual);
		} catch (ContaException e) {
			return "✘ Erro: " + e.getMessage();
		}
	}
}