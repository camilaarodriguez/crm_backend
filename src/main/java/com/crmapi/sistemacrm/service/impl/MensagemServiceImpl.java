package com.crmapi.sistemacrm.service.impl;

import com.crmapi.sistemacrm.dto.mensagem.MensagemCreateDTO;
import com.crmapi.sistemacrm.dto.mensagem.MensagemResponseDTO;
import com.crmapi.sistemacrm.exception.ResourceNotFoundException;
import com.crmapi.sistemacrm.mapper.MensagemMapper;
import com.crmapi.sistemacrm.model.Conversa;
import com.crmapi.sistemacrm.model.Mensagem;
import com.crmapi.sistemacrm.model.enums.DirecaoMensagem;
import com.crmapi.sistemacrm.model.enums.StatusEntrega;
import com.crmapi.sistemacrm.repository.ConversaRepository;
import com.crmapi.sistemacrm.repository.MensagemRepository;
import com.crmapi.sistemacrm.service.MensagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MensagemServiceImpl implements MensagemService {

    private final MensagemRepository mensagemRepository;
    private final ConversaRepository conversaRepository;
    private final MensagemMapper mensagemMapper;

    @Override
    public MensagemResponseDTO enviar(MensagemCreateDTO dto) {
        Conversa conversa = conversaRepository.findById(dto.conversaId())
                .orElseThrow(() -> new ResourceNotFoundException("Conversa nao encontrada com o id: " + dto.conversaId()));

        Mensagem mensagem = Mensagem.builder()
                .conversa(conversa)
                .direcao(DirecaoMensagem.SAIDA)
                .tipo(dto.tipo())
                .conteudo(dto.conteudo())
                .statusEntrega(StatusEntrega.ENVIADA)
                .enviadaPor(conversa.getVendedor())
                .build();

        Mensagem salva = mensagemRepository.save(mensagem);

        conversa.setUltimaMensagemEm(LocalDateTime.now());
        conversaRepository.save(conversa);

        return mensagemMapper.paraResponseDTO(salva);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MensagemResponseDTO> listarPorConversa(Long conversaId) {
        return mensagemRepository.findByConversaIdOrderByCriadoEmAsc(conversaId).stream()
                .map(mensagemMapper::paraResponseDTO)
                .toList();
    }
}