
-- Special statements for oracle:post databases
create sequence seq_q_sql nocache;
create sequence seq_q_parameters nocache;
create sequence seq_q_results nocache;
create sequence seq_q_filters nocache;
create sequence seq_q_properties nocache;

create or replace trigger tr_lm_q_sql
 before update on q_sql
 for each row begin :new.LAST_MODIFIED:=SYSDATE; end tr_lm_q_sql;;
create or replace trigger tr_lm_q_parameters
 before update on q_parameters
  for each row begin :new.LAST_MODIFIED:=SYSDATE; end tr_lm_q_parameters;;
create or replace trigger tr_lm_q_results
 before update on q_results
 for each row begin :new.LAST_MODIFIED:=SYSDATE; end tr_lm_q_results;;
create or replace trigger tr_lm_q_filters
 before update on q_filters
 for each row begin :new.LAST_MODIFIED:=SYSDATE; end tr_lm_q_filters;;
create or replace trigger tr_lm_q_properties
 before update on q_properties
  for each row begin :new.LAST_MODIFIED:=SYSDATE; end tr_lm_q_properties;;

comment on table q_sql is 'The SQL queries.';
comment on table q_parameters is 'The SQL or filter parameters.';
comment on table q_results is 'The query results.';
comment on table q_filters is 'The SQL filters.';
comment on table q_properties is 'The result properties.';

comment on table q_filter_operators is 'The filter operators. @static name';
comment on table q_result_cardinalities is 'The cardinality of the result. @static name';
comment on table q_query_types is 'The type of query. @static name';
comment on table q_implementations is 'The implementation which would include the SQL. @static name';

