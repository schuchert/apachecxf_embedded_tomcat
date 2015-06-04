Ability: Executing an expression

  Background:
    Given a cleared calculator

  Scenario: parse a multi-part expression
    When the calculator is asked to execute "3 5 * 9 - 4 +"
    Then the result should be "10"
