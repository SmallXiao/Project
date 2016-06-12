package com.project.utils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

public class TrustManager
  implements X509TrustManager
{
  public void checkClientTrusted(X509Certificate[] chain, String authType)
    throws CertificateException
  {
  }

  public void checkServerTrusted(X509Certificate[] chain, String authType)
    throws CertificateException
  {
  }

  public X509Certificate[] getAcceptedIssuers()
  {
    return null;
  }
}

/* Location:           C:\Users\高航\Desktop\微信自定义菜单创建工具+源码（附java源码）\微信自定义菜单\main.jar
 * Qualified Name:     util.TrustManager
 * JD-Core Version:    0.6.2
 */