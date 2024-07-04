## language: en
##noinspection CucumberUndefinedStep
#Feature: update status checklist
#
#  Scenario: Successful update of checklist status on DONE
#    Given checklist with uuid "2900fd68-3139-466b-ba51-04242077b67d" and status "IN_PROGRESS"
#    When I update the checklist status to DONE with duration "23:30:30" for a checklist with uuid "2900fd68-3139-466b-ba51-04242077b67d"
#    Then checklist status should be updated to DONE for checklist with uuid "2900fd68-3139-466b-ba51-04242077b67d"
#    And duration should be "23:30:30" for checklist with uuid "2900fd68-3139-466b-ba51-04242077b67d"
#
#  Scenario: The checklist status is already DONE
#    Given checklist with uuid "2900fd68-3139-466b-ba51-04242077b67d" and with status "DONE"
#    When I update status checklist to DONE with duration "23:30:30" for a checklist with uuid "2900fd68-3139-466b-ba51-04242077b67d"
#    Then an exception should be thrown with the message "Данный чеклист уже опубликован"
#
#  Scenario: Duration not specified
#    Given checklist with uuid "2900fd68-3139-466b-ba51-04242077b67d" and status "IN_PROGRESS"
#    When I update status checklist to DONE with duration "null" for a checklist with uuid "2900fd68-3139-466b-ba51-04242077b67d"
#    Then an exception should be thrown with the message "Введите время,затраченное на проверку чек-листа!"
