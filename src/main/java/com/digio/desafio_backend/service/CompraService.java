package com.digio.desafio_backend.service;

import com.digio.desafio_backend.dto.ClienteFielDTO;
import com.digio.desafio_backend.dto.CompraDTO;
import com.digio.desafio_backend.dto.TipoVinhoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompraService {
    public List<CompraDTO> listarComprasOrdenadas() {
        return List.of();
    }

    public CompraDTO buscarMaiorCompraPorAno(Integer ano) {
        return new CompraDTO();
    }

    public List<ClienteFielDTO> listarClientesFieis() {
        return List.of();
    }

    public TipoVinhoDTO buscarRecomendacaoVinho(Long clienteId) {
        return new TipoVinhoDTO();
    }
}
