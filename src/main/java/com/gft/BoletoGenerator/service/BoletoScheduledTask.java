package com.gft.BoletoGenerator.service;

import com.gft.BoletoGenerator.model.Boleto;
import com.gft.BoletoGenerator.repository.BoletoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
public class BoletoScheduledTask {

    @Autowired
    private BoletoRepository boletoRepository;

    @Scheduled(cron = "0 0 1 * * *")
    @Transactional
    public void validarEBoletoParaVencido() {
        log.info("Iniciando validação de boletos para alterar status para VENCIDO.");

        List<Boleto> boletosPendentes = boletoRepository.findByStatusBoleto(Boleto.StatusBoleto.PENDENTE);
        LocalDate hoje = LocalDate.now();

        for (Boleto boleto : boletosPendentes) {
            if (boleto.getDataVencimento().isBefore(hoje)) {
                boleto.setStatus(Boleto.StatusBoleto.VENCIDO);
                boletoRepository.save(boleto);
                log.info("Boleto com ID: {} atualizado para VENCIDO.", boleto.getId());
            }
        }

        log.info("Validação de boletos concluída.");
    }
}