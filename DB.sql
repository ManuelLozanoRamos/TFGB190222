create table Usuario (
    username varchar(20) unique not null,
    password varchar(100) not null,
    primary key(username)
);


create table Review (
	idReview int unique not null auto_increment,
    username varchar(20) not null,
    videojuego varchar(50) not null,
    titulo varchar (50) not null,
    nota int not null,
    comentario varchar(150) not null,
    fecha Date not null,
    primary key(idReview),
    foreign key(username) references Usuario (username)
		on delete cascade on update cascade
);

insert into Usuario values('Pepe01', 'kSVUdC4/sUsJgT09+1QPQQ==');
insert into Review values(1, 'Pepe01', 'SuperMarioGalaxy', 'Juegazo', 9, 'Divino de la muerte', '2023-02-14');

select * from Review where videojuego='SuperMarioGalaxy';
select * from Usuario;
select * from Review;
