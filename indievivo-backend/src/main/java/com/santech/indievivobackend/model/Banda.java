package com.santech.indievivobackend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "bandas", uniqueConstraints = {@UniqueConstraint(columnNames = "nombre")})
public class Banda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 2000)
    private String descripcion;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "bandas_generos_musicales", joinColumns = @JoinColumn(name = "banda_id"), inverseJoinColumns = @JoinColumn(name = "genero_musical_id"))
    private Set<GeneroMusical> generos = new HashSet<>();

    @Column(name = "ciudad_origen", nullable = false)
    private String ciudadOrigen;

    @Column(name = "sitio_web")
    private String sitioWeb;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propietario_id", nullable = false)
    private User propietario;

    // Campos de auditoria.
    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;

    // Constructores
    public Banda() {
    }

    public Banda(String nombre,
                 String descripcion,
                 Set<GeneroMusical> generos,
                 String ciudadOrigen,
                 String sitioWeb,
                 User propietario) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.generos = generos;
        this.ciudadOrigen = ciudadOrigen;
        this.sitioWeb = sitioWeb;
        this.propietario = propietario;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<GeneroMusical> getGeneros() {
        return generos;
    }

    public void setGeneros(Set<GeneroMusical> generos) {
        this.generos = generos;
    }

    public String getCiudadOrigen() {
        return ciudadOrigen;
    }

    public void setCiudadOrigen(String ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public User getPropietario() {
        return propietario;
    }

    public void setPropietario(User propietario) {
        this.propietario = propietario;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    // Equals y Hashcode

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Banda banda = (Banda) o;
        return Objects.equals(id, banda.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
