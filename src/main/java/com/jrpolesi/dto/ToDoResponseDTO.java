package com.jrpolesi.dto;

public record ToDoResponseDTO(
        String id,
        String titulo,
        String descricao,
        boolean concluido,
        String dataCriacao
) {
}
