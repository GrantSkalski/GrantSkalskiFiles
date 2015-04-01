create table student_tutor (
  id integer primary key not null,
  student_id integer not null,
  tutor_id integer key not null,
  report text not null,
  unique(student_id, tutor_id)
)
