CREATE TABLE veiculos (
    id UUID PRIMARY KEY NOT NULL,
    tipoVeiculo TEXT NOT NULL,
    placa VARCHAR(8) UNIQUE NOT NULL,
    modelo TEXT NOT NULL,
    cor TEXT NOT NULL,
    entrada TIMESTAMP NOT NULL,
    saida TIMESTAMP
);