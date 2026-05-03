package com.sysbank.service;

import com.sysbank.exception.ContaException;
import com.sysbank.model.Conta;

import java.util.HashMap;
import java.util.Map;

public class ContaService {

	private final Map<Integer, Conta> contas;

	public ContaService() {
		this.contas = new HashMap<>();
	}

	// Issue #2 - Cadastrar Conta
	public void cadastrarConta(int numero) throws ContaException {
		if (contas.containsKey(numero)) {
			throw new ContaException("Já existe uma conta com o número " + numero + ".");
		}
		contas.put(numero, new Conta(numero));
	}

	// Método auxiliar interno
	Conta buscarConta(int numero) throws ContaException {
		Conta conta = contas.get(numero);
		if (conta == null) {
			throw new ContaException("Conta " + numero + " não encontrada.");
		}
		return conta;
	}
}