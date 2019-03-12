package com.rngay.service_authority.util;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyCustomSSLSocketFactory extends SSLSocketFactory {

    private final SSLSocketFactory delegate;

    public MyCustomSSLSocketFactory(SSLSocketFactory delegate) {
        this.delegate = delegate;
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return delegate.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return delegate.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket(Socket socket, String s, int i, boolean b) throws IOException {
        Socket underlyingSocket = delegate.createSocket(socket, s, i, b);
        return overrideProtocol(underlyingSocket);
    }

    @Override
    public Socket createSocket(String s, int i) throws IOException, UnknownHostException {
        Socket underlyingSocket = delegate.createSocket(s, i);
        return overrideProtocol(underlyingSocket);
    }

    @Override
    public Socket createSocket(String s, int i, InetAddress inetAddress, int i1) throws IOException, UnknownHostException {
        Socket underlyingSocket = delegate.createSocket(s, i, inetAddress, i1);
        return overrideProtocol(underlyingSocket);
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        Socket underlyingSocket = delegate.createSocket(inetAddress, i);
        return overrideProtocol(underlyingSocket);
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress1, int i1) throws IOException {
        Socket underlyingSocket = delegate.createSocket(inetAddress, i, inetAddress1, i1);
        return overrideProtocol(underlyingSocket);
    }

    private Socket overrideProtocol(final Socket socket) {
        if (!(socket instanceof SSLSocket)) {
            throw new RuntimeException("An instance of SSLSocket is expected");
        }
        ((SSLSocket) socket).setEnabledProtocols(new String[]{"TLSv1"});
        return socket;
    }

}
