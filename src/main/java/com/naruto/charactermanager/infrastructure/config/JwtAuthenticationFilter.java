package com.naruto.charactermanager.infrastructure.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.naruto.charactermanager.application.services.IUsuarioService;
import com.naruto.charactermanager.domain.constants.AppConst;
import com.naruto.charactermanager.domain.utils.JwtUtils;
import com.naruto.charactermanager.infrastructure.entities.UsuarioEntity;

import java.io.IOException;
import java.util.Optional;

/**
 * Filtro JWT para autenticación
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final IUsuarioService usuarioService;
    private final UserDetailsService userDetailsService;
    
    public JwtAuthenticationFilter(IUsuarioService usuarioService, UserDetailsService userDetailsService) {
        this.usuarioService = usuarioService;
        this.userDetailsService = userDetailsService;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        
        try {
            // Extraer token del header Authorization
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            
            // Verificar si el header existe y tiene el formato correcto
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            
            // Extraer el token
            jwt = authHeader.substring(7);
            
            // Validar el token
            if (!JwtUtils.isTokenValid(jwt)) {
                filterChain.doFilter(request, response);
                return;
            }
            
            // Extraer información del token
            Optional<Long> userIdOptional = JwtUtils.extractUserId(jwt);
            Optional<String> securityStampOptional = JwtUtils.extractSecurityStamp(jwt);
            Optional<Integer> tokenTypeOptional = JwtUtils.extractTokenType(jwt);
            
            if (userIdOptional.isEmpty() || securityStampOptional.isEmpty() || tokenTypeOptional.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }
            
            // Verificar que sea un access token
            if (tokenTypeOptional.get() != AppConst.ACCESS_TOKEN_TYPE) {
                filterChain.doFilter(request, response);
                return;
            }
            
            Long userId = userIdOptional.get();
            String securityStamp = securityStampOptional.get();
            
            // Buscar usuario
            Optional<UsuarioEntity> usuarioOptional = usuarioService.findById(userId);
            if (usuarioOptional.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }
            
            UsuarioEntity usuario = usuarioOptional.get();
            
            // Verificar que el usuario esté activo y el security stamp coincida
            if (!usuario.getActive() || !usuario.getSecurityStamp().equals(securityStamp)) {
                filterChain.doFilter(request, response);
                return;
            }
            
            // Crear autenticación
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getDocumento());
                
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
                );
                
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            
        } catch (Exception e) {
            // Log error pero no interrumpir el flujo
            logger.error("Error procesando JWT token", e);
        }
        
        filterChain.doFilter(request, response);
    }
}

