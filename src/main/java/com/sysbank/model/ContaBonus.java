package com.sysbank.model;

/**
 * Conta com pontuação de bônus. Pontuação inicial: 10 pontos. Acumula pontos em
 * depósitos e transferências recebidas.
 */
public class ContaBonus extends Conta {

	private int pontuacao;

	public ContaBonus(int numero) {
		super(numero);
		this.pontuacao = 10;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	/**
	 * Acrescenta pontuação por depósito: 1 ponto a cada R$ 100,00. Exemplo: R$
	 * 540,00 = 5 pontos.
	 */
	public void adicionarPontuacaoDeposito(double valor) {
		this.pontuacao += (int) (valor / 100);
	}

	/**
	 * Issue #31: nova regra — 1 ponto a cada R$ 150,00 (era R$ 200,00).
	 */
	public void adicionarPontuacaoTransferencia(double valor) {
		this.pontuacao += (int) (valor / 150);
	}

	@Override
	public String toString() {
		return String.format("ContaBonus [numero=%d, saldo=R$ %.2f, pontuacao=%d]", getNumero(), getSaldo(), pontuacao);
	}
}