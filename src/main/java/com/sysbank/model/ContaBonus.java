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
	 * Acrescenta pontuação por transferência recebida: 1 ponto a cada R$ 200,00.
	 * Exemplo: R$ 540,00 = 2 pontos.
	 */
	public void adicionarPontuacaoTransferencia(double valor) {
		this.pontuacao += (int) (valor / 200);
	}

	@Override
	public String toString() {
		return String.format("ContaBonus [numero=%d, saldo=R$ %.2f, pontuacao=%d]", getNumero(), getSaldo(), pontuacao);
	}
}