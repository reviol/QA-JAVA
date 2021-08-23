Feature: Saving user activity in DB


  Scenario: Save data for user by id
    Given We have user id in DB
    When User sent request to get info
    Then We saved information about user request id DB