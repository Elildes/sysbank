package com.sysbank.dto;

public class CadastrarContaRequest {
	private int numero;
	private String tipo; // "simples", "bonus" ou "poupanca"
	private double saldoInicial; // usado por simples e poupanca

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(double saldoInicial) {
		this.saldoInicial = saldoInicial;
	}
}