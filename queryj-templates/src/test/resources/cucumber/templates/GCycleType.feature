Feature: G_CYCLE_TYPES code compiles

  Scenario Outline: Templates bound to G_CYCLE_TYPES table templates compile

    Given the following tables:
    |    table     | parent table | decorated | relationship |   static    |
    | G_CYCLE_TYPES |  |  |  | NAME |

    And the following columns:
    |     table    |         column        | type      | pk    | allows null | readonly |   sequence    | keyword | boolean | length | precision |
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

    And the following queries:
      |       id      |     name      |     dao      |  type  | matches  |                        value                           | validate |
      | cycle.types.find-cycle-type-by-product-type | find-cycle-type-by-product-type | g_cycle_types | select | single |     select * from   g_cycle_types ct where   ct.g_product_type_id = ?       | false |

    And the following query parameters:
      |            id            |     sql       | index |  type  |     name    | validation-value |
      | cycle.types.product_type_id.parameter-1 | cycle.types.find-cycle-type-by-product-type | 1 | long | product_type_id | 1 |

    And the following contents:
      |    table     |         row          |
      | G_CYCLE_TYPES | 1,29,"Primisistema100",4,2,4,179,"primisitema100.sellenddate.hours.offset",179,"primisistema100.sellenddate.bo.hours.offset",10,"primisistema100.interruptiondate.bo.hours.offset" |

    When I generate with per-table <template> for Oracle

    Then the generated per-table <output> file compiles successfully

    Then the queries are validated correctly using the following database:
      |     driver     |     url     |     userName     |     password     |
      | oracle.jdbc.driver.OracleDriver | jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=10.34.10.249)(PORT=1521))(CONNECT_DATA=(SERVER=dedicated)(SERVICE_NAME=PRE2))) | QUERYJGAMES | QUERYJGAMES |

    Examples:
      | template | output |
      | PkStatementSetter | GCycleTypePkStatementSetter.java |
#      | AttributesStatementSetter | GCycleTypeAttributesStatementSetter.java |
#      | ValueObjectFactory | GCycleTypeValueObjectFactory.java |
#      | ValueObject | GCycleTypeValueObject.java |
#      | BaseValueObject | AbstractGCycleType.java |
#      | BaseDAO | GCycleTypeDAO.java |
#      | DAO | OracleGCycleTypeDAO.java |

