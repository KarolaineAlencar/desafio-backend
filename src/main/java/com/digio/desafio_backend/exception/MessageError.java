package com.digio.desafio_backend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageError {

    CLIENTE_NAO_ENCONTRADO("Cliente não encontrado com o ID: %s"),
    PRODUTO_NAO_ENCONTRADO("Produto com código %s não disponível no estoque."),
    SEM_COMPRAS_ANO("Nenhuma compra encontrada para o ano: %s"),
    CLIENTE_SEM_HISTORICO("O cliente não possui histórico de compras para recomendação."),
    ERRO_INTERNO("Ocorreu um erro inesperado no sistema.");

    private final String message;

    public String getFormattedMessage(Object... args) {
        return String.format(this.message, args);
    }
}