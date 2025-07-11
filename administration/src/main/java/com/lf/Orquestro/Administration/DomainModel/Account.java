package com.lf.Orquestro.Administration.DomainModel;

import java.io.Serializable;
import com.lf.Orquestro.DomainModel.User; // Importa a classe base
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts") // Esta será a tabela para os dados específicos de Account
@Inheritance(strategy = InheritanceType.JOINED) // Permite que Account também seja estendida
public class Account extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    // --- Aqui entrarão os atributos específicos da entidade Account ---
    // Por exemplo:
    // @Column(name = "department")
    // private String department;

    // --- Construtores ---

    public Account() {
        super(); // Chama o construtor da classe pai (User)
    }

    // Construtor com os campos da classe pai
    public Account(String fullName, String name, String password, String email) {
        super(fullName, name, password, email); // Repassa os dados para o construtor da classe pai
    }

    // --- Getters e Setters para os novos atributos viriam aqui ---

}