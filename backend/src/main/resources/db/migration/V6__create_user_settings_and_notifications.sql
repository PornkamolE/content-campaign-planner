create table if not exists user_notification_settings (
  user_id bigint primary key,
  email_notifications boolean not null default true,
  in_app_alerts boolean not null default true,
  weekly_reports boolean not null default false,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now(),

  constraint fk_user_notification_settings_user
    foreign key (user_id) references users(id) on delete cascade
);

create table if not exists notifications (
  id bigserial primary key,
  user_id bigint not null,
  title varchar(255) not null,
  message text,
  is_read boolean not null default false,
  notification_type varchar(50) not null default 'INFO',
  action_url varchar(500),
  created_at timestamptz not null default now(),

  constraint fk_notifications_user
    foreign key (user_id) references users(id) on delete cascade
);

create index if not exists idx_notifications_user_id on notifications(user_id);
create index if not exists idx_notifications_user_id_is_read on notifications(user_id, is_read);