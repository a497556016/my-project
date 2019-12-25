package com.heshaowei.myproj.flowable.display.domain;

import java.util.HashMap;
import java.util.Map;

public enum EndpointType
{
  PROCESS(1),  DMN(2),  FORM(3),  CONTENT(4),  CMMN(5),  APP(6);

  private static Map<Integer, EndpointType> map;
  private final int endpointCode;

  static
  {
    map = new HashMap();
    for (EndpointType endpointType : values()) {
      map.put(Integer.valueOf(endpointType.endpointCode), endpointType);
    }
  }

  private EndpointType(int endpointCode)
  {
    this.endpointCode = endpointCode;
  }

  public int getEndpointCode()
  {
    return this.endpointCode;
  }

  public static EndpointType valueOf(int endpointCode)
  {
    return (EndpointType)map.get(Integer.valueOf(endpointCode));
  }
}