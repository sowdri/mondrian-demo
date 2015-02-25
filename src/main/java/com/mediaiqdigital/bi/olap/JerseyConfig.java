package com.mediaiqdigital.bi.olap;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.mediaiqdigital.bi.olap.service.impl.OlapServiceImpl;

@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(OlapServiceImpl.class);
	}

}
