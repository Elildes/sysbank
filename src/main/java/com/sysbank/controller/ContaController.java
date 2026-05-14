package com.sysbank.controller;

import com.sysbank.exception.ContaException;
import com.sysbank.service.ContaService;

public class ContaController {

	private final ContaService contaService;

	public ContaController(ContaService contaService) {
		this.contaService = contaService;
	}

	// Hotfix #30 - Cadastrar Conta Simples com saldo inicial obrigatório
	public String cadastrarConta(int numero, double saldoInicial) {
		try {
			contaService.cadastrarConta(numero, saldoInicial);
			return String.format("✔ Conta simples %d cadastrada. Saldo inicial: R$ %.2f", numero, saldoInicial);
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
}