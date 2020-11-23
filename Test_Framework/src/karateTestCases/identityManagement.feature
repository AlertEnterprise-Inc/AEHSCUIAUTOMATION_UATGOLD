Feature: Identity management Scenarios

Background:
	* url url
	* def postdata = read('classpath:testData/identityManagement.json')
	* def result = call read('classpath:karateTestCases/authToken.feature')
	* def token = result.response.data.access_token
	* def now = function(){ return java.lang.System.currentTimeMillis()}
	* def today = function(){ return java.time.LocalDateTime.now()}
	* def config = dbConfig
	* def DbUtils = Java.type('karateTestCases.DbUtils')
	* def db = new DbUtils(config)

@regression
Scenario: Generate Access Token

Given path 'api/auth/token'
* param grant_type = 'password'
And header Authorization = 'Bearer ' + token
And request postdata.generateAuthToken
When method post
Then status 200
And print response.access_token
And print response.refresh_token



@regression
Scenario: Generate Refresh Token
Given path 'api/auth/token'
* param grant_type = 'password'
And header Authorization = 'Bearer ' + token
And request postdata.generateAuthToken
When method post
Then status 200
And print response.refresh_token

Given path 'api/auth/token'
* param grant_type = 'refresh_token'
* param refresh_token = response.refresh_token
And header Authorization = 'Bearer ' + token
And request postdata.generateAuthToken
When method post
Then status 200
And print response


@regression
Scenario: To find a identity

* def query = "select top 1 type FROM aehscnew.identity_user  where type is not NULL"
* print query
* def type = db.readValue(query)

Given path 'api/identity/find'
And header Authorization = 'Bearer ' + token
* set postdata.findIdentity.filterCriteria[1].valueList[0] = type 
And request postdata.findIdentity
When method post
Then status 200
And print response
And print "total number of identities with type as permanent:" + response.totalElements

@regression
Scenario: To find a identity with master id

* def query = "SELECT top 1 master_identity_id  FROM aehscnew.identity_user  where master_identity_id is not NULL "
* print query
* def masterId = db.readValue(query)

Given path 'api/identity/getby/masterIdentityId'
And header Authorization = 'Bearer ' + token
And print 'Bearer ' + token
* set postdata.findidentitybymasterid.values[0] = masterId
And request postdata.findidentitybymasterid
When method post
Then status 200
And print response

@regression
Scenario: To save a new identity

Given path 'api/identity/external/save'
And header Authorization = 'Bearer ' + token
And print 'Bearer ' + token
And request postdata.saveIdentity
When method post
Then status 200
And print response

@regression
Scenario: Provisioning log api

Given path 'api/provisioninglog/find'
And header Authorization = 'Bearer ' + token
And request postdata.provisioninglog
When method post
Then status 200
And print response






@regression
Scenario: To Deactivate a identity
#create a new identity
Given path 'api/identity/external/save'
And header Authorization = 'Bearer ' + token
And print 'Bearer ' + token
And request postdata.saveIdentity
When method post
Then status 200
And print response.apiMessage.messageText
#deactivate above created identity

Given path 'api/identity/external/deactivate'
And header Authorization = 'Bearer ' + token
And print 'Bearer ' + token
* set postdata.deactivateIdentity.masterIdentityId = response.data.masterIdentityId
* set postdata.deactivateIdentity.requestUuid = response.data.requestUuid
And request postdata.deactivateIdentity
When method post
Then status 200
And print response



@regression
Scenario: To Activate  a identity
#create a new identity
Given path 'api/identity/external/save'
And header Authorization = 'Bearer ' + token
And print 'Bearer ' + token
And request postdata.saveIdentity
When method post
Then status 200
And print response.apiMessage.messageText
* def masterIdentityId = response.data.masterIdentityId
* def requestUuid = response.data.requestUuid

#deactivate above created identity

Given path 'api/identity/external/deactivate'
And header Authorization = 'Bearer ' + token
And print 'Bearer ' + token
* set postdata.deactivateIdentity.masterIdentityId = masterIdentityId
* set postdata.deactivateIdentity.requestorId = masterIdentityId
And request postdata.deactivateIdentity
When method post
Then status 200
And print response


#Activate above created identity

Given path 'api/identity/external/activate'
And header Authorization = 'Bearer ' + token
And print 'Bearer ' + token
* set postdata.activateIdentity.masterIdentityId = masterIdentityId
* set postdata.activateIdentity.requestorId = masterIdentityId
And request postdata.activateIdentity
When method post
Then status 200
And print response





