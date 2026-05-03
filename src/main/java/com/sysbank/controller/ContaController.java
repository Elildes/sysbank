package com.sysbank.controller;

import com.sysbank.exception.ContaException;
import com.sysbank.service.ContaService;

public class ContaController {

	private final ContaService contaService;

	public ContaController(ContaService contaService) {
		this.contaService = contaService;
	}

	// Issue #2 - Cadastrar Conta
	public String cadastrarConta(int numero) {
		try {
			contaService.cadastrarConta(numero);
			return "✔ Conta " + numero + " cadastrada com sucesso. Saldo inicial: R$ 0,00";
		} catch (ContaException e) {
			return "✘ Erro: " + e.getMessage();
		}
	}

	// Issue #3 - Consultar Saldo
	public String consultarSaldo(int numero) {
		try {
			double saldo = contaService.consultarSaldo(numero);
			return String.format("✔ Conta %d | Saldo: R$ %.2f", numero, saldo);
		} catch (ContaException e) {
			return "✘ Erro: " + e.getMessage();
		}
	}
}