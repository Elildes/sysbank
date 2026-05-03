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
			case 2 -> fluxoConsultarSaldo();
			case 3 -> System.out.println("Funcionalidade em desenvolvimento.");
			case 4 -> System.out.println("Funcionalidade em desenvolvimento.");
			case 5 -> System.out.println("Funcionalidade em desenvolvimento.");
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
		System.out.println("  [1] Cadastrar Conta");
		System.out.println("  [2] Consultar Saldo");
		System.out.println("  [3] Credito em Conta");
		System.out.println("  [4] Debito em Conta");
		System.out.println("  [5] Transferencia entre Contas");
		System.out.println("  [0] Sair");
		System.out.println(SEPARADOR);
	}

	private void fluxoCadastrarConta() {
		System.out.println("--- Cadastrar Conta ---");
		int numero = lerInteiro("Numero da conta");
		System.out.println(controller.cadastrarConta(numero));
	}

	private void fluxoConsultarSaldo() {
		System.out.println("--- Consultar Saldo ---");
		int numero = lerInteiro("Numero da conta");
		System.out.println(controller.consultarSaldo(numero));
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
				System.out.println("  ✘ Entrada invalida. Informe um valor numerico (ex: 150.00).");
			}
		}
	}
}