package com.digio.desafio_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    @NotNull
    private Long codigo;

    @NotBlank
    @JsonProperty("tipo_vinho")
    private String tipoVinho;

    @NotNull
    @Positive
    @DecimalMin("0.01")
    private BigDecimal preco;

    @Min(1900)
    @Max(2026)
    @NotNull
    private Integer safra;

    @Min(1900)
    @Max(2026)
    @NotNull
    @JsonProperty("ano_compra")
    private Integer anoCompra;
}
