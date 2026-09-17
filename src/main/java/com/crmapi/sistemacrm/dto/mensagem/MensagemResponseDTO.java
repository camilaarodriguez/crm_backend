package com.crmapi.sistemacrm.dto.mensagem;

import com.crmapi.sistemacrm.model.enums.DirecaoMensagem;
import com.crmapi.sistemacrm.model.enums.StatusEntrega;
import com.crmapi.sistemacrm.model.enums.TipoMensagem;

import java.time.LocalDateTime;

public record MensagemResponseDTO(
        Long id,
        Long conversaId,
        DirecaoMensagem direcao,
        TipoMensagem tipo,
        String conteudo,
        String waMessageId,
        StatusEntrega statusEntrega,
        Long enviadaPorId,
        String enviadaPorNome,
        LocalDateTime criadoEm
) {
}