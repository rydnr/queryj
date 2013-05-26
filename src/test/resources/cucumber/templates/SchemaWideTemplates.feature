Feature: Schema-wide templates

  Scenario: Cucumber feature is generated correctly for a schema

    Given a schema with the following information:
      | name       | user   | vendor |
      | e-commerce | dbuser | Oracle |

    Given a schema with the following tables:
      | table      |
      | users      |
      | addresses  |
      | companies  |
      | partners   |

    And such tables have the following columns:
      | table | column        | type      | pk    | allows null | readonly | sequence  | keyword | boolean | length | precision |
      | users | user_id       | number    | true  | false       | false    | seq_users |         |         |     10 |         0 |
      | users | company_id    | number    | false | true        | false    |           |         |         |     10 |         0 |
      | users | name          | varchar   | false | false       | false    |           |         |         |     30 |           |
      | users | email         | varchar   | false | false       | false    |           |         |         |     30 |         0 |
      | users | registered    | number    | false | false       | false    |           |         | 0,1     |      1 |         1 |
      | users | creation_date | timestamp | false | false       | true     |           | sysdate |         |        |           |

    And the schema defines the following foreign keys:
      | source table | source columns | target table | allows null |
      | users        | company_id     | companies    | true        |

    When I generate with schema-wide CucumberFeature.stg

    Then the generated Cucumber file ECommerce-tables.feature is valid

