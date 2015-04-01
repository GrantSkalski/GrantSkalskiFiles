create table student (
  id integer auto_increment primary key not null,
  lastname varchar(255) not null, 
  firstname varchar(255) not null, 
  enrolled date not null,
  unique(lastname,firstname)
)
