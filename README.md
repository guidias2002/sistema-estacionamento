# API de Sistema de Estacionamento

Esta é uma API desenvolvida em Java utilizando o framework Spring Boot, que gerencia um sistema de estacionamento. A API permite registrar veículos, listar veículos estacionados e todos os registros, registrar a saída de veículos, atualizar dados e excluir registros de veículos.


## Tecnologias Utilizadas

- Java
- Spring Boot
- PostgreSQL
- Spring Data JPA
- Lombok
- Flyway

## Estrutura do Projeto

### Controlador (`VeiculoController`)

O controlador `VeiculoController` é responsável por lidar com as requisições HTTP e invocar os serviços apropriados. Abaixo estão as operações disponíveis:

#### Endpoints

- **Registrar Veículo**

    - **URL:** `/veiculos`
    - **Método:** `POST`
    - **Descrição:** Registra um novo veículo.
    - **Corpo da Requisição:**
    ```json
    {
        "tipoveiculo": "CARRO",
        "placa": "DSD-4144",
        "modelo": "gol",
        "cor": "preto"
    }
    ```

- **Listar Todos os Veículos**

    - **URL:** `/veiculos`
    - **Método:** `GET`
    - **Descrição:** Retorna uma lista de todos os veículos registrados.

- **Listar Veículos Estacionados**

    - **URL:** `/veiculos/estacionados`
    - **Método:** `GET`
    - **Descrição:** Retorna uma lista de veículos que ainda estão estacionados.

- **Registrar Saída de Veículo**

    - **URL:** `/veiculos/saida/{placa}`
    - **Método:** `POST`
    - **Descrição:** Registra a saída de um veículo com a placa fornecida.

- **Listar Registros de um Veículo**

    - **URL:** `/veiculos/registros-por-veiculo/{placa}`
    - **Método:** `GET`
    - **Descrição:** Retorna todos os registros de entrada e saída de um veículo específico.

- **Excluir Veículo pelo ID**

    - **URL:** `/veiculos/excluir/{id}`
    - **Método:** `DELETE`
    - **Descrição:** Exclui um veículo pelo seu ID.

- **Atualizar Veículo pelo ID**

    - **URL:** `/veiculos/atualizar/{id}`
    - **Método:** `PUT`
    - **Descrição:** Atualiza os dados de um veículo pelo seu ID.
 
# Lógica de Tabela de Preços

A lógica de cálculo de preços para o sistema de estacionamento é baseada no tipo de veículo (`carro` ou `moto`) e no tempo de permanência em minutos. O método `calcularValor` é responsável por determinar o valor a ser cobrado com base nas seguintes regras:

## Períodos e Valores Baseados no Tipo de Veículo

- **Carro:**
  - Até 120 minutos: **R$ 10,00**
  - Até 240 minutos: **R$ 20,00**
  - Após 240 minutos: **R$ 20,00** + **R$ 5,00** para cada 30 minutos adicionais
- **Moto:**
  - Até 120 minutos: **R$ 5,00**
  - Até 240 minutos: **R$ 10,00**
  - Após 240 minutos: **R$ 10,00** + **R$ 3,00** para cada 30 minutos adicionais

## Lógica de Cálculo

- **Até 120 Minutos:** 
  - Se o período de estacionamento for de até 120 minutos, o valor base será cobrado de acordo com o tipo de veículo.
- **Entre 120 e 240 Minutos:** 
  - Se o período de estacionamento estiver entre 120 e 240 minutos, um valor fixo maior será cobrado.
- **Mais de 240 Minutos:**
  - Se o período de estacionamento exceder 240 minutos, um valor adicional será cobrado a cada intervalo de 30 minutos, com base no tipo de veículo.

## Exemplo de Cálculo

- Um **carro** estacionado por 3 horas (180 minutos) pagará **R$ 20,00**.
- Uma **moto** estacionada por 5 horas (300 minutos) pagará **R$ 13,00** (R$ 10,00 pelo tempo base até 240 minutos + R$ 3,00 pelo tempo adicional de 60 minutos).

### Serviço (`VeiculoService`)

O serviço `VeiculoService` contém a lógica de negócios para manipulação de veículos. Abaixo estão os métodos principais:

- `registrarVeiculo(VeiculoDto veiculo)`: Registra um novo veículo.
- `listarTodosOsVeiculos()`: Retorna uma lista de todos os veículos.
- `listarVeiculosEstacionados()`: Retorna uma lista de veículos estacionados.
- `registrarSaidaVeiculo(String placa)`: Registra a saída de um veículo.
- `excluirVeiculo(UUID id)`: Exclui um veículo pelo seu ID.
- `atualizarVeiculo(UUID id, VeiculoUpdateDto veiculoUpt)`: Atualiza os dados de um veículo.
- `calcularValor(LocalDateTime entrada, LocalDateTime saida, String tipoVeiculo)`: Calcula o valor a ser pago com base no tempo de permanência e tipo de veículo.
- `calcularPeriodoEmMinuto(LocalDateTime entrada, LocalDateTime saida)`: Calcula o período de permanência em minutos.
- `listarRegistrosDeUmVeiculo(String placa)`: Lista todos os registros de um veículo específico.

### Entidade `Veiculo`

A entidade `Veiculo` representa um veículo estacionado e seus atributos:

- **ID** (`UUID`)
- **Tipo de Veículo** (`tipoVeiculo`)
- **Placa** (`placa`)
- **Modelo** (`modelo`)
- **Cor** (`cor`)
- **Entrada** (`entrada`)
- **Saída** (`saida`)
- **Período em Minutos** (`periodoEmMinutos`)
- **Valor** (`valor`)

### Exceções

As seguintes exceções são tratadas:

- `VehicleNotFoundException`: Lançada quando um veículo não é encontrado.
- `VehicleIsParkedException`: Lançada quando se tenta registrar um veículo que já está estacionado.
- `MethodArgumentNotValidException`: Lançada quando há argumentos inválidos na requisição.

### Repositório (`VeiculoRepository`)

O repositório `VeiculoRepository` é responsável por interagir com o banco de dados. Ele oferece métodos para buscar, salvar, e excluir veículos.

#### Consultas Disponíveis

- `findByPlacaAndSaidaIsNull(String placa)`: Retorna um veículo com a placa fornecida que ainda está estacionado.
- `findVeiculoById(UUID id)`: Retorna um veículo pelo seu ID.
- `findBySaidaNull()`: Retorna todos os veículos que ainda estão estacionados.
- `findVeiculoByPlaca(String placa)`: Retorna todos os registros de um veículo com a placa fornecida.

### Migração

A tabela `veiculos` é criada com a seguinte estrutura:

```sql
CREATE TABLE veiculos (
    id UUID PRIMARY KEY NOT NULL,
    tipoVeiculo TEXT NOT NULL,
    placa VARCHAR(8) NOT NULL,
    modelo TEXT NOT NULL,
    cor TEXT NOT NULL,
    entrada TIMESTAMP NOT NULL,
    saida TIMESTAMP,
    valor TEXT,
    periodoEmMinutos INTEGER
);
