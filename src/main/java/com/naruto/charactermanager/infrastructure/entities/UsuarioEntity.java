package com.naruto.charactermanager.infrastructure.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Comment;
import com.naruto.charactermanager.domain.constants.AppConst;
import com.naruto.charactermanager.domain.inheritance.FullAuditedEntity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * Entidad JPA para persistencia de usuarios
 */
@Entity
@Comment("Usuario del sistema")
@Table(name = "USUARIO")
public class UsuarioEntity extends FullAuditedEntity implements Serializable {

    @Id
    @Comment("Identificador único del usuario")
    @Column(name = "ID_USUARIO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("Documento de identidad del usuario (DNI)")
    @Column(name = "DE_DOCUMENTO", length = AppConst.MAX_DOCUMENT_LENGTH, nullable = false, unique = true)
    @NotBlank(message = "El documento es obligatorio")
    @Pattern(regexp = "^[0-9]+$", message = "El documento solo debe contener números")
    private String documento;

    @Comment("Nombre de usuario para login")
    @Column(name = "DE_NOMBRE_USUARIO", length = AppConst.MAX_USERNAME_LENGTH, nullable = false, unique = true)
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(max = AppConst.MAX_USERNAME_LENGTH, message = "El nombre de usuario no puede exceder " + AppConst.MAX_USERNAME_LENGTH + " caracteres")
    private String nombreUsuario;

    @Comment("Nombre completo del usuario")
    @Column(name = "DE_NOMBRE", length = AppConst.MAX_NAME_LENGTH, nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = AppConst.MAX_NAME_LENGTH, message = "El nombre no puede exceder " + AppConst.MAX_NAME_LENGTH + " caracteres")
    private String nombre;

    @Comment("Dirección de correo electrónico")
    @Column(name = "DE_CORREO", length = AppConst.MAX_EMAIL_LENGTH)
    @Email(message = "El formato del email no es válido")
    @Size(max = AppConst.MAX_EMAIL_LENGTH, message = "El email no puede exceder " + AppConst.MAX_EMAIL_LENGTH + " caracteres")
    private String email;

    @Comment("Contraseña encriptada del usuario")
    @Column(name = "DE_CONTRASENA", length = 255, nullable = false)
    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;

    @Comment("Sello de seguridad para invalidar tokens")
    @Column(name = "DE_SELLO_SEGURIDAD", length = 100, nullable = false)
    private String securityStamp;

    @Comment("Indicador de si el correo está confirmado")
    @Column(name = "ES_CORREO_CONFIRMADO", nullable = false)
    private Boolean isEmailConfirmed;

    @Comment("Contador de intentos fallidos de acceso")
    @Column(name = "CA_INICIO_SESION_FALLIDO", nullable = false)
    private Integer accessFailedCount;

    @Comment("Fecha y hora de finalización del bloqueo de la cuenta")
    @Column(name = "FE_EXP_BLOQUEO", columnDefinition = "TIMESTAMP")
    private Timestamp lockoutEndTime;

    @Comment("Indica si la cuenta del usuario está activa")
    @Column(name = "ES_ACTIVO", nullable = false)
    private Boolean isActive;

    // Constructores
    public UsuarioEntity() {
        this.isActive = Boolean.TRUE;
        this.isEmailConfirmed = Boolean.FALSE;
        this.accessFailedCount = 0;
        this.securityStamp = UUID.randomUUID().toString();
    }

    public UsuarioEntity(String documento, String nombreUsuario, String nombre, String email, String contrasena) {
        this();
        this.documento = documento;
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.normalize();
    }

    // Métodos de auditoría simplificados
    public void createUsuario(String userId) {
        create(userId);
    }

    public void updateUsuario(String userId) {
        modify(userId);
    }

    public void deleteUsuario(String userId) {
        delete(userId);
    }

    public void restoreUsuario(String userId) {
        unDelete();
        modify(userId);
    }

    // Métodos de negocio
    public void normalize() {
        if (this.nombreUsuario != null) {
            this.nombreUsuario = this.nombreUsuario.trim().toUpperCase();
        }
        if (this.email != null) {
            this.email = this.email.trim().toLowerCase();
        }
    }

    public void incrementFailedAttempts() {
        this.accessFailedCount++;
    }

    public void resetFailedAttempts() {
        this.accessFailedCount = 0;
        this.lockoutEndTime = null;
    }

    public void lockAccount(Timestamp lockoutEndTime) {
        this.lockoutEndTime = lockoutEndTime;
    }

    public boolean isLocked() {
        return this.lockoutEndTime != null && this.lockoutEndTime.after(new Timestamp(System.currentTimeMillis()));
    }

    public void updateSecurityStamp() {
        this.securityStamp = UUID.randomUUID().toString();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getSecurityStamp() {
        return securityStamp;
    }

    public void setSecurityStamp(String securityStamp) {
        this.securityStamp = securityStamp;
    }

    public Boolean getEmailConfirmed() {
        return isEmailConfirmed;
    }

    public void setEmailConfirmed(Boolean emailConfirmed) {
        isEmailConfirmed = emailConfirmed;
    }

    public Integer getAccessFailedCount() {
        return accessFailedCount;
    }

    public void setAccessFailedCount(Integer accessFailedCount) {
        this.accessFailedCount = accessFailedCount;
    }

    public Timestamp getLockoutEndTime() {
        return lockoutEndTime;
    }

    public void setLockoutEndTime(Timestamp lockoutEndTime) {
        this.lockoutEndTime = lockoutEndTime;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}

