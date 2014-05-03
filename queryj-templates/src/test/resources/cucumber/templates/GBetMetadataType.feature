Feature: G_BET_METADATA_TYPES code compiles

  Scenario Outline: G_BET_METADATA_TYPES-specific templates compile

    Given the following tables:
    |    table     | parent table | decorated | relationship |   static    |
    | G_BET_METADATA_TYPES |  | false | false | NAME |

    And the following columns:
    |     table    |         column        | type      | pk    | allows null | readonly |   sequence    | keyword | boolean | length | precision |
    | G_BET_METADATA_TYPES | G_BET_METADATA_TYPE_ID | NUMBER | true | false | false |  |  | false | 22 | 0 |
    | G_BET_METADATA_TYPES | NAME | VARCHAR2 | false | false | false |  |  | false | 50 | 0 |

    And the following contents:
      |    table     |         row          |
      | G_BET_METADATA_TYPES | 1,"number_of_bets" |

    When I generate with per-table <template>.stg for Oracle

    Then the generated per-table <output> file compiles successfully

    Examples:
      | template | output |
      | DAO | OracleGBetMetadataTypeDAO.java |
