/*CRIAÇÃO DO BANCO E DAS TABELAS*/
create database BibliotecaDoceLeitura;
use BibliotecaDoceLeitura;

create table usuario (
id int primary key auto_increment,
nome varchar(30) not null,
email varchar(100) not null,
senha text not null,
tipoUsuario varchar(30) not null
); 

create table livro(
id int auto_increment primary key,
titulo varchar(100) not null,
autor varchar(100) not null,
genero varchar(80) not null,
ano_publicacao varchar(4) not null,
descricao text not null,
status_disponibilidade varchar(15) not null default 'Disponível'
);

create table cliente(
id int auto_increment primary key,
nome varchar(100) not null,
email varchar(100) not null,
telefone varchar(13) not null,
status_acesso varchar(15) not null default 'Habilitado'
);

create table emprestimo(
id int auto_increment primary key,
dt_emprestimo date not null,
dt_prevista_devolucao date not null,
cliente_id int not null,
livro_id int not null,
status_vigor varchar(15) not null default 'Ativo',
foreign key(cliente_id) references cliente(id),
foreign key(livro_id) references livro(id)
);

create table devolucao(
id int auto_increment primary key,
emprestimo_id int not null,
dt_devolucao date not null,
qt_dias_atraso int,
vl_multa decimal(6,2),
foreign key(emprestimo_id) references emprestimo(id)
);
-- -----------------------------------------------------------------------------------------------------

/*INSERTS NAS TABELAS*/

INSERT INTO usuario (nome, email, senha, tipoUsuario) VALUES 
('Lucas', 'lucas@email.com', '123', 'Gerente'),
('Laura', 'laura@email.com', '123', 'Atendente'),
('Luis', 'luis@email.com', '123', 'Atendente'); 


insert into livro (titulo, autor, genero, ano_publicacao, descricao) values
('Diario de uma maçã', 'Catarina Greenchin', 'Humor','2000', 'Uma coletânea de poemas reflexivos que exploram a natureza do tempo, da existência e das emoções... de uma maçã.'),
('Códigos do Amanhã', 'Mariana Alves', 'Ficão Científica', '2021', 'Um jovem programador descobre um algoritmo misterioso que pode prever eventos futuros e precisa decidir entre usá-lo para o bem ou para ganho pessoal.'),
('O Labirinto de Aurora', 'Carlos Henrique dos Santos', 'Fantasia', '2018', 'Aurora, uma cidade mística presa entre dimensões, esconde segredos que podem salvar ou destruir o universo. Apenas aqueles que enfrentarem o labirinto podem descobrir a verdade.'),
('Ratatouille', 'Eduardo Lopes', 'Romance', '2007', 'Um chef renomado redescobre sua paixão pela cozinha enquanto explora receitas ancestrais de sua família, misturando amor, perda e superação.'),
('Ecos da Revolução Silenciosa', 'Jacquin Meireles', 'História', '1999', 'Um mergulho profundo nos eventos não documentados que moldaram o movimento de resistência pacífica de um pequeno país e sua luta por independência.');


insert into cliente (nome, email, telefone) values
('Pietro Anderson da Rosa', 'pietro_anderson_darosa@gmail.com',  '92 99911-5105'),
('Otávio Kevin da Silva', 'otavio_kevin@gmail.com', '92 98891-1786'),
('Vera Stefany Benedita Carvalho', 'vera_stefany_carvalho@gmail.com', '92 99490-0662'),
('Isabela Isis Drumond', 'isabela_isis_drumond@gmail.com', '92 99984-6341'),
('Tânia Sebastiana Analu da Conceição', 'tania_daconceicao@gmail.com', '92 99490-8869');


insert into emprestimo(dt_emprestimo, dt_prevista_devolucao, cliente_id, livro_id) values
('2023-09-03', '2023-10-03', 1, 1),
('2023-10-25', '2023-11-25', 2, 2),
('2023-12-20', '2024-01-20', 3, 3),
('2024-02-10', '2024-03-10', 4, 4),
('2024-03-10', '2024-04-12', 4, 4),
('2024-04-05', '2024-05-05', 5, 5);

		
update livro set 
status_disponibilidade = "Emprestado"
where id = 5;


insert into devolucao(emprestimo_id, dt_devolucao, qt_dias_atraso, vl_multa) values
(1, '2023-10-03', null, 0),
(2, '2023-11-25', null, 0),
(3, '2024-01-20', null, 0),
(4, '2024-03-10', null, 0),
(5, '2024-04-13', 1, 1);


update emprestimo set
status_vigor = "Inativo"
where id = 1 or id = 2 or id = 3 or id = 4 or id = 5;


UPDATE cliente c
INNER JOIN emprestimo e ON c.id = e.cliente_id
SET c.status_acesso = 'Bloqueado'
WHERE e.status_vigor = 'Ativo' AND CURDATE() > DATE_ADD(e.dt_prevista_devolucao, INTERVAL 10 DAY);
-- ----------------------------------------------------------------------------------------

select * from usuario;
select * from livro;
select * from cliente;
select * from emprestimo;
select * from devolucao;











