package com.crmapi.sistemacrm.controller;

import com.crmapi.sistemacrm.dto.conversa.ConversaResponseDTO;
import com.crmapi.sistemacrm.service.ConversaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/conversas")
@RequiredArgsConstructor
public class ConversaController {

    private final ConversaService conversaService;

    @GetMapping
    public ResponseEntity<Page<ConversaResponseDTO>> listar(
            @RequestParam(required = false) Long vendedorId,
            Pageable pageable) {
        try {
            return ResponseEntity.ok(conversaService.listar(vendedorId, pageable));
        } catch (Exception e) {
            log.error("Erro no endpoint GET /api/conversas: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConversaResponseDTO> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(conversaService.buscarPorId(id));
        } catch (Exception e) {
            log.error("Erro no endpoint GET /api/conversas/{}: {}", id, e.getMessage());
            throw e;
        }
    }
}