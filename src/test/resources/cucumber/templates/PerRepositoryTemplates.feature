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

    When I generate with per-repository <template>.stg for Oracle

    Then the generated per-repository <output> file compiles successfully

  Examples:
    | template          | output                            |
    | DataAccessManager | ECommerceDataAccessManager.java  |
