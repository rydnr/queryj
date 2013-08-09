Feature: Per-custom-results templates

  Scenario Outline: Per-custom-results templates compile

    And the following custom results:
    | id | class | matches |
    | user.multiple_user_report | com.foo.bar.vo.UserReport | multiple |

    And the following properties:
    | id                | custom-result-id          | column_name  | index | type       | nullable |
    | user.user_id      | user.multiple_user_report | user_id      |     1 | long       |    false |
    | user.total_amount | user.multiple_user_report | total_amount |     2 | BigDecimal |    false |

    When I generate with per-custom-result <template>.stg for Oracle

    Then the generated per-custom-result <output> file compiles successfully

    Examples:
      | template                 | output                            |
      | CustomRowMapper          | UserReportRowMapper.java          |
#      | CustomResultSetExtractor | UserReportResultSetExtractor.java |
#      | CustomBaseValueObject    | AbstractUserReport.java           |
#      | CustomValueObject        | UserReport.java                   |
#      | CustomValueObjectFactory | UserReportFactory.java            |
#      | CustomValueObjectImpl    | UserReportImpl.java               |

