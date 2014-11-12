package library.webservice;
import java.io.IOException;

import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.ServiceConnection;


public class MyAndroidHttpTransport extends HttpTransportSE {
    public MyAndroidHttpTransport(String url) {
        super(url);
    }
    @Override
    protected ServiceConnection getServiceConnection() throws IOException {
        ServiceConnectionSE serviceConnection = new ServiceConnectionSE(url);
        serviceConnection.setConnectionTimeOut(8000);
        serviceConnection.setReadTimeOut(8000);
        return serviceConnection;
    }
    
}