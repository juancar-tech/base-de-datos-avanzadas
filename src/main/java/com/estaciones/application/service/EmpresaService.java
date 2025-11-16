package com.estaciones.application.service;

import com.estaciones.application.dto.EmpresaDTO;
import com.estaciones.domain.repository.EmpresaRepository;
import com.estaciones.domain.repository.EstacionTerrestreRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpresaService {
    
    private final EmpresaRepository empresaRepository;
    private final EstacionTerrestreRepository estacionTerrestreRepository;
    
    public EmpresaService(
            EmpresaRepository empresaRepository,
            EstacionTerrestreRepository estacionTerrestreRepository) {
        this.empresaRepository = empresaRepository;
        this.estacionTerrestreRepository = estacionTerrestreRepository;
    }
    
    public Optional<EmpresaDTO> findEmpresaConMasEstacionesTerrestres() {
        return empresaRepository.findEmpresaConMasEstacionesTerrestres()
            .map(empresa -> {
                long totalEstaciones = estacionTerrestreRepository.findAll().stream()
                    .filter(e -> e.empresa().idEmpresa().equals(empresa.idEmpresa()))
                    .count();
                return new EmpresaDTO(
                    empresa.idEmpresa(),
                    empresa.nombreRotulo(),
                    totalEstaciones
                );
            });
    }
}

