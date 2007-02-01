
-- Generated SQL Constraints
-- --------------------------------------------------------------------
--     Target Database:   oracle
--     SQL Generator:     tedia2sql -- v1.2.12
--     Generated at:      Fri Aug 12 13:32:02 2005
--     Input Files:       ../../docs/queryj-sql-per.dia

create index i_q_sql_01 on q_sql  (result_id)  tablespace INDX
  pctfree 10
  initrans 2
  storage (initial 4M next 4M minextents 1 maxextents 4096 pctincrease 0) ;
create index i_q_properties_01 on q_properties  (result_id)  tablespace INDX
  pctfree 10
  initrans 2
  storage (initial 4M next 4M minextents 1 maxextents 4096 pctincrease 0) ;
create index i_q_parameters_01 on q_parameters  (sql_id)  tablespace INDX
  pctfree 10
  initrans 2
  storage (initial 4M next 4M minextents 1 maxextents 4096 pctincrease 0) ;
create index i_q_parameters_02 on q_parameters  (filter_id)  tablespace INDX
  pctfree 10
  initrans 2
  storage (initial 4M next 4M minextents 1 maxextents 4096 pctincrease 0) ;
create index i_q_filters_01 on q_filters  (sql_id)  tablespace INDX
  pctfree 10
  initrans 2
  storage (initial 4M next 4M minextents 1 maxextents 4096 pctincrease 0) ;
create index i_q_filters_02 on q_filters  (parent_filter_id)  tablespace INDX
  pctfree 10
  initrans 2
  storage (initial 4M next 4M minextents 1 maxextents 4096 pctincrease 0) ;
alter table q_sql add constraint fk_q_sql_implementations
  foreign key (implementation_id)
  references q_implementations (implementation_id)  ;
alter table q_sql add constraint fk_q_query_types
  foreign key (query_type_id)
  references q_query_types (query_type_id)  ;
alter table q_results add constraint fk_q_result_cardinalities
  foreign key (result_cardinality_id)
  references q_result_cardinalities (result_cardinality_id)  ;
alter table q_properties add constraint fk_q_result_properties
  foreign key (result_id)
  references q_results (result_id)  ;
alter table q_filters add constraint fk_q_filter_operators
  foreign key (filter_operator_id)
  references q_filter_operators (filter_operator_id)  ;
alter table q_sql add constraint fk_q_sql_result
  foreign key (result_id)
  references q_results (result_id)  ;
alter table q_parameters add constraint fk_q_sql_parameter
  foreign key (sql_id)
  references q_sql (sql_id)  ;
alter table q_filters add constraint fk_q_parent_filter
  foreign key (parent_filter_id)
  references q_filters (filter_id) one ;
alter table q_filters add constraint fk_q_sql_filter
  foreign key (sql_id)
  references q_sql (sql_id)  ;
alter table q_parameters add constraint fk_q_filter_parameters
  foreign key (filter_id)
  references q_filters (filter_id)  ;

