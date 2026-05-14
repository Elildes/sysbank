package com.sysbank.view;

import com.sysbank.controller.ContaController;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuView {

	private static final String SEPARADOR = "=".repeat(50);

	private final ContaController controller;
	private final Scanner scanner;

	public MenuView(ContaController controller) {
		this.controller = controller;
		this.scanner = new Scanner(System.in);
	}

	public void iniciar() {
		System.out.println(SEPARADOR);
		System.out.println("       SYS BANK - Sistema Bancario");
		System.out.println(SEPARADOR);

		boolean executando = true;
		while (executando) {
			exibirMenu();
			int opcao = lerInteiro("Opcao");
			System.out.println();

			switch (opcao) {
			case 1 -> fluxoCadastrarConta();
			case 2 -> fluxoCadastrarContaBonus();
			case 3 -> fluxoCadastrarContaPoupanca();
			case 4 -> fluxoConsultarSaldo();
			case 5 -> fluxoCredito();
			case 6 -> fluxoDebito();
			case 7 -> fluxoTransferencia();
			case 8 -> fluxoRenderJuros();
			case 0 -> {
				executando = false;
				System.out.println("Encerrando. Ate logo!");
			}
			default -> System.out.println("✘ Opcao invalida.");
			}
			System.out.println();
		}
	}

	private void exibirMenu() {
		System.out.println(SEPARADOR);
		System.out.println("  [1] Cadastrar Conta Simples");
		System.out.println("  [2] Cadastrar Conta Bonus");
		System.out.println("  [3] Cadastrar Conta Poupanca");
		System.out.println("  [4] Consultar Saldo");
		System.out.println("  [5] Credito em Conta");
		System.out.println("  [6] Debito em Conta");
		System.out.println("  [7] Transferencia entre Contas");
		System.out.println("  [8] Render Juros (Poupanca)");
		System.out.println("  [0] Sair");
		System.out.println(SEPARADOR);
	}

	// Hotfix #30: solicita saldo inicial
	private void fluxoCadastrarConta() {
		System.out.println("--- Cadastrar Conta Simples ---");
		int numero = lerInteiro("Numero da conta");
		double saldoInicial = lerDouble("Saldo inicial");
		System.out.println(controller.cadastrarConta(numero, saldoInicial));
	}

	// v2 - mantido de staging
	private void fluxoCadastrarContaBonus() {
		System.out.println("--- Cadastrar Conta Bonus ---");
		int numero = lerInteiro("Numero da conta");
		System.out.println(controller.cadastrarContaBonus(numero));
	}

	// v2 - mantido de staging
	private void fluxoCadastrarContaPoupanca() {
		System.out.println("--- Cadastrar Conta Poupanca ---");
		int numero = lerInteiro("Numero da conta");
		double saldoInicial = lerDouble("Saldo inicial");
		System.out.println(controller.cadastrarContaPoupanca(numero, saldoInicial));
	}

	private void fluxoConsultarSaldo() {
		System.out.println("--- Consultar Saldo ---");
		int numero = lerInteiro("Numero da conta");
		System.out.println(controller.consultarSaldo(numero));
	}

	private void fluxoCredito() {
		System.out.println("--- Credito em Conta ---");
		int numero = lerInteiro("Numero da conta");
		double valor = lerDouble("Valor do credito");
		System.out.println(controller.credito(numero, valor));
	}

	private void fluxoDebito() {
		System.out.println("--- Debito em Conta ---");
		int numero = lerInteiro("Numero da conta");
		double valor = lerDouble("Valor do debito");
		System.out.println(controller.debito(numero, valor));
	}

	private void fluxoTransferencia() {
		System.out.println("--- Transferencia entre Contas ---");
		int origem = lerInteiro("Numero da conta de origem");
		int destino = lerInteiro("Numero da conta de destino");
		double valor = lerDouble("Valor da transferencia");
		System.out.println(controller.transferencia(origem, destino, valor));
	}

	// v2 - mantido de staging
	private void fluxoRenderJuros() {
		System.out.println("--- Render Juros (todas as contas Poupanca) ---");
		double taxa = lerDouble("Taxa de juros (%)");
		System.out.println(controller.renderJuros(taxa));
	}

	private int lerInteiro(String campo) {
		while (true) {
			System.out.print(campo + ": ");
			try {
				int valor = scanner.nextInt();
				scanner.nextLine();
				return valor;
			} catch (InputMismatchException e) {
				scanner.nextLine();
				System.out.println("  ✘ Entrada invalida. Informe um numero inteiro.");
			}
		}
	}

	private double lerDouble(String campo) {
		while (true) {
			System.out.print(campo + " (R$): ");
			try {
				double valor = scanner.nextDouble();
				scanner.nextLine();
				return valor;
			} catch (InputMismatchException e) {
				scanner.nextLine();
				System.out.println("  ✘ Entrada invalida. Informe um valor numerico (ex: 10.5).");
			}
		}
	}
}