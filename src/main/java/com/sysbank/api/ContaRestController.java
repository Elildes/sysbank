package com.sysbank.api;

import com.sysbank.dto.CadastrarContaRequest;
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
}