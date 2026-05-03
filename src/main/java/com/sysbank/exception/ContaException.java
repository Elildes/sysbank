package com.sysbank.exception;

/**
 * Exceção de domínio para operações bancárias inválidas. Utilizada para
 * sinalizar erros de negócio (conta inexistente, conta duplicada, etc).
 */
public class ContaException extends Exception {

	public ContaException(String mensagem) {
		super(mensagem);
	}
}
