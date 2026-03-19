alter table users
add column if not exists full_name varchar(255);

alter table users
add column if not exists avatar_url varchar(500);

alter table users
add column if not exists location varchar(255);

alter table users
add column if not exists job_title varchar(150);

alter table users
add column if not exists organization_name varchar(255);

alter table users
add column if not exists plan_name varchar(150);

alter table users
add column if not exists two_factor_enabled boolean not null default false;

update users
set full_name = split_part(email, '@', 1)
where full_name is null;

update users
set organization_name = 'Campaign Planner'
where organization_name is null;

update users
set plan_name = 'Free Plan'
where plan_name is null;