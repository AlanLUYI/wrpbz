package net.htwater.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ysf.excelUtil.ExcelIn;

public class TideExcelIn extends ExcelIn {
	private static final Logger log=Logger.getLogger(TideExcelIn.class.getName());
	public TideExcelIn(File excelFile, File xmlFile) {
		super(excelFile, xmlFile);
	}

	@Override
	protected void ExcelSave() {
		String excelName = this.curEntityCode;
		System.out.println(excelName);
		
		//this.procAdminDivison();
	}
	
	public List<Map<String, Object>> getMyData(){
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> lst = this.getListDatas();
		return lst;
	}

	@Override
	protected void ProcLogsSava() {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] agrs){

	}

}
