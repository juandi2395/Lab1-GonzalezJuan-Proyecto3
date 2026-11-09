package com.gonzalezjuan.gonzalezjuanlab1proyecto3.service;

import com.gonzalezjuan.gonzalezjuanlab1proyecto3.dto.RegistroUsuarioRequestDTO;
import com.gonzalezjuan.gonzalezjuanlab1proyecto3.dto.UsuarioResponseDTO;
import com.gonzalezjuan.gonzalezjuanlab1proyecto3.model.Rol;
import com.gonzalezjuan.gonzalezjuanlab1proyecto3.model.Usuario;
import com.gonzalezjuan.gonzalezjuanlab1proyecto3.repository.RolRepository;
import com.gonzalezjuan.gonzalezjuanlab1proyecto3.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponseDTO registrar(RegistroUsuarioRequestDTO request) {

        String username = request.username().trim();

        if (usuarioRepository.findByUsername(username).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El nombre de usuario ya está registrado"
            );
        }

        Rol rolUser = rolRepository.findByNombre("USER")
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "El rol USER no está configurado"
                ));

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(request.password()));
        usuario.setEnabled(true);
        usuario.setRoles(Set.of(rolUser));

        Usuario guardado = usuarioRepository.save(usuario);

        return convertirDTO(guardado);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listar() {

        return usuarioRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Usuario::getId))
                .map(this::convertirDTO)
                .toList();
    }

    private UsuarioResponseDTO convertirDTO(Usuario usuario) {

        Set<String> roles = usuario.getRoles()
                .stream()
                .map(Rol::getNombre)
                .collect(Collectors.toSet());

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getEnabled(),
                roles
        );
    }
}
