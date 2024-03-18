Feature: Feeds feature

  Background:
    * url "http://localhost:8080"
    * def expectedExpiredTokenResponse = {"errors":[{"message":"expired token"}]}


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


  Scenario: Get feeds for logged in user bob
    Given path 'api/v1/feeds'
    And header Authorization = 'Bearer '+ bob_auth_token
    When method GET
    Then status 200
    And match $ contains {tweets:'#notnull'}
    And match $ contains {nextToken:'#uuid'}
    Given path '/api/v1/feeds'
    And param token = karate.response.nextToken
    And header Authorization = 'Bearer '+ bob_auth_token
    When method GET
    Then status 200


  Scenario: Get feeds with and invalid nextToken
    Given path '/api/v1/feeds'
    And param token = '12d39bf6-88c0-4c34-9e8b-ddaae5f88c15'
    And header Authorization = 'Bearer '+ bob_auth_token
    When method GET
    Then status 400
    And  match $ == expectedExpiredTokenResponse



