package com.digio.desafio_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    @NotBlank
    private String nome;

    @NotBlank
    @Pattern(regexp = "\\d+", message = "O campo deve conter apenas números")
    @Size(min = 11, max = 11, message = "O CPF deve conter 11 dígitos numéricos")
    private String cpf;

    @NotNull
    private List<CompraDTO> compras;
}
