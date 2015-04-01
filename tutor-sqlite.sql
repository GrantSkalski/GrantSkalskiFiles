create table tutor (
  id integer primary key not null,
  lastname varchar(255) not null collate nocase, 
  firstname varchar(255) not null collate nocase, 
  email varchar(255) not null collate nocase, 
  subject_id integer key not null,
  unique(lastname,firstname)
)
