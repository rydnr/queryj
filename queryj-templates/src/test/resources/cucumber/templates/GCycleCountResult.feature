Feature: Classes associated to custom results compile

  Scenario Outline: Custom result GCycleCount work

    Given the following custom result:
      | id | class |
      | g.cycle.count | GCycle |

    And the following custom result properties:
      | name       | type | nullable |
      | g_cycle_id | int  | false    |
      | count      | long | false    |

    When I use the custom result template <template> for Oracle

    Then the generated custom result-specific <output> file compiles successfully

  Examples:
    | template                 | output                             |
    | CustomValueObjectFactory | GCycleCountFactory.java            |
    | CustomValueObject        | GCycleCount.java                   |
    | CustomBaseValueObject    | AbstractGCycleCount.java           |
    | CustomValueObjectImpl    | GCycleCountImpl.java               |
#    | CustomResultSetExtractor | GCycleCountResultSetExtractor.java |