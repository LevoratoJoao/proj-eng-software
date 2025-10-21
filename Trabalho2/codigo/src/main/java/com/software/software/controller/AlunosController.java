package com.software.software.controller;

import com.software.software.services.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alunos")
public class AlunosController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping("/{id}/responsavel-email")
    public ResponseEntity<String> getEmailResponsavel(@PathVariable Long id) {
        return ResponseEntity.ok(alunoService.getEmailResponsavelByAlunoId(id));
    }
}
