Feature: DAO template

  Scenario: DAO template compiles

    Given the following table:
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

    When I generate with DAO.stg

    Then the generated UserDAO.java compiles successfully
