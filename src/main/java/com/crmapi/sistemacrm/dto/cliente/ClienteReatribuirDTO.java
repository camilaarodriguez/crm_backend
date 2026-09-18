package com.crmapi.sistemacrm.dto.cliente;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClienteReatribuirDTO(

        @NotNull(message = "O novoVendedorId e obrigatorio")
        Long novoVendedorId,

        @Size(max = 255, message = "O motivo deve ter no maximo 255 caracteres")
        String motivo
) {
}