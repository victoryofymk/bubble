Feature: Mernagria
  打开百度进行搜索

  Scenario: registe new pet
    Given I am on the "new pet" page
    And I click the "registe" button
    Then I should go to the "register" page