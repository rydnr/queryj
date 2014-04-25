Feature: Classes associated to custom results compile

  Scenario Outline: Custom result GCycleCount work

    Given the following custom result:
      | result | dao | type |
      | cycle.count | G_CYCLES | |

    And the following custom result properties:
      | name       | type |
      | g_cycle_id | int  |
      | count      | long |

    When I use the custom result template <template> for Oracle

    Then the generated custom result-specific <output> file compiles successfully

  Examples:
    | template                 | output                        |
    | CustomResultSetExtractor | GCycleCountResultSetExtractor |
