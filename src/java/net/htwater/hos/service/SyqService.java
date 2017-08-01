/**
 * 水雨情服务接口
 * @author RYX
 * @Date 2014-02-17
 * @since v 1.0
 */ 
package net.htwater.hos.service;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;



public interface SyqService {
	static final String HT_NBSQDB = "HT_NBSQDB"; 
	/**
	 * 风向风速
	 * POWER风力，VALUE风速，DIR风向
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> Querywind(String TM);
	/**
	 * 流量过程
	 * z水位/潮位,q流量
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> Queryll_line(String TM1,String TM2,String stcd);
	/**
	 * 流量流速
	 * z水位/潮位,q流量,xsavv流速,xsa断面面积
	 * @Date 2014-02-17
	 * @since v 1.0
	 */
	public List<Map<String, Object>> Queryll(String TM);
	/**
	 * 水库河道水位/潮位过程
	 * 参数说明：sttp站点类型，RS水库,RV河道,TT潮位
	 * z水位/潮位
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> Queryswcw_line(String TM1,String TM2,String stcd,String sttp);
	/**
	 * 水库实时水位
	 * Z水位，ZKR库容，Z8八点水位，Z8SK8点库容，CTZ控制水位，KZKR控制水位，RATIO实时库容占控制库容比例，TYPE ‘B大型M中型S1小一S2小二，ORDNO排序’
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> Queryreswater(String TM);
	/**
	 * 三江干流水位
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return  Z水位，WRZ警戒水位，GRZ保证水位，TREND涨落趋势
	 */
	public List<Map<String, Object>> QueryMRvWater(String TM);
	/**
	 * 平原水位
	 * Z水位，WRZ警戒水位，GRZ保证水位，TREND涨落趋势，ISRCONTROL是否控制站
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> QueryPRvWater(String TM);
	/**
	 * 潮位
	 * tdz潮位，guard警戒潮位，ensure保证潮位，poltp塘顶高，ordno排序号
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> Querytidewater(String TM);
	/**
	 * 雨量站时段雨量过程 
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return VALUE雨量
	 */
	public List<Map<String, Object>> Queryrian_hour_line(String TM1,String TM2,String stcd);
	/**
	 * 雨量站时段雨量
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return VALUE雨量，CITY城市
	 */
	public List<Map<String, Object>> Queryrian_hour(String TM1,String TM2);
	/**
	 * 雨量站日雨量过程
	 * 如果要求02-02至02-05的雨量过程，TM1取02-02 08:00，TM2取02-05 08:00
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return VALUE雨量
	 */
	public List<Map<String, Object>> Queryrian_day_line(String TM1,String TM2,String stcd);
	/**
	 * 雨量站日雨量
	 * 参数TM2的时间为当前时间的晚八点时间，例如现在时间是2013-12-09 16:25,求一日雨量，TM2='2013-12-10 08:00',TM1='2013-12-09 08:00'
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> Queryrian_day(String TM1,String TM2);
	/**
	 * 区县面时段雨量过程
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return VALUE雨量
	 */
	public List<Map<String, Object>> Queryarearian_hour_line(String TM1,String TM2,String region);
	/**
	 * 区县面时段雨量
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return VALUE雨量，COUNT_POINT站点数
	 */
	public List<Map<String, Object>> Queryarearian_hour(String TM1,String TM2);
	/**
	 * 区县面日雨量过程线
	 * 如果要求02-02至02-05的雨量过程，TM1取02-02 08:00，TM2取02-05 08:00
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return VALUE雨量
	 */
	public List<Map<String, Object>> Queryarearian_day_line(String TM1,String TM2,String region);
	/**
	 * 区县面日雨量
	 * 参数TM2的时间为当前时间的晚八点时间，例如现在时间是2013-12-09 16:25,求一日雨量，TM2='2013-12-10 08:00',TM1='2013-12-09 08:00'
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return
	 */
	public List<Map<String, Object>> Queryarearian_day(String TM1,String TM2);
	/**
	 * 大型水库面雨量过程
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return  VALUE雨量
	 */
	public List<Map<String, Object>> Queryresrian_line(String TM1,String TM2,String rsnm);
	/**
	 * 大型水库面雨量
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return RSNM水库 drp雨量
	 */
	public List<Map<String, Object>> Queryresrian(String TM1,String TM2);
	/**
	 * 水库承洪
	 * TM1是当前时间，TM2是对比时间
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return sw1,kr1当前水位、库容,  sw2,kr2,tm2cha对比水位、库容、库容差，xxsw,xxkr,xxyl汛限水位、库容、承载雨量，
	 * zcsw,zckr,zcyl正常水位、库容、承载雨量，jhsw,jhkr,jhyl校核水位、库容、承载雨量，sjsw,sjkr,sjyl设计水位、库容、承载雨量
	 * fhgsw,fhgkr,fhgcha,fhgyl防洪高水位、库容、库容差、承载雨量
	 */
	public List<Map<String, Object>> QueryResSupport(String TM1,String TM2);
	/**
	 * 库容曲线
	 * @Date 2014-02-17
	 * @since v 1.0
	 * @return Xvalue水位，Yvalue库容
	 */
	public List<Map<String, Object>> QueryResCapacity(String stnm);
	
	/**
	 * 平原承洪
	 * @Date 2014-02-20
	 * @since v 1.0
	 * @return 
	 */
	public List<Map<String, Object>> QueryPlainCapacity(String TM);
}
