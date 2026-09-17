package com.crmapi.sistemacrm.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClienteUpdateDTO(

        @NotBlank(message = "O nome e obrigatorio")
        @Size(max = 150, message = "O nome deve ter no maximo 150 caracteres")
        String nome,

        @Email(message = "O email informado e invalido")
        String email,

        @NotBlank(message = "O telefone e obrigatorio")
        @Size(max = 20, message = "O telefone deve ter no maximo 20 caracteres")
        String telefone,

        String documento,

        String empresa,

        String observacoes,

        @NotNull(message = "O vendedorId e obrigatorio")
        Long vendedorId
) {
}