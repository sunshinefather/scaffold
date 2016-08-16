package com.assets.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil
{
  public static void setCookie(HttpServletResponse request, String param1, String param2)
  {
    Cookie localCookie = new Cookie(param1, param2);
    request.addCookie(localCookie);
  }

  public static String getCookie(HttpServletRequest request, String paramString)
  {
    String localObject = "";
    Cookie[] arrayOfCookie = request.getCookies();
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