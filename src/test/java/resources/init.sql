CREATE TABLE IF NOT EXISTS notice(
id INT PRIMARY KEY AUTO_INCREMENT,
code VARCHAR(255),
openingDate timestamp,
finalDate timestamp,
object text,
migrated int
);

create table if not exists dashboard(
id INT PRIMARY KEY AUTO_INCREMENT,
migrated int
);

-- insert into notice values (null, '220/2020', '2020-10-10 00:00:00', '2020-10-10 00:00:00', 'Teste', 0);
-- insert into dashboard values (null, 0);
