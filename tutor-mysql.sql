create table tutor (
  id int auto_increment primary key not null,
  lastname varchar(255) not null, 
  firstname varchar(255) not null, 
  email varchar(255) not null, 
  subject_id int not null,
  key(subject_id),
  unique(lastname,firstname)
)
