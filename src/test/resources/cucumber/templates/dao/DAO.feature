Feature: DAO template

  Scenario: DAO template compiles

    Given the following tables:
    | repository | table     | parent table | decorated | relationship | static |
    | users      | user      |              |           |              |        |

    And the following columns:
    | table | column        | type      | pk    | allows null | readonly | sequence  | foreing key | keyword | boolean | length | precision |
    | user  | user_id       | number    | true  | false       | false    | seq_users |             |         |         |     10 |         0 |
    | user  | company_id    | number    | false | true        | false    |           | fk1         |         |         |     10 |         0 |
    | user  | name          | varchar   | false | false       | false    |           |             |         |         |     30 |           |
    | user  | email         | varchar   | false | false       | false    |           |             |         |         |     30 |         0 |
    | user  | registered    | number    | false | false       | false    |           |             |         | 0,1     |      1 |         1 |
    | user  | creation_date | timestamp | false | false       | true     |           |             | sysdate |         |        |           |

    And the following foreign keys:
    | id  | source table | source columns | target table | target columns |
    | fk1 | user         | company_id     | companies    | company_id     |

    When I generate with DAO.stg

    Then the generated UserDAO.java compiles successfully
