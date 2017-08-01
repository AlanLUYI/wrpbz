/**
 * 
 */
package net.htwater.proceDataReport;

import java.beans.IntrospectionException;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.htwater.Util.CommonMethod;
import net.sf.json.JSON;

import org.apache.log4j.Logger;

import ysf.excelUtil.ExcelIn;
import cn.miao.framework.dao.BaseDao;
import cn.miao.framework.factory.DaoFactory;

/**
 * @author yangshengfei
 * @createTime 2015年1月27日
 * @updateTime 2015年4月6日
 * @描述:基础数据上报处理
 */
public class ProcDataBase extends ExcelIn {
	private static final Logger log=Logger.getLogger(ProcDataBase.class.getName());
	public static String region="";
	/**
	 * @param excelFile
	 * @param xmlFile
	 */
	public ProcDataBase(String userName,File excelFile, File xmlFile) {
		super(userName,excelFile, xmlFile);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 描述:需实现此抽象方法进行数据入库
	 * @createTime 2015年1月27日
	 * @updateTime 2015年1月27日
	 * @return 
	 */
	/* (non-Javadoc)
	 * @see ysf.excelUtil.ExcelIn#ExcelSave()
	 */
	@Override
	protected void ExcelSave() {
		// TODO Auto-generated method stub
		String excelName=this.curEntityCode;//xml配置表名
		
		//以下代码实现对上传数据为空表实现业务逻辑
		if(!(this.getListDatas().size()>0)){
			this.ProcReport.put(excelName, "数据为空，请填写数据再上传！");
			this.allProcLogs.append(excelName+":数据为空，请填写数据再上传！");
			return;
		}
		
		StringBuffer checkErrorString=this.errorString;
		if (checkErrorString.length() > 0) {//对excel每一个sheet进行检查，如果有错误则不存入数据库
			String[] strArr = checkErrorString.toString().split("<br>");
			for(String s: strArr){
				log.error(s);
			}
			
			this.ProcReport.put(excelName, "解析入库失败,请查看处理信息");
			return;
		}
		
		  
		switch (excelName) {
		case "表1_行政区划基本情况表":
			try {
				this.ProcAdminDivisions(excelName);
			} catch (IllegalAccessException | InstantiationException
					| InvocationTargetException | IntrospectionException e1) {
				// TODO Auto-generated catch block
				log.error(e1);
			}
			break;
		case "表2_山洪灾害影响情况表":
			try {
				this.ProcFloodDisasters(excelName);				
			} catch (IllegalAccessException | InstantiationException
					| InvocationTargetException | IntrospectionException e2) {
				// TODO Auto-generated catch block
				log.error(e2);
			}
		break;
		case "表3_小流域基本情况表":
			try {
				this.ProcSmallWaterBase(excelName);
			} catch (IllegalAccessException | InstantiationException
					| InvocationTargetException | IntrospectionException e3) {
				// TODO Auto-generated catch block
				log.error(e3);
			}
		break;
		case "表4_小流域和乡镇村关联表":
			try {
				this.ProcSmallWaterLink(excelName);
			} catch (IllegalAccessException | InstantiationException
					| InvocationTargetException | IntrospectionException e4) {
				// TODO Auto-generated catch block
				log.error(e4);
			}
		break;
		case "表5_危险区基本情况表":
			try {
				this.ProcDangerZoneBase(excelName);
			} catch (IllegalAccessException | InstantiationException
					| InvocationTargetException | IntrospectionException e5) {
				// TODO Auto-generated catch block
				log.error(e5);
			}
		break;
		case "表6_测站基本信息表":
			try {
				this.ProcStationBaseInfo(excelName);
			} catch (IllegalAccessException | InstantiationException
					| InvocationTargetException | IntrospectionException e6) {
				// TODO Auto-generated catch block
				log.error(e6);
			}
		break;
		case "表7_雨量测站极值表":
			try {
				this.ProcRainStaExtremum(excelName);
			} catch (IllegalAccessException | InstantiationException
					| InvocationTargetException | IntrospectionException e7) {
				// TODO Auto-generated catch block
				log.error(e7);
			}
		break;
		case "表8_水位测站极值表":
			try {
				this.ProcWaterLevelExtremum(excelName);
			} catch (IllegalAccessException | InstantiationException
					| InvocationTargetException | IntrospectionException e8) {
				// TODO Auto-generated catch block
				log.error(e8);
			}
		break;
		case "表9_山洪灾害预警指标":
			try {
				this.ProcDisasterWarnIndica(excelName);
			} catch (IllegalAccessException | InstantiationException
					| InvocationTargetException | IntrospectionException e9) {
				// TODO Auto-generated catch block
				log.error(e9);
			}
		break;
		case "表11_历史山洪灾害情况表":
			try {
				this.ProcHistoryDisaster(excelName);
			} catch (IllegalAccessException | InstantiationException
					| InvocationTargetException | IntrospectionException e11) {
				// TODO Auto-generated catch block
				log.error(e11);
			}
		break;
		case "表12_河流基本情况表":
			try {
				this.ProcRiverBase(excelName);
			} catch (IllegalAccessException | InstantiationException
					| InvocationTargetException | IntrospectionException e12) {
				// TODO Auto-generated catch block
				log.error(e12);
			}
		break;
		case "表13_堤防基本情况表":
			try {
				this.ProcDikeBase(excelName);
			} catch (IllegalAccessException | InstantiationException
					| InvocationTargetException | IntrospectionException e13) {
				// TODO Auto-generated catch block
				log.error(e13);
			}
		break;
		case "表14_水库基本情况表":
			try {
				this.ProcResBase(excelName);
			} catch (IllegalAccessException | InstantiationException
					| InvocationTargetException | IntrospectionException e14) {
				// TODO Auto-generated catch block
				log.error(e14);
			}
		break;
		/*case "表15_预警部门情况":
			try {
				this.ProcWarnDepartment(excelName);
			} catch (IllegalAccessException | InstantiationException
					| InvocationTargetException | IntrospectionException e15) {
				// TODO Auto-generated catch block
				log.error(e15);
			}
		break;*/
		case "表15_预警人员基本情况表":
			try {
				this.ProcWarnPersonBase(excelName);
			} catch (IllegalAccessException | InstantiationException
					| InvocationTargetException | IntrospectionException e16) {
				// TODO Auto-generated catch block
				log.error(e16);
			}
		break;
		case "表10_预警设施":
			try {
				this.ProcWarnDevices(excelName);
			} catch (IllegalAccessException | InstantiationException
					| InvocationTargetException | IntrospectionException e10) {
				// TODO Auto-generated catch block
				log.error(e10);
			}
		break;
		case "表16_各类责任人名单":
			try {
				this.ProcDutyPerson(excelName);
			} catch (IllegalAccessException | InstantiationException
					| InvocationTargetException | IntrospectionException e17) {
				// TODO Auto-generated catch block
				log.error(e17);
			}
		break;
		case "表17_物资储备情况统计表":
			try {
				this.ProcSuppliesStatics(excelName);
			} catch (IllegalAccessException | InstantiationException
					| InvocationTargetException | IntrospectionException e18) {
				// TODO Auto-generated catch block
				log.error(e18);
			}
		break;
		case "表18_抢险队基本情况统计表":
			try {
				this.ProcResTeamStatics(excelName);
			} catch (IllegalAccessException | InstantiationException
					| InvocationTargetException | IntrospectionException e19) {
				// TODO Auto-generated catch block
				log.error(e19);
			}
		break;
		case "表19_防汛抗旱物资分管负责人及物资联系人通讯录":
			try {
				this.ProcFloodSuppliesContacts(excelName);
			} catch (IllegalAccessException | InstantiationException
					| InvocationTargetException | IntrospectionException e20) {
				// TODO Auto-generated catch block
				log.error(e20);
			}
		break;
		case "表20_防汛抗旱物资储备仓库基本情况调查表":
			try {
				this.ProcStorageBase(excelName);
			} catch (IllegalAccessException | InstantiationException
					| InvocationTargetException | IntrospectionException e21) {
				// TODO Auto-generated catch block
				log.error(e21);
			}
		break;
		case "表21_社会防汛抢险应急资源调查统计表":
			try {
				this.ProcSocialResourcesStatics(excelName);
			} catch (IllegalAccessException | InstantiationException
					| InvocationTargetException | IntrospectionException e22) {
				// TODO Auto-generated catch block
				log.error(e22);
			}
		break;
		case "表22_大流量移动水泵统计表":
			try {
				this.ProcHighFlowPumpStatics(excelName);
			} catch (IllegalAccessException | InstantiationException
					| InvocationTargetException | IntrospectionException e23) {
				// TODO Auto-generated catch block
				log.error(e23);
			}
		break;
		default:
			this.ProcReport.put(excelName, "解析入库失败,后台没有相应的处理代码");
			log.info("["+excelName+"]后台没有相应的处理代码！");
			break;
		}
		
		this.ProcReport.put(excelName, "解析入库成功");
	
	}
	
	/* (non-Javadoc)
	 * @see ysf.excelUtil.ExcelIn#ProcLogsSava()
	 */
	@Override
	protected void ProcLogsSava() {
		// TODO Auto-generated method stub
		String userName=getUserName();
		String[] logs=this.allProcLogs.toString().split("<br>");
		String status=this.allProcLogs.length()>0?"处理失败":"处理成功";
		new CommonMethod().ProcLogs(userName,region, "基础数据", this.fileName, status, logs);//记录处理日志
	}
	
	/**
	 * 
	 * 描述:处理，大流量移动水泵统计表
	 * @createTime 2015年2月9日
	 * @updateTime 2015年2月9日
	 * @return
	 */
	private void ProcHighFlowPumpStatics(String excelName) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		   List<HighFlowPumpStaticsClass> items= new ArrayList<HighFlowPumpStaticsClass>();
		   for(int i = 0 ; i<this.getListDatas().size();i++){
			   Map<?, ?> excelCol = (Map<?, ?>) this.getListDatas().get(i);   
			   HighFlowPumpStaticsClass item = new HighFlowPumpStaticsClass();	
			   
			   HighFlowPumpStaticsClass obj = (HighFlowPumpStaticsClass) convertMap(item.getClass(), excelCol);
			   items.add(obj);
		   }
		  
		   for(int i = 0;i<items.size();i++){
			   try{
				   int ret=SaveHighFlowPumpStatics(items.get(i));
				   log.info(ret==1?excelName+"更新:第"+i+"条记录成功":excelName+"更新:第"+i+"条记录失败");
			   }catch(Exception e){
				   log.error(e);
			   }
		   }
	}
	
	/**
	 * 
	 * 描述:此处是保存excel数据到数据库方法，为了保证数据不被错误覆盖，请使用同步方法或同步块！(大流量移动水泵统计表),
	 * @createTime 2015年2月9日
	 * @updateTime 2015年2月9日
	 * @return
  	 * Modified By lxj 20150519
	 * 更新该条记录中   regionid  字段值为当前region
	 * 在方法 SaveResTeamStatics以及数据库存储过程，同步增加了 插入字段 regionid 
	 */
	@SuppressWarnings("finally")
	private synchronized int SaveHighFlowPumpStatics(HighFlowPumpStaticsClass item) {
		BaseDao dao = DaoFactory.getDao("HOS");
		int isinsert=0;

		try {
			dao.executeSQL("{call HighFlowPumpStatics_DataReport(?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?)}", new Object[]{
					item.getName(),item.getCount(),item.getStoragePlace(),item.getStorageUnit(),item.getFlow(),
					item.getHead(),item.getPower(),item.getIsCar(),item.getIsWeight(),item.getIsPower(),item.getInlet(),
					item.getOutlet(),item.getCable(),item.getIsControlBox(),region
			});
			
			isinsert=1;
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e); 
		}finally{
			return isinsert;
		}
	}
	
	/**
	 * 
	 * 描述:处理，社会防汛抢险应急资源调查统计表
	 * @createTime 2015年2月9日
	 * @updateTime 2015年2月9日
	 * @return
	 */
	private void ProcSocialResourcesStatics(String excelName) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		   List<SocialResourcesStaticsClass> items= new ArrayList<SocialResourcesStaticsClass>();
		   for(int i = 0 ; i<this.getListDatas().size();i++){
			   Map<?, ?> excelCol = (Map<?, ?>) this.getListDatas().get(i);   
			   SocialResourcesStaticsClass item = new SocialResourcesStaticsClass();	
			   
			   SocialResourcesStaticsClass obj = (SocialResourcesStaticsClass) convertMap(item.getClass(), excelCol);
			   items.add(obj);
		   }
		  
		   for(int i = 0;i<items.size();i++){
			   try{
				   int ret=SaveSocialResourcesStatics(items.get(i));
				   log.info(ret==1?excelName+"更新:第"+i+"条记录成功":excelName+"更新:第"+i+"条记录失败");
			   }catch(Exception e){
				   log.error(e);
			   }
		   }
	}
	
	/**
	 * 
	 * 描述:此处是保存excel数据到数据库方法，为了保证数据不被错误覆盖，请使用同步方法或同步块！(社会防汛抢险应急资源调查统计表),
	 * @createTime 2015年2月9日
	 * @updateTime 2015年2月9日
	 * @return
 	 * Modified By lxj 20150519
	 * 更新该条记录中   regionid  字段值为当前region
	 * 在方法 SaveResTeamStatics以及数据库存储过程，同步增加了 插入字段 regionid 
	 */
	@SuppressWarnings("finally")
	private synchronized int SaveSocialResourcesStatics(SocialResourcesStaticsClass item) {
		BaseDao dao = DaoFactory.getDao("HOS");
		int isinsert=0;

		try {
			dao.executeSQL("{call SocialResourcesStatics_DataReport(?,?,?,?,?,?,?,?)}", new Object[]{
					item.getName(),item.getBread(),item.getManufacturer(),item.getAddress(),
					item.getCompanySize(),item.getDailyInventory(),item.getProductionCapa(),region
			});
			
			isinsert=1;
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e); 
		}finally{
			return isinsert;
		}
	}
	
	/**
	 * 
	 * 描述:处理，防汛抗旱物资储备仓库基本情况调查表
	 * @createTime 2015年2月9日
	 * @updateTime 2015年2月9日
	 * @return
	 */
	private void ProcStorageBase(String excelName) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		   List<StorageBaseClass> items= new ArrayList<StorageBaseClass>();
		   for(int i = 0 ; i<this.getListDatas().size();i++){
			   Map<?, ?> excelCol = (Map<?, ?>) this.getListDatas().get(i);   
			   StorageBaseClass item = new StorageBaseClass();	
			   
			   StorageBaseClass obj = (StorageBaseClass) convertMap(item.getClass(), excelCol);
			   items.add(obj);
		   }
		  
		   for(int i = 0;i<items.size();i++){
			   try{
				   int ret=SaveStorageBase(items.get(i));
				   log.info(ret==1?excelName+"更新:第"+i+"条记录成功":excelName+"更新:第"+i+"条记录失败");
			   }catch(Exception e){
				   log.error(e);
			   }
		   }
	}
	
	/**
	 * 
	 * 描述:此处是保存excel数据到数据库方法，为了保证数据不被错误覆盖，请使用同步方法或同步块！(防汛抗旱物资储备仓库基本情况调查表),
	 * @createTime 2015年2月9日
	 * @updateTime 2015年2月9日
	 * @return
	 * Modified By lxj 20150519
	 * 更新该条记录中   regionid  字段值为当前region
	 * 在方法 SaveResTeamStatics以及数据库存储过程，同步增加了 插入字段 regionid
	 */
	@SuppressWarnings("finally")
	private synchronized int SaveStorageBase(StorageBaseClass item) {
		BaseDao dao = DaoFactory.getDao("HOS");
		int isinsert=0;

		try {
			dao.executeSQL("{call StorageBase_DataReport(?,?,?,?,?,?,?,?,?,?,?)}", new Object[]{
					item.getUnit(),item.getReserveName(),item.getDetailAddress(),item.getStorageArea(),
					item.getBuildYear(),item.getStorageConditions(),item.getStorageStruct(),item.getTrafficConditions(),
					item.getLotd(),item.getLatd(),region
			});
			
			isinsert=1;
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e); 
		}finally{
			return isinsert;
		}
	}
	
	/**
	 * 
	 * 描述:处理，防汛抗旱物资分管负责人及物资联系人通讯录
	 * @createTime 2015年2月9日
	 * @updateTime 2015年2月9日
	 * @return
	 */
	private void ProcFloodSuppliesContacts(String excelName) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		   List<FloodSuppliesContactsClass> items= new ArrayList<FloodSuppliesContactsClass>();
		   for(int i = 0 ; i<this.getListDatas().size();i++){
			   Map<?, ?> excelCol = (Map<?, ?>) this.getListDatas().get(i);   
			   FloodSuppliesContactsClass item = new FloodSuppliesContactsClass();	
			   
			   FloodSuppliesContactsClass obj = (FloodSuppliesContactsClass) convertMap(item.getClass(), excelCol);
			   items.add(obj);
		   }
		  
		   for(int i = 0;i<items.size();i++){
			   try{
				   int ret=SaveFloodSuppliesContacts(items.get(i));
				   log.info(ret==1?excelName+"更新:第"+i+"条记录成功":excelName+"更新:第"+i+"条记录失败");
			   }catch(Exception e){
				   log.error(e);
			   }
		   }
	}
	
	/**
	 * 
	 * 描述:此处是保存excel数据到数据库方法，为了保证数据不被错误覆盖，请使用同步方法或同步块！(防汛抗旱物资分管负责人及物资联系人通讯录),
	 * @createTime 2015年2月9日
	 * @updateTime 2015年2月9日
	 * @return
	 */
	@SuppressWarnings("finally")
	private synchronized int SaveFloodSuppliesContacts(FloodSuppliesContactsClass item) {
		BaseDao dao = DaoFactory.getDao("HOS");
		int isinsert=0;

		try {
			dao.executeSQL("{call FloodSuppliesContacts_DataReport(?,?,?,?,?,?,?,?,?,?)}", new Object[]{
					item.getCounties(),item.getAreaCode(),item.getDutyPhone(),item.getFax(),
					item.getPerInCharge(),item.getFzrofficePhone(),item.getFzrmobilePhone(),item.getAttn(),
					item.getJbrofficePhone(),item.getJbrmobilePhone()
			});
			
			isinsert=1;
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e); 
		}finally{
			return isinsert;
		}
	}
	
	/**
	 * 
	 * 描述:处理，抢险队基本情况统计表
	 * @createTime 2015年2月9日
	 * @updateTime 2015年2月9日
	 * @return
	 */
	private void ProcResTeamStatics(String excelName) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		   List<ResTeamStaticsClass> items= new ArrayList<ResTeamStaticsClass>();
		   for(int i = 0 ; i<this.getListDatas().size();i++){
			   Map<?, ?> excelCol = (Map<?, ?>) this.getListDatas().get(i);   
			   ResTeamStaticsClass item = new ResTeamStaticsClass();	
			   
			   ResTeamStaticsClass obj = (ResTeamStaticsClass) convertMap(item.getClass(), excelCol);
			   items.add(obj);
		   }
		  
		   for(int i = 0;i<items.size();i++){
			   try{
				   int ret=SaveResTeamStatics(items.get(i));
				   log.info(ret==1?excelName+"更新:第"+i+"条记录成功":excelName+"更新:第"+i+"条记录失败");
			   }catch(Exception e){
				   log.error(e);
			   }
		   }
	}
	
	/**
	 * 
	 * 描述:此处是保存excel数据到数据库方法，为了保证数据不被错误覆盖，请使用同步方法或同步块！(抢险队基本情况统计表),
	 * @createTime 2015年2月9日
	 * @updateTime 2015年2月9日
	 * @return
	 * Modified By lxj 20150519
	 * 更新该条记录中   regionid  字段值为当前region
	 * 在方法 SaveResTeamStatics以及数据库存储过程，同步增加了 插入字段 regionid
	 */
	@SuppressWarnings("finally")
	private synchronized int SaveResTeamStatics(ResTeamStaticsClass item) {
		BaseDao dao = DaoFactory.getDao("HOS");
		int isinsert=0;

		try {
			dao.executeSQL("{call ResTeamStatics_DataReport(?,?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?)}", new Object[]{item.getResTeamType(),
					item.getResTeamName(),item.getResTeamPlace(),item.getProfessionalRes(),item.getUnitNature(),item.getNumberStaff(),
					item.getNowPerson(),item.getMachine(),item.getEarthMachine(),item.getLiftingDevice(),item.getTransportCar(),
					item.getDivingDevice(),item.getDrainDevice(),item.getPump(),item.getAssault(),item.getPowerBoat(),
					item.getBoat(),item.getPowerDevice(),item.getTotalAsset(),item.getDeviceWorth(),item.getContact(),
					item.getContactWay(),item.getLotd(),item.getLatd(),region
			});
			
			isinsert=1;
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e); 
		}finally{
			return isinsert;
		}
	}
	
	/**
	 * 
	 * 描述:处理，物资储备情况统计表
	 * @createTime 2015年2月9日
	 * @updateTime 2015年2月9日
	 * @return
	 */
	private void ProcSuppliesStatics(String excelName) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		   List<SuppliesStaticsClass> items= new ArrayList<SuppliesStaticsClass>();
		   for(int i = 0 ; i<this.getListDatas().size();i++){
			   Map<?, ?> excelCol = (Map<?, ?>) this.getListDatas().get(i);   
			   SuppliesStaticsClass item = new SuppliesStaticsClass();	
			   
			   SuppliesStaticsClass obj = (SuppliesStaticsClass) convertMap(item.getClass(), excelCol);
			   items.add(obj);
		   }
		  
		   for(int i = 0;i<items.size();i++){
			   try{
				   int ret=SaveSuppliesStatics(items.get(i));				   
				   log.info(ret==1?excelName+"更新:第"+i+"条记录成功":excelName+"更新:第"+i+"条记录失败");
 			   }catch(Exception e){
				   log.error(e);
			   }
		   }
	}
	
	/**
	 * 
	 * 描述:此处是保存excel数据到数据库方法，为了保证数据不被错误覆盖，请使用同步方法或同步块！(物资储备情况统计表),
	 * @createTime 2015年2月9日
	 * @updateTime 2015年2月9日
	 * @return 
	 * Modified By lxj 20150519
	 * 更新该条记录中   regionid  字段值为当前region
	 * 在方法 SaveSuppliesStatics以及数据库存储过程，同步增加了 插入字段 regionid
	 */
	@SuppressWarnings("finally")
	private synchronized int SaveSuppliesStatics(SuppliesStaticsClass item) {
		BaseDao dao = DaoFactory.getDao("HOS");
		int isinsert=0;

		try {
			dao.executeSQL("{call SuppliesStatics_DataReport(?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,?)}", new Object[]{
					item.getresponCategory(),item.getreservePoint(),item.getwareSpace(),item.getstrBags(),item.getsack(),
					item.getbags(),item.getexpanBag(),item.getwowen(),item.getnonWowen(),item.getgeobrane(),
					item.gettarp(),item.getrubber(),item.getlifeJacket(),item.getlifeBuoy(),item.getdumDevice(),
					item.getthrowing(),item.getassault(),item.getcraft(),item.getpowerBoat(),item.getboating(),
					item.getpulator(),item.getSand(),item.getStone(),item.getWood(),item.getBamboo(),item.getLeadWire(),
					item.getNails(),item.getPipes(),item.getCage(),item.getNylon(),item.getGeonerate(),item.getDiesel(),
					item.getElectricMotor(),item.getFixedPump(),item.getMobilePump(),item.getSmobileGenerator(),item.getFulePump(),
					item.getGasAndDie(),item.getcable(),item.getaqueducts(),item.getrope(),item.gettent(),item.getsecurityCap(),
					item.getgloves(),item.getrainCoat(),item.getrainBoots(),item.getumbralla(),item.getworklight(),
					item.getspotlights(),item.getmobileLight(),item.getalertDevice(),item.getsatePhone(),item.getmobileLightVe(),
					item.getflashLight(),item.getshovel(),item.getsteelHo(),item.gettwisWheel(),item.getchainHosi(),
					item.gethammers(),item.getexcavator(),item.getbulldozer(),item.getloader(),item.getdumpTrack(),item.getcrane(),
					item.gettransport(),item.gettakeCar(),item.getpumpCar(),item.getdistrBox(),item.getwashMachine(),
					item.getpullWaterCar(),item.getstorageCar(),item.getwaterDevice(),item.getfindWateDevice(),item.getsprayUnits(),
					item.getsprayDevices(),item.getsuppliesWorth(),item.getcontact(),item.getcontactWays(),item.getlotd(),item.getlatd(),region
			});
			
			isinsert=1;
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e); 
		}finally{
			return isinsert;
		}
	}
	
	/**
	 * 
	 * 描述:处理，各类责任人名单
	 * @createTime 2015年2月6日
	 * @updateTime 2015年2月6日
	 * @return
	 */
	private void ProcDutyPerson(String excelName) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		   List<DutyPersonClass> items= new ArrayList<DutyPersonClass>();
		   for(int i = 0 ; i<this.getListDatas().size();i++){
			   Map<?, ?> excelCol = (Map<?, ?>) this.getListDatas().get(i);   
			   DutyPersonClass item = new DutyPersonClass();	
			   
			   DutyPersonClass obj = (DutyPersonClass) convertMap(item.getClass(), excelCol);
			   items.add(obj);
		   }
		  
		   for(int i = 0;i<items.size();i++){
			   try{
				   int ret=SaveDutyPerson(items.get(i));
				   log.info(ret==1?excelName+"更新:第"+i+"条记录成功":excelName+"更新:第"+i+"条记录失败");
			   }catch(Exception e){
				   log.error(e);
			   }
		   }
	}
	
	/**
	 * 
	 * 描述:此处是保存excel数据到数据库方法，为了保证数据不被错误覆盖，请使用同步方法或同步块！(各类责任人名单),
	 * @createTime 2015年2月6日
	 * @updateTime 2015年2月6日
	 * @return
	 */
	@SuppressWarnings("finally")
	private synchronized int SaveDutyPerson(DutyPersonClass item) {
		BaseDao dao = DaoFactory.getDao("HOS");
		int isinsert=0;
		try {
			dao.executeSQL("{call DutyPerson_DataReport(?,?,?,?,?,?,?,?,?)}", new Object[]{
					item.getname(),item.getunit(),item.getposition(),item.getofficeTell(),
					item.getCountry(),
					item.getmobile(),item.gettype(),item.getrestype(),item.getremark()
			});
			
			isinsert=1;
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e); 
		}finally{
			return isinsert;
		}
	}
	
	/**
	 * 
	 * 描述:处理，预警设施
	 * @createTime 2015年2月5日
	 * @updateTime 2015年2月5日
	 * @return
	 */
	private void ProcWarnDevices(String excelName) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		   List<WarnDevicesClass> items= new ArrayList<WarnDevicesClass>();
		   for(int i = 0 ; i<this.getListDatas().size();i++){
			   Map<?, ?> excelCol = (Map<?, ?>) this.getListDatas().get(i);   
			   WarnDevicesClass item = new WarnDevicesClass();	
			   
			   WarnDevicesClass obj = (WarnDevicesClass) convertMap(item.getClass(), excelCol);
			   items.add(obj);
		   }
		  
		   for(int i = 0;i<items.size();i++){
			   try{
				   int ret=SaveWarnDevices(items.get(i));
				   log.info(ret==1?excelName+"更新:第"+i+"条记录成功":excelName+"更新:第"+i+"条记录失败");
			   }catch(Exception e){
				   log.error(e);
			   }
		   }
	}
	
	/**
	 * 
	 * 描述:此处是保存excel数据到数据库方法，为了保证数据不被错误覆盖，请使用同步方法或同步块！(预警设施),
	 * @createTime 2015年2月5日
	 * @updateTime 2015年2月5日
	 * @return
	 */
	@SuppressWarnings("finally")
	private synchronized int SaveWarnDevices(WarnDevicesClass item) {
		BaseDao dao = DaoFactory.getDao("HOS");
		int isinsert=0;

		try {
			dao.executeSQL("{call WarnDevices_DataReport(?,?,?,?,?,?,?,?)}", new Object[]{
					item.getdeviceName(),item.getDeviceType(),item.getNativeCountry(),item.getLon(),
					item.getLat(),item.getAdminName(),item.getPhone(),item.getMobile()
			});
			
			isinsert=1;
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e); 
		}finally{
			return isinsert;
		}
	}
	
	/**
	 * 
	 * 描述:处理，预警人员基本情况表
	 * @createTime 2015年2月5日
	 * @updateTime 2015年2月5日
	 * @return
	 */
	private void ProcWarnPersonBase(String excelName) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		   List<WarnPersonBaseClass> items= new ArrayList<WarnPersonBaseClass>();
		   for(int i = 0 ; i<this.getListDatas().size();i++){
			   Map<?, ?> excelCol = (Map<?, ?>) this.getListDatas().get(i);   
			   WarnPersonBaseClass item = new WarnPersonBaseClass();	
	
			   WarnPersonBaseClass obj = (WarnPersonBaseClass) convertMap(item.getClass(), excelCol);
			   items.add(obj);
		   }
		  
		   for(int i = 0;i<items.size();i++){
			   try{
				   int ret=SaveWarnPersonBase(items.get(i));
				   log.info(ret==1?excelName+"更新:第"+i+"条记录成功":excelName+"更新:第"+i+"条记录失败");
			   }catch(Exception e){
				   log.error(e);
			   }
		   }
	}
	
	/**
	 * 
	 * 描述:此处是保存excel数据到数据库方法，为了保证数据不被错误覆盖，请使用同步方法或同步块！(预警人员基本情况表),
	 * @createTime 2015年2月5日
	 * @updateTime 2015年2月5日
	 * @return
	 */
	@SuppressWarnings("finally")
	private synchronized int SaveWarnPersonBase(WarnPersonBaseClass item) {
		BaseDao dao = DaoFactory.getDao("HOS");
		int isinsert=0;

		try {
			dao.executeSQL("{call WarnPersonBase_DataReport(?,?,?,?,?,?,?,?)}", new Object[]{
					item.getpersonCD(),item.getadcd(),item.getname(),item.getsex(),item.getcompany(),
					item.getposition(),item.getmobile(),item.getofficeTel()
			});
			
			isinsert=1;
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e); 
		}finally{
			return isinsert;
		}
	}
	
	/**
	 * 
	 * 描述:处理，预警部门情况
	 * @createTime 2015年2月5日
	 * @updateTime 2015年2月5日
	 * @return
	 */
	/*private void ProcWarnDepartment(String excelName) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		   List<WarnDepartmentClass> items= new ArrayList<WarnDepartmentClass>();
		   for(int i = 0 ; i<this.getListDatas().size();i++){
			   Map<?, ?> excelCol = (Map<?, ?>) this.getListDatas().get(i);   
			   WarnDepartmentClass item = new WarnDepartmentClass();	
			   
			   WarnDepartmentClass obj = (WarnDepartmentClass) convertMap(item.getClass(), excelCol);
			   items.add(obj);
		   }
		  
		   for(int i = 0;i<items.size();i++){
			   try{
				   int ret=SaveWarnDepartment(items.get(i));
				   log.info(ret==1?excelName+"更新:第"+i+"条记录成功":excelName+"更新:第"+i+"条记录失败");
			   }catch(Exception e){
				   log.error(e);
			   }
		   }
	}*/
	
	/**
	 * 
	 * 描述:此处是保存excel数据到数据库方法，为了保证数据不被错误覆盖，请使用同步方法或同步块！(预警部门情况),
	 * @createTime 2015年2月5日
	 * @updateTime 2015年2月5日
	 * @return
	 */
	/*@SuppressWarnings("finally")
	private synchronized int SaveWarnDepartment(WarnDepartmentClass item) {
		BaseDao dao = DaoFactory.getDao("HOS");
		int isinsert=0;

		try {
			dao.executeSQL("{call WarnDepartment_DataReport(?,?,?,?,?,?)}", new Object[]{
					item.getadcd(),item.getwarnGradeID(),item.getwarnTypeID(),
					item.getwarnStatusID(),item.getdeptCD(),item.getwarnRange()
			});
			
			isinsert=1;
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e); 
		}finally{
			return isinsert;
		}
	}*/
	
	/**
	 * 
	 * 描述:处理，水库基本情况表
	 * @createTime 2015年2月5日
	 * @updateTime 2015年2月5日
	 * @return
	 */
	private void ProcResBase(String excelName) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		   List<ResBaseClass> items= new ArrayList<ResBaseClass>();
		   for(int i = 0 ; i<this.getListDatas().size();i++){
			   Map<?, ?> excelCol = (Map<?, ?>) this.getListDatas().get(i);   
			   ResBaseClass item = new ResBaseClass();	
			   ResBaseClass obj = (ResBaseClass) convertMap(item.getClass(), excelCol);
			   items.add(obj);
		   }
		  
		   for(int i = 0;i<items.size();i++){
			   try{
				   int ret=SaveResBase(items.get(i));
				   log.info(ret==1?excelName+"更新:第"+i+"条记录成功":excelName+"更新:第"+i+"条记录失败");
			   }catch(Exception e){
				   log.error(e);
			   }
		   }
	}
	
	/**
	 * 
	 * 描述:此处是保存excel数据到数据库方法，为了保证数据不被错误覆盖，请使用同步方法或同步块！(水库基本情况表),
	 * @createTime 2015年2月5日
	 * @updateTime 2015年2月5日
	 * @return
	 */
	@SuppressWarnings("finally")
	private synchronized int SaveResBase(ResBaseClass item) {
		BaseDao dao = DaoFactory.getDao("HOS");
		int isinsert=0;

		try {
			dao.executeSQL("{call ResBase_DataReport(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", new Object[]{
					item.getrscd(),item.getrsnm(),item.getstcd(),item.getdmstatpl(),item.getenRvnm3(),item.getsupeAdun(),
					item.getengsdate(),item.getdrbsar(),item.getxhst(),item.getdsfllv(),item.getnrwtlv(),item.getflz(),
					item.getflzst(),item.getfldz(),item.getfldcp(),item.getdmtp(),item.getdmtpln(),item.getmxdmhg(),
					item.getyhdyDmtpwd(),item.getdscndtpy(),item.getinbtcgel(),item.getmxdsy(),item.getflds(),item.getflch(),
					item.getflac(),item.getxlllDsfllv(),item.getxlllChfllv(),item.getdwcnstds(),item.getpower(),
					item.getsafetm(),item.getsafegrade(),item.getsafefiles(),item.getdwysqn(),item.getxyyjsd()
			});
			
			isinsert=1;
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e); 
		}finally{
			return isinsert;
		}
	}
	
	/**
	 * 
	 * 描述:处理，堤防基本情况表
	 * @createTime 2015年2月5日
	 * @updateTime 2015年2月5日
	 * @return
	 */
	private void ProcDikeBase(String excelName) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		   List<DikeBaseClass> items= new ArrayList<DikeBaseClass>();
		   for(int i = 0 ; i<this.getListDatas().size();i++){
			   Map<?, ?> excelCol = (Map<?, ?>) this.getListDatas().get(i);   
			   DikeBaseClass item = new DikeBaseClass();	
			   
			   DikeBaseClass obj = (DikeBaseClass) convertMap(item.getClass(), excelCol);
			   items.add(obj);
		   }
		  
		   for(int i = 0;i<items.size();i++){
			   try{
				   int ret=SaveDikeBase(items.get(i));
				   log.info(ret==1?excelName+"更新:第"+i+"条记录成功":excelName+"更新:第"+i+"条记录失败");
			   }catch(Exception e){
				   log.error(e);
			   }
		   }
	}
	
	/**
	 * 
	 * 描述:此处是保存excel数据到数据库方法，为了保证数据不被错误覆盖，请使用同步方法或同步块！(堤防基本情况表),
	 * @createTime 2015年2月5日
	 * @updateTime 2015年2月5日
	 * @return
	 */
	@SuppressWarnings("finally")
	private synchronized int SaveDikeBase(DikeBaseClass item) {
		BaseDao dao = DaoFactory.getDao("HOS");
		int isinsert=0;

		try {
			dao.executeSQL("{call DikeBase_DataReport(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", new Object[]{
					item.getdkcd(),item.getdknm(),item.getbnscbgpl(),item.getbnscenpl(),item.getbnsctp(),
					item.getbnscln(),item.getbntpel(),item.getcrdkhg(),item.getdsfllv(),item.getgnwtlv(),
					item.getalwtlv(),item.getdsfl(),item.getalfl(),item.getprinar(),item.getptpp()
			});
			
			isinsert=1;
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e); 
		}finally{
			return isinsert;
		}
	}
	
	/**
	 * 
	 * 描述:处理，河流基本情况表
	 * @createTime 2015年2月5日
	 * @updateTime 2015年2月5日
	 * @return
	 */
	private void ProcRiverBase(String excelName) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		   List<RiverBaseClass> items= new ArrayList<RiverBaseClass>();
		   for(int i = 0 ; i<this.getListDatas().size();i++){
			   Map<?, ?> excelCol = (Map<?, ?>) this.getListDatas().get(i);   
			   RiverBaseClass item = new RiverBaseClass();	
			   
			   RiverBaseClass obj = (RiverBaseClass) convertMap(item.getClass(), excelCol);
			   items.add(obj);
		   }
		  
		   for(int i = 0;i<items.size();i++){
			   try{
				   int ret=SaveRiverBase(items.get(i));
				   log.info(ret==1?excelName+"更新:第"+i+"条记录成功":excelName+"更新:第"+i+"条记录失败");
			   }catch(Exception e){
				   log.error(e);
			   }
		   }
	}
	
	/**
	 * 
	 * 描述:此处是保存excel数据到数据库方法，为了保证数据不被错误覆盖，请使用同步方法或同步块！(河流基本情况表),
	 * @createTime 2015年2月5日
	 * @updateTime 2015年2月5日
	 * @return
	 */
	@SuppressWarnings("finally")
	private synchronized int SaveRiverBase(RiverBaseClass item) {
		BaseDao dao = DaoFactory.getDao("HOS");
		int isinsert=0;

		try {
			dao.executeSQL("{call RiverBase_DataReport(?,?,?,?,?,?,?,?)}", new Object[]{
					item.getrvcd(),item.getrvnm(),item.getrvpl(),item.getmnstln(),item.getttdrbsar(),
					item.getdrbspp(),item.getmxsfds(),item.getavtm()
			});
			
			isinsert=1;
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e); 
		}finally{
			return isinsert;
		}
	}
	
	/**
	 * 
	 * 描述:处理，历史山洪灾害情况表
	 * @createTime 2015年2月4日
	 * @updateTime 2015年2月4日
	 * @return
	 */
	private void ProcHistoryDisaster(String excelName) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		   List<HistoryDisasterClass> items= new ArrayList<HistoryDisasterClass>();
		   for(int i = 0 ; i<this.getListDatas().size();i++){
			   Map<?, ?> excelCol = (Map<?, ?>) this.getListDatas().get(i);   
			   HistoryDisasterClass item = new HistoryDisasterClass();	
			   
			   HistoryDisasterClass obj = (HistoryDisasterClass) convertMap(item.getClass(), excelCol);
			   items.add(obj);
		   }
		  
		   for(int i = 0;i<items.size();i++){
			   try{
				   int ret=SaveHistoryDisaster(items.get(i));
				   log.info(ret==1?excelName+"更新:第"+i+"条记录成功":excelName+"更新:第"+i+"条记录失败");
			   }catch(Exception e){
				   log.error(e);
			   }
		   }
	}
	
	/**
	 * 
	 * 描述:此处是保存excel数据到数据库方法，为了保证数据不被错误覆盖，请使用同步方法或同步块！(历史山洪灾害情况表),
	 * @createTime 2015年2月4日
	 * @updateTime 2015年2月4日
	 * @return
	 */
	@SuppressWarnings("finally")
	private synchronized int SaveHistoryDisaster(HistoryDisasterClass item) {
		BaseDao dao = DaoFactory.getDao("HOS");
		int isinsert=0;

		try {
			dao.executeSQL("{call HistoryDisaster_DataReport(?,?,?)}", new Object[]{
					item.getadcd(),item.gethzrdtm(),item.gethzrddesc()
			});
			
			isinsert=1;
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e); 
		}finally{
			return isinsert;
		}
	}
	
	/**
	 * 
	 * 描述:处理，山洪灾害预警指标
	 * @createTime 2015年2月4日
	 * @updateTime 2015年2月4日
	 * @return
	 */
	private void ProcDisasterWarnIndica(String excelName) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		   List<DisasterWarnIndicaClass> items= new ArrayList<DisasterWarnIndicaClass>();
		   for(int i = 0 ; i<this.getListDatas().size();i++){
			   Map<?, ?> excelCol = (Map<?, ?>) this.getListDatas().get(i);   
			   DisasterWarnIndicaClass item = new DisasterWarnIndicaClass();	
			   
			   DisasterWarnIndicaClass obj = (DisasterWarnIndicaClass) convertMap(item.getClass(), excelCol);
			   items.add(obj);
		   }
		  
		   for(int i = 0;i<items.size();i++){
			   try{
				   int ret=SaveDisasterWarnIndica(items.get(i));
				   log.info(ret==1?excelName+"更新:第"+i+"条记录成功":excelName+"更新:第"+i+"条记录失败");
			   }catch(Exception e){
				   log.error(e);
			   }
		   }
	}
	
	/**
	 * 
	 * 描述:此处是保存excel数据到数据库方法，为了保证数据不被错误覆盖，请使用同步方法或同步块！(山洪灾害预警指标),
	 * @createTime 2015年2月4日
	 * @updateTime 2015年2月4日
	 * @return
	 */
	@SuppressWarnings("finally")
	private synchronized int SaveDisasterWarnIndica(DisasterWarnIndicaClass item) {
		BaseDao dao = DaoFactory.getDao("HOS");
		int isinsert=0;

		try {
			dao.executeSQL("{call DisasterWarnIndica_DataReport(?,?,?,?,?,?,?,?,?,?)}", new Object[]{
					item.getstruleid(),item.getstcd(),item.getstindex(),item.getwarngradeid(),item.getwarntypeid(),
					item.getstthreshold(),item.getstindexunit(),item.getstdt(),item.getstrulevalidtime(),
					item.getstindexnm()
			});
			
			isinsert=1;
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e); 
		}finally{
			return isinsert;
		}
	}
	
	/**
	 * 
	 * 描述:处理，水位测站极值表
	 * @createTime 2015年2月4日
	 * @updateTime 2015年2月4日
	 * @return
	 */
	private void ProcWaterLevelExtremum(String excelName) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		   List<WaterLevelExtremumClass> items= new ArrayList<WaterLevelExtremumClass>();
		   for(int i = 0 ; i<this.getListDatas().size();i++){
			   Map<?, ?> excelCol = (Map<?, ?>) this.getListDatas().get(i);   
			   WaterLevelExtremumClass item = new WaterLevelExtremumClass();	
			   
			   WaterLevelExtremumClass obj = (WaterLevelExtremumClass) convertMap(item.getClass(), excelCol);
			   items.add(obj);
		   }
		  
		   for(int i = 0;i<items.size();i++){
			   try{
				   int ret=SaveWaterLevelExtremum(items.get(i));
				   log.info(ret==1?excelName+"更新:第"+i+"条记录成功":excelName+"更新:第"+i+"条记录失败");
			   }catch(Exception e){
				   log.error(e);
			   }
		   }
	}
	
	/**
	 * 
	 * 描述:此处是保存excel数据到数据库方法，为了保证数据不被错误覆盖，请使用同步方法或同步块！(水位测站极值表),
	 * @createTime 2015年2月4日
	 * @updateTime 2015年2月4日
	 * @return
	 */
	@SuppressWarnings("finally")
	private synchronized int SaveWaterLevelExtremum(WaterLevelExtremumClass item) {
		BaseDao dao = DaoFactory.getDao("HOS");
		int isinsert=0;

		try {
			dao.executeSQL("{call WaterLevelExtremum_DataReport(?,?,?,?,?)}", new Object[]{
					item.getstcd(),item.getihz(),item.getihztime(),item.getthz(),item.getthztime()
			});
			
			isinsert=1;
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e); 
		}finally{
			return isinsert;
		}
	}
	
	/**
	 * 
	 * 描述:处理，雨量测站极值表
	 * @createTime 2015年2月4日
	 * @updateTime 2015年2月4日
	 * @return
	 */
	private void ProcRainStaExtremum(String excelName) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		   List<RainStaExtremumClass> items= new ArrayList<RainStaExtremumClass>();
		   for(int i = 0 ; i<this.getListDatas().size();i++){
			   Map<?, ?> excelCol = (Map<?, ?>) this.getListDatas().get(i);   
			   RainStaExtremumClass item = new RainStaExtremumClass();	
			   
			   RainStaExtremumClass obj = (RainStaExtremumClass) convertMap(item.getClass(), excelCol);
			   items.add(obj);
		   }
		  
		   for(int i = 0;i<items.size();i++){
			   try{
				   int ret=SaveRainStaExtremum(items.get(i));
				   log.info(ret==1?excelName+"更新:第"+i+"条记录成功":excelName+"更新:第"+i+"条记录失败");
			   }catch(Exception e){
				   log.error(e);
			   }
		   }
	}
	
	/**
	 * 
	 * 描述:此处是保存excel数据到数据库方法，为了保证数据不被错误覆盖，请使用同步方法或同步块！(雨量测站极值表),
	 * @createTime 2015年2月4日
	 * @updateTime 2015年2月4日
	 * @return
	 */
	@SuppressWarnings("finally")
	private synchronized int SaveRainStaExtremum(RainStaExtremumClass item) {
		BaseDao dao = DaoFactory.getDao("HOS");
		int isinsert=0;

		try {
			dao.executeSQL("{call RainStaExtremum_DataReport(?,?,?,?,?,?,?,?,?,?,?)}", new Object[]{
					item.getstcd(),item.getmaxDrp1h(),item.getmaxDrp3h(),item.getmaxDrp6h(),
					item.getmaxDrp12h(),
					item.getmaxDrp24h(),item.gettm1h(),item.gettm3h(),item.gettm6h(),item.gettm12h(),
					item.gettm24h()
			});
			
			isinsert=1;
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e); 
		}finally{
			return isinsert;
		}
	}
	
	/**
	 * 
	 * 描述:处理，测站基本信息表
	 * @createTime 2015年2月4日
	 * @updateTime 2015年2月4日
	 * @return
	 */
	private void ProcStationBaseInfo(String excelName) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		   List<StationBaseInfoClass> items= new ArrayList<StationBaseInfoClass>();
		   for(int i = 0 ; i<this.getListDatas().size();i++){
			   Map<?, ?> excelCol = (Map<?, ?>) this.getListDatas().get(i);   
			   StationBaseInfoClass item = new StationBaseInfoClass();	
			   
			   StationBaseInfoClass obj = (StationBaseInfoClass) convertMap(item.getClass(), excelCol);
			   items.add(obj);
		   }
		  
		   for(int i = 0;i<items.size();i++){
			   try{
				   int ret=SaveStationBaseInfo(items.get(i));
				   log.info(ret==1?excelName+"更新:第"+i+"条记录成功":excelName+"更新:第"+i+"条记录失败");
			   }catch(Exception e){
				   log.error(e);
			   }
		   }
	}
	
	/**
	 * 
	 * 描述:此处是保存excel数据到数据库方法，为了保证数据不被错误覆盖，请使用同步方法或同步块！(测站基本信息表),
	 * @createTime 2015年2月4日
	 * @updateTime 2015年2月4日
	 * @return
	 */
	@SuppressWarnings("finally")
	private synchronized int SaveStationBaseInfo(StationBaseInfoClass item) {
		BaseDao dao = DaoFactory.getDao("HOS");
		int isinsert=0;

		try {
			dao.executeSQL("{call StationBaseInfo_DataReport(?,?,?,?,?,?,?,?,?,?,?,?)}", new Object[]{
					item.getstcd(),item.getstnm(),item.getrvnm(),item.gethnnm(),item.getbsnm(),
					item.getlgtd(),item.getlttd(),item.getstlc(),item.getsttp(),item.getphcd(),
					item.getcacd(),item.getadcd()
			});
			
			isinsert=1;
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e); 
		}finally{
			return isinsert;
		}
	}
	
	/**
	 * 
	 * 描述:处理，危险区基本情况表
	 * @createTime 2015年2月4日
	 * @updateTime 2015年2月4日
	 * @return
	 */
	private void ProcDangerZoneBase(String excelName) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		   List<DangerZoneBaseClass> items= new ArrayList<DangerZoneBaseClass>();
		   for(int i = 0 ; i<this.getListDatas().size();i++){
			   Map<?, ?> excelCol = (Map<?, ?>) this.getListDatas().get(i);   
			   DangerZoneBaseClass item = new DangerZoneBaseClass();	
			   
			   DangerZoneBaseClass obj = (DangerZoneBaseClass) convertMap(item.getClass(), excelCol);
			   items.add(obj);
		   }
		  
		   for(int i = 0;i<items.size();i++){
			   try{
				   int ret=SaveDangerZoneBase(items.get(i));
				   log.info(ret==1?excelName+"更新:第"+i+"条记录成功":excelName+"更新:第"+i+"条记录失败");
			   }catch(Exception e){
				   log.error(e);
			   }
		   }
	}
	
	/**
	 * 
	 * 描述:此处是保存excel数据到数据库方法，为了保证数据不被错误覆盖，请使用同步方法或同步块！(危险区基本情况表),
	 * @createTime 2015年2月4日
	 * @updateTime 2015年2月6日
	 * @return
	 */
	@SuppressWarnings("finally")
	private synchronized int SaveDangerZoneBase(DangerZoneBaseClass item) {
		BaseDao dao = DaoFactory.getDao("HOS");
		int isinsert=0;

		try {
			dao.executeSQL("{call DangerZoneBase_DataReport(?,?,?,?,?,?)}", new Object[]{
					item.getdacd(),item.getadcd(),item.getname(),item.getaddress(),
					item.getpopulation(),item.gethouse()
			});
			
			isinsert=1;
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e); 
		}finally{
			return isinsert;
		}
	}
	
	/**
	 * 
	 * 描述:处理，小流域和乡镇村关联表
	 * @createTime 2015年2月3日
	 * @updateTime 2015年2月3日
	 * @return
	 */
	private void ProcSmallWaterLink(String excelName) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		   List<SmallWaterLinkClass> items= new ArrayList<SmallWaterLinkClass>();
		   for(int i = 0 ; i<this.getListDatas().size();i++){
			   Map<?, ?> excelCol = (Map<?, ?>) this.getListDatas().get(i);   
			   SmallWaterLinkClass item = new SmallWaterLinkClass();	
			   
			   SmallWaterLinkClass obj = (SmallWaterLinkClass) convertMap(item.getClass(), excelCol);
			   items.add(obj);
		   }
		  
		   for(int i = 0;i<items.size();i++){
			   try{
				   int ret=SaveSmallWaterLink(items.get(i));
				   log.info(ret==1?excelName+"更新:第"+i+"条记录成功":excelName+"更新:第"+i+"条记录失败");
			   }catch(Exception e){
				   log.error(e);
			   }
		   }
	}
	
	/**
	 * 
	 * 描述:此处是保存excel数据到数据库方法，为了保证数据不被错误覆盖，请使用同步方法或同步块！(小流域和乡镇村关联表),
	 * @createTime 2015年2月3日
	 * @updateTime 2015年2月3日
	 * @return
	 */
	@SuppressWarnings("finally")
	private synchronized int SaveSmallWaterLink(SmallWaterLinkClass item) {
		BaseDao dao = DaoFactory.getDao("HOS");
		int isinsert=0;

		try {
			dao.executeSQL("{call SmallWaterLink_DataReport(?,?)}", new Object[]{
					item.getcacd(),item.getadcd()
			});
			
			isinsert=1;
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e); 
		}finally{
			return isinsert;
		}
	}
	
	/**
	 * 
	 * 描述:处理，小流域基本情况表
	 * @createTime 2015年2月3日
	 * @updateTime 2015年2月3日
	 * @return
	 */
	private void ProcSmallWaterBase(String excelName) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		   List<SmallWaterBaseClass> items= new ArrayList<SmallWaterBaseClass>();
		   for(int i = 0 ; i<this.getListDatas().size();i++){
			   Map<?, ?> excelCol = (Map<?, ?>) this.getListDatas().get(i);   
			   SmallWaterBaseClass item = new SmallWaterBaseClass();	
			   
			   SmallWaterBaseClass obj = (SmallWaterBaseClass) convertMap(item.getClass(), excelCol);		
			   items.add(obj);
		   }
		  
		   for(int i = 0;i<items.size();i++){
			   try{
				   int ret=SaveSmallWaterBase(items.get(i));
				   log.info(ret==1?excelName+"更新:第"+i+"条记录成功":excelName+"更新:第"+i+"条记录失败");
			   }catch(Exception e){
				   log.error(e);
			   }
		   }
	}
	/**
	 * 
	 * 描述:此处是保存excel数据到数据库方法，为了保证数据不被错误覆盖，请使用同步方法或同步块！(小流域基本情况表),
	 * @createTime 2015年2月3日
	 * @updateTime 2015年2月3日
	 * @return
	 */
	@SuppressWarnings("finally")
	private synchronized int SaveSmallWaterBase(SmallWaterBaseClass item) {
		BaseDao dao = DaoFactory.getDao("HOS");
		int isinsert=0;
		//log.info(item.getcacd());
		try {
			dao.executeSQL("{call SmallWaterBase_DataReport(?,?,?,?,?,?,?,?)}", new Object[]{
					item.getcacd(),item.getcanm(),item.getcaarea(),item.getriver(),item.getrvlen(),item.getgradient(),
					item.getrvhead(),item.getrvmouth()
			});
			
			isinsert=1;
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e); 
		}finally{
			return isinsert;
		}
	}
	
	/**
	 * 描述:处理,山洪灾害影响情况表
	 * @createTime 2015年1月28日
	 * @updateTime 2015年1月28日
	 * @return 
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 */
	private void ProcFloodDisasters(String excelName) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		List<FloodDisClass> items= new ArrayList<FloodDisClass>();
		   for(int i = 0 ; i<this.getListDatas().size();i++){
			   Map<?, ?> excelCol = (Map<?, ?>) this.getListDatas().get(i);   
			   FloodDisClass item = new FloodDisClass();			
			   FloodDisClass obj = (FloodDisClass) convertMap(item.getClass(), excelCol);
			   items.add(obj);			
		   }
		  
		   for(int i = 0;i<items.size();i++){
			   try{
				   int ret=SaveFloodDisasters(items.get(i));
				   log.info(ret==1?excelName+"更新:第"+i+"条记录成功":excelName+"更新:第"+i+"条记录失败");
			   }catch(Exception e){
				   log.error(e);
			   }
		   }
	}
	
	/**
	 * 描述:此处是保存excel数据到数据库方法，为了保证数据不被错误覆盖，请使用同步方法或同步块！(山洪灾害影响情况),
	 * @createTime 2015年1月27日
	 * @updateTime 2015年1月27日
	 * @return 
	 */
	@SuppressWarnings("finally")
	private synchronized int SaveFloodDisasters(FloodDisClass item) {
		BaseDao dao = DaoFactory.getDao("HOS");
		/*String querySQL="select * from ZT_AD_DisInflu_B where ADCD=?";
		
		String updateSQL="update ZT_AD_DisInflu_B set hulPopulation=?,hulHousehold=?,"
		   		+ "hulHouse=?,hulFarmland=?,holPopulation=?,holHousehold=?,holSoiHouse=?,CutoffDate=? where ADCD=?";
		
		String insertSQL="insert into ZT_AD_DisInflu_B(ADCD,hulPopulation,hulHousehold,"
		   		+ "hulHouse,hulFarmland,holPopulation,holHousehold,holSoiHouse,CutoffDate) values(?,?,?,?,?,?,?,?,?)";*/
		
		int isinsert=0;
		//boolean successOr=false;
		
		try {
			/*if(dao.executeQuery(querySQL, new Object[]{item.getadcd()}).size()>0){
				   //更新
				successOr=dao.executeSQL(updateSQL, new Object[]{item.getholPopulation(),item.gethulHousehold(),item.gethulHouse(),
						item.gethulFarmland(),item.getholPopulation(),item.getholHousehold(),
						item.getholSoiHouse(),item.getcutoffDate(),item.getadcd()});
				isinsert=successOr?1:0;
			   }else{
				   //插入
				successOr=dao.executeSQL(insertSQL, new Object[]{item.getadcd(),item.getholPopulation(),item.gethulHousehold(),item.gethulHouse(),
						item.gethulFarmland(),item.getholPopulation(),item.getholHousehold(),
						item.getholSoiHouse(),item.getcutoffDate()});
				isinsert=successOr?2:0;
			   }*/
			dao.executeSQL("{call FloodDisasters_DataReport(?,?,?,?,?,?,?,?,?)}", new Object[]{
					item.getadcd(),item.gethulPopulation(),item.gethulHousehold(),item.gethulHouse(),
					item.gethulFarmland(),item.getholPopulation(),item.getholHousehold(),item.getholSoiHouse(),item.getcutoffDate()
			});
			
			isinsert=1;
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e); 
		}finally{
			return isinsert;
		}
	}
	
	/**
	 * 描述:处理,行政区划基本情况表
	 * @createTime 2015年1月28日
	 * @updateTime 2015年1月28日
	 * @return 
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 */
	private void ProcAdminDivisions(String excelName) throws IllegalAccessException, InstantiationException, InvocationTargetException, IntrospectionException {
		List<AdminDiviClass> items= new ArrayList<AdminDiviClass>();
		   for(int i = 0 ; i<this.getListDatas().size();i++){
			   Map<?, ?> excelCol = (Map<?, ?>) this.getListDatas().get(i);   
			   AdminDiviClass item = new AdminDiviClass();			   
			   AdminDiviClass obj = (AdminDiviClass) convertMap(item.getClass(), excelCol);
			   items.add(obj);			
		   }
		   
		   for(int i = 0;i<items.size();i++){
			   try{
				   int ret=SaveAdminDivisions(items.get(i));
				   log.info(ret==1?excelName+"更新:第"+i+"条记录成功":excelName+"更新:第"+i+"条记录失败");
			   }catch(Exception e){
				   log.error(e);
			   }
		   }
	}
	
	/**
	 * 描述:此处是保存excel数据到数据库方法，为了保证数据不被错误覆盖，请使用同步方法或同步块！(行政区划基本情况表)
	 * @createTime 2015年1月27日
	 * @updateTime 2015年1月27日
	 * @return 
	 */
	@SuppressWarnings("finally")
	private synchronized int SaveAdminDivisions(AdminDiviClass item) {
		BaseDao dao = DaoFactory.getDao("HOS");
		/*String querySQL="select * from ZT_AD_Info_B where ADCD=?";
		String updateSQL="update ZT_AD_Info_B set LandArea=?,CultiArea=?,"
		   		+ "Population=?,Household=?,House=?,CutoffDate=? where ADCD=?";
		String insertSQL="insert into ZT_AD_Info_B(ADCD,LandArea,CultiArea,"
		   		+ "Population,Household,House,CutoffDate) values(?,?,?,?,?,?,?)";*/
		int isinsert=0;
		//boolean successOr=false;
		
		try {
			/*if(dao.executeQuery(querySQL, new Object[]{item.getadcd()}).size()>0){
				   //更新
				successOr=dao.executeSQL(updateSQL, new Object[]{item.getlandArea(),item.getcultiArea(),item.getpopulation(),
						   item.gethousehold(),item.gethouse(),item.getcutoffDate(),item.getadcd()});
				isinsert=successOr?1:0;
			   }else{
				   //插入
				successOr=dao.executeSQL(insertSQL, new Object[]{item.getadcd(),item.getlandArea(),item.getcultiArea(),item.getpopulation(),
						   item.gethousehold(),item.gethouse(),item.getcutoffDate()});
				isinsert=successOr?2:0;
			   }*/
			dao.executeSQL("{call AdminDivisions_DataReport(?,?,?,?,?,?,?,?)}", new Object[]{
					item.getadcd(),item.getadnm(),item.getlandArea(),item.getcultiArea(),item.getpopulation(),
					item.gethousehold(),item.gethouse(),item.getcutoffDate()
			});
			
			isinsert=1;
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e); 
		}finally{
			return isinsert;
		}
	}	
}
