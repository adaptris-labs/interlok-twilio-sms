package io.github.quotidianennui;

import com.adaptris.core.ServiceCase;
import com.adaptris.security.password.Password;

public class TwilioSmsTest extends ServiceCase {

  @Override
  protected Object retrieveObjectForSampleConfig() {
    try {
      return new TwilioSmsService().withAccountSid("MyAccountSID")
          .withAuthToken(Password.encode("MyAuthToken", Password.PORTABLE_PASSWORD)).withFrom("+44123456789")
          .withTo("%message{SMS_TO}");
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
