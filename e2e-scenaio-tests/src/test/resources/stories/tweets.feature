Feature: Tweets Service
  Background:
    * url "http://localhost:8080"

    # ben auth token
    * path '/token'
    * header Authorization = 'Basic YmVuOmJlbnNwYXNzd29yZA=='
    * method GET
    * status 200
    * match $ contains { accessToken: "#present" }
    * def ben_auth_token = $.accessToken

    # bob auth token
    * path '/token'
    * header Authorization = 'Basic Ym9iOmJvYnNwYXNzd29yZA=='
    * method GET
    * status 200
    * match $ contains { accessToken: "#present" }
    * def bob_auth_token = $.accessToken

  Scenario Outline: Tweets by ben
    Given path '/api/v1/tweet'
    And request { content: '<tweet>' }
    And header Authorization = 'Bearer ' +  ben_auth_token
    When method POST
    Then status 200
    And match $.id != null

    Examples:
      |tweet|
      |Hello tweet!|
      |Hello tweet -2!|
      |Brigh sunny day!|
      |Disucssion with intuit today|

  Scenario Outline: Tweets by bob
    Given path '/api/v1/tweet'
    And request { content: '<tweet>' }
    And header Authorization = 'Bearer ' +  bob_auth_token
    When method POST
    Then status 200
    And match $.id != null

    Examples:
      |random tweet|
      |Hello chirpers!|
      |chirp! chirp!|
      |Night owl|
      |Disucssion with intuit today|

  Scenario: Ben follows Bob
    Given path '/api/v1/following'
    And request { id: 2 }
    And header Content-Type = 'application/json'
    And header Authorization = 'Bearer ' +  ben_auth_token
    When method POST
    Then assert responseStatus == 201 || responseStatus == 400

  Scenario: Bob follows Ben
    Given path '/api/v1/following'
    And request { id: 1 }
    And header Content-Type = 'application/json'
    And header Authorization = 'Bearer ' +  bob_auth_token
    When method POST
    Then  assert responseStatus == 201 || responseStatus == 400

  Scenario: Ben follows
    Given path '/api/v1/following'
    And header Authorization = 'Bearer ' +  ben_auth_token
    When method GET
    Then status 200
    And match $ == { followingIds : [2]}

  Scenario: Bob follows
    Given path '/api/v1/following'
    And header Authorization = 'Bearer ' +  bob_auth_token
    When method GET
    Then status 200
    And match $.followingIds contains 1



