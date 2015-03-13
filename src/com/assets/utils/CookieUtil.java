package com.assets.utils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil
{
  public static void setCookie(HttpServletResponse paramHttpServletResponse, String paramString1, String paramString2)
  {
    Cookie localCookie = new Cookie(paramString1, paramString2);
    paramHttpServletResponse.addCookie(localCookie);
  }

  public static String getCookie(HttpServletRequest paramHttpServletRequest, String paramString)
  {
    String localObject = "";
    Cookie[] arrayOfCookie = paramHttpServletRequest.getCookies();
    Cookie localCookie = null;
    String str1 = null;
    String str2 = null;
    for (int i = 0; i < arrayOfCookie.length; i++)
    {
      localCookie = arrayOfCookie[i];
      str1 = localCookie.getValue();
      str2 = localCookie.getName();
      if (str2.equals(paramString))
      {
        localObject = str1;
        break;
      }
    }
    return localObject;
  }
}