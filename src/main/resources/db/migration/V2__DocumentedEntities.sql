create table if not exists address (
  id      serial,
  address varchar(30) not null,

  constraint address_id_pk  primary key (id),
  constraint address_unique unique (address),
  constraint address_length check (char_length(address) > 0)
);



create table if not exists room (
  id         serial,
  room       varchar(30) not null,
  address_id integer     not null,

  constraint room_id_pk    primary key (id),
  constraint address_id_fk foreign key (address_id) references address(id) on delete cascade,
  constraint room_length   check (char_length(room) > 0)
);



create table if not exists task (
  id               serial,
  task             varchar(30) not null,
  repeat_in        integer     not null,
  duration         integer     not null default 10,
  score            integer     not null default 5,
  default_proirity integer     not null default 1,
  current_priority integer     not null,
  last_completed   integer     not null default 0,
  room_id          integer     not null,

  constraint task_id_pk             primary key (id),
  constraint room_id_fk             foreign key (room_id) references address(id) on delete cascade,
  constraint task_length            check (char_length(task) > 0),
  constraint repeat_in_limit        check (repeat_in > 0 and repeat_in <= 14),
  constraint duration_limit         check (duration > 0 and duration <= 60),
  constraint score_limit            check (score > 0 and score <= 10),
  constraint default_priority_limit check (default_proirity > 0),
  constraint current_priority_limit check (current_priority >= default_proirity),
  constraint last_completed_limit   check (last_completed >= 0)
);



create table if not exists goal (
  id serial,
  goal varchar(30) not null,
  score_cap integer not null,
  current_score integer not null default 0,
  status boolean default false,

  constraint goal_id_pk          primary key (id),
  constraint goal_length         check (char_length(goal) > 0),
  constraint score_cap_limit     check (score_cap > 0),
  constraint current_score_limit check (current_score >= 0 and current_score <= score_cap)
);