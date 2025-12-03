package com.gac116.corridarua.controller;

import com.gac116.corridarua.model.Equipe;
import com.gac116.corridarua.model.Piloto;
import com.gac116.corridarua.repository.EquipeRepository;
import com.gac116.corridarua.repository.PilotoRepository;
import com.gac116.corridarua.dto.RegistroDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.ArrayList; // Importante para criar listas vazias

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private PilotoRepository pilotoRepository;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @GetMapping
    public String home(Model model) {
        List<Equipe> equipes = equipeRepository.findAll();
        // Proteção: se vier nulo, cria lista vazia
        if (equipes == null) equipes = new ArrayList<>();
        
        model.addAttribute("equipes", equipes);
        model.addAttribute("titulo", "Bem-vindo ao CorridaRua!");
        return "home"; 
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registro")
    public String registroForm(Model model) {
        System.out.println("--- Acessando registro (GET) ---");
        
        model.addAttribute("registroDto", new RegistroDTO());
        
        // --- PROTEÇÃO CONTRA LISTA NULA ---
        List<Equipe> lista = null;
        try {
            lista = equipeRepository.findAll();
        } catch (Exception e) {
            System.out.println("Erro ao buscar equipes: " + e.getMessage());
        }

        if (lista == null) {
            lista = new ArrayList<>(); // Cria lista vazia para não dar erro no HTML
            System.out.println("Aviso: Lista de equipes era nula, criada lista vazia.");
        }
        
        model.addAttribute("equipes", lista);
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarPiloto(@ModelAttribute RegistroDTO dto, Model model) {
        try {
            if (pilotoRepository.findByUsername(dto.getUsername()).isPresent()) {
                throw new IllegalArgumentException("Este nome de usuário já está em uso!");
            }

            Equipe equipe = null;
            if (dto.getEquipeId() != null) {
                equipe = equipeRepository.findById(dto.getEquipeId()).orElse(null);
            } else if (dto.getNovaEquipeNome() != null && !dto.getNovaEquipeNome().isEmpty()) {
                equipe = new Equipe();
                equipe.setNome(dto.getNovaEquipeNome());
                equipe.setCidade(dto.getNovaEquipeCidade());
                equipeRepository.save(equipe);
            }

            Piloto novoPiloto = new Piloto();
            novoPiloto.setNome(dto.getNomePiloto());
            novoPiloto.setUsername(dto.getUsername());
            novoPiloto.setPassword(passwordEncoder.encode(dto.getPassword()));
            novoPiloto.setEquipe(equipe);
            
            pilotoRepository.save(novoPiloto);

            return "redirect:/login?registrado=true";

        } catch (Exception e) {
            model.addAttribute("erro", "Erro: " + e.getMessage());
            
            // Recarrega lista com proteção também no erro
            List<Equipe> lista = equipeRepository.findAll();
            if (lista == null) lista = new ArrayList<>();
            model.addAttribute("equipes", lista);
            
            model.addAttribute("registroDto", dto); 
            return "registro";
        }
    }
}