Feature: Acme-wide classes compile

  Scenario Outline: Templates bound to Acme repository compile

    Given the repository whose name is Acme, composed of:
      | table         | parent table | decorated | relationship |   static    |
      | G_CYCLE_TYPES |              |   false   |     false    |    false    |
      | G_DRAWS       |              |   false   |     false    |    false    |

    When I use the repository-wide <template>.stg for Oracle

    Then the generated repository-wide <output> file compiles successfully

  Examples:
    | template | output |
    | BaseResultSetExtractor | AcmeResultSetExtractor.java |
#    | ConfigurationProperties | queryj-acme.properties |
#    | DAOChooser | AcmeDAOChooser.java |
#    | DataAccessManager| AcmeDataAccessManager.java |
