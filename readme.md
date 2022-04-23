# API para Barbearia
Projeto feito para estudar o Java Spring (Java 8), usando JPA (Hibernate) e Postgres SQL.<br>
O objetivo é criar uma API que controla os agendamentos de uma barbearia, com a possibilidade de cadastro de funcionários e clientes.<br>
<br>

# Instalação

Basta clonar o projeto e utilizá-lo. Para o Eclipse, importe-o como Maven Project. <br>
É necessária uma ferramenta de testes de API como Postman para utilizar esse app.

<br>

# Utilização 

## Cadastro de cliente e prestador
<br>

```bash
POST localhost:8080/clientes
ou
POST localhost:8080/prestadores
```
body
```javascript
{   
    "nome": "Henrietta Bradley",
    "cpf": "96291956071",
    "codigoPostal": "69945970",
    "complemento": "Casa X",
    "origem": "BR"
}
```

CPFs são aceitos em quaisquer formatos, porém armazenados somente números. O CEP aceita somente números. São aceitos códigos postais brasileiros e canadeneses (BR ou CA em origem).

## Agendamentos

```bash
POST localhost:8080/agendamentos
```
body
```Javascript
{
    "dia": "17/03/2024",
    "horario": "08:00",
    "cpfCliente": "34842981202",
    "cpfPrestador" : "11806097788"
}
```

Dia em formato DD/MM/YYYY e horário no formato HH:mm. São aceitos agendamentos em horário comercial no começo da hora (08:00, 09:00, ... , 17:00) apenas em dias úteis. <br>
É permitido um agendamento por cliente em um dia e um cliente por prestador a cada hora.

<br>
<hr>
<br>

# Barber shop API
Project developed to study Java Spring (Java 8) using JPA (Hibernate) and Postgres SQL.
The main goel was to create an API that controls appointments in a barber shop.with the possibility to register personnel and customers.

<br>

# Translation

As the code is in portuguese by now, here are some keywords used in the project

| English | Portuguese |
| :-------: | :----------: |
| Customer | Cliente |
| Personnel | Prestador |
| Appointment | Agendamento |
| Address | Endereço |
| Person | Pessoa |
| Postal (Zip code) | Código postal (CEP)|
| Social ID | CPF |
| Complement | Complemento |
| Origin | Origem |

<br>


# Installation 

You only have to clone the project to utilize it. For Eclipse IDE, import as a Maven project. <br>
You will also need an API testing platform like Postman to test the app.
<br>

# Utilization 

## Customer and personnel register
<br>

```bash
POST localhost:8080/clientes
ou
POST localhost:8080/prestadores
```
body
```javascript
{   
    "nome": "Henrietta Bradley",
    "cpf": "96291956071",
    "codigoPostal": "69945970",
    "complemento": "Casa X",
    "origem": "BR"
}
```

CPFs are accepted in any format but only the numbers are stored. codigoPostal only takes numbers (zip code). Only brazilian and canadian zip codes are accepted (BR or CA in origem)

## Appointments

```bash
POST localhost:8080/agendamentos
```
body
```Javascript
{
    "dia": "17/03/2024",
    "horario": "08:00",
    "cpfCliente": "34842981202",
    "cpfPrestador" : "11806097788"
}
```
Day in DD/MM/YYYY format and time in HH:mm format. Only hourly time between 8am and 5pm are accepted and in working days. <br>
Only one appointment per day is accepted for a customer and only one customer for a personnel in each hour.