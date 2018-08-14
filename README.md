# twilio-sms
The suggested name was cuddly-octo-meme; but this will allow you to do this to send an SMS via twilio whereever you can use an Interlok service...

```
<send-sms-via-twilio>
  <from>+twiliophonenumber</from>
  <to>+myphonenumber, or $message{SMS_TO} or something</to>
  <account-sid>[AccountSID]</account-sid>
  <auth-token>[AuthToken]</auth-token>
  <text-body class="string-payload-data-input-parameter"/>
</send-sms-via-twilio>
```
## This branch

This branch is working towards a build.gradle that allows you to "build a distribution" that contains your interlok config and
any dependencies that you require.

Even though it does not have any service-tester components; the ![service-tester](https://github.com/adaptris/interlok-service-tester-example) could be integrated into it. At the moment, because of possibly licensing issues, the interlok-binary artefact is in our private repositories; however you can easily generate your own version by just installing the adapter, and using the generated executables.

## Why ![Interlok Hammer](https://img.shields.io/badge/certified-interlok%20hammer-red.svg)

This is a real example of how easy it is to develop something for Interlok. 
* you might want to notify people when a message goes bad.
* Yeah, you could use the Twilio REST API instead... 

```
curl 'https://api.twilio.com/2010-04-01/Accounts/[AccountSID]/Messages.json' -X POST \
--data-urlencode 'To=+myphonenumber' \
--data-urlencode 'From=+twiliophonenumber' \
--data-urlencode 'Body=Hello World' \
-u [AccountSID]:[AuthToken]
```

## Things to do

* Because Twilio.init() is static, means you can't have multiple sids/auth-tokens in play; need to do something directly with the underlying REST client - might this be a generic "TwilioConnection".
* Add proxy support, cos you know corporates love their proxies.

