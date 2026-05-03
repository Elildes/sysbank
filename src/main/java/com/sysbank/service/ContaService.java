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

	// Issue #3 - Consultar Saldo
	public double consultarSaldo(int numero) throws ContaException {
		return buscarConta(numero).getSaldo();
	}

	// Issue #4 - Crédito
	public void credito(int numero, double valor) throws ContaException {
		validarValor(valor);
		Conta conta = buscarConta(numero);
		conta.setSaldo(conta.getSaldo() + valor);
	}
	
	// Issue #5 - Débito (saldo negativo permitido)
	public void debito(int numero, double valor) throws ContaException {
		validarValor(valor);
		Conta conta = buscarConta(numero);
		conta.setSaldo(conta.getSaldo() - valor);
	}

	// Método auxiliar interno
	Conta buscarConta(int numero) throws ContaException {
		Conta conta = contas.get(numero);
		if (conta == null) {
			throw new ContaException("Conta " + numero + " não encontrada.");
		}
		return conta;
	}

	private void validarValor(double valor) throws ContaException {
		if (valor <= 0) {
			throw new ContaException("O valor da operação deve ser positivo.");
		}
	}
}