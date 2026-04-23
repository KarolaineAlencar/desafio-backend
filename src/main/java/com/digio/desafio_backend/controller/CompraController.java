package com.digio.desafio_backend.controller;

import com.digio.desafio_backend.dto.ClienteFielDTO;
import com.digio.desafio_backend.dto.CompraDTO;
import com.digio.desafio_backend.dto.TipoVinhoDTO;
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
    public ResponseEntity<List<CompraDTO>> listarComprasOrdenadas() {
        return ResponseEntity.ok(compraService.listarComprasOrdenadas());
    }

    @GetMapping("/maior-compra/{ano}")
    public ResponseEntity<CompraDTO> buscarMaiorCompraPorAno(
            @PathVariable Integer ano) {

        return ResponseEntity.ok(compraService.buscarMaiorCompraPorAno(ano));
    }

    @GetMapping("/clientes-fieis")
    public ResponseEntity<List<ClienteFielDTO>> listarClientesFieis() {

        return ResponseEntity.ok(compraService.listarClientesFieis());
    }

    @GetMapping("/recomendacao/{clienteId}/tipo")
    public ResponseEntity<TipoVinhoDTO> buscarRecomendacaoVinho(
            @PathVariable Long clienteId) {

        return ResponseEntity.ok(compraService.buscarRecomendacaoVinho(clienteId));
    }
}
