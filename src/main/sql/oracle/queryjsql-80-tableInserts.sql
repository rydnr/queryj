
-- Generated SQL Insert statements
-- --------------------------------------------------------------------
--     Target Database:   oracle
--     SQL Generator:     tedia2sql -- v1.2.12
--     Generated at:      Fri Aug 12 13:32:02 2005
--     Input Files:       ../../docs/queryj-sql-per.dia


-- inserts for q_filter_operators
insert into q_filter_operators values ( 1, 'and' ) ;
insert into q_filter_operators values ( 2, 'or' ) ;

-- inserts for q_result_cardinalities
insert into q_result_cardinalities values ( 1, 'none' ) ;
insert into q_result_cardinalities values ( 2, 'single' ) ;
insert into q_result_cardinalities values ( 3, 'multiple' ) ;

-- inserts for q_query_types
insert into q_query_types values ( 1, 'select' ) ;
insert into q_query_types values ( 2, 'insert' ) ;
insert into q_query_types values ( 3, 'update' ) ;
insert into q_query_types values ( 4, 'delete' ) ;

-- inserts for q_implementations
insert into q_implementations values ( 1, 'all' ) ;
insert into q_implementations values ( 2, 'mysql' ) ;
insert into q_implementations values ( 3, 'xml' ) ;
insert into q_implementations values ( 4, 'oracle' ) ;

