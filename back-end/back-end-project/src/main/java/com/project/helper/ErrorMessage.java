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
  public static final String DUPLICATE_EMAIL = "Register failed! Email already exists!";
  public static final String LOGIN_NOT_MATCH = "Log in failed! Email and Password does not match!";
  public static final String USER_NOT_FOUND = "Log in failed! Email not found!";
  public static final String TOTAL_USHER = "Book failed! TotalUsher must be greater than 0";
  public static final String EVENT_DATE_NULL = "Book failed! EventDate cannot be null";
  public static final String EVENT_DATE_PAST = "Book failed! EventDate must be after the current date";
  public static final String NAME_EMPTY = "Book failed! Name cannot be null or empty";
  public static final String VENUE_EMPTY = "Book failed! Venue cannot be null or empty";
  public static final String IMAGE_REQUIRED = "Save portfolio failed! Image cannot be null!";
  public static final String OUTFIT_ID_REQUIRED = "Save portfolio failed! Outfit Id cannot be null!";
  public static final String EVENT_DATE_REQUIRED = "Save portfolio failed! Event date cannot be null!";
  public static final String VENUE_REQUIRED = "Save portfolio failed! Venue cannot be null!";
  public static final String NAME_REQUIRED = "Save portfolio failed! Name cannot be null!";
}
