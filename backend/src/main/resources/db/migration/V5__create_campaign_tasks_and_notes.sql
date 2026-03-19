create table if not exists campaign_tasks (
  id bigserial primary key,
  campaign_id bigint not null,
  title varchar(255) not null,
  description text,
  status varchar(50) not null default 'PENDING',
  priority varchar(50) not null default 'MEDIUM',
  due_at timestamptz,
  completed_at timestamptz,
  assigned_user_id bigint,
  created_by bigint,
  updated_by bigint,
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now(),

  constraint fk_campaign_tasks_campaign
    foreign key (campaign_id) references campaigns(id) on delete cascade,

  constraint fk_campaign_tasks_assigned_user
    foreign key (assigned_user_id) references users(id) on delete set null,

  constraint fk_campaign_tasks_created_by
    foreign key (created_by) references users(id) on delete set null,

  constraint fk_campaign_tasks_updated_by
    foreign key (updated_by) references users(id) on delete set null
);

create index if not exists idx_campaign_tasks_campaign_id on campaign_tasks(campaign_id);
create index if not exists idx_campaign_tasks_status on campaign_tasks(status);
create index if not exists idx_campaign_tasks_assigned_user_id on campaign_tasks(assigned_user_id);

create table if not exists campaign_notes (
  id bigserial primary key,
  campaign_id bigint not null,
  note_type varchar(50) not null default 'INTERNAL_NOTE',
  content text not null,
  created_by bigint,
  created_at timestamptz not null default now(),

  constraint fk_campaign_notes_campaign
    foreign key (campaign_id) references campaigns(id) on delete cascade,

  constraint fk_campaign_notes_created_by
    foreign key (created_by) references users(id) on delete set null
);

create index if not exists idx_campaign_notes_campaign_id on campaign_notes(campaign_id);