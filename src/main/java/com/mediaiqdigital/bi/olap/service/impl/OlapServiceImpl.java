package com.mediaiqdigital.bi.olap.service.impl;

import javax.ws.rs.WebApplicationException;

import org.olap4j.OlapConnection;
import org.olap4j.OlapException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediaiqdigital.bi.olap.service.CellSetToMdxResultConverter;
import com.mediaiqdigital.bi.olap.service.OlapService;
import com.mediaiqdigital.bi.olap.value.MdxResult;

@Service
public class OlapServiceImpl implements OlapService {

	@Autowired
	private OlapConnection olapConnection;

	@Autowired
	private CellSetToMdxResultConverter converter;

	@Override
	public MdxResult query(String mdx) {
		try {
			return converter.convert(olapConnection.createStatement()
					.executeOlapQuery(mdx));
		} catch (OlapException e) {
			e.printStackTrace();
			throw new WebApplicationException(e);
		}
	}
}
