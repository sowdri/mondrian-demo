package com.mediaiqdigital.bi.olap.service;

import org.olap4j.CellSet;

import com.mediaiqdigital.bi.olap.value.MdxResult;

public interface CellSetToMdxResultConverter {

	MdxResult convert(CellSet cellSet);
}
