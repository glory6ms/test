package action;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.Case;

import service.UserDAO;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import entity.CartEntity;
import entity.OrdEntity;
import entity.OrderEntity;
import entity.Remainroom;
import entity.Room;
import entity.RoomEntity;
import entity.TableEntity;
import entity.VegetableEntity;
import entity.VipEntity;
import javafx.scene.control.Alert;

public class UserAction extends SuperAction implements ModelDriven<VipEntity> {

	private static final long serialVersionUID = 1L;
	
	private VipEntity vip=new VipEntity();

	
	// 未注册时回到首页
	public String returnToIndex1() {
		return "returnToIndex1";
	}

	// 注册后回到首页
	public String returnToIndex2() {
		return "returnToIndex2";
	}

	// 注销用户并退出
	public String logout() {
		session.invalidate();
		return "logout_success";
	}

	// 跳到用户登录页面
	public String login() {
		return "goto_login";
	}

	// 跳转到用户注册页面
	public String goto_register() {
		return "goto_register";
	}

	// 验证用户登陆信息
	public String loginSuccess() {
		UserDAO userDAO = new UserDAO();
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		if (userDAO.userLogin(name, password)) {
			// 在session中保存登陆成功的用户名
			session.setAttribute("user_name", name);
			return "user_login_success";
		} else {
			ActionContext.getContext().put("loginmessage", "登录失败");
			return "user_login_faliure";
		}
	}

	// 保存进用户注册信息
	public String saveRegister() {
		UserDAO userDAO = new UserDAO();
		String password=userDAO.getMD5String(request.getParameter("hyPassword"));
		VipEntity vip=new VipEntity(
				request.getParameter("hyUser"),
				password,
				request.getParameter("hyName"),
				request.getParameter("hyIdcard"),
				request.getParameter("hyGender"),
				request.getParameter("hyTel"));
//		User user = new User(request.getParameter("name"),
//				request.getParameter("idnumber"),
//				request.getParameter("password"), request.getParameter("phone"));
		
		userDAO.saveNewUser(vip);
		session.setAttribute("user_name", vip.getHyUser());
		return "saveRegister_success";
	}

	// 用户未注册时查看酒店房间
	public String viewRoom() {
		UserDAO userDAO = new UserDAO();
		List<RoomEntity> list = userDAO.queryRoom_View();
		// 放进request中
		if (list != null) {
			request.setAttribute("AllHome_list", list);
		}
		
		return "viewRoom_success";
	}
	

	// 用户未注册时查询指定类型房间信息
	public String queryOneTypeHome() {
		String roomtype = request.getParameter("roomtype");
		int rt=1;
		if (roomtype.equals("*")) {
		//	roomtype = "1','2','3','4','5";
			rt=0;
		}
		else{rt=selectType(roomtype);}
		UserDAO userDAO = new UserDAO();
		List<RoomEntity> rList;
		if(rt==0) {
			rList=userDAO.queryOneTypeHome();
		}else {
			rList = userDAO.queryOneTypeHome(rt);
		}
		if (rList != null) {
			request.setAttribute("AllHome_list", rList);
		}
		return "Firstuser_queryOneTypeHome_success";
	}



	// 修改用户信息
	public String updateUser() {
		UserDAO userDAO = new UserDAO();
		VipEntity vip = userDAO.queryUserByName(session.getAttribute("user_name")
				.toString());
		// 放进session中
		if (vip != null) {
			session.setAttribute("user_self", vip);
		}
		return "goto_updateUser";
	}

	// 保存用户提交的个人信息
	public String saveUpdateOne() {
		UserDAO userDAO = new UserDAO();
		VipEntity user = userDAO.queryUserByName(session.getAttribute("user_name")
				.toString());
		String password=userDAO.getMD5String(request.getParameter("hyPassword"));
		user.setHyPassword(password);
		userDAO.updateUser(user);
		session.setAttribute("user_self", user);
		return "saveUpdateOne_success";
	}

	// 用户登录后查询预订信息
	public String queryOrder() {
		UserDAO userDAO = new UserDAO();
		VipEntity vip = userDAO.queryUserByName(session.getAttribute("user_name")
				.toString());
		List<OrderEntity> list = userDAO.queryUserOrder(vip.getHyName());
		if (list != null) {
			session.setAttribute("User_OrderList", list);
		}
		return "user_queryOrder_success";
	}

	// 查询历史住房记录
	public String historyRoom() {
		UserDAO userDAO = new UserDAO();
		VipEntity vip=userDAO.queryUserByName(session.getAttribute(
				"user_name").toString());
		List<OrderEntity> cList = userDAO.queryHistory(vip.getHyId());
		if (cList != null) {
			session.setAttribute("User_HistoryList", cList);
		}
		return "historyRoom_success";
	}



	public String orderRoom() {
		UserDAO userDAO = new UserDAO();
		List<RoomEntity> list = userDAO.queryRoom_View();
		// 放进request中
		if (list != null) {
			request.setAttribute("AllHome_list", list);
		}
		return "goto_orderRoom";
	}

	// 用户登陆后查询指定类型房间信息
	public String queryOneTypeHomeAfter() throws ParseException {
	
		UserDAO userDAO = new UserDAO();
		List<OrderEntity> rList;
		System.out.println("010101"+request.getParameter("timein")+"252525");
		if(request.getParameter("timein")=="") {
			ActionContext.getContext().put("tablemessage", "预定失败,请选择住宿时间");
			return "saveRegister_success";
		}else {
		Date timein = new SimpleDateFormat("yyyy-MM-dd").parse(request
				.getParameter("timein"));
		Date timeout = new SimpleDateFormat("yyyy-MM-dd").parse(request
				.getParameter("timeout"));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String x=sdf.format(timein);
		String y=sdf.format(timeout);
		session.setAttribute("timein", x);
		session.setAttribute("timeout", y);
	//	System.out.println(x);
//		if(rt==0) {
//			rList=userDAO.queryOneTypeHome();
//		}else {
//			rList = userDAO.queryOneTypeHome(rt);
//		}
		rList=userDAO.queryOneHome(x, y);
		List<RoomEntity> roomlist=new ArrayList<RoomEntity>();
		for(int i=0;i<rList.size();++i) {
			RoomEntity rm=userDAO.queryRoomByNumber(rList.get(i).getRoomNum());
			roomlist.add(rm);
		}
		List<RoomEntity> krlist=userDAO.queryOneTypeHome();
		List<RoomEntity> list=new ArrayList<RoomEntity>();
		for(RoomEntity r:krlist) {
			boolean flag = true;
			for(RoomEntity rl:roomlist) {
				if(rl.getRoomId()==r.getRoomId()) {
					flag = false;
					break;
				}
			}
			if(flag){
				list.add(r);
			}
		}
		if (rList != null) {
			request.setAttribute("AllHome_list", list);
		}
	
		return "queryOneTypeHomeAfter_success";
		}
	}

	// 预定房间
	public String selectOrderRoom() throws ParseException {
		
		String rt = request.getParameter("roomNum");
//		Date timein = new SimpleDateFormat("yyyy-MM-dd").parse(request
//				.getParameter("timein"));
//		Date timeout = new SimpleDateFormat("yyyy-MM-dd").parse(request
//				.getParameter("timeout"));
//		
		UserDAO userDAO = new UserDAO();
//		roomtype=selectType(rt);
		RoomEntity re=userDAO.queryRoomByNumber(rt);
		request.setAttribute("roomnum", re.getRoomNum());
		request.setAttribute("bednum", re.getBedNum());
//		request.setAttribute("timein", timein);
//		request.setAttribute("timeout", timeout);
		//-------------------------------------------------------------------------------
	//	session.setAttribute("roomtype", rt);
		return "selectOrderRoom_success";
		
	}
	
	// 预定桌位
	public String selectOrderTable() {
		String sn = request.getParameter("tableId");
		//System.out.println(sn);
		UserDAO userDAO = new UserDAO();
		List<CartEntity> list = userDAO.queryOneTypeCart();
		System.out.println(list.toString() + "===================="
				+ list.size());
		String type=(String)session.getAttribute("ttype");
		System.out.println("0v0v0v0v"+type);
		if(type==null) {
			ActionContext.getContext().put("tablemessage", "预定失败,请选择用餐时间");
			return "saveRegister_success";
		}else {
		userDAO.ordtable(type, Integer.parseInt(sn));
		if (list != null) {
			request.setAttribute("AllCart_listt", list);
		}
		//-------------------------------------------------------------------------------
		session.setAttribute("tableId", sn);
		return "selectOrderTable_success";
		}
	}
	
	//判断用户选择的房型
	public int selectType(String rt) {
		int roomtype=1;
		switch(rt){
			case "单人房":roomtype=1;break;
			case "双人房":roomtype=2;break;
			case "大床房":roomtype=3;break;
			case "电脑房":roomtype=4;break;
			case "经济房":roomtype=5;break;
			default:roomtype=2;break;
		}
		return roomtype;
	}

	// 提交预订信息
	public String saveOrderRoom() throws ParseException {
		UserDAO userDAO = new UserDAO();
		String roomNum=request.getParameter("roomNum");
		System.out.println(roomNum);
		VipEntity vip=userDAO.queryUserByName(session.getAttribute("user_name")
				.toString());
		Date timein = new SimpleDateFormat("yyyy-MM-dd").parse(request
				.getParameter("timein"));
		Date timeout = new SimpleDateFormat("yyyy-MM-dd").parse(request
				.getParameter("timeout"));
		long day=(timeout.getTime()-timein.getTime())/(24*60*60*1000);    
		System.out.println("相隔的天数="+day);          // 相隔  天 
		RoomEntity roomentity=userDAO.queryRoomByNumber(roomNum);
		int price=roomentity.getRoomprice().intValue();
		price=(int) (day*price);
		OrderEntity order=new OrderEntity( vip.getHyId(),roomNum,vip.getHyName(), vip.getHyTel(), timein, timeout, "0",price);
		userDAO.saveNewOrder(order);
		return "saveOrderRoom_success";
	}

	
	
	// 用户查看桌位信息
	public String viewTable() {
		UserDAO userDAO = new UserDAO();
		List<TableEntity> list = userDAO.queryTable_View();
		// 放进request中
		if (list != null) {
			request.setAttribute("AllTable_list", list);
		}
		return "viewTable_success";
	}
	
	// 用户查看桌位信息
	public String queryOneTableType() {
		UserDAO userDAO = new UserDAO();
		String tabletype=request.getParameter("tabletype");
		session.setAttribute("ttype", tabletype);
		List<TableEntity> list = userDAO.queryTable(tabletype);
		// 放进request中
		if (list != null) {
			request.setAttribute("AllTable_list", list);
		}
		
		return "viewTable_success";
	}
//未注册时
	public String viewCart() {
		
		UserDAO userDAO = new UserDAO();
		List<CartEntity> list = userDAO.queryOneTypeCart();
		
		
		// 放进request中
		
		if (list != null) {
			request.setAttribute("AllCart_listt", list);
		}
		return "viewCart_success";
	}
	
	// 用户注册时查询指定类型餐饮信息
	public String queryOneTypeCart() {
		String carttype = request.getParameter("carttype");
		int rt=1;
		if (carttype.equals("*")) {
		//	roomtype = "1','2','3','4','5";
			rt=0;
		}
	
		UserDAO userDAO = new UserDAO();
		List<CartEntity> rList;
		if(rt==0) {
			rList=userDAO.queryOneTypeCart();
		}else {
			rList = userDAO.queryOneTypeCart(carttype);
		}
		if (rList != null) {
			request.setAttribute("AllCart_listt", rList);
		}
		return "queryOneTypeCart_success";
	}
	// 用户注册时查询指定类型餐饮信息
		public String queryCart() {
			String carttype = request.getParameter("carttype");
			int rt=1;
			if (carttype.equals("*")) {
			//	roomtype = "1','2','3','4','5";
				rt=0;
			}
		
			UserDAO userDAO = new UserDAO();
			List<CartEntity> rList;
			if(rt==0) {
				rList=userDAO.queryOneTypeCart();
			}else {
				rList = userDAO.queryOneTypeCart(carttype);
			}
			if (rList != null) {
				request.setAttribute("AllCart_listt", rList);
			}
			return "queryCart_success";
		}
	//选购商品
	public String selectOrderCart() {
		String cartid=request.getParameter("cartid");
		int id=Integer.parseInt(cartid);
		UserDAO userDAO = new UserDAO();
		CartEntity cart=userDAO.queryCartById(id);
		if(cart.getCartNum()<1) {
			ActionContext.getContext().put("cartmessage", "购买失败，库存不足");
			return "saveOrderCart_success";
		}else {
			int cartNum=cart.getCartNum();
		String Name=(String)session.getAttribute("user_name");
		int tableid=Integer.parseInt((String)session.getAttribute("tableId"));
		VipEntity vip=userDAO.queryUserByName(Name);
		OrdEntity ord = new OrdEntity(vip.getHyId(), vip.getHyName(), vip.getHyTel(), "0", tableid, cart.getCartId(), cart.getCartPrice(),cart.getCartName());
		userDAO.saveNewOrd(ord);
		cart.setCartNum(cartNum-1);
		userDAO.saveCart(cart);
		//System.out.println(sn);
		List<CartEntity> list = userDAO.queryOneTypeCart();
		System.out.println(list.toString() + "===================="
				+ list.size());
		if (list != null) {
			request.setAttribute("AllCart_listt", list);
		}
		ActionContext.getContext().put("cartmessage", "商品已加入购物车");
		return "saveOrderCart_success";
		}
	}
	
	//选购商品
		public String selectOrderVeg() {
			String cartid=request.getParameter("vegid");
			int id=Integer.parseInt(cartid);
			UserDAO userDAO = new UserDAO();
			String Name=(String)session.getAttribute("user_name");
			
			VipEntity vip=userDAO.queryUserByName(Name);
			VegetableEntity veg=userDAO.queryVegById(id);
			if(veg.getVegNum()<1) {
				ActionContext.getContext().put("cartmessage", "购买失败，库存不足");
				return "viewVeg_success";
			}else {
			OrdEntity ord=new OrdEntity(vip.getHyId(), vip.getHyName(), vip.getHyTel(), "10", veg.getVegId(), veg.getVegPrice(),veg.getVegName());
			userDAO.saveNewOrd(ord);
			
			List<VegetableEntity> list = userDAO.queryVeg();
			// 放进request中
			if (list != null) {
				request.setAttribute("AllVeg_list", list);
			}
			ActionContext.getContext().put("cartmessage", "商品已加入购物车");
			return "viewVeg_success";
			}
		}
		//计算餐饮订单
	public String CountCart() {
		int tableid=Integer.parseInt((String)session.getAttribute("tableId"));
		String Name=(String)session.getAttribute("user_name");
		if((String)session.getAttribute("tableId")==null) {
			return "returnToIndex2";
		}else {
		BigDecimal count =new BigDecimal(0);
		List<OrdEntity> rList;
		UserDAO userDAO = new UserDAO();
		VipEntity vip=userDAO.queryUserByName(Name);
		rList=userDAO.CountOrd(tableid,"0",vip.getHyId());
		
		
		for(int i=0;i<rList.size();++i) {
			count=count.add(rList.get(i).getOrdPrice());
		}
		System.out.println("价格是 ："+count);
		request.setAttribute("count", count);
		request.setAttribute("AllOrdCart_list", rList);
		return "countOrderCart_success";
		}
	}
	//计算农产品订单
public String CountVeg() {
	int tableid=0;
	BigDecimal count =new BigDecimal(0);
	List<OrdEntity> rList;
	UserDAO userDAO = new UserDAO();
	String Name=(String)session.getAttribute("user_name");
	VipEntity vip=userDAO.queryUserByName(Name);
	rList=userDAO.CountOrd(tableid,"10",vip.getHyId());
	for(int i=0;i<rList.size();++i) {
		count=count.add(rList.get(i).getOrdPrice());
	}
	System.out.println("价格是 ："+count);
	request.setAttribute("count", count);
	request.setAttribute("AllOrdCart_list", rList);
	return "countOrderVeg_success";
}
	//提交餐饮订单
	public String saveOrderCart() {
		int tableid=Integer.parseInt((String)session.getAttribute("tableId"));
		UserDAO userDAO = new UserDAO();
		List<OrdEntity> rList;
		String Name=(String)session.getAttribute("user_name");
		VipEntity vip=userDAO.queryUserByName(Name);
		rList=userDAO.CountOrd(tableid,"0",vip.getHyId());
		if(rList!=null) {
			for(int i=0;i<rList.size();++i) {
				rList.get(i).setDes("1");
				userDAO.saveCartOrd(rList.get(i));
			}
		}
		TableEntity td=userDAO.queryTableById(tableid);
		td.setDes("0");
		userDAO.updateTable(td);
		return "user_login_success";
	}
	
	//提交农产品订单
		public String saveOrderVeg() {
			
			UserDAO userDAO = new UserDAO();
			List<OrdEntity> rList;
			String Name=(String)session.getAttribute("user_name");
			VipEntity vip=userDAO.queryUserByName(Name);
			rList=userDAO.CountOrd(0,"10",vip.getHyId());
			if(rList!=null) {
				for(int i=0;i<rList.size();++i) {
					rList.get(i).setDes("11");
					userDAO.saveCartOrd(rList.get(i));
				}
			}
			return "savetOrderVeg_success";
		}
	//删除所选餐饮
	public String deleteOrderCart() {
		BigDecimal count =new BigDecimal(0);
		int tableid=Integer.parseInt((String)session.getAttribute("tableId"));
		int ordid=Integer.parseInt((String)request.getParameter("ordid"));
		UserDAO userDAO = new UserDAO();
		OrdEntity ord=userDAO.queryOrdById(ordid);
		CartEntity ca=userDAO.queryCartById(ord.getCartId());
		ord.setDes("-1");
		userDAO.saveCartOrd(ord);
		ca.setCartNum(ca.getCartNum()+1);
		userDAO.saveCart(ca);
		List<OrdEntity> rList;
		String Name=(String)session.getAttribute("user_name");
		VipEntity vip=userDAO.queryUserByName(Name);
		rList=userDAO.CountOrd(tableid,"0",vip.getHyId());
		for(int i=0;i<rList.size();++i) {
		
			count=count.add(rList.get(i).getOrdPrice());
		}
		request.setAttribute("count", count);
		request.setAttribute("AllOrdCart_list", rList);
		return "countOrderCart_success";
	}
	//删除所选农产品
	public String deleteOrderVeg() {
		BigDecimal count =new BigDecimal(0);
		int ordid=Integer.parseInt((String)request.getParameter("ordid"));
		UserDAO userDAO = new UserDAO();
		OrdEntity ord=userDAO.queryOrdById(ordid);
		
		
		ord.setDes("-1");
		userDAO.saveCartOrd(ord);
		VegetableEntity veg=userDAO.queryVegById(ord.getCartId());
		veg.setVegNum(veg.getVegNum()+1);
		userDAO.saveVeg(veg);
		List<OrdEntity> rList;
		String Name=(String)session.getAttribute("user_name");
		VipEntity vip=userDAO.queryUserByName(Name);
		rList=userDAO.CountOrd(0,"10",vip.getHyId());
		for(int i=0;i<rList.size();++i) {
			count=count.add(rList.get(i).getOrdPrice());
		}
		request.setAttribute("count", count);
		request.setAttribute("AllOrdCart_list", rList);
		return "countOrderVeg_success";
	}
	//所有特色农产品
	public String viewvegetables() {
		UserDAO userDAO = new UserDAO();
		List<VegetableEntity> list = userDAO.queryVeg();
		// 放进request中
		if (list != null) {
			request.setAttribute("AllVeg_list", list);
		}
		
		return "viewVeg_success";
	}
	
	// 查询历史餐饮记录
	public String historyCart() {
		UserDAO userDAO = new UserDAO();
		VipEntity vip=userDAO.queryUserByName(session.getAttribute(
				"user_name").toString());
		List<OrdEntity> cList = userDAO.queryHistoryCart(vip.getHyId(),1);
		if (cList != null) {
			int num=cList.size();
			BigDecimal money=new BigDecimal(0);
			for(int i=0;i<num;i++) {
				money=money.add(cList.get(i).getOrdPrice());
			}
			
			request.setAttribute("num", num);
			request.setAttribute("money", money);
			
			request.setAttribute("User_HistoryCartList", cList);
			System.out.println("gggggggggggggg");
		}
		return "historyCart_success";
	}

	// 查询农产品购买记录
	public String historyVeg() {
		UserDAO userDAO = new UserDAO();
		VipEntity vip=userDAO.queryUserByName(session.getAttribute(
				"user_name").toString());
		List<OrdEntity> cList = userDAO.queryHistoryCart(vip.getHyId(),2);
	
		if (cList != null) {
			int num=cList.size();
			BigDecimal money=new BigDecimal(0);
			for(int i=0;i<num;i++) {
				money=money.add(cList.get(i).getOrdPrice());
			}
			
			request.setAttribute("num", num);
			request.setAttribute("money", money);
			request.setAttribute("User_HistoryVegList", cList);
			
			System.out.println("gggggggggggggg");
		}
		return "historyVeg_success";
	}
	
	// 客房退订
	public String deleteOrder() {
		UserDAO userDAO = new UserDAO();
		int id=Integer.parseInt((String)request.getParameter("orderhid"));
		OrderEntity od=userDAO.queryOrderById(id);
		od.setDes("-1");
		userDAO.saveNewOrder(od);
		VipEntity vip = userDAO.queryUserByName(session.getAttribute("user_name")
				.toString());
		List<OrderEntity> list = userDAO.queryUserOrder(vip.getHyName());
		if (list != null) {
			session.setAttribute("User_OrderList", list);
		}
		return "user_queryOrder_success";
	}
	@Override
	public VipEntity getModel() {
		// TODO Auto-generated method stub
		return null;
	}

}
