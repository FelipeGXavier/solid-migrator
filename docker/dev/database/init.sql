create table notice(
id serial,
code text,
openingDate timestamp,
finalDate timestamp,
object text,
migrated int
);

create table dashboard(
id serial,
migrated int
);

insert into notice values (1, '220/2020', '2020-10-10 00:00:00', '2020-10-10 00:00:00', 'Teste', 0);

