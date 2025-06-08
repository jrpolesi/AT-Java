package com.jrpolesi.dto;

public record ToDoRequestDTO(
        String titulo,
        String descricao,
        boolean concluido
) {
}
