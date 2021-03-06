
/**
 * BookingCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package soap.booking;

    /**
     *  BookingCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class BookingCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public BookingCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public BookingCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for allerRetour method
            * override this method for handling normal response from allerRetour operation
            */
           public void receiveResultallerRetour(
                    soap.booking.BookingStub.AllerRetourResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from allerRetour operation
           */
            public void receiveErrorallerRetour(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for unbook method
            * override this method for handling normal response from unbook operation
            */
           public void receiveResultunbook(
                    soap.booking.BookingStub.UnbookResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from unbook operation
           */
            public void receiveErrorunbook(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for all method
            * override this method for handling normal response from all operation
            */
           public void receiveResultall(
                    soap.booking.BookingStub.AllResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from all operation
           */
            public void receiveErrorall(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for booking method
            * override this method for handling normal response from booking operation
            */
           public void receiveResultbooking(
                    soap.booking.BookingStub.BookingResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from booking operation
           */
            public void receiveErrorbooking(java.lang.Exception e) {
            }
                


    }
    