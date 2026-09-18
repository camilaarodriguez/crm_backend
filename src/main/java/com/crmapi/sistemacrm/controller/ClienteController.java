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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> criar(@Valid @RequestBody ClienteCreateDTO dto) {
        ClienteResponseDTO criado = clienteService.criar(dto);
        URI location = URI.create("/api/clientes/" + criado.id());
        return ResponseEntity.created(location).body(criado);
    }

    @GetMapping
    public ResponseEntity<Page<ClienteResponseDTO>> listar(
            @RequestParam(required = false) String busca,
            @RequestParam(required = false) StatusFunil status,
            @RequestParam(required = false) Long vendedorId,
            @RequestParam(required = false) Boolean incluirInativos,
            Pageable pageable) {
        Page<ClienteResponseDTO> pagina = clienteService.listar(busca, status, vendedorId, incluirInativos, pageable);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long id,
                                                        @Valid @RequestBody ClienteUpdateDTO dto) {
        return ResponseEntity.ok(clienteService.atualizar(id, dto));
    }

    @PatchMapping("/{id}/status-funil")
    public ResponseEntity<ClienteResponseDTO> atualizarStatusFunil(@PathVariable Long id,
                                                                   @Valid @RequestBody ClienteStatusFunilDTO dto) {
        return ResponseEntity.ok(clienteService.atualizarStatusFunil(id, dto));
    }

    @PatchMapping("/{id}/reatribuir")
    public ResponseEntity<ClienteResponseDTO> reatribuir(@PathVariable Long id,
                                                         @Valid @RequestBody ClienteReatribuirDTO dto) {
        return ResponseEntity.ok(clienteService.reatribuir(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}