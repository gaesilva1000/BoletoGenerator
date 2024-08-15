package com.gft.BoletoGenerator.service;

import com.gft.BoletoGenerator.model.Boleto;
import com.gft.BoletoGenerator.repository.BoletoRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BoletoService {

    @Autowired
    private BoletoRepository boletoRepository;

    public Boleto criarBoleto(Boleto boleto) {
        log.info("Criando boleto para a pessoa com ID: {}", boleto.getPessoaId());

        if (boletoRepository.existsById(boleto.getId())) {
            throw new IllegalArgumentException("Boleto com ID " + boleto.getId() + " já existe");
        }

        Boleto criado = boletoRepository.save(boleto);
        log.info("Boleto criado com sucesso. ID do boleto: {}", criado.getId());
        return criado;
    }

    public List<Boleto> buscarBoletosPorPessoaId(Long pessoaId) {
        log.info("Buscando boletos para a pessoa com ID: {}", pessoaId);
        List<Boleto> boletos = boletoRepository.findByPessoaIdOrderByDataVencimento(pessoaId);
        log.info("Total de boletos encontrados: {}", boletos.size());
        return boletos;
    }

    public Optional<Boleto> buscarBoletoPorId(Long id) {
        log.info("Buscando boleto com ID: {}", id);
        Optional<Boleto> boleto = boletoRepository.findById(id);
        if (boleto.isPresent()) {
            log.info("Boleto encontrado com ID: {}", boleto.get().getId());
        } else {
            log.warn("Boleto com ID: {} não encontrado.", id);
        }
        return boleto;
    }

    @Transactional
    public Boleto pagarBoleto(Long id, BigDecimal valorPago, LocalDate dataPagamento) {
        log.info("Iniciando pagamento do boleto com ID: {}", id);

        Boleto boleto = boletoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Boleto não encontrado"));

        if (!boleto.getStatus().equals(Boleto.StatusBoleto.PENDENTE)) {
            log.error("Boleto com ID: {} não está pendente. Status atual: {}", id, boleto.getStatus());
            throw new IllegalArgumentException("Pagamento só é permitido se o boleto estiver Pendente.");
        }

        if (!boleto.getValorDocumento().equals(valorPago)) {
            log.error("Valor pago {} não corresponde ao valor do documento {}. ID do boleto: {}", valorPago, boleto.getValorDocumento(), id);
            throw new IllegalArgumentException("Valor pago deve ser igual ao valor do documento.");
        }

        if (!dataPagamento.equals(LocalDate.now())) {
            log.error("Data de pagamento {} não corresponde à data atual {}. ID do boleto: {}", dataPagamento, LocalDate.now(), id);
            throw new IllegalArgumentException("Data de pagamento deve ser igual à data atual.");
        }

        boleto.setValorPago(valorPago);
        boleto.setDataPagamento(dataPagamento);
        boleto.setStatus(Boleto.StatusBoleto.PAGO);

        Boleto pago = boletoRepository.save(boleto);
        log.info("Boleto com ID: {} pago com sucesso.", id);
        return pago;
    }

    public void excluirBoleto(Long id) {
        log.info("Excluindo boleto com ID: {}", id);

        Boleto boleto = boletoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Boleto não encontrado com ID: {}", id);
                    return new IllegalArgumentException("Boleto não encontrado");
                });

        if (boleto.getStatus().equals(Boleto.StatusBoleto.PAGO)) {
            log.error("Tentativa de exclusão de boleto com status PAGO. ID do boleto: {}", id);
            throw new IllegalArgumentException("Não é permitido excluir boleto com status PAGO.");
        }

        boletoRepository.deleteById(id);
        log.info("Boleto com ID: {} excluído com sucesso.", id);
    }
}
