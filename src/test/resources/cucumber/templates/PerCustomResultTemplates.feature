Feature: Per-custom-results templates

  Scenario Outline: Per-custom-results templates compile

    And the following custom results:
    | id | class | matches |
    | user.multiple_user_report | com.foo.bar.vo.UserReport | multiple |

    And the following properties:
    | id                | custom-result-id          | column_name  | type       |
    | user.user_id      | user.multiple_user_report | user_id      | long       |
    | user.total_amount | user.multiple_user_report | total_amount | BigDecimal |

    When I generate with per-custom-result <template>.stg for Oracle

    Then the generated per-custom-result <output> file compiles successfully

    Examples:
      | template                 | output                                          |
      | CustomResultSetExtractor | OracleMultipleUserReportResultSetExtractor.java |
      | CustomBaseValueObject    | AbstractUserReport.java                         |
      | CustomValueObject        | UserReport.java                                 |
      | CustomValueObjectFactory | UserReportFactory.java                          |
      | CustomValueObjectImpl    | UserReportImpl.java                             |

