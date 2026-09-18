package com.crmapi.sistemacrm.controller;

import com.crmapi.sistemacrm.dto.usuario.UsuarioCreateDTO;
import com.crmapi.sistemacrm.dto.usuario.UsuarioResponseDTO;
import com.crmapi.sistemacrm.dto.usuario.UsuarioStatusDTO;
import com.crmapi.sistemacrm.dto.usuario.UsuarioUpdateDTO;
import com.crmapi.sistemacrm.exception.BusinessException;
import com.crmapi.sistemacrm.model.enums.UsuarioRole;
import com.crmapi.sistemacrm.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criar(@Valid @RequestBody UsuarioCreateDTO dto) {
        try {
            UsuarioResponseDTO criado = usuarioService.criar(dto);
            URI location = URI.create("/api/usuarios/" + criado.id());
            return ResponseEntity.created(location).body(criado);
        } catch (Exception e) {
            log.error("Erro no endpoint POST /api/usuarios: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioResponseDTO>> listar(
            @RequestParam(required = false) String busca,
            @RequestParam(required = false) UsuarioRole role,
            @RequestParam(required = false) Boolean ativo,
            Pageable pageable) {
        try {
            Page<UsuarioResponseDTO> pagina = usuarioService.listar(busca, role, ativo, pageable);
            return ResponseEntity.ok(pagina);
        } catch (Exception e) {
            log.error("Erro no endpoint GET /api/usuarios: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(usuarioService.buscarPorId(id));
        } catch (Exception e) {
            log.error("Erro no endpoint GET /api/usuarios/{}: {}", id, e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable Long id,
                                                        @Valid @RequestBody UsuarioUpdateDTO dto) {
        try {
            return ResponseEntity.ok(usuarioService.atualizar(id, dto));
        } catch (Exception e) {
            log.error("Erro no endpoint PUT /api/usuarios/{}: {}", id, e.getMessage());
            throw e;
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<UsuarioResponseDTO> atualizarStatus(@PathVariable Long id,
                                                              @Valid @RequestBody UsuarioStatusDTO dto) {
        try {
            return ResponseEntity.ok(usuarioService.atualizarStatus(id, dto));
        } catch (Exception e) {
            log.error("Erro no endpoint PATCH /api/usuarios/{}/status: {}", id, e.getMessage());
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            usuarioService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Erro no endpoint DELETE /api/usuarios/{}: {}", id, e.getMessage());
            throw e;
        }
    }
}