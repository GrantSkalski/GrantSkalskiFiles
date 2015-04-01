create table student (
  id integer primary key not null,
  lastname varchar(255) not null collate nocase, 
  firstname varchar(255) not null collate nocase, 
  enrolled date not null,
  unique(lastname,firstname)
)
