create schema GameRatingsDB;
use GameRatingsDB;

create table Usuario (
    username varchar(20) unique not null,
    password varchar(100) not null,
    mail varchar(100) unique not null,
	fechaRegistro Date not null,
    activado bit not null,
    primary key(username)
);

create table Token (
	token varchar(123) unique not null,
    fechaCreacion Datetime not null,
    fechaValidez Datetime not null,
    primary key(token)
);

create table Game (
	nombre varchar(75) unique not null,
    plataforma1 varchar(40) not null,
	plataforma2 varchar(40),
	plataforma3 varchar(40),
    desarrolladora varchar(50) not null,
    genero1 varchar(25) not null,
    genero2 varchar(25),
    genero3 varchar(25),
    notaMedia float default 0 not null,
    fechaLanzamiento Date not null,
    fechaRegistro Date not null,
    primary key(nombre)
);

create table Review (
	idReview int unique not null auto_increment,
    username varchar(20) not null,
    videojuego varchar(75) not null,
    titulo varchar (75) not null,
    nota int not null,
    comentario varchar(500) not null,
    fechaRegistro Date not null,
    primary key(idReview),
    foreign key(username) references Usuario (username)
		on delete cascade on update cascade,
	foreign key(videojuego) references Game (nombre)
		on delete cascade on update cascade
);


delimiter $$
create trigger actualizarNotaMediaInsert
after insert on Review 
for each Row
begin 
	update Game 
    set notaMedia = (
		select avg(nota) 
        from Review 
        where videojuego = NEW.videojuego
	) 
	where nombre = NEW.videojuego; 
end$$

delimiter $$
create trigger actualizarNotaMediaUpdate
after update on Review 
for each Row
begin 
	update Game 
    set notaMedia = (
		select avg(nota) 
        from Review 
        where videojuego = NEW.videojuego
	) 
	where nombre = NEW.videojuego; 
end$$

delimiter $$
create trigger actualizarNotaMediaDelete
after delete on Review
for each row
begin
    declare total_notas int;
    declare total_reviews int;
    set total_notas = (select sum(nota) from Review where videojuego = old.videojuego);
    set total_reviews = (select count(*) from Review where videojuego = old.videojuego);
    if total_reviews > 0 then
        update Game set notaMedia = total_notas/total_reviews where nombre = old.videojuego;
    else
        update Game set notaMedia = 0 where nombre = old.videojuego;
    end if;
end$$



insert into Usuario values('admin', 'asPnyoYyuVLIAnZfMQ8blw==', 'gameratingsweb@gmail.com', '2023-04-06', 1);
insert into Game values('Super Mario Galaxy', 'Nintendo Wii', 'Nintendo Switch', null, 'Nintendo', 'Acción', 'Plataformas', null, 5, '2007-11-01', '2023-06-04');
insert into Review values(1, 'admin', 'Super Mario Galaxy', 'Juegazo', 9, 'Divino de la muerte', '2023-02-14');

