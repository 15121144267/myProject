package com.banshengyuan.feima.view.customview.citypickerview.utils;


import com.banshengyuan.feima.view.customview.citypickerview.model.CityModel;
import com.banshengyuan.feima.view.customview.citypickerview.model.DistrictModel;
import com.banshengyuan.feima.view.customview.citypickerview.model.ProvinceModel;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;


public class XmlParserHandler extends DefaultHandler {

	/**
	 * 存储所有的解析对象
	 */
	private List<ProvinceModel> provinceList = new ArrayList<>();

	public XmlParserHandler() {

	}

	public List<ProvinceModel> getDataList() {
		return provinceList;
	}

	@Override
	public void startDocument() throws SAXException {
		// 当读到第一个开始标签的时候，会触发这个方法
	}

	ProvinceModel provinceModel = new ProvinceModel();
	CityModel cityModel = new CityModel();
	DistrictModel districtModel = new DistrictModel();

	@Override
	public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
		// 当遇到开始标记的时候，调用这个方法
		switch (qName) {
			case "province":
				provinceModel = new ProvinceModel();
				provinceModel.setName(attributes.getValue(0));
				provinceModel.setCityList(new ArrayList<>());
				break;
			case "city":
				cityModel = new CityModel();
				cityModel.setName(attributes.getValue(0));
				cityModel.setDistrictList(new ArrayList<>());
				break;
			case "district":
				districtModel = new DistrictModel();
				districtModel.setName(attributes.getValue(0));
				districtModel.setZipcode(attributes.getValue(1));
				break;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// 遇到结束标记的时候，会调用这个方法
		switch (qName) {
			case "district":
				cityModel.getDistrictList().add(districtModel);
				break;
			case "city":
				provinceModel.getCityList().add(cityModel);
				break;
			case "province":
				provinceList.add(provinceModel);
				break;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
	}

}
