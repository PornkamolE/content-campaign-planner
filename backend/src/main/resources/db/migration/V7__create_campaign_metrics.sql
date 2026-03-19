create table if not exists campaign_metrics (
  id bigserial primary key,
  campaign_id bigint not null,
  metric_key varchar(100) not null,
  metric_label varchar(255) not null,
  metric_value numeric(18,4),
  metric_unit varchar(50),
  metric_change_percent numeric(10,4),
  extra_text varchar(255),
  created_at timestamptz not null default now(),
  updated_at timestamptz not null default now(),

  constraint fk_campaign_metrics_campaign
    foreign key (campaign_id) references campaigns(id) on delete cascade,

  constraint uq_campaign_metrics_campaign_metric_key
    unique (campaign_id, metric_key)
);

create index if not exists idx_campaign_metrics_campaign_id on campaign_metrics(campaign_id);