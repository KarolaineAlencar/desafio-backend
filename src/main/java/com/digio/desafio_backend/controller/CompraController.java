package com.digio.desafio_backend.controller;

import com.digio.desafio_backend.dto.ClienteDTO;
import com.digio.desafio_backend.dto.ProdutoDTO;
import com.digio.desafio_backend.service.CompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;

    @GetMapping("/compras")
    public ResponseEntity<List<ClienteDTO>> listarComprasOrdenadas() {
        return ResponseEntity.ok(compraService.listarComprasOrdenadas());
    }

    @GetMapping("/maior-compra/{ano}")
    public ResponseEntity<ClienteDTO> buscarMaiorCompraPorAno(
            @PathVariable Integer ano) {

        return ResponseEntity.ok(compraService.buscarMaiorCompraPorAno(ano));
    }

    @GetMapping("/clientes-fieis")
    public ResponseEntity<List<ClienteDTO>> listarClientesFieis() {

        return ResponseEntity.ok(compraService.listarClientesFieis());
    }

    @GetMapping("/recomendacao/{clienteId}/tipo")
    public ResponseEntity<ProdutoDTO> buscarRecomendacaoVinho(
            @PathVariable Long clienteId) {

        return ResponseEntity.ok(compraService.buscarRecomendacaoVinho(clienteId));
    }
}
