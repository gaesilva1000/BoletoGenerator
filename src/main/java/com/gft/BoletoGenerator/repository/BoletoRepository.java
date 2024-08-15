package com.gft.BoletoGenerator.repository;

import com.gft.BoletoGenerator.model.Boleto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoletoRepository extends JpaRepository<Boleto, Long> {
    List<Boleto> findByPessoaIdOrderByDataVencimento(Long pessoaId);

    @Query("SELECT b FROM Boleto b WHERE b.status = :status")
    List<Boleto> findByStatusBoleto(@Param("status") Boleto.StatusBoleto status);
}
