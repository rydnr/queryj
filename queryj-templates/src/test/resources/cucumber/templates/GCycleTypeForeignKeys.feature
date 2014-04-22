Feature: G_CYCLE_TYPES foreign keys code compiles

  Scenario Outline: Templates bound to G_CYCLE_TYPES' foreign keys compile

    Given the following foreign key:
      |    source     |         column(s)    | target  | allows null |
      | G_CYCLE_TYPES | G_FIRST_DRAW_TYPE_ID | G_DRAWS |    false    |

    And the referred tables are:
    |     table     | parent table | decorated | relationship |   static    |
    | G_CYCLE_TYPES |              |           |              |    NAME     |
    |    G_DRAWS    |              |           |              |             |

    And the tables' columns are:
    |     table     |         column        | type      | pk    | allows null | readonly |   sequence    | keyword | boolean | length | precision |
    | G_CYCLE_TYPES | G_CYCLE_TYPE_ID | NUMBER | true | false | false |  |  | false | 22 | 0 |
    | G_CYCLE_TYPES | G_PRODUCT_TYPE_ID | NUMBER | false | false | false |  |  | false | 22 | 0 |
    | G_CYCLE_TYPES | NAME | VARCHAR2 | false | false | false |  |  | false | 50 | 0 |
    | G_CYCLE_TYPES | G_FIRST_DRAW_TYPE_ID | NUMBER | false | false | false |  |  | false | 22 | 0 |
    | G_CYCLE_TYPES | G_LAST_DRAW_TYPE_ID | NUMBER | false | false | false |  |  | false | 22 | 0 |
    | G_CYCLE_TYPES | NUMBER_OF_WEEKS | NUMBER | false | false | false |  |  | false | 22 | 0 |
    | G_CYCLE_TYPES | BEFORE_SELL_DEFAULT | NUMBER | false | true | false |  |  | false | 22 | 0 |
    | G_CYCLE_TYPES | BEFORE_SELL_CONF_KEY | CLOB | false | true | false |  |  | false | 4000 | 0 |
    | G_CYCLE_TYPES | BEFORE_SELL_BO_DEFAULT | NUMBER | false | true | false |  |  | false | 22 | 0 |
    | G_CYCLE_TYPES | BEFORE_SELL_BO_CONF_KEY | VARCHAR2 | false | true | false |  |  | false | 50 | 0 |
    | G_CYCLE_TYPES | BEFORE_INTERRUPT_BO_DEFAULT | NUMBER | false | true | false |  |  | false | 22 | 0 |
    | G_CYCLE_TYPES | BEFORE_INTERRUPT_BO_CONF_KEY | VARCHAR2 | false | true | false |  |  | false | 50 | 0 |
    |    G_DRAWS    | G_DRAW_ID | NUMBER | true | false | false |  |  | false | 22 | 0 |

    When I generate with per-foreign key <template> for Oracle

    Then the generated per-foreign key <output> file compiles successfully

    Examples:
      | template | output |
      | FkStatementSetter | GCycleTypeFkStatementSetter.java |