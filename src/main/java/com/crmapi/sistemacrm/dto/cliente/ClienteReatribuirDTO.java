package com.crmapi.sistemacrm.dto.cliente;

import jakarta.validation.constraints.NotNull;

public record ClienteReatribuirDTO(

        @NotNull(message = "O novoVendedorId e obrigatorio")
        Long novoVendedorId,

        String motivo
) {
}