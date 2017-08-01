package net.htwater.hos.service.impl;

import java.util.List;
import java.util.Map;

import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;
import net.htwater.hos.service.FxRelateService;

public class FxRelateServiceImpl implements FxRelateService{
	BaseDao dao = DaoFactory.getDao(DB_SHANHONG);
	
	public List<Map<String,Object>> queryMaterial(){
		String sql = "select id, responCategory ,reservePoint,wareSpace,strBags,sack,bags,expanBag,wowen,nonWowen,geobrane,tarp,rubber,lifeJacket,lifeBuoy,dumDevice,throwing"+
					 ",assault,craft,powerBoat,boating,pulator,sand,stone,wood,bamboo,leadWire,nails,pipes,cage,nylon,geonerate,diesel,electricMotor,fixedPump,mobilePump"+
					 ",sMobileGenerator,fulePump,gasAndDie,cable,aqueducts,rope,tent,securityCap,gloves,rainCoat,rainBoots,umbralla,worklight,spotlights,mobileLight,alertDevice"+
					 ",satePhone,mobileLightVe,flashLight,shovel,steelHo,twisWheel,chainHosi,hammers,excavator,bulldozer,loader,dumpTrack,crane,transport,takeCar,pumpCar,distrBox"+
					 ",washMachine,pullWaterCar,storageCar,waterDevice,findWateDevice,sprayUnits,sprayDevices,suppliesWorth,contact,contactWays,lotd,latd,b.regionnm as city FROM ZT_SuppliesStatics as a "+
					 " left join sys_region as b on a.regionid = b.regionid";		
		
		return dao.executeQuery(sql.toUpperCase());
	}
	
	public List<Map<String,Object>> queryResTeam(){
		String sql = "SELECT id, resTeamType,resTeamName,resTeamPlace,professionalRes,unitNature,numberStaff,nowPerson,machine,earthMachine,liftingDevice,transportCar"+
					 ",divingDevice,drainDevice,pump,assault,powerBoat,boat,powerDevice,totalAsset,deviceWorth,contact,contactWay,lotd,latd,b.regionnm as city FROM ZT_ResTeamStatics as a "+
				" left join sys_region as b on a.regionid=b.regionid";		
		return dao.executeQuery(sql.toUpperCase());
	}

	@Override
	public List<Map<String, Object>> getBaseInfo(String type) {
		String sql="";
		if(type.equals("region")){
			sql="select * from ZT_AD_Info_B";
		}else if(type.equals("zaihai")){
			sql="select a.*,b.ADNM from ZT_AD_DisInflu_B as a left join ZT_AD_Info_B as b on a.ADCD=b.ADCD";
		}else if(type.equals("valley")){
			sql="select * from ZT_CA_Info_B";
		}else if(type.equals("danger")){
			sql="select a.*,b.ADNM from ZT_DA_Info_B as a left join ZT_AD_Info_B as b on a.ADCD=b.ADCD";
		}else if(type.equals("station")){
			sql="select * from ZT_ST_station_B";
		}else if(type.equals("rule")){
			sql="select a.*,b.STNM from ZT_STWarnRule_B as a left join ZT_ST_station_B as b on a.STCD=b.STCD";
		}else if(type.equals("device")){
			sql="select * from ZT_WarnDevices";
		}else if(type.equals("disaster")){
			sql="select a.*,b.ADNM from ZT_AD_His_HZRD_B as a left join ZT_AD_Info_B as b on a.ADCD=b.ADCD";
		}
		if(!sql.equals("")){
			return dao.executeQuery(sql);
		}else{
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> getSHBaseInfo(String type) {
		String sql="";
		if(type.equals("fangzhiqu")){
			sql="select a.*,citycd=left(a.行政区代码,6),b.行政区名称 as NM,b.行政区名称,b.经度,b.纬度 from D_防治区社会经济调查成果汇总表  as a left join D_行政区划总体情况表 as b on a.行政区代码=b.行政区代码";
		}else if(type.equals("zaihai")){
			sql="select a.*,citycd=left(a.行政区代码,6),b.行政区名称 as NM,b.行政区名称 from D_历史山洪灾害情况汇总表  as a left join D_行政区划总体情况表 as b on a.行政区代码=b.行政区代码";
		}else if(type.equals("res")){
			sql="select *,citycd=left(行政区代码,6),水库名称 as NM,citycd=left(行政区代码,6),(case when 总库容>=10000 then 'B' when 总库容>=1000 then 'M' when 总库容>=100 then 'S1' when 总库容>=10 then 'S2' else 'P' end) as TYPE from D_水库工程汇总表";
		}else if(type.equals("gate")){
			sql="select *,citycd=left(行政区代码,6),水闸名称 as NM,(case when 过闸流量<20 then 'S2' when 过闸流量>=20 then 'S1' when 过闸流量>=100 then 'M' when 过闸流量>=1000 then 'B' end) as TYPE from D_水闸工程汇总表";
		}else if(type.equals("dike")){
			sql="select *,citycd=left(行政区代码,6),堤防名称 as NM from D_堤防工程汇总表";
		}else if(type.equals("weixianqu")){
			sql="select *,citycd=left(行政区代码,6),危险区名称 as NM from D_危险区基本情况信息调查成果汇总表";
		}else if(type.equals("jingji")){
			sql="select a.*,citycd=left(a.行政区代码,6),b.行政区名称 as NM,b.行政区名称,b.经度,b.纬度 from D_县市社会经济基本情况统计表 as a left join D_行政区划总体情况表 as b on a.行政区代码=b.行政区代码 ";
		}else if(type.equals("xingzhengqu")){
			sql="select *,citycd=left(行政区代码,6),行政区名称 as NM from D_行政区划总体情况表";
		}else if(type.equals("zidongzhan")){
			sql="select *,citycd=left(行政区代码,6),测站名称 as NM from D_自动监测站汇总表";
		}else if(type.equals("yuliangzhan")){
			sql="select *,citycd=left(行政区代码,6),站点位置 as NM from D_简易雨量站汇总表";
		}else if(type.equals("shuiweizhan")){
			sql="select *,citycd=left(行政区代码,6),站点位置 as NM from D_简易水位站信息表";
		}
		if(!sql.equals("")){
			return dao.executeQuery(sql);
		}else{
			return null;
		}
	}
}
