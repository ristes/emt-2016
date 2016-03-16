package mk.ukim.finki.emt.store.model;

/**
 * Created by ristes on 3/9/16.
 */
public enum Provider {
  LOCAL {
    @Override
    public String getLoginUrl() {
      return "/login";
    }
  },
  GITHUB {
    @Override
    public String getLoginUrl() {
      return "/login/github";
    }
  },
  FACEBOOK {
    @Override
    public String getLoginUrl() {
      return "/login/facebook";
    }
  };

  public String getLoginUrl() {
    return null;
  }
}
