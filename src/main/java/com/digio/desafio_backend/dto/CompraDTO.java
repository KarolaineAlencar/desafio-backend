package com.digio.desafio_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompraDTO {

    @NotNull
    private Long codigo;

    @NotNull
    private Integer quantidade;
}
