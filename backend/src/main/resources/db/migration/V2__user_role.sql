create table if not exists roles (
  id bigserial primary key,
  name varchar(50) not null unique
);

create table if not exists users (
  id bigserial primary key,
  email varchar(255) not null unique,
  password varchar(255) not null,
  enabled boolean not null default true,
  created_at timestamptz not null default now()
);

create table if not exists user_roles (
  user_id bigint not null,
  role_id bigint not null,
  primary key (user_id, role_id),
  constraint fk_user_roles_user foreign key (user_id) references users(id) on delete cascade,
  constraint fk_user_roles_role foreign key (role_id) references roles(id) on delete cascade
);

insert into roles (name) values ('ADMIN') on conflict (name) do nothing;
insert into roles (name) values ('USER') on conflict (name) do nothing;
