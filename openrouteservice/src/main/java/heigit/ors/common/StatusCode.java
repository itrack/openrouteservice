package heigit.ors.common;

public class StatusCode 
{
	 /**
     * Status code (400) indicating the request sent by the client was
     * syntactically incorrect.
     */
   public static int BAD_REQUEST = javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
   /**
    * Status code (405) indicating that the method specified in the
    * <code><em>Request-Line</em></code> is not allowed for the resource
    * identified by the <code><em>Request-URI</em></code>.
    */
   public static int METHOD_NOT_ALLOWED = javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED;
   
   /**
    * Status code (500) indicating an error inside the HTTP server
    * which prevented it from fulfilling the request.
    */
   public static int INTERNAL_SERVER_ERROR = javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
   
   /**
    * Status code (503) indicating that the End Point is
    * temporarily overloaded, and unable to handle the request.
    */
   public static int SERVICE_UNAVAILABLE =  javax.servlet.http.HttpServletResponse.SC_SERVICE_UNAVAILABLE;
}
