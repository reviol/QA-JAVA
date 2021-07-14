Feature: Save data in DB


  Scenario: Save information about activity for user id "2" in BD
    Given We have user id "2" in system
    When Getting information about user "2" with name "Janet" and surname "Weaver"
    Then DB saved the information about request for user id "2"


#  @smoke
  Scenario Outline: Save information about activity for user in BD
    Given We have user id "<id>" in system
    When Getting information about user "<id>" with name "<name>" and surname "<surname>"
    Then DB saved the information about request for user id "<id>"
    Examples:
      | id | name   | surname |
      | 1  | George | Bluth   |
      | 2  | Janet  | Weaver  |
      | 3  | Emma   | Wong    |
      | 4  | Eve    | Holt    |
      | 5  | Charles| Morris  |


