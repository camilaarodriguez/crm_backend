package com.crmapi.sistemacrm.dto.conversa;

import com.crmapi.sistemacrm.model.enums.StatusConversa;

import java.time.LocalDateTime;

public record ConversaResponseDTO(
        Long id,
        Long clienteId,
        String clienteNome,
        Long vendedorId,
        String vendedorNome,
        StatusConversa status,
        Integer naoLidas,
        LocalDateTime ultimaMensagemEm,
        LocalDateTime janelaExpiraEm,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {
}