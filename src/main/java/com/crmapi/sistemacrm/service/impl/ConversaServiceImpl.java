package com.crmapi.sistemacrm.service.impl;

import com.crmapi.sistemacrm.dto.conversa.ConversaResponseDTO;
import com.crmapi.sistemacrm.exception.ResourceNotFoundException;
import com.crmapi.sistemacrm.mapper.ConversaMapper;
import com.crmapi.sistemacrm.model.Conversa;
import com.crmapi.sistemacrm.repository.ConversaRepository;
import com.crmapi.sistemacrm.service.ConversaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConversaServiceImpl implements ConversaService {

    private final ConversaRepository conversaRepository;
    private final ConversaMapper conversaMapper;

    @Override
    public Page<ConversaResponseDTO> listar(Long vendedorId, Pageable pageable) {
        Page<Conversa> pagina = (vendedorId != null)
                ? conversaRepository.findByVendedorId(vendedorId, pageable)
                : conversaRepository.findAll(pageable);
        return pagina.map(conversaMapper::paraResponseDTO);
    }

    @Override
    public ConversaResponseDTO buscarPorId(Long id) {
        Conversa conversa = conversaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conversa nao encontrada com o id: " + id));
        return conversaMapper.paraResponseDTO(conversa);
    }
}