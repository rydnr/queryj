
-- Generated SQL Constraints Drop statements
-- --------------------------------------------------------------------
--     Target Database:   oracle
--     SQL Generator:     tedia2sql -- v1.2.12
--     Generated at:      Fri Aug 12 13:32:02 2005
--     Input Files:       ../../docs/queryj-sql-per.dia

drop index i_q_sql_01;
drop index i_q_properties_01;
drop index i_q_parameters_01;
drop index i_q_parameters_02;
drop index i_q_filters_01;
drop index i_q_filters_02;
alter table q_sql drop constraint fk_q_sql_implementations ;
alter table q_sql drop constraint fk_q_query_types ;
alter table q_results drop constraint fk_q_result_cardinalities ;
alter table q_properties drop constraint fk_q_result_properties ;
alter table q_filters drop constraint fk_q_filter_operators ;
alter table q_sql drop constraint fk_q_sql_result ;
alter table q_parameters drop constraint fk_q_sql_parameter ;
alter table q_filters drop constraint fk_q_parent_filter ;
alter table q_filters drop constraint fk_q_sql_filter ;
alter table q_parameters drop constraint fk_q_filter_parameters ;

