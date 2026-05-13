package com.sysbank.service;

import com.sysbank.exception.ContaException;
import com.sysbank.model.Conta;
import com.sysbank.model.ContaBonus;
import com.sysbank.model.ContaPoupanca;

import java.util.HashMap;
import java.util.Map;

public class ContaService {

	private final Map<Integer, Conta> contas;

	public ContaService() {
		this.contas = new HashMap<>();
	}

	// Issue #2 - Cadastrar Conta Simples
	public void cadastrarConta(int numero) throws ContaException {
		validarNumeroDuplicado(numero);
		contas.put(numero, new Conta(numero));
	}

	// Issue #16 - Cadastrar Conta Bônus
	public void cadastrarContaBonus(int numero) throws ContaException {
		validarNumeroDuplicado(numero);
		contas.put(numero, new ContaBonus(numero));
	}

	// Issue #28 (v3 Req 1) - Conta Poupança com saldo inicial obrigatório
	public void cadastrarContaPoupanca(int numero, double saldoInicial) throws ContaException {
		validarNumeroDuplicado(numero);
		if (saldoInicial < 0) {
			throw new ContaException("O saldo inicial nao pode ser negativo.");
		}
		contas.put(numero, new ContaPoupanca(numero, saldoInicial));
	}

	// Issue #3 - Consultar Saldo
	public double consultarSaldo(int numero) throws ContaException {
		return buscarConta(numero).getSaldo();
	}

	// Consultar informações completas da conta
	public String consultarInfoConta(int numero) throws ContaException {
		Conta conta = buscarConta(numero);
		if (conta instanceof ContaBonus cb) {
			return String.format("Conta %d | Tipo: Bonus | Saldo: R$ %.2f | Pontuacao: %d pts", numero,
					conta.getSaldo(), cb.getPontuacao());
		}
		if (conta instanceof ContaPoupanca) {
			return String.format("Conta %d | Tipo: Poupanca | Saldo: R$ %.2f", numero, conta.getSaldo());
		}
		return String.format("Conta %d | Tipo: Simples | Saldo: R$ %.2f", numero, conta.getSaldo());
	}

	// Issue #4 - Crédito
	public void credito(int numero, double valor) throws ContaException {
		validarValor(valor);
		Conta conta = buscarConta(numero);
		conta.setSaldo(conta.getSaldo() + valor);
		if (conta instanceof ContaBonus cb) {
			cb.adicionarPontuacaoDeposito(valor);
		}
	}

	// Issue #5 - Débito
	public void debito(int numero, double valor) throws ContaException {
		validarValor(valor);
		Conta conta = buscarConta(numero);
		if (conta.getSaldo() < valor) {
			throw new ContaException("Saldo insuficiente para realizar o débito.");
		}
		conta.setSaldo(conta.getSaldo() - valor);
	}

	// Issue #6 - Transferência
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
		if (destino instanceof ContaBonus cb) {
			cb.adicionarPontuacaoTransferencia(valor);
		}
	}

	// Issue #17 - Render Juros em todas as Contas Poupança
	public void renderJurosEmTodasPoupancas(double taxaPercentual) throws ContaException {
		if (taxaPercentual <= 0) {
			throw new ContaException("A taxa de juros deve ser positiva.");
		}
		contas.values().stream().filter(c -> c instanceof ContaPoupanca).map(c -> (ContaPoupanca) c)
				.forEach(c -> c.renderJuros(taxaPercentual));
	}

	// Método auxiliar interno
	Conta buscarConta(int numero) throws ContaException {
		Conta conta = contas.get(numero);
		if (conta == null) {
			throw new ContaException("Conta " + numero + " não encontrada.");
		}
		return conta;
	}

	private void validarNumeroDuplicado(int numero) throws ContaException {
		if (contas.containsKey(numero)) {
			throw new ContaException("Já existe uma conta com o número " + numero + ".");
		}
	}

	// Bug #18 - validação separada: negativo e zero com mensagens distintas
	private void validarValor(double valor) throws ContaException {
		if (valor < 0) {
			throw new ContaException("Operacao nao permitida: o valor nao pode ser negativo.");
		}
		if (valor == 0) {
			throw new ContaException("Operacao nao permitida: o valor deve ser maior que zero.");
		}
	}
}