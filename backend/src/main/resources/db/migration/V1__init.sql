create table if not exists app_health (
  id bigserial primary key,
  created_at timestamptz not null default now()
);
