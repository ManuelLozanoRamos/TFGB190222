create table Usuario (
    username varchar(20) unique not null,
    password varchar(100) not null,
	fechaRegistro Date not null,
    primary key(username)
);

create table Game (
	nombre varchar(75) unique not null,
    plataforma varchar(40) not null,
    desarrolladora varchar(50) not null,
    genero1 varchar(25) not null,
    genero2 varchar(25),
    genero3 varchar(25),
    notaMedia float default 5,
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
    comentario varchar(200) not null,
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
for each Row
begin 
	update Game 
    set notaMedia = (
		select avg(nota)
        from Review 
        where videojuego = OLD.videojuego
	) 
	where nombre = OLD.videojuego; 
    
    IF (SELECT notaMedia FROM Game WHERE nombre = OLD.videojuego) IS NULL THEN
        UPDATE Game SET notaMedia = 5 WHERE nombre = OLD.videojuego;
    END IF;
    
end$$


insert into Usuario values('admin', 'asPnyoYyuVLIAnZfMQ8blw==', '2023-04-06');
insert into Game values('Super Mario Galaxy', 'Nintendo Wii', 'Nintendo', 'Acción', 'Plataformas', null, 5, '2007-11-01', '2023-06-04');
insert into Review values(1, 'admin', 'Super Mario Galaxy', 'Juegazo', 9, 'Divino de la muerte', '2023-02-14');

select * from Review where videojuego='Super Mario Galaxy';
select * from Usuario;
select * from Review;
select * from Game;
