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

