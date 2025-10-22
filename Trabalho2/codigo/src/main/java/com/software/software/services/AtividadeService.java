package com.software.software.services;

import com.software.software.models.Atividade;
import com.software.software.repository.AlunosRepository;
import com.software.software.repository.AtividadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AtividadeService {

    private final AtividadeRepository atividadeRepository;

    public List<Atividade> getOverdueActivities(LocalDate currentDate) {
        return atividadeRepository.findAtividadeByDataEntrega(currentDate);
    }
}
