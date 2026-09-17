package com.crmapi.sistemacrm.dto.mensagem;

import com.crmapi.sistemacrm.model.enums.TipoMensagem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MensagemCreateDTO(

        @NotNull(message = "O conversaId e obrigatorio")
        Long conversaId,

        @NotNull(message = "O tipo e obrigatorio")
        TipoMensagem tipo,

        @NotBlank(message = "O conteudo e obrigatorio")
        String conteudo
) {
}