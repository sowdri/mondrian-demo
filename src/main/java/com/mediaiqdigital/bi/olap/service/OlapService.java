package com.mediaiqdigital.bi.olap.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.mediaiqdigital.bi.olap.value.MdxResult;

@Path("/olap/server")
public interface OlapService {

	@POST
	@Path("/query")
	MdxResult query(String mdx);

}
