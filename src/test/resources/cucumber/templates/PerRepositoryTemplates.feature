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


  Scenario: Cucumber feature is generated correctly for a repository

    Given a repository with the following information:
      | name       | user   | vendor |
      | e-commerce | dbuser | Oracle |

    Given a repository with the following tables:
      | table      |
      | users      |
      | addresses  |
      | companies  |
      | partners   |

    And the following columns:
      | table | column        | type      | pk    | allows null | readonly | sequence  | keyword | boolean | length | precision |
      | user  | user_id       | number    | true  | false       | false    | seq_users |         |         |     10 |         0 |
      | user  | company_id    | number    | false | true        | false    |           |         |         |     10 |         0 |
      | user  | name          | varchar   | false | false       | false    |           |         |         |     30 |           |
      | user  | email         | varchar   | false | false       | false    |           |         |         |     30 |         0 |
      | user  | registered    | number    | false | false       | false    |           |         | 0,1     |      1 |         1 |
      | user  | creation_date | timestamp | false | false       | true     |           | sysdate |         |        |           |

    And the following foreign keys:
      | source table | source columns | target table | allows null |
      | user         | company_id     | companies    | true        |

    When I generate with per-repository CucumberFeature.stg

    Then the generated Cucumber file ECommerce-tables.feature is valid

