package com.software.software.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.software.software.controller.dtos.observation.RequestObservationDto; 
import com.software.software.services.StudentsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

// Imports estáticos para facilitar a leitura do teste
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Teste de Integração da Camada de Controle (Controller)
 * Verifica se a API REST está respondendo corretamente.
 */
@WebMvcTest(StudentsController.class) // Carrega apenas o contexto Web para este Controller
class StudentsControllerTest {

    @Autowired
    private MockMvc mockMvc; // Simula as requisições HTTP 

    @MockBean
    private StudentsService studentsService; // Cria um Mock do Service (não acessa o banco de verdade aqui)

    @Autowired
    private ObjectMapper objectMapper; // Transforma Objetos Java em JSON

    @Test
    void shouldReturnCreatedWhenObservationIsSent() throws Exception {
        // 1. ARRANGE (Preparação)
        // Gerando um ID aleatório para simular a chamada da API
        Long randomStudentId = (long) (Math.random() * 1000); 
        
        RequestObservationDto requestDto = new RequestObservationDto(
            "O aluno demonstrou grande evolução na leitura.", 
            "whatsapp"
        );

        // 2. ACT (Ação) & 3. ASSERT (Verificação)
        mockMvc.perform(post("/students/{id}/observations", randomStudentId)
                .contentType(MediaType.APPLICATION_JSON) // Avisa que estamos enviando JSON
                .content(objectMapper.writeValueAsString(requestDto))) // Converte o DTO para String JSON
                
                // Espera que a resposta seja 201 CREATED
                .andExpect(status().isCreated());
    }
}