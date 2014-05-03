Feature: Per-table templates

  Scenario Outline: Per-table templates compile for a type 1 table

    Given the following tables:
      |    table     | parent table | decorated | relationship |   static    |
      | g_draws      |              |           |              |             |
      | g_draw_types |              |           |              | description |

    And the following columns:
      |     table    |         column        | type      | pk    | allows null | readonly |   sequence    | keyword | boolean | length | precision |
      | g_draws      | g_draw_id             | number    | true  | false       | false    | seq_g_draws   |         |         |     10 |         0 |
      | g_draws      | g_draw_type_id        | number    | false | false       | false    |               |         |         |     10 |         0 |
      | g_draws      | drawing_date          | date      | false | false       | false    |               |         |         |        |           |
      | g_draws      | g_draw_state_id       | number    | false | false       | false    |               |         |         |     10 |         0 |
      | g_draws      | last_modified         | timestamp | false | false       | false    |               |         |         |        |           |
      | g_draws      | creation_date         | timestamp | false | false       | false    |               | sysdate |         |        |           |
      | g_draw_types | g_draw_type_id        | number    | true  | false       | false    | seq_g_draws   |         |         |     10 |         0 |
      | g_draw_types | description           | varchar   | false | false       | false    |               |         |         |     40 |           |
      | g_draw_types | g_lottery_provider_id | number    | false | false       | false    |               | sysdate |         |        |           |

    And the following foreign keys:
      | source table | source columns | target table | allows null |
      | g_draws      | g_draw_type_id | d_draw_types | false       |

    And the following queries:
      |       id      |     name      |     dao      |  type  | matches  |                        value                           | validate |
      | first.select  | first-select  | g_draws      | select | single   | select * from g_draws where g_draw_id = ?              |   true   |
      | second.select | second-select | g_draws      | select | multiple | select * from g_draws where drawing_date > sysdate - 7 |   true   |
      | third.select  | third-select  | g_draw_types | select | multiple | select * from g_draw_types where description like ?    |   true   |
      | fourth.select | fourth-select | g_draws      | select | multiple | select * from g_draws where drawing_date = ?           |   true   |

    And the following query parameters:
      |            id            |     sql       | index |  type  |     name    | validation-value |
      | g_draws.g_draw_id        | first.select  |   1   |  long  | drawId      |         1        |
      | g_draw_types.description | third.select  |   1   | String | description |  'Euromillones'  |
      | g_draws.drawing_date     | fourth.select |   1   |  Date  | drawingDate |   '2014/02/15'   |

    And the following contents:
      |    table     |         row          |
      | g_draw_types | 1, "Euromillions", 1 |
      | g_draw_types | 2, "El_Gordo",     1 |
      | g_draw_types | 3, "Primitiva",    1 |

    When I generate with per-table <template>.stg for Oracle

    Then the generated per-table <output> file compiles successfully

    Then the queries are validated correctly using the following database:
      |     driver     |     url     |     userName     |     password     |
      | oracle.jdbc.driver.OracleDriver | jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=10.34.10.249)(PORT=1521))(CONNECT_DATA=(SERVER=dedicated)(SERVICE_NAME=PRE2))) | QUERYJGAMES | QUERYJGAMES |

  Examples:
    | template           | output                       |
    | DAO                | OracleGDrawDAO.java          |
#    | ResultSetExtractor | GDrawResultSetExtractor.java |
