package com.sysbank.model;

/**
 * Entidade que representa uma conta bancária. Contém apenas os atributos número
 * e saldo, conforme especificação.
 */
public class Conta {

	private int numero;
	private double saldo;

	/**
	 * Cria uma nova conta com saldo inicial zero.
	 *
	 * @param numero número identificador da conta
	 */
	public Conta(int numero) {
		this.numero = numero;
		this.saldo = 0.0;
	}

	// Hotfix #30: construtor com saldo inicial obrigatório
	public Conta(int numero, double saldoInicial) {
		this.numero = numero;
		this.saldo = saldoInicial;
	}

	public int getNumero() {
		return numero;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	@Override
	public String toString() {
		return String.format("Conta [numero=%d, saldo=R$ %.2f]", numero, saldo);
	}
}
