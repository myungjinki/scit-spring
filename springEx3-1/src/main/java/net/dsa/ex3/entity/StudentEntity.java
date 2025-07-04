package net.dsa.ex3.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student3")
public class StudentEntity {

    @Id
    @Column(name = "sid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;		// PK

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "major")
    private String major;
    @Column(name = "java")
    private String java;
    @Column(name = "db")
    private String db;
    @Column(name = "web")
    private String web;
}