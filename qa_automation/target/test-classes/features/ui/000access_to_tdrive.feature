Feature: Access T-Drive via the Base URL 

@sanityTest 
Scenario: Open T-Drive base url 
	Given I open T-Drive url on browser 
	Then I validate login page opened 
	
@sanityTest 
Scenario: Verify login page details 
	Given I open T-Drive url on browser 
	Then I validate login page details 
	
@sanityTest 
Scenario: Verify home page after login sucessfully 
	Given I login to tdrive application 
	Then I am on Dashboard page 
	
@sanityTest 
Scenario: Verify menu icon(hamburger symbol) on the extreme left corner of page 
	Given I login to tdrive application 
	Then I am on Dashboard page 
	When I click on menu icon 
	Then I verify navigation pane with logged in user details 
	
@sanityTest
Scenario Outline: Verify authentication with invalid credential 
	Given I open T-Drive url on browser 
	When I enter "<username>" and "<password>" 
	Then I should get "<message>" message
	Examples:	
    | username  |password | message |
    |  tdive2   |  tdrive  |  Wrong username or password |
    #|  tdive2   |    |  Can't be blank  |
   # |           | password   | Can't be blank  |
    #|			|			|	 Can't be blank			|		
    
    
	
@sanityTest 
Scenario: Signout 
	Given I login to tdrive application 
	When I am on Dashboard page 
	Then I signout 
	
	
	