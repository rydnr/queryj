
-- Generated SQL Constraints Drop statements
-- --------------------------------------------------------------------
--     Target Database:   innodb
--     SQL Generator:     tedia2sql -- v1.2.8
--     Generated at:      Thu Aug 18 08:54:25 2005
--     Input File:        ../../docs/queryj-sql-per.dia

drop index i_q_sql_01 on q_sql;
drop index i_q_properties_01 on q_properties;
drop index i_q_parameters_01 on q_parameters;
drop index i_q_parameters_02 on q_parameters;
drop index i_q_filters_01 on q_filters;
drop index i_q_filters_02 on q_filters;
-- alter table q_sql drop constraint fk_q_sql_implementations --(is implicitly done)
-- alter table q_sql drop constraint fk_q_query_types --(is implicitly done)
-- alter table q_results drop constraint fk_q_result_cardinalities --(is implicitly done)
-- alter table q_properties drop constraint fk_q_result_properties --(is implicitly done)
-- alter table q_filters drop constraint fk_q_filter_operators --(is implicitly done)
-- alter table q_sql drop constraint fk_q_sql_result --(is implicitly done)
-- alter table q_parameters drop constraint fk_q_sql_parameter --(is implicitly done)
-- alter table q_filters drop constraint fk_q_parent_filter --(is implicitly done)
-- alter table q_filters drop constraint fk_q_sql_filter --(is implicitly done)
-- alter table q_parameters drop constraint fk_q_filter_parameters --(is implicitly done)

