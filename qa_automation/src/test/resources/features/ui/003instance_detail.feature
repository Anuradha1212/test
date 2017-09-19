@sanityTest
Feature: Validate instance detail page 

@sanityTest
Scenario: Verify instance detail page on screen 
	Given I login to tdrive application 
	And I navigate to "Customer management" page 
	When I enter customer detail to search "BevMo" 
	And I click on "BevMo" customer details 
	And I click on instance from instance list 
	Then I should get instance detail page 
	
@sanityTest 
Scenario: Verify slider min max value on screen 
	Given I login to tdrive application 
	And I navigate to "Customer management" page 
	When I enter customer detail to search "BevMo" 
	And I click on "BevMo" customer details 
	And I click on instance from instance list 
	Then I verify slider min max value 
	
@sanityTest 
Scenario: Validate cancel button functionality on update instance page 
	Given I login to tdrive application 
	And I navigate to "Customer management" page 
	When I enter customer detail to search "BevMo" 
	And I click on "BevMo" customer details 
	And I click on instance from instance list 
	And I verify cancel button functionality 
	
@sanityTest 
Scenario: Validate watchlist status and status icon colour 
	Given I login to tdrive application 
	And I navigate to "Customer management" page 
	When I enter customer detail to search "BevMo" 
	And I click on "BevMo" customer details 
	And I click on instance from instance list 
	Then I verify watchlist status icon colour 
	Then I verify the message text colour same as status icon 
	
@sanityTest 
Scenario: Validate watchlist status icon message colour 
	Given I login to tdrive application 
	And I navigate to "Customer management" page 
	When I enter customer detail to search "BevMo" 
	And I click on "BevMo" customer details 
	And I click on instance from instance list 
	Then I verify the message text colour same as status icon 
	
@sanityTest 
Scenario: Validate watchlist start and end date format 
	Given I login to tdrive application 
	And I navigate to "Customer management" page 
	When I enter customer detail to search "BevMo" 
	And I click on "BevMo" customer details 
	And I click on instance from instance list 
	Then I verify start and end date format 
	
@sanityTest
Scenario: Validate watchlist From and To attribute
	Given I login to tdrive application 
	And I navigate to "Customer management" page 
	When I enter customer detail to search "BevMo" 
	And I click on "BevMo" customer details 
	And I click on instance from instance list 
	Then I verify From and To attribute of watchlist 
	
@sanityTest 
Scenario: Validate flyover over status 
	Given I login to tdrive application 
	#	And I navigate to "Customer management" page 
	When I enter customer detail to search "BevMo" 
	And I click on "BevMo" customer details 
	And I click on instance from instance list 
	Then I verify flyover over status icon 
	
@sanityTest 
Scenario: Validate cross button functionality on update instance page 
	Given I login to tdrive application 
	#	And I navigate to "Customer management" page 
	When I enter customer detail to search "BevMo" 
	And I click on "BevMo" customer details 
	And I click on instance from instance list 
	And I verify cross button functionality
