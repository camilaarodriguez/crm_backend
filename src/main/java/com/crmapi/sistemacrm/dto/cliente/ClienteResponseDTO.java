package com.crmapi.sistemacrm.dto.cliente;

import com.crmapi.sistemacrm.model.enums.StatusFunil;

import java.time.LocalDateTime;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        String documento,
        String empresa,
        String observacoes,
        Long vendedorId,
        String vendedorNome,
        StatusFunil statusFunil,
        Boolean ativo,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {
}