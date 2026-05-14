package com.sysbank.model;

public class Conta {

	private int numero;
	private double saldo;

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