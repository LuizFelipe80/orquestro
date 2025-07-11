package com.lf.Orquestro.DomainModel.enums;


public enum State {
	
    ACTIVE("Ativo"),
    INACTIVE("Inativo"),
    TRASHED("Na Lixeira");

    private final String description;

    State(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}