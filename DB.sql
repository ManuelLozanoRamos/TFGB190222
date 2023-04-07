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
    generos varchar(79) not null,
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

insert into Usuario values('admin', 'asPnyoYyuVLIAnZfMQ8blw==', '2023-06-04');
insert into Game values('Super Mario Galaxy', 'Nintendo Wii', 'Nintendo', 'Acción, Plataformas', '2007-01-11', '2023-06-04');
insert into Review values(1, 'admin', 'Super Mario Galaxy', 'Juegazo', 9, 'Divino de la muerte', '2023-02-14');

select * from Review where videojuego='Super Mario Galaxy';
select * from Usuario;
select * from Review;
