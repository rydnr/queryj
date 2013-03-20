Feature: DAO template

  Scenario: DAO template compiles
  Given the following tables:
    | repository | table | column1 | column1_type | column1_comment               | column2 | column2_type | column3     | column3_type |
    | nlp-webapp | user  | user_id | number       | The id of the user @oraseq s1 | name    | varchar      | age         | number       |
    | test       | car   | car_id  | number       | The id of the car  @oraseq s2 | name    | varchar      | launch_date | timestamp    | 
    | tool       | tool  | tool_id | number       | The id of the tool @oraseq s3 | path    | varchar      | version     | varchar      |
  When I generate with DAO.stg
  Then the generated file compiles correctly
