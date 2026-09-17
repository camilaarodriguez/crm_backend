package com.crmapi.sistemacrm.controller;

import com.crmapi.sistemacrm.dto.mensagem.MensagemCreateDTO;
import com.crmapi.sistemacrm.dto.mensagem.MensagemResponseDTO;
import com.crmapi.sistemacrm.service.MensagemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mensagens")
@RequiredArgsConstructor
public class MensagemController {

    private final MensagemService mensagemService;

    @PostMapping
    public ResponseEntity<MensagemResponseDTO> enviar(@Valid @RequestBody MensagemCreateDTO dto) {
        return ResponseEntity.status(201).body(mensagemService.enviar(dto));
    }

    @GetMapping("/conversa/{conversaId}")
    public ResponseEntity<List<MensagemResponseDTO>> listarPorConversa(@PathVariable Long conversaId) {
        return ResponseEntity.ok(mensagemService.listarPorConversa(conversaId));
    }
}