Feature: G_CYCLE_TYPE_METADATA code compiles

  Scenario Outline: G_CYCLE_TYPE_METADATA-specific templates compile

    Given the following tables:
    |    table     | parent table | decorated | relationship |   static    |
    | G_CYCLE_TYPE_METADATA |  | false | false |  |

    And the following columns:
    |     table    |         column        | type      | pk    | allows null | readonly |   sequence    | keyword | boolean | length | precision |
    | G_CYCLE_TYPE_METADATA | G_CYCLE_TYPE_MD_TYPE_ID | NUMBER | true | false | false |  |  | false | 22 | 0 |
    | G_CYCLE_TYPE_METADATA | G_CYCLE_TYPE_ID | NUMBER | true | false | false |  |  | false | 22 | 0 |
    | G_CYCLE_TYPE_METADATA | VALUE | VARCHAR2 | false | true | false |  |  | false | 100 | 0 |

    When I generate with per-table <template>.stg for Oracle

    Then the generated per-table <output> file compiles successfully

    Examples:
      | template | output |
      | ValueObjectFactory | GTaxProductFactory.java  |
