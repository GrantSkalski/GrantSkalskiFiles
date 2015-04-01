create table student_tutor (
  id int auto_increment primary key not null,
  student_id int not null,
  tutor_id int not null,
  report text not null,
  key(student_id),
  key(tutor_id),
  unique(student_id, tutor_id)
)
