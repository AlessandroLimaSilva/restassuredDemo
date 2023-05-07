DROP DATABASE IF EXISTS DADOS_TESTE_API;

CREATE DATABASE IF NOT EXISTS DADOS_TESTE_API;

USE DATABASE DADOS_TESTE_API;

CREATE TABLE projeto(
    NAME VARCHAR(100),
    STATUS INT NOT NULL,
    ENABLED INT NOT NULL,
    VIEW_STATE INT NOT NULL,
    ACCESS_MIN INT NOT NULL,
    DESCRIPTION VARCHAR(240),
    CATEGORY_ID INT NOT NULL,
    INHERIT_GLOBAL INT NOT NULL,
    PRIMARY KEY (NAME)
);

CREATE TABLE usuario(
    USERNAME VARCHAR(240),
    REALNAME VARCHAR(240),
    EMAIL VARCHAR(240),
    ENABLED INT NOT NULL,
    PROTECTED INT NOT NULL,
    ACCESS_LEVEL INT NOT NULL,
    COOKIE_STRING VARCHAR(100),
    PRIMARY KEY (USERNAME)
);

drop table if exists issue cascade;
create table issue (
	id int not null auto_increment primary key,
	summary varchar(64),
	description varchar(64),
	additional_information varchar(64),
	sticky varchar(64),
	project_id decimal (15, 5),
	project_name varchar(64),
	category_id decimal (15, 5),
	category_name varchar(64),
	handler_name varchar(64),
	view_state_id decimal (15, 5),
	view_state_name varchar(64),
	priority_name varchar(64),
	severity_name varchar(64),
	reproducibility_name varchar(64),
	custom_fields_value varchar(64),
	custom_fields_field_id decimal (15, 5),
	custom_fields_field_name varchar(64),
	tags_name varchar(64)

);

##Dados da Tabela de Projeto
INSERT INTO projeto(NAME, STATUS, ENABLED, VIEW_STATE, ACCESS_MIN, DESCRIPTION, CATEGORY_ID, INHERIT_GLOBAL)
VALUES('teste01','10','1','10','10','teste automatizado','1','1'),
('teste02','30','1','10','10','teste automatizado','1','1'),
('teste03','50','1','10','10','teste automatizado','1','1'),
('teste04','70','1','10','10','teste automatizado','1','1'),
('teste05','10','1','10','10','teste automatizado','1','0'),
('teste06','30','1','10','10','teste automatizado','1','0'),
('teste07','50','1','10','10','teste automatizado','1','0'),
('teste08','70','1','10','10','teste automatizado','1','0'),
('teste09','10','1','50','10','teste automatizado','1','1'),
('teste10','30','1','50','10','teste automatizado','1','1'),
('teste11','50','1','50','10','teste automatizado','1','1'),
('teste12','70','1','50','10','teste automatizado','1','1'),
('teste13','10','1','50','10','teste automatizado','1','0'),
('teste14','30','1','50','10','teste automatizado','1','0'),
('teste15','50','1','50','10','teste automatizado','1','0'),
('teste16','70','1','50','10','teste automatizado','1','0');

##Dados da Tabela Usuarios
INSERT INTO usuario(USERNAME,REALNAME,EMAIL,ENABLED,PROTECTED,ACCESS_LEVEL,COOKIE_STRING, TIPO_USUARIO)
VALUES('UserAdministrador','Administrador teste','UserAdministrador@teste.com','1','0','90','UserAdministrador','administrador'),
('UserGerente','Gerente teste','UserGerente@teste.com','1','0','70','UserGerente','gerente'),
('UserDesenvolvedor','Desenvolvedor teste','UserDesenvolvedor@teste.com','1','0','55','UserDesenvolvedor','desenvolvedor'),
('UserAtualizador','Atualizador teste','UserAtualizador@teste.com','1','0','40','UserAtualizador','atualizador'),
('UserRelator','Relator teste','UserRelator@teste.com','1','0','25','UserRelator','relator'),
('UserVisualizador','Visualizador teste','UserVisualizador@teste.com','1','0','10','UserVisualizador','visualizador');

## Banco do mantis Tabela de Projetos
INSERT INTO mantis_project_table(id,name, status, enabled, view_state, access_min, description, category_id, inherit_global)
VALUES('3','teste01','10','1','10','10','teste automatizado','1','1'),
('4','teste02','30','1','10','10','teste automatizado','1','1'),
('5','teste03','50','1','10','10','teste automatizado','1','1'),
('6','teste04','70','1','10','10','teste automatizado','1','1'),
('7','teste05','10','1','10','10','teste automatizado','1','0'),
('8','teste06','30','1','10','10','teste automatizado','1','0'),
('9','teste07','50','1','10','10','teste automatizado','1','0'),
('10','teste08','70','1','10','10','teste automatizado','1','0'),
('11','teste09','10','1','50','10','teste automatizado','1','1'),
('12','teste10','30','1','50','10','teste automatizado','1','1'),
('13','teste11','50','1','50','10','teste automatizado','1','1'),
('14','teste12','70','1','50','10','teste automatizado','1','1'),
('15','teste13','10','1','50','10','teste automatizado','1','0'),
('16','teste14','30','1','50','10','teste automatizado','1','0'),
('17','teste15','50','1','50','10','teste automatizado','1','0'),
('18','teste16','70','1','50','10','teste automatizado','1','0')

## Banco do mantis  Tabela de Usuarios
INSERT INTO mantis_user_table(username,realname,email,enabled,protected,access_level,cookie_string)
VALUES('UserAdministrador','Administrador teste','UserAdministrador@teste.com','1','0','90','UserAdministrador'),
('UserGerente','Gerente teste','UserGerente@teste.com','1','0','70','UserGerente'),
('UserDesenvolvedor','Desenvolvedor teste','UserDesenvolvedor@teste.com','1','0','55','UserDesenvolvedor'),
('UserAtualizador','Atualizador teste','UserAtualizador@teste.com','1','0','40','UserAtualizador'),
('UserRelator','Relator teste','UserRelator@teste.com','1','0','25','UserRelator'),
('UserVisualizador','Visualizador teste','UserVisualizador@teste.com','1','0','10','UserVisualizador');


insert into issue (summary ,description ,additional_information ,sticky ,project_id ,project_name ,category_id ,category_name ,handler_name ,view_state_id ,view_state_name ,priority_name ,severity_name ,reproducibility_name ,custom_fields_value ,custom_fields_field_id ,custom_fields_field_name ,tags_name)
values ('Sample REST issue' ,'Description for sample REST issue.' ,'More info about the issue' ,'false' ,1.0 ,'mantisbt' ,5.0 ,'bugtracker' ,'vboctor' ,10.0 ,'public' ,'normal' ,'trivial' ,'always' ,'Seattle' ,4.0 ,'The City' ,'mantishub');