package com.epam.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by infinity on 09.03.16.
 */
public class LocaleTag extends TagSupport implements DynamicAttributes{

    private String locale;
    private Map<String,String> map = new LinkedHashMap<>();

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public void setDynamicAttribute(String s, String param, Object value) throws JspException {
        map.put(param,(String) value);
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            if (map.containsKey(locale)){
                pageContext.getOut().print("<option value=\"" + locale + "\">" + map.get(locale) +"</option>");
                for (Map.Entry<String,String> elem : map.entrySet()){
                    if (!elem.getKey().equals(locale))
                        pageContext.getOut().print("<option value=\"" + elem.getKey() + "\">" + elem.getValue() +"</option>");
                }
            }else {
                for (Map.Entry<String,String> elem : map.entrySet()){
                        pageContext.getOut().print("<option value=\"" + elem.getKey() + "\">" + elem.getValue() +"</option>");
                }
            }
        } catch(IOException e) {
            throw new JspException("Error: " + e.getMessage());
        }
        return SKIP_BODY;
    }
}
