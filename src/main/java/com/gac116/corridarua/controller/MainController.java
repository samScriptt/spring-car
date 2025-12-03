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
        model.addAttribute("registroDto", new RegistroDTO());
        model.addAttribute("equipes", equipeRepository.findAll());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarPiloto(@ModelAttribute RegistroDTO dto, Model model) {
        try {
            // 1. Verificar se usuário já existe
            if (pilotoRepository.findByUsername(dto.getUsername()).isPresent()) {
                throw new IllegalArgumentException("Este nome de usuário já está em uso!");
            }

            // 2. Criar ou buscar Equipe
            Equipe equipe = null;
            if (dto.getEquipeId() != null) {
                equipe = equipeRepository.findById(dto.getEquipeId()).orElse(null);
            } else if (dto.getNovaEquipeNome() != null && !dto.getNovaEquipeNome().isEmpty()) {
                equipe = new Equipe();
                equipe.setNome(dto.getNovaEquipeNome());
                equipe.setCidade(dto.getNovaEquipeCidade());
                equipeRepository.save(equipe);
            }

            // 3. Criar Piloto
            Piloto novoPiloto = new Piloto();
            novoPiloto.setNome(dto.getNomePiloto());
            novoPiloto.setUsername(dto.getUsername());
            novoPiloto.setPassword(passwordEncoder.encode(dto.getPassword()));
            novoPiloto.setEquipe(equipe);
            
            pilotoRepository.save(novoPiloto);

            return "redirect:/login?registrado=true";

        } catch (Exception e) {
            // Se der erro, volta para o formulário e mostra a mensagem
            model.addAttribute("erro", "Erro ao registrar: " + e.getMessage());
            model.addAttribute("equipes", equipeRepository.findAll());
            // Importante: devolve o DTO preenchido para o usuário não perder o que digitou
            model.addAttribute("registroDto", dto); 
            return "registro";
        }
    }
}