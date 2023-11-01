package com.example.spring.jpos.spring.jpos;

import java.io.IOException;

import org.jpos.iso.ISOChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.util.SimpleLogListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.jpos.util.*; 
import org.jpos.iso.channel.*; 
import org.jpos.iso.packager.*;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws ISOException, IOException {
		
		Logger logger = new Logger(); 
		logger.addListener (new SimpleLogListener (System.out));
		
		String hostname = "127.0.0.1";
		int portNumber = 8001;
		
		ISOMsg m = new ISOMsg(); 
		
//		ISOPackager packager = new GenericPackager("CustomConfig.xml");
//		m.setPackager(packager);
		
//		m.set (0, "0800"); 
//		m.set (3, "000000"); 
//		m.set (11, "000001"); 
//		m.set (41, "29110001"); 
//		m.set (60, "jPOS 6"); 
//		m.set (70, "301");
		
//		m.set(0, "0800");
//		m.set(7, "0223103040"); // Date and Time Transmission
//		m.set(11, "303804"); // System Trace Audit number
//		m.set(70, "001"); // Network Management Information Code //random number
		
		m.setMTI("0100");
		m.set(2,"4160211510908933");
		m.set(3,"310000");
		m.set(7, "0223103040");
		m.set(11, "303804");
		m.set(70, "001");
		
		ISOChannel channel = new PostChannel(hostname, portNumber, new EuroPackager()); // new EuroPackager()
		((LogSource)channel).setLogger (logger, "test-channel");
		channel.connect();
		channel.send(m);
		m.dump(System.out, " ");
		ISOMsg r = channel.receive();
		r.dump(System.out, " ");
		//r.dump(System.out, "");
		channel.disconnect();
		
		SpringApplication.run(Application.class, args);
	}

}
