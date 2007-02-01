
-- Generated SQL Schema
-- --------------------------------------------------------------------
--     Target Database:   oracle
--     SQL Generator:     tedia2sql -- v1.2.12
--     Generated at:      Fri Aug 12 13:32:02 2005
--     Input Files:       ../../docs/queryj-sql-per.dia


-- q_sql
-- The SQL queries.
create table q_sql (
  sql_id                    number not null,	-- use seq_sql
  implementation_id         number not null,	-- The specific implementation used to generate code for this query.
  query_type_id             number not null,	-- The type of query.
  result_id                 number,	-- The result identifier.
  validate                  number(1) not null,	-- The draw the prize is associated to.
  value                     varchar2(1000) not null,	-- The sql sentence.
  last_modified             date default SYSDATE not null,	-- When the record was last modified.
  creation_date             date default SYSDATE not null,	-- When the record was created.
  constraint pk_Q_sql primary key (sql_id)
    using index
    tablespace INDX
    pctfree 10
    initrans 2
    storage (initial 4M next 4M minextents 1 maxextents 4096 pctincrease 0)
) 
  tablespace DATA
  pctfree 10
  initrans 1
  storage (initial 4M next 4M minextents 1 maxextents 4096 pctincrease 0) ;

-- q_results
-- The SQL results.
create table q_results (
  result_id                 number not null,	-- use seq_results
  result_cardinality_id     number not null,	-- How many instances returned.
  class_name                varchar2(200) not null,	-- The class name whose instances contain the result data.
  last_modified             date default SYSDATE not null,	-- When the record was last modified.
  creation_date             date default SYSDATE not null,	-- When the record was created.
  constraint pk_Q_results primary key (result_id)
    using index
    tablespace INDX
    pctfree 10
    initrans 2
    storage (initial 4M next 4M minextents 1 maxextents 4096 pctincrease 0)
) 
  tablespace DATA
  pctfree 10
  initrans 1
  storage (initial 4M next 4M minextents 1 maxextents 4096 pctincrease 0) ;

-- q_properties
-- The SQL results' properties.
create table q_properties (
  property_id               number not null,	-- use seq_properties
  result_id                 number not null,	-- The result identifier.
  name                      varchar2(20) not null,	-- The property name (used as parameter name).
  type                      varchar2(10) not null,	-- The type of the property.
  index                     number,	-- The parameter index, used unless column_name is not null.
  column_name               varchar2(30) not null,	-- The column name, used if index is null.
  last_modified             date default SYSDATE not null,	-- When the record was last modified.
  creation_date             date default SYSDATE not null,	-- When the record was created.
  constraint pk_Q_properties primary key (property_id)
    using index
    tablespace INDX
    pctfree 10
    initrans 2
    storage (initial 4M next 4M minextents 1 maxextents 4096 pctincrease 0)
) 
  tablespace DATA
  pctfree 10
  initrans 1
  storage (initial 4M next 4M minextents 1 maxextents 4096 pctincrease 0) ;

-- q_parameters
-- The SQL parameters.
create table q_parameters (
  parameter_id              number not null,	-- use seq_parameters
  sql_id                    number,	-- The query identifier (cannot be not null if filter_id is not null).
  filter_id                 number,	-- The filter identifier (cannot be not null if sql_id is not null)
  name                      varchar2(20) not null,	-- The parameter name.
  type                      varchar2(10) not null,	-- The parameter type.
  index                     number(2) not null,	-- The index of the parameter in the SQL sentence.
  validation_value          varchar2(30),	-- The value to validate the query.
  last_modified             date default SYSDATE not null,	-- When the record was last modified.
  creation_date             date default SYSDATE not null,	-- When the record was created.
  constraint pk_Q_parameters primary key (parameter_id)
    using index
    tablespace INDX
    pctfree 10
    initrans 2
    storage (initial 4M next 4M minextents 1 maxextents 4096 pctincrease 0)
) 
  tablespace DATA
  pctfree 10
  initrans 1
  storage (initial 4M next 4M minextents 1 maxextents 4096 pctincrease 0) ;

-- q_filters
-- The SQL filters.
create table q_filters (
  filter_id                 number not null,	-- use seq_filters
  filter_operator_id        number not null,	-- The operator used in this filter.
  parent_filter_id          number,	-- The identifier of the parent filter.
  sql_id                    number,	-- The sql identifier (for the parent filter).
  negate                    number(1) not null,	-- Whether this filter should be negated when including in the parent.
  last_modified             date default SYSDATE not null,	-- When the record was last modified.
  creation_date             date default SYSDATE not null,	-- When the record was created.
  constraint pk_Q_filters primary key (filter_id)
    using index
    tablespace INDX
    pctfree 10
    initrans 2
    storage (initial 4M next 4M minextents 1 maxextents 4096 pctincrease 0)
) 
  tablespace DATA
  pctfree 10
  initrans 1
  storage (initial 4M next 4M minextents 1 maxextents 4096 pctincrease 0) ;

-- q_filter_operators
-- The SQL filter operators.
create table q_filter_operators (
  filter_operator_id        number not null,
  name                      varchar2(5) not null,	-- The operator itself.
  constraint pk_Q_filter_operators primary key (filter_operator_id)
    using index
    tablespace INDX
    pctfree 10
    initrans 2
    storage (initial 128K next 128K minextents 1 maxextents 4096 pctincrease 0)
) 
  tablespace DATA
  pctfree 10
  initrans 1
  storage (initial 128K next 128K minextents 1 maxextents 4096 pctincrease 0) ;

-- q_query_types
-- The type of the query.
create table q_query_types (
  query_type_id             number not null,
  name                      varchar2(20) not null,	-- The query type.
  constraint pk_Q_query_types primary key (query_type_id)
    using index
    tablespace INDX
    pctfree 10
    initrans 2
    storage (initial 128K next 128K minextents 1 maxextents 4096 pctincrease 0)
) 
  tablespace DATA
  pctfree 10
  initrans 1
  storage (initial 128K next 128K minextents 1 maxextents 4096 pctincrease 0) ;

-- q_result_cardinalities
-- The nature of the result, in terms of its cardinality.
create table q_result_cardinalities (
  result_cardinality_id     number not null,
  name                      varchar2(20) not null,	-- The query type.
  constraint pk_Q_result_cardinalities primary key (result_cardinality_id)
    using index
    tablespace INDX
    pctfree 10
    initrans 2
    storage (initial 128K next 128K minextents 1 maxextents 4096 pctincrease 0)
) 
  tablespace DATA
  pctfree 10
  initrans 1
  storage (initial 128K next 128K minextents 1 maxextents 4096 pctincrease 0) ;

-- q_implementations
-- The type of the generated code.
create table q_implementations (
  implementation_id         number not null,
  name                      varchar2(20) not null,	-- The implementation name.
  constraint pk_Q_implementations primary key (implementation_id)
    using index
    tablespace INDX
    pctfree 10
    initrans 2
    storage (initial 128K next 128K minextents 1 maxextents 4096 pctincrease 0)
) 
  tablespace DATA
  pctfree 10
  initrans 1
  storage (initial 128K next 128K minextents 1 maxextents 4096 pctincrease 0) ;










