create table if not exists campaigns (
  id bigserial primary key,
  code varchar(50) not null unique,
  name varchar(255) not null,
  description text,
  status varchar(50) not null default 'DRAFT',
  priority varchar(50) not null default 'MEDIUM',
  start_date date,
  end_date date,
  total_budget numeric(15,2) not null default 0,
  spent_budget numeric(15,2) not null default 0,
  primary_goal varchar(255),
  primary_goal_value varchar(100),
  current_reach bigint not null default 0,

  target_demographics text,
  target_interests text,
  target_locations text,

  owner_user_id bigint,
  created_by bigint,
  updated_by bigint,

  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now(),

  constraint fk_campaigns_owner_user
    foreign key (owner_user_id) references users(id) on delete set null,

  constraint fk_campaigns_created_by
    foreign key (created_by) references users(id) on delete set null,

  constraint fk_campaigns_updated_by
    foreign key (updated_by) references users(id) on delete set null
);

create index if not exists idx_campaigns_status on campaigns(status);
create index if not exists idx_campaigns_owner_user_id on campaigns(owner_user_id);
create index if not exists idx_campaigns_start_date on campaigns(start_date);
create index if not exists idx_campaigns_end_date on campaigns(end_date);

create table if not exists campaign_platforms (
  id bigserial primary key,
  campaign_id bigint not null,
  platform_code varchar(50) not null,
  created_at timestamptz not null default now(),

  constraint fk_campaign_platforms_campaign
    foreign key (campaign_id) references campaigns(id) on delete cascade,

  constraint uq_campaign_platforms_campaign_platform
    unique (campaign_id, platform_code)
);

create index if not exists idx_campaign_platforms_campaign_id on campaign_platforms(campaign_id);