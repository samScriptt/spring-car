package com.gac116.corridarua.controller;

import com.gac116.corridarua.model.Equipe;
import com.gac116.corridarua.model.Piloto; // <--- FALTAVA ISSO
import com.gac116.corridarua.repository.EquipeRepository;
import com.gac116.corridarua.repository.PilotoRepository; // <--- FALTAVA ISSO
import com.gac116.corridarua.dto.RegistroDTO; // <--- IMPORTANTE PARA O DTO

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute; // <--- FALTAVA ISSO
import org.springframework.web.bind.annotation.PostMapping; // <--- FALTAVA ISSO
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller principal para rotas web (ex: home, login).
 */
@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private PilotoRepository pilotoRepository;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    /**
     * Rota para a página inicial.
     */
    @GetMapping
    public String home(Model model) {
        List<Equipe> equipes = equipeRepository.findAll();
        model.addAttribute("equipes", equipes);
        model.addAttribute("titulo", "Bem-vindo ao CorridaRua!");
        return "home"; // Corrigido para "home" (se o seu arquivo se chama home.html) ou "index"
    }

    /**
     * Rota para o formulário de login.
     */
    @GetMapping("/login")
    public String login() {
        return "login"; // Precisa existir o templates/login.html
    }

    @GetMapping("/registro")
    public String registroForm(Model model) {
        model.addAttribute("registroDto", new RegistroDTO());
        model.addAttribute("equipes", equipeRepository.findAll());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarPiloto(@ModelAttribute RegistroDTO dto) {
        // 1. Criar ou buscar Equipe
        Equipe equipe = null;
        if (dto.getEquipeId() != null) {
            equipe = equipeRepository.findById(dto.getEquipeId()).orElse(null);
        } else if (dto.getNovaEquipeNome() != null && !dto.getNovaEquipeNome().isEmpty()) {
            equipe = new Equipe();
            equipe.setNome(dto.getNovaEquipeNome());
            equipe.setCidade(dto.getNovaEquipeCidade());
            // Salvamos a nova equipe antes de associar
            equipeRepository.save(equipe);
        }

        // 2. Criar Piloto
        Piloto novoPiloto = new Piloto();
        novoPiloto.setNome(dto.getNomePiloto());
        novoPiloto.setUsername(dto.getUsername());
        
        // Criptografar senha
        novoPiloto.setPassword(passwordEncoder.encode(dto.getPassword())); 
        
        novoPiloto.setEquipe(equipe);
        
        pilotoRepository.save(novoPiloto);

        return "redirect:/login?registrado=true";
    }
}