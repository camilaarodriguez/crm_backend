package com.crmapi.sistemacrm.controller;

import com.crmapi.sistemacrm.dto.mensagem.MensagemCreateDTO;
import com.crmapi.sistemacrm.dto.mensagem.MensagemResponseDTO;
import com.crmapi.sistemacrm.service.MensagemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/mensagens")
@RequiredArgsConstructor
public class MensagemController {

    private final MensagemService mensagemService;

    @PostMapping
    public ResponseEntity<MensagemResponseDTO> enviar(@Valid @RequestBody MensagemCreateDTO dto) {
        try {
            return ResponseEntity.status(201).body(mensagemService.enviar(dto));
        } catch (Exception e) {
            log.error("Erro no endpoint POST /api/mensagens: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/conversa/{conversaId}")
    public ResponseEntity<List<MensagemResponseDTO>> listarPorConversa(@PathVariable Long conversaId) {
        try {
            return ResponseEntity.ok(mensagemService.listarPorConversa(conversaId));
        } catch (Exception e) {
            log.error("Erro no endpoint GET /api/mensagens/conversa/{}: {}", conversaId, e.getMessage());
            throw e;
        }
    }
}