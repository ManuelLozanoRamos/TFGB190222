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



insert into Usuario values('admin', 'asPnyoYyuVLIAnZfMQ8blw==', 'gameratingsweb@gmail.com', '2023-05-20', 1);
insert into Usuario values('usuario', 'ICKlNhENmCWW2Aws5m2T/A==', 'usuario@gmail.com', '2023-05-20', 1);

insert into Game values('Super Mario Galaxy', 'Nintendo Wii', 'Nintendo Switch', null, 'Nintendo', 'Accion', 'Plataformas', null, 0, '2007-11-01', '2023-05-20');
insert into Game values('The Last of Us', 'PlayStation 3', null, null, 'Naughty Dog', 'Accion', 'Aventuras', 'Survival horror', 0, '2013-06-14', '2023-05-20');
insert into Game values('Xenoblade Chronicles 2', 'Nintendo Switch', null, null, 'Nintendo', 'JRPG', 'Aventuras', null, 0, '2017-12-01', '2023-05-20');
insert into Game values('Pokemon Diamante', 'Nintendo DS', null, null, 'Game Freak', 'Aventuras', 'JRPG', null, 0, '2006-09-28', '2023-05-20');
insert into Game values('Halo: Combat Evolved', 'Xbox', 'Xbox 360', null, 'Bungie Studios', 'Shooter', 'Accion', null, 0, '2001-11-15', '2023-05-20');
insert into Game values('The Legend of Zelda: Ocarina of Time', 'Nintendo 64', 'Nintendo 3DS', null, 'Nintendo', 'Puzzles', 'Aventuras', 'Accion', 0, '1998-11-21', '2023-05-20');

insert into Review values(1, 'usuario', 'Super Mario Galaxy', 'El mejor mario en 3D', 9, 'Super Mario Galaxy es un titulo inolvidable. Con su innovador gameplay espacial y niveles cautivadores, te sumerges en una odisea cosmica llena de diversion. Los controles son precisos, la musica es magica y los graficos deslumbrantes. Cada galaxia es unica y desafiante, con sorpresas en cada esquina. La historia te atrapa y te conecta emocionalmente con los personajes. Es una experiencia de juego imprescindible que te dejara maravillado.', '2023-05-20');
insert into Review values(2, 'admin', 'The Last of Us', 'Produccion de pelicula', 8, 'The Last of Us es una obra maestra del genero de supervivencia. Con una narrativa emotiva y realista, graficos impresionantes y un combate intenso, ofrece una experiencia inolvidable. Una historia impactante y personajes memorables hacen de este juego un imprescindible para los amantes de los videojuegos.', '2023-05-20');
insert into Review values(3, 'admin', 'Xenoblade Chronicles 2', 'El mejor JRPG jamas creado', 10, 'Xenoblade Chronicles 2 es una obra maestra epica. Su vasto mundo abierto, personajes carismaticos y emocionante historia te absorben por completo. Los combates estrategicos son intensos y adictivos. El diseno visual y la banda sonora son deslumbrantes. Con innumerables misiones secundarias y secretos por descubrir, te garantiza horas de juego. Es una experiencia inolvidable que combina a la perfeccion narrativa, exploracion y accion. Un imprescindible para los amantes de los juegos de rol.', '2023-05-20');
insert into Review values(4, 'usuario', 'Pokemon Diamante', 'El mejor juego de la Nintendo DS', 9, 'Pokemon Diamante es un clasico atemporal que deleita a los fanaticos de la franquicia. Con su cautivadora region de Sinnoh, te embarcas en una emocionante aventura para convertirte en el Campeon Pokemon. Los nuevos Pokemon introducidos en esta entrega son fascinantes y variados. Pokemon Diamante es siendo un juego encantador que te mantendra enganchado durante horas de diversion y nostalgia.', '2023-05-20');
insert into Review values(5, 'admin', 'Halo: Combat Evolved', 'Muy divertido', 7, 'Halo: Combat Evolved revoluciono los juegos de disparos. Su accion frenetica, armas iconicas y un multijugador adictivo lo convierten en un clasico perdurable. Los graficos y el diseno de niveles son impresionantes, sumergiendote en un vasto universo alienigena. La historia epica y la musica envolvente te mantienen enganchado. Es un hito en la industria del videojuego que ha dejado una marca imborrable.', '2023-05-20');
insert into Review values(6, 'usuario', 'The Legend of Zelda: Ocarina of Time', 'El mejor videojuego de todos los tiempos', 10, 'The Legend of Zelda: Ocarina of Time es una obra maestra atemporal. Su cautivadora historia, puzzles ingeniosos y emocionantes batallas te sumergen en un mundo de fantasia inolvidable. Los graficos y la musica son deslumbrantes, creando una atmosfera magica. Con una jugabilidad innovadora y personajes carismaticos, este clasico perdura como uno de los mejores juegos de todos los tiempos. Una experiencia inolvidable que ha dejado huella en la industria del videojuego.', '2023-05-20');
