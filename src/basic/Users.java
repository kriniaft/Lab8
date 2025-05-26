package basic;

import java.time.LocalDateTime;

public class Users {
    private long id;
    private String login;
    private String hashPW;
    private LocalDateTime time;


public Users(long id, String login, String passwordHash, LocalDateTime createdAt) {
    this.id = id;
    this.login = login;
    this.hashPW = passwordHash;
    this.time = createdAt;
}

  public long getId() {
    return id;
  }

  public String getLogin() {
    return login;
  }

  public String getPasswordHash() {
    return hashPW;
  }

  public LocalDateTime getCreatedAt() {
    return time;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public void setPasswordHash(String passwordHash) {
    this.hashPW = passwordHash;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.time = createdAt;
  }
}