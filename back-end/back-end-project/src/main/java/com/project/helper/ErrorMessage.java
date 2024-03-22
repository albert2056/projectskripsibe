package com.project.helper;

public class ErrorMessage {
  public static final String PASSWORD_UPPERCASE = "Register failed! Password must have at least one uppercase letter.";
  public static final String PASSWORD_LOWERCASE = "Register failed! Password must have at least one lowercase letter.";
  public static final String PASSWORD_NUMBER = "Register failed! Password must have at least one numeric digit.";
  public static final String PASSWORD_LENGTH = "Register failed! Password length must be 8 or more";
  public static final String PHONENUM_NUMBER = "Register failed! Phone number must be only numeric digit.";
  public static final String PHONENUM_LENGTH = "Register failed! Phone number length must be 10 or more";
  public static final String NAME = "Register failed! Name cannot be empty!";
  public static final String EMAIL = "Register failed! Email must contain '@'";
  public static final String LOGIN_NOT_MATCH = "Log in failed! Email and Password does not match!";
  public static final String USER_NOT_FOUND = "Log in failed! Email not found!";
}
