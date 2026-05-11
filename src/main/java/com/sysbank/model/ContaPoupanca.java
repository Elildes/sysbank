package com.sysbank.model;

/**
 * Conta Poupança: mesmos atributos da Conta simples. Operação adicional:
 * renderJuros(taxa), exclusiva deste tipo.
 */
public class ContaPoupanca extends Conta {

	public ContaPoupanca(int numero) {
		super(numero);
	}

	/**
	 * Aplica juros ao saldo com base na taxa percentual informada. Exemplo:
	 * saldo=200, taxa=10.5 → saldo=221
	 *
	 * @param taxaPercentual taxa de juros em % (ex: 10.5 para 10,5%)
	 */
	public void renderJuros(double taxaPercentual) {
		setSaldo(getSaldo() * (1 + taxaPercentual / 100));
	}

	@Override
	public String toString() {
		return String.format("ContaPoupanca [numero=%d, saldo=R$ %.2f]", getNumero(), getSaldo());
	}
}