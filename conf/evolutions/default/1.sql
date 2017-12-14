# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table m_barcode (
  barcode_id                    serial not null,
  barcode                       varchar(255),
  is_deleted                    boolean,
  created_at                    timestamp not null,
  updated_at                    timestamp not null,
  constraint pk_m_barcode primary key (barcode_id)
);

create table m_customer (
  customer_id                   serial not null,
  store_id                      integer,
  barcode_id                    integer,
  customer_number               varchar(255),
  customer_name                 varchar(255),
  birthday                      timestamp,
  gender_div                    varchar(255),
  is_deleted                    boolean,
  created_at                    timestamp not null,
  updated_at                    timestamp not null,
  constraint pk_m_customer primary key (customer_id)
);

create table m_store (
  store_id                      serial not null,
  store_name                    varchar(255),
  is_deleted                    boolean,
  created_at                    timestamp not null,
  updated_at                    timestamp not null,
  constraint pk_m_store primary key (store_id)
);

create table t_usage_history (
  customer_id                   integer,
  service_id                    integer,
  is_deleted                    boolean,
  created_at                    timestamp not null,
  updated_at                    timestamp not null
);

create table t_vital_signs (
  customer_id                   integer,
  body_temperature              float,
  low_blood_pressure            integer,
  high_blood_pressure           integer,
  heart_rate                    integer,
  coma_scale                    integer,
  respiration_rate              varchar(255),
  is_deleted                    boolean,
  created_at                    timestamp not null,
  updated_at                    timestamp not null
);

alter table t_vital_signs add constraint fk_t_vital_signs_customer_id foreign key (customer_id) references m_customer (customer_id) on delete restrict on update restrict;
create index ix_t_vital_signs_customer_id on t_vital_signs (customer_id);


# --- !Downs

alter table if exists t_vital_signs drop constraint if exists fk_t_vital_signs_customer_id;
drop index if exists ix_t_vital_signs_customer_id;

drop table if exists m_barcode cascade;

drop table if exists m_customer cascade;

drop table if exists m_store cascade;

drop table if exists t_usage_history cascade;

drop table if exists t_vital_signs cascade;

