package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.json.*;


import service.conn1;
import entity.Counts;
import entity.Ships;
import entity.points;


public class UserDAO {
	@SuppressWarnings("null")
	public static List<Ships> SelectAll(){
		List<Ships> list = new ArrayList<Ships>();
		String sql="select * from ships";
		ResultSet rs;
		try {
			rs=conn1.executeQuery(sql);
			while(rs.next()){
				Ships sp = new Ships();
				sp.setMMSI(rs.getInt("MMSI"));
				sp.setWidth(rs.getFloat("width"));
				sp.setLength(rs.getFloat("length"));
				sp.setTypeid(rs.getInt("typeid"));
				sp.setType(rs.getString("shiptype"));
				sp.setName(rs.getString("shipname"));
				sp.setLng(rs.getFloat("lng"));
				sp.setLat(rs.getFloat("lat"));
				
				list.add(sp);
//				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
		}
		conn1.close();
		return list;
	}

	@SuppressWarnings("null")
	public static List<Counts> CountShips(){
		List<Counts> list = new ArrayList<Counts>();
		for(int i=1;i<30;i++) {
		String sql="select count(*) as count from ships where typeid="+i+"";
		System.out.println(sql);
		ResultSet rs;
		try {
			rs=conn1.executeQuery(sql);
			if(rs.next()){
				System.out.println("counting..");
				int num=rs.getInt(1);
				System.out.println(num);
				Counts s=new Counts();
				s.setNum(num);
				s.setTypeid(i);
				s.setTypename("a");
				list.add(s);
			}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
				
}
