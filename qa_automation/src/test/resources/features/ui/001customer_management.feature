Feature: Validate Customer list page 

@sanityTest
Scenario: Validate customer management page 
	Given I login to tdrive application 
	And I navigate to "Customer management" page 
	Then I Verify "Customer management" Page on screen 

@sanityTest
Scenario: Validate customer list page details 
	Given I login to tdrive application 
	And I navigate to "Customer management" page 
	Then I verify customer management list page details 
	
@sanityTest
Scenario: Validate search option available to filter down the customer listing with valid values 
	Given I login to tdrive application 
	And I navigate to "Customer management" page 
	Then I verify search option filter valid value
	
	
@sanityTest
Scenario: Validate pagination functionality on customer listing page 
	Given I login to tdrive application 
	And I navigate to "Customer management" page 
	Then I verify "customer list" pagination bar is displayed 
	And I verify pagination functionality 
	
@sanityTest
Scenario: Validate sorting button on customer listing page 
	Given I login to tdrive application 
	And I navigate to "Customer management" page 
	Then I verify "name" "ascending" order 
	Then I verify "name" "descending" order 
	#Then I verify "location" "ascending" order 
	
@sanityTest
Scenario: Select a customer from a list
	Given I login to tdrive application 
	And I navigate to "Customer management" page 
	When I enter customer detail to search "BevMo" 
	And I click on "BevMo" customer details
	Then I verify "BevMo" customer detail title 

	
	
      