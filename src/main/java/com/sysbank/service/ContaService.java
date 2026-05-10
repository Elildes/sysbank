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

	// Issue #5 - Débito
	// Bug #15: adicionada verificação de saldo insuficiente
	public void debito(int numero, double valor) throws ContaException {
		validarValor(valor);
		Conta conta = buscarConta(numero);
		if (conta.getSaldo() < valor) {
			throw new ContaException("Saldo insuficiente para realizar o débito.");
		}
		conta.setSaldo(conta.getSaldo() - valor);
	}

	// Issue #6 - Transferência
	// Bug #15: adicionada verificação de saldo insuficiente na conta de origem
	public void transferencia(int numeroOrigem, int numeroDestino, double valor) throws ContaException {
		validarValor(valor);
		if (numeroOrigem == numeroDestino) {
			throw new ContaException("Conta de origem e destino não podem ser iguais.");
		}
		Conta origem = buscarConta(numeroOrigem);
		Conta destino = buscarConta(numeroDestino);
		if (origem.getSaldo() < valor) {
			throw new ContaException("Saldo insuficiente na conta de origem.");
		}
		origem.setSaldo(origem.getSaldo() - valor);
		destino.setSaldo(destino.getSaldo() + valor);
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