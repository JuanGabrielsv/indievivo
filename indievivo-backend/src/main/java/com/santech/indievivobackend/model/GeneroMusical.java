package com.santech.indievivobackend.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "generos_musicales", uniqueConstraints = @UniqueConstraint(columnNames = "nombre"))
public class GeneroMusical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    // Constructores

    public GeneroMusical() {
    }

    public GeneroMusical(String nombre) {
        this.nombre = nombre;
    }

    // Getters and Setters

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

    // Equal y Hashcode

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GeneroMusical that = (GeneroMusical) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
