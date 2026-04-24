package com.digio.desafio_backend.controller;

import com.digio.desafio_backend.dto.ClienteDTO;
import com.digio.desafio_backend.dto.ProdutoDTO;
import com.digio.desafio_backend.service.CompraService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.digio.desafio_backend.commons.CommonsTest.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CompraController.class)
@DisplayName("Testes de Endpoint - CompraController")
public class CompraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CompraService compraService;

    @Test
    @DisplayName("GET /compras - Deve retornar lista de compras usando constantes de rota e path")
    void deveListarComprasOrdenadas() throws Exception {
        ClienteDTO joaoDto = criarClienteDTOJoao();

        Mockito.when(compraService.listarComprasOrdenadas()).thenReturn(List.of(joaoDto));

        mockMvc.perform(get(ENDPOINT_COMPRAS))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nome").value(NOME_JOAO))
                .andExpect(jsonPath("$[0].compras[0].valorTotal").value(200.00));
    }

    @Test
    @DisplayName("GET /maior-compra/{ano} - Deve buscar maior compra usando constante de rota")
    void deveBuscarMaiorCompraPorAno() throws Exception {
        ClienteDTO joaoDto = criarClienteDTOJoao();

        Mockito.when(compraService.buscarMaiorCompraPorAno(ANO_2021)).thenReturn(joaoDto);

        mockMvc.perform(get(ENDPOINT_MAIOR_COMPRA, ANO_2021))
                .andExpect(status().isOk())
                .andExpect(jsonPath(PATH_NOME).value(NOME_JOAO))
                .andExpect(jsonPath(PATH_CPF).value(CPF_JOAO));
    }

    @Test
    @DisplayName("GET /clientes-fieis - Deve validar retorno de lista usando constantes")
    void deveListarClientesFieis() throws Exception {
        Mockito.when(compraService.listarClientesFieis()).thenReturn(List.of(criarClienteDTOJoao()));

        mockMvc.perform(get(ENDPOINT_CLIENTES_FIEIS))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nome").value(NOME_JOAO));
    }

    @Test
    @DisplayName("GET /recomendacao - Deve validar campos do JSON usando caminhos da Commons")
    void deveBuscarRecomendacaoVinho() throws Exception {
        Long id = 1L;
        ProdutoDTO tintoDto = criarProdutoDTOTinto();

        Mockito.when(compraService.buscarRecomendacaoVinho(id)).thenReturn(tintoDto);

        mockMvc.perform(get(ENDPOINT_RECOMENDACAO, id))
                .andExpect(status().isOk())
                .andExpect(jsonPath(PATH_TIPO_VINHO).value(TIPO_TINTO))
                .andExpect(jsonPath(PATH_CODIGO).value(COD_TINTO));
    }
}