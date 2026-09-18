package com.crmapi.sistemacrm.model;

import com.crmapi.sistemacrm.model.enums.StatusFunil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false, unique = true, length = 20)
    private String telefone;

    @Column(unique = true, length = 18)
    private String documento;

    @Column(length = 150)
    private String empresa;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendedor_id")
    private Usuario vendedor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_funil", nullable = false, length = 20)
    private StatusFunil statusFunil;

    @Column(nullable = false)
    @Builder.Default
    private Boolean ativo = true;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Conversa> conversas = new ArrayList<>();

    @PrePersist
    protected void aoPersistir() {
        this.criadoEm = LocalDateTime.now();
        this.atualizadoEm = LocalDateTime.now();
        if (this.ativo == null) {
            this.ativo = true;
        }
        if (this.statusFunil == null) {
            this.statusFunil = StatusFunil.NOVO;
        }
    }

    @PreUpdate
    protected void aoAtualizar() {
        this.atualizadoEm = LocalDateTime.now();
    }
}