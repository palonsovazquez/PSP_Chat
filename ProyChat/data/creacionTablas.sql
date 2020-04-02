DROP TABLE  if exists UsuariosChat;
DROP TABLE  if exists Mensaje;
DROP TABLE  if exists Chat;
DROP TABLE  if exists Usuario;
create table Usuario(
idUsuario varchar(36) primary key NOT NULL ,
nombre varchar(30),
secLevel integer
);
CREATE TABLE Chat(
idChat varchar(36) primary key NOT NULL ,
nombre varchar(30)
);
create table Mensaje(
idMensaje varchar(36) primary key NOT NULL ,
idUsuario varchar(36) NOT NULL ,
idChat varchar(36) NOT NULL,
texto varchar(200) NOT NULL,
fecha Long ,
foreign key (idUsuario) references Usuario(idUsuario),
foreign key (idChat) references Chat(idChat)
);

CREATE TABLE UsuariosChat(
idUsuario varchar(36) NOT NULL ,
idChat varchar(36) NOT NULL,
foreign key (idUsuario) references Usuario(idUsuario),
foreign key (idChat) references Chat(idChat)
primary key (idUsuario,idChat)
);
INSERT INTO Usuario(idUsuario,nombre,secLevel) values ('87d5564e-51e1-4fb2-ab25-760818521ea6','jose',0);

INSERT INTO Usuario(idUsuario,nombre,secLevel) values ('87d5564e-51e1-4fb2-ab25-760818521ea9','maria',0);


INSERT INTO Chat(idChat,nombre) values ('00000000-0000-0000-0000-000000000000','GrupalGeneral');


INSERT INTO Mensaje(idMensaje,idUsuario,idChat,fecha,texto) values ('8eecd038-d181-4346-a3a8-abcdc1351a55','87d5564e-51e1-4fb2-ab25-760818521ea6','00000000-0000-0000-0000-000000000000','15102','hola');



INSERT INTO UsuariosChat(idChat,idUsuario) values ('00000000-0000-0000-0000-000000000000','87d5564e-51e1-4fb2-ab25-760818521ea6');
INSERT INTO UsuariosChat(idChat,idUsuario) values ('00000000-0000-0000-0000-000000000000','87d5564e-51e1-4fb2-ab25-760818521ea9');