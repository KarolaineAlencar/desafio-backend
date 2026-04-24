package com.digio.desafio_backend.service;

import com.digio.desafio_backend.domain.Cliente;
import com.digio.desafio_backend.dto.ClienteDTO;
import com.digio.desafio_backend.dto.ProdutoDTO;
import com.digio.desafio_backend.repository.ClienteRepository;
import com.digio.desafio_backend.repository.ProdutoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.digio.desafio_backend.commons.CommonsTest.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompraServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private CompraService compraService;

    @Test
    @DisplayName("Deve listar compras ordenadas por valor crescente (Maria 50.00 antes de João 200.00)")
    void deveListarComprasOrdenadas() {
        when(clienteRepository.findAll()).thenReturn(criarListaClientesMock());
        when(produtoRepository.findAll()).thenReturn(criarListaProdutosMock());

        List<ClienteDTO> resultado = compraService.listarComprasOrdenadas();

        assertEquals(NOME_MARIA, resultado.get(0).getNome());
        assertEquals(NOME_JOAO, resultado.get(1).getNome());
    }

    @Test
    @DisplayName("Deve retornar o Top 3 clientes fiéis em ordem decrescente de gasto")
    void deveRetornarTop3ClientesFieis() {
        List<Cliente> baseClientes = new ArrayList<>(criarListaClientesMock());
        baseClientes.add(criarClienteFiel());

        when(clienteRepository.findAll()).thenReturn(baseClientes);
        when(produtoRepository.findAll()).thenReturn(criarListaProdutosMock());

        List<ClienteDTO> resultado = compraService.listarClientesFieis();

        assertEquals("Carlos Rico", resultado.getFirst().getNome(), "O maior gasto deve estar em primeiro");
        assertTrue(resultado.size() <= 3);
    }

    @Test
    @DisplayName("Deve encontrar a maior compra do ano de 2021")
    void deveBuscarMaiorCompraPorAno() {
        when(clienteRepository.findAll()).thenReturn(criarListaClientesMock());
        when(produtoRepository.findAll()).thenReturn(criarListaProdutosMock());

        ClienteDTO resultado = compraService.buscarMaiorCompraPorAno(ANO_2021);

        assertNotNull(resultado);
        assertEquals(NOME_JOAO, resultado.getNome());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o ano pesquisado não possuir registros")
    void deveFalharParaAnoInexistente() {
        when(clienteRepository.findAll()).thenReturn(criarListaClientesMock());
        when(produtoRepository.findAll()).thenReturn(criarListaProdutosMock());

        assertThrows(RuntimeException.class, () -> compraService.buscarMaiorCompraPorAno(1990));
    }

    @Test
    @DisplayName("Deve recomendar vinho baseado no tipo mais frequente do histórico")
    void deveRecomendarVinhoCerto() {
        Cliente joao = criarClienteJoao();
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(joao));
        when(produtoRepository.findAll()).thenReturn(criarListaProdutosMock());

        ProdutoDTO recomendacao = compraService.buscarRecomendacaoVinho(1L);

        assertEquals(TIPO_TINTO, recomendacao.getTipoVinho());
        assertEquals(COD_TINTO, recomendacao.getCodigo());
    }
}