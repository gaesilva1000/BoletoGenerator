package com.gft.BoletoGenerator.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Boleto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pessoa_id", nullable = false)
    private Long pessoaId;

    @Column(nullable = false)
    private BigDecimal valorDocumento;

    @Column(nullable = false)
    private LocalDate dataVencimento;

    private BigDecimal valorPago;

    private LocalDate dataPagamento;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusBoleto status;

    public enum StatusBoleto {
        PENDENTE,
        PAGO,
        VENCIDO
    }
}
