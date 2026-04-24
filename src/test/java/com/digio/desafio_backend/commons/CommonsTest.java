package com.digio.desafio_backend.commons;

import com.digio.desafio_backend.domain.Cliente;
import com.digio.desafio_backend.domain.Compra;
import com.digio.desafio_backend.domain.Produto;
import com.digio.desafio_backend.dto.ClienteDTO;
import com.digio.desafio_backend.dto.CompraDTO;
import com.digio.desafio_backend.dto.ProdutoDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CommonsTest {

    public static final String ENDPOINT_COMPRAS = "/v1/compras";
    public static final String ENDPOINT_MAIOR_COMPRA = "/v1/maior-compra/{ano}";
    public static final String ENDPOINT_CLIENTES_FIEIS = "/v1/clientes-fieis";
    public static final String ENDPOINT_RECOMENDACAO = "/v1/recomendacao/{clienteId}/tipo";

    public static final String PATH_NOME = "$.nome";
    public static final String PATH_CPF = "$.cpf";
    public static final String PATH_TIPO_VINHO = "$.tipo_vinho";
    public static final String PATH_CODIGO = "$.codigo";

    public static final String NOME_JOAO = "João Silva";
    public static final String CPF_JOAO = "11122233344";
    public static final String NOME_MARIA = "Maria Oliveira";
    public static final String CPF_MARIA = "55566677788";

    public static final Long COD_TINTO = 10L;
    public static final String TIPO_TINTO = "Tinto";
    public static final BigDecimal PRECO_TINTO = new BigDecimal("100.00");

    public static final Long COD_BRANCO = 20L;
    public static final String TIPO_BRANCO = "Branco";
    public static final BigDecimal PRECO_BRANCO = new BigDecimal("50.00");

    public static final Integer ANO_2021 = 2021;
    public static final Integer ANO_2022 = 2022;

    public static Produto criarProdutoTinto() {
        return new Produto(1L, COD_TINTO, TIPO_TINTO, PRECO_TINTO, 2019, ANO_2021);
    }

    public static Produto criarProdutoBranco() {
        return new Produto(2L, COD_BRANCO, TIPO_BRANCO, PRECO_BRANCO, 2020, ANO_2022);
    }

    public static Cliente criarClienteJoao() {
        Cliente cliente = new Cliente(1L, NOME_JOAO, CPF_JOAO, new ArrayList<>());
        cliente.setCompras(new ArrayList<>());
        cliente.getCompras().add(new Compra(1L, COD_TINTO, 2, cliente));
        return cliente;
    }

    public static Cliente criarClienteMaria() {
        Cliente cliente = new Cliente(2L, NOME_MARIA, CPF_MARIA, new ArrayList<>());
        cliente.setCompras(new ArrayList<>());
        cliente.getCompras().add(new Compra(2L, COD_BRANCO, 1, cliente));
        return cliente;
    }

    public static Cliente criarClienteFiel() {
        Cliente cliente = new Cliente(3L, "Carlos Rico", "333", new ArrayList<>());
        cliente.getCompras().add(new Compra(3L, COD_TINTO, 50, cliente));

        return cliente;
    }

    public static List<Produto> criarListaProdutosMock() {
        return List.of(criarProdutoTinto(), criarProdutoBranco());
    }

    public static List<Cliente> criarListaClientesMock() {
        return List.of(criarClienteJoao(), criarClienteMaria());
    }

    public static ClienteDTO criarClienteDTOJoao() {
        return ClienteDTO.builder()
                .nome(NOME_JOAO)
                .cpf(CPF_JOAO)
                .compras(List.of(CompraDTO.builder()
                        .codigo(COD_TINTO)
                        .quantidade(2)
                        .valorTotal(PRECO_TINTO.multiply(BigDecimal.valueOf(2)))
                        .build()))
                .build();
    }

    public static ProdutoDTO criarProdutoDTOTinto() {
        return ProdutoDTO.builder()
                .codigo(COD_TINTO)
                .tipoVinho(TIPO_TINTO)
                .preco(PRECO_TINTO)
                .safra(2019)
                .anoCompra(ANO_2021)
                .build();
    }
}