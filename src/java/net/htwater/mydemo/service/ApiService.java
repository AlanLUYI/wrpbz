package net.htwater.mydemo.service;

import java.util.List;
import java.util.Map;

public interface ApiService {
	/**
	 * 等值面判断之前是否生成过速
	 * 
	 * @return int
	 */
    public String getPathIsExit(String path);
    
    /**
	 * 读取雨量数据 并转成TXT
	 * 
	 * @return int
	 */
    public Map<String,Object> getRainDatetoTxt(List<Map<String,Object>> lst,String pathHeader,String path,String tm_begin,String tm_end,String city,String clipLayer,String step,String Coordinate,String Stationtype);
  
}
