package com.example.Exam.security.entities;

import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode
@Entity
@Table(name = "roles")
public class Role {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Id
    private ERole name;

    public Role(ERole name) {
        this.name = name;
    }

    public Role(){}
    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}
