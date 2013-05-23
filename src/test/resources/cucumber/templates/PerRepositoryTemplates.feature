Feature: Per-repository templates

  Scenario Outline: Per-repository templates compile for a repository

    Given a repository with the following information:
      | name       | user   | vendor |
      | e-commerce | dbuser | Oracle |

    Given a repository with the following tables:
      | table      |
      | users      |
      | addresses  |
      | companies  |
      | partners   |

    When I generate with per-repository <template>.stg

    Then the generated per-repository <output> file compiles successfully

  Examples:
    | template                     | output                                 |
    | DataAccessManager            | ECommerceDataAccessManager.java        |
    | BasePreparedStatementCreator | ECommercePreparedStatementCreator.java |
    | BaseResultSetExtractor       | ECommerceResultSetExtractor.java       |
    | DAOChooser                   | ECommerceDAOChooser.java               |
    | DAOListener                  | ECommerceDAOListener.java              |
    | DAOListenerImpl              | ECommerceDAOListenerImpl.java          |
    | JdbcTemplate                 | ECommerceJdbcTemplate.java             |
    | JndiUtils                    | ECommerceJndiUtils.java                |
    | StatisticsProvider           | ECommerceStatisticsProvider.java       |
    | ThreadLocalBag               | ECommerceThreadLocalBag.java           |
    | ConfigurationProperties      | e-commerce-queryj.properties           |
    | DataAccessContextLocal       | e-commerce-dataAccessLocal.xml.sample  |
    | CucumberFeature              | ECommerce-tables.feature              |

  Scenario: queryj.properties is generated correctly for a repository

    Given a repository with the following information:
      | name       | user   | vendor |
      | e-commerce | dbuser | Oracle |

    Given a repository with the following tables:
      | table      |
      | users      |
      | addresses  |
      | companies  |
      | partners   |

    When I generate with per-repository ConfigurationProperties.stg

    Then the generated properties e-commerce-queryj.properties is valid

  Scenario: dataAccessContext-local.xml is generated correctly for a repository

    Given a repository with the following information:
      | name       | user   | vendor |
      | e-commerce | dbuser | Oracle |

    Given a repository with the following tables:
      | table      |
      | users      |
      | addresses  |
      | companies  |
      | partners   |

    When I generate with per-repository DataAccessContextLocal.stg

    Then the generated Spring file e-commerce-dataAccessContext-local.xml.sample is valid

