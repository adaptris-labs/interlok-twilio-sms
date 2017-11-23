package io.github.quotidianennui;

import static org.apache.commons.lang3.StringUtils.abbreviate;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.adaptris.annotation.ComponentProfile;
import com.adaptris.annotation.DisplayOrder;
import com.adaptris.annotation.InputFieldHint;
import com.adaptris.core.AdaptrisMessage;
import com.adaptris.core.CoreException;
import com.adaptris.core.ServiceException;
import com.adaptris.core.ServiceImp;
import com.adaptris.core.common.StringPayloadDataInputParameter;
import com.adaptris.core.util.Args;
import com.adaptris.core.util.ExceptionHelper;
import com.adaptris.interlok.config.DataInputParameter;
import com.adaptris.security.exc.PasswordException;
import com.adaptris.security.password.Password;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 * Sends the first 160 characters of the configured source as an SMS message to the specified number.
 * 
 * @config send-sms-via-twilio
 *
 */
@ComponentProfile(summary = "Send a SMS message to the specified number", tag = "service,sms,twilio", since = "3.6.6")
@DisplayOrder(order =
{
    "accountSid", "authToken", "from", "to"
})
@XStreamAlias("send-sms-via-twilio")
public class TwilioSmsService extends ServiceImp {

  private static final int MAX_LEN = 160;

  @NotBlank
  @InputFieldHint(expression = true)
  private String from;

  @NotBlank
  @InputFieldHint(expression = true)
  private String to;

  @NotBlank
  @InputFieldHint(style = "PASSWORD")
  private String accountSid;
  @NotBlank
  @InputFieldHint(style = "PASSWORD")
  private String authToken;

  @NotNull
  @Valid
  private DataInputParameter<String> textBody;

  public TwilioSmsService() {
    setTextBody(new StringPayloadDataInputParameter());
  }

  @Override
  public void doService(AdaptrisMessage msg) throws ServiceException {
    try {
      String msgBody = abbreviate(defaultIfBlank(getTextBody().extract(msg), ""), MAX_LEN);
      PhoneNumber from = new PhoneNumber(msg.resolve(getFrom()));
      PhoneNumber to = new PhoneNumber(msg.resolve(getTo()));
      Message txtMsg = Message.creator(to, from, msgBody).create();
      log.trace("Message [{}] sent to [{}]", txtMsg.getSid(), to);
    }
    catch (Exception e) {
      throw ExceptionHelper.wrapServiceException(e);
    }
  }

  @Override
  public void prepare() throws CoreException {
    try {
      Args.notBlank(getFrom(), "from");
      Args.notBlank(getTo(), "to");
      Args.notBlank(getAccountSid(), "accountSid");
      Args.notBlank(getAuthToken(), "authToken");
      Args.notNull(getTextBody(), "message");
    }
    catch (Exception e) {
      throw ExceptionHelper.wrapCoreException(e);
    }
  }

  @Override
  protected void initService() throws CoreException {
    try {
      Twilio.init(Password.decode(getAccountSid()), Password.decode(getAuthToken()));
    }
    catch (PasswordException e) {
      throw ExceptionHelper.wrapCoreException(e);
    }
  }

  @Override
  protected void closeService() {
    // There isn't a twilio.invalidate method, but
    // it just does...Twilio.setRestClient(null);
  }

  public TwilioSmsService withFrom(String s) {
    setFrom(s);
    return this;
  }

  public TwilioSmsService withTo(String s) {
    setTo(s);
    return this;
  }

  public TwilioSmsService withAccountSid(String s) {
    setAccountSid(s);
    return this;
  }

  public TwilioSmsService withAuthToken(String s) {
    setAuthToken(s);
    return this;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String sender) {
    this.from = Args.notBlank(sender, "sender");
  }

  public String getTo() {
    return to;
  }

  public void setTo(String receiver) {
    this.to = Args.notBlank(receiver, "receiver");
  }

  public String getAccountSid() {
    return accountSid;
  }

  public void setAccountSid(String accountSid) {
    this.accountSid = Args.notBlank(accountSid, "accountSid");
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken = Args.notBlank(authToken, "authToken");
  }

  public DataInputParameter<String> getTextBody() {
    return textBody;
  }

  public void setTextBody(DataInputParameter<String> message) {
    this.textBody = Args.notNull(message, "message");
  }

}
