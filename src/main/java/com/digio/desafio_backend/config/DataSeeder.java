package com.digio.desafio_backend.config;

import com.digio.desafio_backend.domain.Cliente;
import com.digio.desafio_backend.domain.Compra;
import com.digio.desafio_backend.domain.Produto;
import com.digio.desafio_backend.repository.ClienteRepository;
import com.digio.desafio_backend.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;

    @Override
    public void run(String... args) throws Exception {
        if (produtoRepository.count() > 0) {
            log.info("Banco de dados já populado. Pulando a carga inicial.");
            return;
        }

        log.info("Iniciando a carga de dados oficial...");

        // 1. Populando Produtos (Vinhos)
        List<Produto> produtos = List.of(
                new Produto(null, 1L, "Tinto", new BigDecimal("229.99"), 2017, 2018),
                new Produto(null, 2L, "Branco", new BigDecimal("126.50"), 2018, 2019),
                new Produto(null, 3L, "Rosé", new BigDecimal("121.75"), 2019, 2020),
                new Produto(null, 4L, "Espumante", new BigDecimal("134.25"), 2020, 2021),
                new Produto(null, 5L, "Chardonnay", new BigDecimal("128.99"), 2021, 2022),
                new Produto(null, 6L, "Tinto", new BigDecimal("327.50"), 2016, 2017),
                new Produto(null, 7L, "Branco", new BigDecimal("125.25"), 2017, 2018),
                new Produto(null, 8L, "Rosé", new BigDecimal("120.99"), 2018, 2019),
                new Produto(null, 9L, "Espumante", new BigDecimal("135.50"), 2019, 2020),
                new Produto(null, 10L, "Chardonnay", new BigDecimal("130.75"), 2020, 2021),
                new Produto(null, 11L, "Tinto", new BigDecimal("128.99"), 2017, 2018),
                new Produto(null, 12L, "Branco", new BigDecimal("106.50"), 2018, 2019),
                new Produto(null, 13L, "Rosé", new BigDecimal("121.75"), 2019, 2020),
                new Produto(null, 14L, "Espumante", new BigDecimal("134.25"), 2020, 2021),
                new Produto(null, 15L, "Chardonnay", new BigDecimal("188.99"), 2021, 2022),
                new Produto(null, 16L, "Tinto", new BigDecimal("127.50"), 2016, 2017),
                new Produto(null, 17L, "Branco", new BigDecimal("125.25"), 2017, 2018),
                new Produto(null, 18L, "Rosé", new BigDecimal("120.99"), 2018, 2019),
                new Produto(null, 19L, "Espumante", new BigDecimal("135.50"), 2019, 2020),
                new Produto(null, 20L, "Chardonnay", new BigDecimal("130.75"), 2020, 2021)
        );
        produtoRepository.saveAll(produtos);

        // 2. Populando Clientes e Compras
        List<Cliente> clientes = new ArrayList<>();

        // Geraldo
        Cliente c1 = new Cliente(null, "Geraldo Pedro Julio Nascimento", "05870189179", new ArrayList<>());
        c1.getCompras().add(new Compra(null, 1L, 6, c1));
        c1.getCompras().add(new Compra(null, 15L, 4, c1));
        c1.getCompras().add(new Compra(null, 10L, 2, c1));
        c1.getCompras().add(new Compra(null, 5L, 3, c1));
        c1.getCompras().add(new Compra(null, 2L, 5, c1));
        clientes.add(c1);

        // Vitória
        Cliente c2 = new Cliente(null, "Vitória Alícia Mendes", "20623850567", new ArrayList<>());
        c2.getCompras().add(new Compra(null, 1L, 8, c2));
        clientes.add(c2);

        // Teresinha
        Cliente c3 = new Cliente(null, "Teresinha Daniela Galvão", "04372012950", new ArrayList<>());
        c3.getCompras().add(new Compra(null, 14L, 3, c3));
        c3.getCompras().add(new Compra(null, 20L, 3, c3));
        c3.getCompras().add(new Compra(null, 4L, 2, c3));
        clientes.add(c3);

        // Gabriel
        Cliente c4 = new Cliente(null, "Gabriel Rafael Dias", "85067950013", new ArrayList<>());
        c4.getCompras().add(new Compra(null, 17L, 6, c4));
        c4.getCompras().add(new Compra(null, 19L, 4, c4));
        clientes.add(c4);

        // Andreia
        Cliente c5 = new Cliente(null, "Andreia Emanuelly da Mata", "27737287426", new ArrayList<>());
        c5.getCompras().add(new Compra(null, 5L, 6, c5));
        c5.getCompras().add(new Compra(null, 4L, 4, c5));
        c5.getCompras().add(new Compra(null, 3L, 2, c5));
        c5.getCompras().add(new Compra(null, 17L, 3, c5));
        c5.getCompras().add(new Compra(null, 13L, 5, c5));
        c5.getCompras().add(new Compra(null, 14L, 5, c5));
        clientes.add(c5);

        // Natália
        Cliente c6 = new Cliente(null, "Natália Sandra da Cruz", "03763001590", new ArrayList<>());
        c6.getCompras().add(new Compra(null, 6L, 6, c6));
        c6.getCompras().add(new Compra(null, 4L, 4, c6));
        clientes.add(c6);

        // Catarina
        Cliente c7 = new Cliente(null, "Catarina Sebastiana Analu Almeida", "88901767767", new ArrayList<>());
        c7.getCompras().add(new Compra(null, 16L, 6, c7));
        c7.getCompras().add(new Compra(null, 2L, 4, c7));
        clientes.add(c7);

        // Hadassa
        Cliente c8 = new Cliente(null, "Hadassa Daniela Sales", "1051252612", new ArrayList<>());
        c8.getCompras().add(new Compra(null, 19L, 3, c8));
        c8.getCompras().add(new Compra(null, 17L, 3, c8));
        c8.getCompras().add(new Compra(null, 12L, 2, c8));
        clientes.add(c8);

        // Kaique
        Cliente c9 = new Cliente(null, "Kaique Danilo Alves", "20634031392", new ArrayList<>());
        c9.getCompras().add(new Compra(null, 8L, 3, c9));
        clientes.add(c9);

        // Pietra
        Cliente c10 = new Cliente(null, "Pietra Antônia Brenda Silva", "74302668512", new ArrayList<>());
        c10.getCompras().add(new Compra(null, 3L, 3, c10));
        clientes.add(c10);

        // Maitê
        Cliente c11 = new Cliente(null, "Maitê Kamilly Corte Real", "022484638124", new ArrayList<>());
        c11.getCompras().add(new Compra(null, 19L, 6, c11));
        c11.getCompras().add(new Compra(null, 15L, 4, c11));
        clientes.add(c11);

        // Isis
        Cliente c12 = new Cliente(null, "Isis Isis Ramos", "29457224965", new ArrayList<>());
        c12.getCompras().add(new Compra(null, 18L, 6, c12));
        c12.getCompras().add(new Compra(null, 1L, 4, c12));
        clientes.add(c12);

        // Fabiana
        Cliente c13 = new Cliente(null, "Fabiana Melissa Nunes", "824643755772", new ArrayList<>());
        c13.getCompras().add(new Compra(null, 18L, 2, c13));
        c13.getCompras().add(new Compra(null, 10L, 10, c13));
        clientes.add(c13);

        // Ian
        Cliente c14 = new Cliente(null, "Ian Joaquim Giovanni Santos", "96718391344", new ArrayList<>());
        c14.getCompras().add(new Compra(null, 15L, 6, c14));
        c14.getCompras().add(new Compra(null, 14L, 4, c14));
        c14.getCompras().add(new Compra(null, 3L, 20, c14));
        c14.getCompras().add(new Compra(null, 17L, 13, c14));
        c14.getCompras().add(new Compra(null, 2L, 15, c14));
        clientes.add(c14);

        clienteRepository.saveAll(clientes);

        log.info("Carga de dados concluída com sucesso! Total de clientes: {}", clienteRepository.count());
    }
}