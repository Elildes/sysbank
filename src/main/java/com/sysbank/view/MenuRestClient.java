package com.sysbank.view;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

/**
 * Cliente de terminal que consome a própria API REST via HTTP. Roda em uma
 * thread separada após o Spring Boot subir completamente. Compartilha os mesmos
 * dados em memória com a API (mesmo ContaService).
 */
@Component
public class MenuRestClient {

	private static final String BASE_URL = "http://localhost:8080/banco/conta";
	private static final String SEPARADOR = "=".repeat(50);

	private final HttpClient http = HttpClient.newHttpClient();
	private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

	@Value("${sysbank.menu.enabled:true}")
	private boolean menuEnabled;

	@EventListener(ApplicationReadyEvent.class)
	public void iniciarEmThread() {
		if (!menuEnabled) {
			return; // menu desabilitado (ex.: container Docker, sem stdin)
		}
		// Roda em thread separada para não bloquear o Spring Boot
		new Thread(this::iniciar, "terminal-menu").start();
	}

	private void iniciar() {
		System.out.println("\n" + SEPARADOR);
		System.out.println("       SYS BANK - Sistema Bancario (API REST)");
		System.out.println(SEPARADOR);

		boolean executando = true;
		while (executando) {
			exibirMenu();
			int opcao = lerInteiro("Opcao");
			System.out.println();

			switch (opcao) {
			case 1 -> fluxoCadastrarConta("simples");
			case 2 -> fluxoCadastrarContaBonus();
			case 3 -> fluxoCadastrarConta("poupanca");
			case 4 -> fluxoConsultarSaldo();
			case 5 -> fluxoConsultarConta();
			case 6 -> fluxoCredito();
			case 7 -> fluxoDebito();
			case 8 -> fluxoTransferencia();
			case 9 -> fluxoRenderJuros();
			case 0 -> {
				executando = false;
				System.out.println("Encerrando. Ate logo!");
				System.exit(0);
			}
			default -> System.out.println("[!] Opcao invalida.");
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
		System.out.println("  [5] Consultar Dados da Conta");
		System.out.println("  [6] Credito em Conta");
		System.out.println("  [7] Debito em Conta");
		System.out.println("  [8] Transferencia entre Contas");
		System.out.println("  [9] Render Juros (Poupanca)");
		System.out.println("  [0] Sair");
		System.out.println(SEPARADOR);
	}

	// ---------------------------------------------------------------
	// Fluxos de cada operação
	// ---------------------------------------------------------------

	private void fluxoCadastrarConta(String tipo) {
		System.out.println("--- Cadastrar Conta " + (tipo.equals("simples") ? "Simples" : "Poupanca") + " ---");
		int numero = lerInteiro("Numero da conta");
		double saldoInicial = lerDouble("Saldo inicial");
		String body = String.format(Locale.US, "{\"numero\":%d,\"tipo\":\"%s\",\"saldoInicial\":%.2f}", numero, tipo,
				saldoInicial);
		System.out.println(post(BASE_URL + "/", body));
	}

	private void fluxoCadastrarContaBonus() {
		System.out.println("--- Cadastrar Conta Bonus ---");
		int numero = lerInteiro("Numero da conta");
		String body = String.format("{\"numero\":%d,\"tipo\":\"bonus\"}", numero);
		System.out.println(post(BASE_URL + "/", body));
	}

	private void fluxoConsultarSaldo() {
		System.out.println("--- Consultar Saldo ---");
		int numero = lerInteiro("Numero da conta");
		System.out.println(get(BASE_URL + "/" + numero + "/saldo"));
	}

	private void fluxoConsultarConta() {
		System.out.println("--- Consultar Dados da Conta ---");
		int numero = lerInteiro("Numero da conta");
		System.out.println(get(BASE_URL + "/" + numero));
	}

	private void fluxoCredito() {
		System.out.println("--- Credito em Conta ---");
		int numero = lerInteiro("Numero da conta");
		double valor = lerDouble("Valor do credito");
		String body = String.format(Locale.US, "{\"valor\":%.2f}", valor);
		System.out.println(put(BASE_URL + "/" + numero + "/credito", body));
	}

	private void fluxoDebito() {
		System.out.println("--- Debito em Conta ---");
		int numero = lerInteiro("Numero da conta");
		double valor = lerDouble("Valor do debito");
		String body = String.format(Locale.US, "{\"valor\":%.2f}", valor);
		System.out.println(put(BASE_URL + "/" + numero + "/debito", body));
	}

	private void fluxoTransferencia() {
		System.out.println("--- Transferencia entre Contas ---");
		int origem = lerInteiro("Numero da conta de origem");
		int destino = lerInteiro("Numero da conta de destino");
		double valor = lerDouble("Valor da transferencia");
		String body = String.format(Locale.US, "{\"from\":%d,\"to\":%d,\"amount\":%.2f}", origem, destino, valor);
		System.out.println(put(BASE_URL + "/transferencia", body));
	}

	private void fluxoRenderJuros() {
		System.out.println("--- Render Juros (todas as contas Poupanca) ---");
		double taxa = lerDouble("Taxa de juros (%)");
		String body = String.format(Locale.US, "{\"taxa\":%.2f}", taxa);
		System.out.println(put(BASE_URL + "/rendimento", body));
	}

	// ---------------------------------------------------------------
	// Métodos HTTP
	// ---------------------------------------------------------------

	private String get(String url) {
		try {
			HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
			HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());
			return formatarResposta(res);
		} catch (Exception e) {
			return "✘ Erro ao conectar com a API: " + e.getMessage();
		}
	}

	private String post(String url, String jsonBody) {
		try {
			HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
			HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());
			return formatarResposta(res);
		} catch (Exception e) {
			return "✘ Erro ao conectar com a API: " + e.getMessage();
		}
	}

	private String put(String url, String jsonBody) {
		try {
			HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).header("Content-Type", "application/json")
					.PUT(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
			HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());
			return formatarResposta(res);
		} catch (Exception e) {
			return "✘ Erro ao conectar com a API: " + e.getMessage();
		}
	}

	private String formatarResposta(HttpResponse<String> res) {
		int status = res.statusCode();
		String corpo = res.body();
		if (status >= 200 && status < 300) {
			return "✔ " + corpo;
		} else {
			return "✘ Erro (" + status + "): " + corpo;
		}
	}

	// ---------------------------------------------------------------
	// Leitura de entrada
	// ---------------------------------------------------------------

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