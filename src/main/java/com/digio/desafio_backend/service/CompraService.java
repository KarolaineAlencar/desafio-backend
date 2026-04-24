package com.digio.desafio_backend.service;

import com.digio.desafio_backend.domain.Cliente;
import com.digio.desafio_backend.domain.Compra;
import com.digio.desafio_backend.domain.Produto;
import com.digio.desafio_backend.dto.ClienteDTO;
import com.digio.desafio_backend.dto.CompraDTO;
import com.digio.desafio_backend.dto.ProdutoDTO;
import com.digio.desafio_backend.exception.BusinessException;
import com.digio.desafio_backend.exception.MessageError;
import com.digio.desafio_backend.exception.ResourceNotFoundException;
import com.digio.desafio_backend.repository.ClienteRepository;
import com.digio.desafio_backend.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.digio.desafio_backend.exception.MessageError.CLIENTE_NAO_ENCONTRADO;
import static com.digio.desafio_backend.exception.MessageError.CLIENTE_SEM_HISTORICO;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CompraService {

    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public List<ClienteDTO> listarComprasOrdenadas() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<Produto> produtos = produtoRepository.findAll();

        return clientes.stream()
                .map(cliente -> mapToDTO(cliente, produtos))
                .sorted(Comparator.comparing(this::calcularValorTotalCliente))
                .toList();
    }

    public ClienteDTO buscarMaiorCompraPorAno(Integer ano) {
        List<Cliente> clientes = clienteRepository.findAll();
        List<Produto> produtos = produtoRepository.findAll();

        var maiorVendaEncontrada = clientes.stream()
                .flatMap(cliente -> cliente.getCompras().stream()
                        .map(compra -> {
                            Produto p = produtos.stream()
                                    .filter(prod -> prod.getCodigo().equals(compra.getCodigo()))
                                    .findFirst().orElse(null);

                            if (p == null || !p.getAnoCompra().equals(ano)) return null;

                            BigDecimal valorTotal = p.getPreco().multiply(BigDecimal.valueOf(compra.getQuantidade()));

                            return new Object() {
                                final Cliente dono = cliente;
                                final Compra dadosCompra = compra;
                                final BigDecimal total = valorTotal;
                            };
                        }))
                .filter(Objects::nonNull)
                .max(Comparator.comparing(obj -> obj.total))
                .orElseThrow(() -> new ResourceNotFoundException(MessageError.SEM_COMPRAS_ANO.getFormattedMessage(ano)));

        CompraDTO compraDTO = CompraDTO.builder()
                .codigo(maiorVendaEncontrada.dadosCompra.getCodigo())
                .quantidade(maiorVendaEncontrada.dadosCompra.getQuantidade())
                .valorTotal(maiorVendaEncontrada.total)
                .build();

        return ClienteDTO.builder()
                .nome(maiorVendaEncontrada.dono.getNome())
                .cpf(maiorVendaEncontrada.dono.getCpf())
                .compras(List.of(compraDTO))
                .build();
    }

    public List<ClienteDTO> listarClientesFieis() {
        log.info("Calculando nossos Top 3 clientes mais fieis!");
        List<Cliente> clientes = clienteRepository.findAll();
        List<Produto> produtos = produtoRepository.findAll();

        return clientes.stream()
                .sorted((c1, c2) -> {
                    BigDecimal totalC1 = calcularGastoTotal(c1, produtos);
                    BigDecimal totalC2 = calcularGastoTotal(c2, produtos);
                    return totalC2.compareTo(totalC1);
                })
                .limit(3)
                .map(cliente -> mapToDTO(cliente, produtos))
                .toList();
    }

    public ProdutoDTO buscarRecomendacaoVinho(Long clienteId) {
        log.info("Gerando recomendação de vinho para o cliente ID: {}", clienteId);
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException(CLIENTE_NAO_ENCONTRADO.getFormattedMessage(clienteId)));

        List<Produto> todosProdutos = produtoRepository.findAll();

        Map<String, Integer> consumoPorTipo = cliente.getCompras().stream()
                .map(compra -> todosProdutos.stream()
                        .filter(p -> p.getCodigo().equals(compra.getCodigo()))
                        .findFirst().orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        Produto::getTipoVinho,
                        Collectors.summingInt(p -> 1)
                ));

        String tipoMaisComprado = consumoPorTipo.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new BusinessException(CLIENTE_SEM_HISTORICO.getMessage()));

        Produto vinhoRecomendado = todosProdutos.stream()
                .filter(p -> p.getTipoVinho().equalsIgnoreCase(tipoMaisComprado))
                .min(Comparator.comparing(Produto::getCodigo))
                .orElseThrow(() -> new ResourceNotFoundException(MessageError.PRODUTO_NAO_ENCONTRADO.getFormattedMessage(tipoMaisComprado)));
        return converterParaProdutoDTO(vinhoRecomendado);
    }

    private ClienteDTO mapToDTO(Cliente cliente, List<Produto> produtos) {
        List<CompraDTO> comprasDTO = cliente.getCompras().stream()
                .map(compra -> {
                    BigDecimal preco = produtos.stream()
                            .filter(p -> p.getCodigo().equals(compra.getCodigo()))
                            .map(Produto::getPreco)
                            .findFirst()
                            .orElse(BigDecimal.ZERO);

                    return CompraDTO.builder()
                            .codigo(compra.getCodigo())
                            .quantidade(compra.getQuantidade())
                            .valorTotal(preco.multiply(BigDecimal.valueOf(compra.getQuantidade())))
                            .build();
                })
                .toList();

        return ClienteDTO.builder()
                .nome(cliente.getNome())
                .cpf(cliente.getCpf())
                .compras(comprasDTO)
                .build();
    }

    private BigDecimal calcularValorTotalCliente(ClienteDTO dto) {
        return dto.getCompras().stream()
                .map(CompraDTO::getValorTotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calcularGastoTotal(Cliente cliente, List<Produto> produtos) {
        return cliente.getCompras().stream()
                .map(compra -> {
                    Produto produto = produtos.stream()
                            .filter(prod -> prod.getCodigo().equals(compra.getCodigo()))
                            .findFirst()
                            .orElse(null);

                    if (produto == null) return BigDecimal.ZERO;
                    return produto.getPreco().multiply(BigDecimal.valueOf(compra.getQuantidade()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private ProdutoDTO converterParaProdutoDTO(Produto p) {
        return ProdutoDTO.builder()
                .codigo(p.getCodigo())
                .tipoVinho(p.getTipoVinho())
                .preco(p.getPreco())
                .safra(p.getSafra())
                .anoCompra(p.getAnoCompra())
                .build();
    }
}