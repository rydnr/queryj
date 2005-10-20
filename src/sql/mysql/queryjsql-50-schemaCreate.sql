
-- Generated SQL Schema
-- --------------------------------------------------------------------
--     Target Database:   innodb
--     SQL Generator:     tedia2sql -- v1.2.8
--     Generated at:      Thu Aug 18 08:54:25 2005
--     Input File:        ../../docs/queryj-sql-per.dia


-- q_sql
create table q_sql (
  sql_id                    integer not null,
  implementation_id         integer not null,
  query_type_id             integer not null,
  result_id                 integer,
  validate_sql              integer(1) not null,
  value                     varchar(255) not null,
  last_modified             timestamp not null,
  creation_date             timestamp not null,
  constraint pk_q_sql primary key (sql_id)
) type = InnoDB ;

-- q_results
create table q_results (
  result_id                 integer not null,
  result_cardinality_id     integer not null,
  class_name                varchar(200) not null,
  last_modified             timestamp not null,
  creation_date             timestamp not null,
  constraint pk_q_results primary key (result_id)
) type = InnoDB ;

-- q_properties
create table q_properties (
  property_id               integer not null,
  result_id                 integer not null,
  name                      varchar(20) not null,
  type                      varchar(10) not null,
  idx                       integer,
  column_name               varchar(30) not null,
  last_modified             timestamp not null,
  creation_date             timestamp not null,
  constraint pk_q_properties primary key (property_id)
) type = InnoDB ;

-- q_parameters
create table q_parameters (
  parameter_id              integer not null,
  sql_id                    integer,
  filter_id                 integer,
  name                      varchar(20) not null,
  type                      varchar(10) not null,
  idx                       integer(2),
  validation_value          varchar(30) null,
  last_modified             timestamp not null,
  creation_date             timestamp not null,
  constraint pk_q_parameters primary key (parameter_id)
) type = InnoDB ;

-- q_filters
create table q_filters (
  filter_id                 integer not null,
  filter_operator_id        integer not null,
  parent_filter_id          integer,
  sql_id                    integer,
  negate                    integer(1) not null,
  last_modified             timestamp not null,
  creation_date             timestamp not null,
  constraint pk_q_filters primary key (filter_id)
) type = InnoDB ;

-- q_filter_operators
create table q_filter_operators (
  filter_operator_id        integer not null,
  name                      varchar(5) not null,
  constraint pk_q_filter_operators primary key (filter_operator_id)
) type = InnoDB ;

-- q_query_types
create table q_query_types (
  query_type_id             integer not null,
  name                      varchar(20) not null,
  constraint pk_q_query_types primary key (query_type_id)
) type = InnoDB ;

-- q_result_cardinalities
create table q_result_cardinalities (
  result_cardinality_id     integer not null,
  name                      varchar(20) not null,
  constraint pk_q_result_cardinalities primary key (result_cardinality_id)
) type = InnoDB ;

-- q_implementations
create table q_implementations (
  implementation_id         integer not null,
  name                      varchar(20) not null,
  constraint pk_q_implementations primary key (implementation_id)
) type = InnoDB ;

