package com.sysbank.api;

import com.sysbank.dto.CadastrarContaRequest;
import com.sysbank.dto.ValorRequest;
import com.sysbank.exception.ContaException;
import com.sysbank.service.ContaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/banco/conta")
public class ContaRestController {

	private final ContaService contaService;

	public ContaRestController(ContaService contaService) {
		this.contaService = contaService;
	}

	// Issue #42 - POST /banco/conta/
	@PostMapping("/")
	public ResponseEntity<String> cadastrarConta(@RequestBody CadastrarContaRequest req) {
		try {
			String tipo = req.getTipo() == null ? "simples" : req.getTipo().toLowerCase();
			switch (tipo) {
			case "bonus" -> contaService.cadastrarContaBonus(req.getNumero());
			case "poupanca" -> contaService.cadastrarContaPoupanca(req.getNumero(), req.getSaldoInicial());
			default -> contaService.cadastrarConta(req.getNumero(), req.getSaldoInicial());
			}
			return ResponseEntity.status(HttpStatus.CREATED)
					.body("Conta " + req.getNumero() + " (" + tipo + ") cadastrada.");
		} catch (ContaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// Issue #43 - GET /banco/conta/{id}
	@GetMapping("/{id}")
	public ResponseEntity<String> consultarConta(@PathVariable int id) {
		try {
			return ResponseEntity.ok(contaService.consultarInfoConta(id));
		} catch (ContaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// Issue #44 - GET /banco/conta/{id}/saldo
	@GetMapping("/{id}/saldo")
	public ResponseEntity<String> consultarSaldo(@PathVariable int id) {
		try {
			double saldo = contaService.consultarSaldo(id);
			return ResponseEntity.ok(String.format("Conta %d | Saldo: R$ %.2f", id, saldo));
		} catch (ContaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	// Issue #45 - PUT /banco/conta/{id}/credito
	@PutMapping("/{id}/credito")
	public ResponseEntity<String> credito(@PathVariable int id, @RequestBody ValorRequest req) {
		try {
			contaService.credito(id, req.getValor());
			return ResponseEntity.ok(contaService.consultarInfoConta(id));
		} catch (ContaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// Issue #46 - PUT /banco/conta/{id}/debito
	@PutMapping("/{id}/debito")
	public ResponseEntity<String> debito(@PathVariable int id, @RequestBody ValorRequest req) {
		try {
			contaService.debito(id, req.getValor());
			return ResponseEntity.ok(contaService.consultarInfoConta(id));
		} catch (ContaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}