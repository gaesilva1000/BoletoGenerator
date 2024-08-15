package com.gft.BoletoGenerator.controller;

import com.gft.BoletoGenerator.model.Boleto;
import com.gft.BoletoGenerator.service.BoletoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/boletos")

public class BoletoController {

    private final BoletoService boletoService;

    public BoletoController(BoletoService boletoService) {
        this.boletoService = boletoService;
    }

    @PostMapping
    public ResponseEntity<Boleto> criarBoleto(@RequestBody  Boleto boleto) {
        Boleto novoBoleto = boletoService.criarBoleto(boleto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoBoleto);
    }

    @GetMapping("/pessoas/{pessoaId}/boletos")
    public ResponseEntity<List<Boleto>> buscarBoletosPorPessoaId(@PathVariable Long pessoaId) {
        List<Boleto> boletos = boletoService.buscarBoletosPorPessoaId(pessoaId);
        return ResponseEntity.ok(boletos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Boleto> buscarBoletoPorId(@PathVariable Long id) {
        return boletoService.buscarBoletoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/pagamento")
    public ResponseEntity<Boleto> pagarBoleto(@PathVariable Long id,
                                              @RequestParam BigDecimal valorPago,
                                              @RequestParam LocalDate dataPagamento) {
        Boleto boletoPago = boletoService.pagarBoleto(id, valorPago, dataPagamento);
        return ResponseEntity.ok(boletoPago);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirBoleto(@PathVariable Long id) {
        boletoService.excluirBoleto(id);
        return ResponseEntity.noContent().build();
    }
}
