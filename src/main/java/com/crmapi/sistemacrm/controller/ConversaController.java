package com.crmapi.sistemacrm.controller;

import com.crmapi.sistemacrm.dto.conversa.ConversaResponseDTO;
import com.crmapi.sistemacrm.service.ConversaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conversas")
@RequiredArgsConstructor
public class ConversaController {

    private final ConversaService conversaService;

    @GetMapping
    public ResponseEntity<Page<ConversaResponseDTO>> listar(
            @RequestParam(required = false) Long vendedorId,
            Pageable pageable) {
        return ResponseEntity.ok(conversaService.listar(vendedorId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConversaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(conversaService.buscarPorId(id));
    }
}