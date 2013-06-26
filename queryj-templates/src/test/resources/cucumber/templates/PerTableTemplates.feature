Feature: Per-table templates

  Scenario Outline: Per-table templates compile for a type 1 table

    Given the following tables:
    | table | parent table | decorated | relationship | static |
    | user  |              |           |              |        |

    And the following columns:
    | table | column        | type      | pk    | allows null | readonly | sequence  | keyword | boolean | length | precision |
    | user  | user_id       | number    | true  | false       | false    | seq_users |         |         |     10 |         0 |
    | user  | company_id    | number    | false | true        | false    |           |         |         |     10 |         0 |
    | user  | name          | varchar   | false | false       | false    |           |         |         |     30 |           |
    | user  | email         | varchar   | false | false       | false    |           |         |         |     30 |         0 |
    | user  | registered    | number    | false | false       | false    |           |         | 0,1     |      1 |         1 |
    | user  | creation_date | timestamp | false | false       | true     |           | sysdate |         |        |           |

    And the following foreign keys:
    | source table | source columns | target table | allows null |
    | user         | company_id     | companies    | true        |

    And the following queries:
      |       id      |     name      |  dao |  type  |                     value                         |
      | first.select  | first-select  | user | select | select user_id, name from users where user_id = ? |
      | second.select | second-select | user | select | select user_id, name from users where name = ?    |

    And the following query parameters:
      |      id      |      sql       | index |  type  |  name  |
      | user.user_id |  first.select  |   1   |  long  | userId |
      | user.name    |  second.select |   1   | String |  name  |

    When I generate with per-table <template>.stg for Oracle

    Then the generated per-table <output> file compiles successfully

    Examples:
      | template           | output                            |
      | DAO                | OracleUserDAO.java                |
      | BaseDAO            | UserDAO.java                      |
      | DAOFactory         | UserDAOFactory.java               |
      | PkStatementSetter  | OracleUserPkStatementSetter.java  |
      | ResultSetExtractor | OracleUserResultSetExtractor.java |
      | BaseValueObject    | AbstractUserValueObject.java      |
      | ValueObject        | UserValueObject.java              |
      | ValueObjectFactory | UserValueObjectFactory.java       |
      | ValueObjectImpl    | UserValueObjectImpl.java          |

  Scenario Outline: Per-table templates compiles for a type 2 table

    Given the following tables:
      | table       | parent table | decorated | relationship | static |
      | car_brands  |              |           |              | name   |

    And the following columns:
      | table      | column    | type      | pk    | allows null | readonly | sequence  | keyword | boolean | length | precision |
      | car_brands | brand_id  | number    | true  | false       | false    |           |         |         |     10 |         0 |
      | car_brands | name      | varchar   | false | false       | false    |           |         |         |     30 |           |

    And the following values:
      | car_brands | brand_id |       name |
      | car_brands |        1 |       SEAT |
      | car_brands |        2 |    CITROEN |
      | car_brands |        3 | VOLKSWAGEN |
      | car_brands |        4 |       AUDI |

    When I generate with per-table <template>.stg for Oracle

    Then the generated per-table <output> file compiles successfully

    Examples:
      | template           | output                            |
      | DAO                | OracleUserDAO.java                |
      | BaseDAO            | UserDAO.java                      |
      | DAOFactory         | UserDAOFactory.java               |
      | PkStatementSetter  | OracleUserPkStatementSetter.java  |
      | ResultSetExtractor | OracleUserResultSetExtractor.java |
      | BaseValueObject    | AbstractUserValueObject.java      |
      | ValueObject        | UserValueObject.java              |
      | ValueObjectFactory | UserValueObjectFactory.java       |
      | ValueObjectImpl    | UserValueObjectImpl.java          |
