package com.bigbig.util;



// Signtature requires the following fields
/*
 * GET\n //HTTP Verb
\n    //Content-Encoding
\n    //Content-Language
\n    //Content-Length (empty string when zero)
\n    //Content-MD5
\n    //Content-Type
\n    //Date
\n    //If-Modified-Since
\n    //If-Match
\n    //If-None-Match
\n    //If-Unmodified-Since
\n    //Range
x-ms-date:Fri, 26 Jun 2015 23:39:12 GMT\nx-ms-version:2015-02-21\n    //CanonicalizedHeaders
/myaccount/mycontainer\ncomp:metadata\nrestype:container\ntimeout:20    //CanonicalizedResource
*/

public class StringSignaturHelper {
    String httpMethod;
    String contentEnconding;
    String contentLanguage;
    String contentType;
    String contentLength;
    String contentMD5;
    String date;
    String ifModifiedSince ;
    String ifMatch;
    String ifNoneMatch;
    String ifUnModifiedSince;
    String range;
    String canonicalizedHeaders;
    String canonicalizedResources;

    public String buildStringToSign() {
        if(httpMethod!=null && canonicalizedHeaders!=null && canonicalizedResources !=null) {
            StringBuilder sb = new StringBuilder();
            sb.append(httpMethod);
            sb.append(contentEnconding);
            sb.append(contentLanguage);
            sb.append(contentType);
            sb.append(contentLength);
            sb.append(contentMD5);
            sb.append(date);
            sb.append(ifModifiedSince);
            sb.append(ifMatch);
            sb.append(ifNoneMatch);
            sb.append(ifUnModifiedSince);
            sb.append(range);
            sb.append(canonicalizedHeaders);
            sb.append(canonicalizedResources);
            return sb.toString();
        }
        else return null;
    }

    public void addCannonicalHeader(String headerTitle,String headerValue) {
        if(canonicalizedHeaders!=null)
            canonicalizedHeaders+=headerTitle+":"+headerValue+"\n";
        else
            canonicalizedHeaders=headerTitle+":"+headerValue+"\n";
    }

    public void addCannoicalResource(String cannonicalResource) {
        if(canonicalizedResources!=null)
            canonicalizedResources+="\n"+cannonicalResource;
        else
            canonicalizedResources=cannonicalResource;
    }

    public StringSignaturHelper() {
        httpMethod =null;
        contentEnconding="\n";
        contentLanguage="\n";
        contentType="\n";
        contentLength="\n";
        contentMD5="\n";
        date="\n";
        ifModifiedSince="\n";
        ifMatch="\n";
        ifNoneMatch="\n";
        ifUnModifiedSince="\n";
        range="\n";
        canonicalizedHeaders=null;
        canonicalizedResources=null;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod+"\n";
    }

    public String getContentEnconding() {
        return contentEnconding;
    }

    public void setContentEnconding(String contentEnconding) {
        this.contentEnconding = contentEnconding+"\n";
    }

    public String getContentLanguage() {
        return contentLanguage;
    }

    public void setContentLanguage(String contentLanguage) {
        this.contentLanguage = contentLanguage+"\n";
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType+"\n";
    }

    public String getContentLength() {
        return contentLength;
    }

    public void setContentLength(String contentLength) {
        this.contentLength = contentLength+"\n";
    }

    public String getContentMD5() {
        return contentMD5;
    }

    public void setContentMD5(String contentMD5) {
        this.contentMD5 = contentMD5+"\n";
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date+"\n";
    }

    public String getIfModifiedSince() {
        return ifModifiedSince;
    }

    public void setIfModifiedSince(String ifModifiedSince) {
        this.ifModifiedSince = ifModifiedSince+"\n";
    }

    public String getIfMatch() {
        return ifMatch;
    }

    public void setIfMatch(String ifMatch) {
        this.ifMatch = ifMatch+"\n";
    }

    public String getIfNoneMatch() {
        return ifNoneMatch;
    }

    public void setIfNoneMatch(String ifNoneMatch) {
        this.ifNoneMatch = ifNoneMatch+"\n";
    }

    public String getIfUnModifiedSince() {
        return ifUnModifiedSince;
    }

    public void setIfUnModifiedSince(String ifUnModifiedSince) {
        this.ifUnModifiedSince = ifUnModifiedSince+"\n";
    }
    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range +"\n";
    }

    public String getCanonicalizedHeaders() {
        return canonicalizedHeaders;
    }

    public String getCanonicalizedResources() {
        return canonicalizedResources;
    }

}