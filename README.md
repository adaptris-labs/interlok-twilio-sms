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

# Why

This is a real example of how easy it is to develop something for Interlok. Doesn't mean you should do it ![Interlok Hammer](https://img.shields.io/badge/certified-interlok%20hammer-red.svg)
* you might want to notify people when a message goes bad.
* Yeah, you could use the Twilio REST API instead... 

```
curl 'https://api.twilio.com/2010-04-01/Accounts/[AccountSID]/Messages.json' -X POST \
--data-urlencode 'To=+myphonenumber' \
--data-urlencode 'From=+twiliophonenumber' \
--data-urlencode 'Body=Hello World' \
-u [AccountSID]:[AuthToken]
```
