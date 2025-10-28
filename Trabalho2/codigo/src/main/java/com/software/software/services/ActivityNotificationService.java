package com.software.software.services;

import com.software.software.models.Activity;
import com.software.software.patterns.ActivityObserver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviço que implementa o padrão Observer para notificações de atividades
 * Gerencia a lista de observadores e coordena as notificações
 */
@Service
public class ActivityNotificationService {
    // Lista de observadores registrados para receber notificações
    private final List<ActivityObserver> observers = new ArrayList<>();

    /**
     * Registra um novo observador na lista
     * Permite que múltiplos componentes sejam notificados sobre atividades em atraso
     */
    public void addObserver(ActivityObserver observer) {
        observers.add(observer);
    }

    /**
     * Notifica todos os observadores sobre uma atividade em atraso
     * Implementa o padrão Observer disparando eventos para todos os registrados
     */
    public void notifyOverdueActivity(Long studentId, Activity activity) {
        observers.forEach(observer -> observer.onActivityOverdue(studentId, activity));
    }
}
