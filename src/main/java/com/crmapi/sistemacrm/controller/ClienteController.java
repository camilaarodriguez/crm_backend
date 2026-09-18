package com.crmapi.sistemacrm.controller;

import com.crmapi.sistemacrm.dto.cliente.ClienteCreateDTO;
import com.crmapi.sistemacrm.dto.cliente.ClienteReatribuirDTO;
import com.crmapi.sistemacrm.dto.cliente.ClienteResponseDTO;
import com.crmapi.sistemacrm.dto.cliente.ClienteStatusFunilDTO;
import com.crmapi.sistemacrm.dto.cliente.ClienteUpdateDTO;
import com.crmapi.sistemacrm.model.enums.StatusFunil;
import com.crmapi.sistemacrm.service.ClienteService;
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
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> criar(@Valid @RequestBody ClienteCreateDTO dto) {
        try {
            ClienteResponseDTO criado = clienteService.criar(dto);
            URI location = URI.create("/api/clientes/" + criado.id());
            return ResponseEntity.created(location).body(criado);
        } catch (Exception e) {
            log.error("Erro no endpoint POST /api/clientes: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<Page<ClienteResponseDTO>> listar(
            @RequestParam(required = false) String busca,
            @RequestParam(required = false) StatusFunil status,
            @RequestParam(required = false) Long vendedorId,
            @RequestParam(required = false) Boolean incluirInativos,
            Pageable pageable) {
        try {
            Page<ClienteResponseDTO> pagina = clienteService.listar(busca, status, vendedorId, incluirInativos, pageable);
            return ResponseEntity.ok(pagina);
        } catch (Exception e) {
            log.error("Erro no endpoint GET /api/clientes: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(clienteService.buscarPorId(id));
        } catch (Exception e) {
            log.error("Erro no endpoint GET /api/clientes/{}: {}", id, e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long id,
                                                        @Valid @RequestBody ClienteUpdateDTO dto) {
        try {
            return ResponseEntity.ok(clienteService.atualizar(id, dto));
        } catch (Exception e) {
            log.error("Erro no endpoint PUT /api/clientes/{}: {}", id, e.getMessage());
            throw e;
        }
    }

    @PatchMapping("/{id}/status-funil")
    public ResponseEntity<ClienteResponseDTO> atualizarStatusFunil(@PathVariable Long id,
                                                                   @Valid @RequestBody ClienteStatusFunilDTO dto) {
        try {
            return ResponseEntity.ok(clienteService.atualizarStatusFunil(id, dto));
        } catch (Exception e) {
            log.error("Erro no endpoint PATCH /api/clientes/{}/status-funil: {}", id, e.getMessage());
            throw e;
        }
    }

    @PatchMapping("/{id}/reatribuir")
    public ResponseEntity<ClienteResponseDTO> reatribuir(@PathVariable Long id,
                                                         @Valid @RequestBody ClienteReatribuirDTO dto) {
        try {
            return ResponseEntity.ok(clienteService.reatribuir(id, dto));
        } catch (Exception e) {
            log.error("Erro no endpoint PATCH /api/clientes/{}/reatribuir: {}", id, e.getMessage());
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            clienteService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Erro no endpoint DELETE /api/clientes/{}: {}", id, e.getMessage());
            throw e;
        }
    }
}