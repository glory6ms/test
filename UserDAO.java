package service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import util.HibernateSessionFactory;
import entity.CartEntity;
import entity.OrdEntity;
import entity.OrderEntity;

import entity.VipEntity;
import entity.RoomEntity;
import entity.TableEntity;
import entity.VegetableEntity;

public class UserDAO {
	// 插入新用户
	public void saveNewUser(VipEntity vip) {
		Transaction transaction = null;
		try {
			Session session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
			session.save(vip);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			if (transaction != null) {
				transaction = null;
			}
		}
	}

	// 查询酒店所有空房信息（查视图）
	public List<RoomEntity> queryRoom_View() {
		Transaction transaction = null;
		List<RoomEntity> rlist = null;
		String hqlString = "";
		try {
			Session session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
			hqlString = "from RoomEntity";
			Query query = session.createQuery(hqlString);
			rlist = query.list();
			transaction.commit();
			return rlist;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			return rlist;
		} finally {
			if (transaction != null) {
				transaction = null;
			}
		}
	}
	

	// 查询农庄所有空桌信息（查视图）
	public List<TableEntity> queryTable_View() {
		Transaction transaction = null;
		List<TableEntity> rlist = null;
		String hqlString = "";
		try {
			Session session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
			hqlString = "from TableEntity";
			Query query = session.createQuery(hqlString);
			rlist = query.list();
			transaction.commit();
			return rlist;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			return rlist;
		} finally {
			if (transaction != null) {
				transaction = null;
			}
		}
	}
	// 查询农庄所有空桌信息（查视图）
		public List<TableEntity> queryTable(String type) {
			Transaction transaction = null;
			List<TableEntity> rlist = null;
			String hqlString = "";
			try {
				Session session = HibernateSessionFactory.getSession();
				transaction = session.beginTransaction();
				hqlString = "from TableEntity where des!='"+type+"'";
				Query query = session.createQuery(hqlString);
				rlist = query.list();
				transaction.commit();
				return rlist;
			} catch (Exception e) {
				e.printStackTrace();
				transaction.rollback();
				return rlist;
			} finally {
				if (transaction != null) {
					transaction = null;
				}
			}
		}

	// 查询指定类型房间信息
	public List<RoomEntity> queryOneTypeHome(int rt) {
		Transaction transaction = null;
		List<RoomEntity> rlist = null;
		String hqlString = "";
		try {
			Session session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
			//hqlString = "from RoomEntity where roomType in(" + rt + ")";
			hqlString = "from RoomEntity where roomType="+rt+"";
			Query query = session.createQuery(hqlString);
			rlist = query.list();

			transaction.commit();
			return rlist;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			return rlist;
		} finally {
			if (transaction != null) {
				transaction = null;
			}
		}
	}

	// 查询所有类型房间信息
		public List<RoomEntity> queryOneTypeHome() {
			Transaction transaction = null;
			List<RoomEntity> rlist = null;
			String hqlString = "";
			try {
				Session session = HibernateSessionFactory.getSession();
				transaction = session.beginTransaction();
				hqlString = "from RoomEntity";
				Query query = session.createQuery(hqlString);
				rlist = query.list();
				transaction.commit();
				return rlist;
			} catch (Exception e) {
				e.printStackTrace();
				transaction.rollback();
				return rlist;
			} finally {
				if (transaction != null) {
					transaction = null;
				}
			}
		}
		
		// 查询所有类型房间信息
				public List<OrderEntity> queryOneHome(String start,String end) {
					Transaction transaction = null;
					List<OrderEntity> rlist = null;
					String hqlString = "";
					try {
						Session session = HibernateSessionFactory.getSession();
						transaction = session.beginTransaction();
						hqlString = "from OrderEntity where (indate>'"+start+"' and outdate<'"+end+"') or (indate<'"+start+"' and outdate>'"+end+"') or (outdate>'"+start+"' and outdate<'"+end+"')";
						Query query = session.createQuery(hqlString);
						rlist = query.list();
						transaction.commit();
						return rlist;
					} catch (Exception e) {
						e.printStackTrace();
						transaction.rollback();
						return rlist;
					} finally {
						if (transaction != null) {
							transaction = null;
						}
					}
				}


	// 查询所有类型餐饮信息
			public List<CartEntity> queryOneTypeCart() {
				Transaction transaction = null;
				List<CartEntity> rlist = null;
				String hqlString = "";
				try {
					Session session = HibernateSessionFactory.getSession();
					transaction = session.beginTransaction();
					hqlString = "from CartEntity";
					Query query = session.createQuery(hqlString);
					rlist = query.list();
					transaction.commit();
					return rlist;
				} catch (Exception e) {
					e.printStackTrace();
					transaction.rollback();
					return rlist;
				} finally {
					if (transaction != null) {
						transaction = null;
					}
				}
			}
			
			// 选定桌位
						public void ordtable(String type,int id) {
							Transaction transaction = null;
							
							String hqlString = "";
							try {
								Session session = HibernateSessionFactory.getSession();
								transaction = session.beginTransaction();
								hqlString = "update TableEntity set des='"+type+"' where tableId="+id+"";
								Query query = session.createQuery(hqlString);
								query.executeUpdate();
								transaction.commit();
							
							} catch (Exception e) {
								e.printStackTrace();
								transaction.rollback();
							} finally {
								if (transaction != null) {
									transaction = null;
								}
							}
						}
						
			// 查询指定类型餐饮信息
			public List<CartEntity> queryOneTypeCart(String rt) {
				Transaction transaction = null;
				List<CartEntity> rlist = null;
				String hqlString = "";
				try {
					Session session = HibernateSessionFactory.getSession();
					transaction = session.beginTransaction();
					//hqlString = "from RoomEntity where roomType in(" + rt + ")";
					hqlString = "from CartEntity where cartKind='"+rt+"'";
					Query query = session.createQuery(hqlString);
					rlist = query.list();
					transaction.commit();
					return rlist;
				} catch (Exception e) {
					e.printStackTrace();
					transaction.rollback();
					return rlist;
				} finally {
					if (transaction != null) {
						transaction = null;
					}
				}
			}
			
			
		

	// 用户登录
	public boolean userLogin(String name, String password) {
		// 事物对象
		Transaction transaction = null;
		String hql = "";
		try {
			Session session = HibernateSessionFactory.getSessionFactory()
					.openSession();
			// 开启事务
			transaction = session.beginTransaction();
			hql = "from VipEntity where hyUser=? and hyPassword=MD5('"+password+"')";
			Query query = session.createQuery(hql);
			// 传入hql中两个占位符的参数
			query.setParameter(0, name);
			List list = query.list();
			// 提交事务,必须在返回之前
			transaction.commit();
			// 开始查询
			if (list.size() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			return false;
		} finally {
			if (transaction != null) {
				transaction = null;
			}
		}
	}

	// 显示个人信息
	public VipEntity queryUserByName(String name) {
		Transaction transaction = null;
		List<VipEntity> clist = null;
		VipEntity vip = null;
		String hql = "";
		try {
			Session session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
			hql = "from VipEntity where hyUser='" + name + "'";
			Query query = session.createQuery(hql);
			clist = query.list();
			vip = clist.get(0);
			transaction.commit();
			return vip;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			return vip;
		} finally {
			if (transaction != null) {
				transaction = null;
			}
		}
	}

	// 保存用户修改的个人信息
	public boolean updateUser(VipEntity user) {
		Transaction transaction = null;
		try {
			Session session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
			session.update(user);
			transaction.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			return false;
		} finally {
			if (transaction != null) {
				transaction = null;
			}
		}
	}

	// 查询用户所有预定订单
	public List<OrderEntity> queryUserOrder(String name) {
		Transaction transaction = null;
		List<OrderEntity> list = null;
		String hqlString = "";
		try {
			Session session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
			hqlString = "from OrderEntity where hyName='" + name + "'and des='0' order by orderId desc";
			Query query = session.createQuery(hqlString);
			list = query.list();
			transaction.commit();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			return list;
		} finally {
			if (transaction != null) {
				transaction = null;
			}
		}
	}

	// 查看历史住房信息
	public List<OrderEntity> queryHistory(int id) {
		Transaction transaction = null;
		List<OrderEntity> sList = null;
		String hqlString = "";
		try {
			Session session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
//			hqlString = "from Check where status in ('已退房','入住成功') and user.name='"
//					+ name + "'";
			hqlString="from OrderEntity where hyId="+id+"and des='2' order by orderId desc";
			Query query = session.createQuery(hqlString);
			sList = query.list();
			transaction.commit();
			return sList;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			return sList;
		} finally {
			if (transaction != null) {
				transaction = null;
			}
		}
	}

	
	// 查看历史餐饮信息
		public List<OrdEntity> queryHistoryCart(int id,int sta) {
			Transaction transaction = null;
			List<OrdEntity> sList = null;
			String hqlString = "";
			try {
				Session session = HibernateSessionFactory.getSession();
				transaction = session.beginTransaction();
//				hqlString = "from Check where status in ('已退房','入住成功') and user.name='"
//						+ name + "'";
				if(sta==1) {
					hqlString="from OrdEntity where hyId="+id+"and des='1' order by ordId desc";
				}
				else {
					hqlString="from OrdEntity where hyId="+id+"and des='11' order by ordId desc";
				}
				Query query = session.createQuery(hqlString);
				query.setFirstResult(0);
				query.setMaxResults(8);
				sList = query.list();
				transaction.commit();
				return sList;
			} catch (Exception e) {
				e.printStackTrace();
				transaction.rollback();
				return sList;
			} finally {
				if (transaction != null) {
					transaction = null;
				}
			}
		}

	// 查询指定类型房间的房间号
	public List<RoomEntity> queryOneTypeHomeNumber(int roomtype) {
		Transaction transaction = null;
		List<RoomEntity> rlist = null;
		String hqlString = "";
		try {
			Session session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
			hqlString = "from RoomEntity where roomType="+ roomtype
					+"";
			Query query = session.createQuery(hqlString);
			rlist = query.list();
			transaction.commit();
			return rlist;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			return rlist;
		} finally {
			if (transaction != null) {
				transaction = null;
			}
		}
	}
	
	// 查询指定座位号
	public List<TableEntity> querySitNumber(Integer sn) {
		Transaction transaction = null;
		List<TableEntity> rlist = null;
		String hqlString = "";
		try {
			Session session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
			hqlString = "from TableEntity where sitNum="+ sn
					+"";
			Query query = session.createQuery(hqlString);
			rlist = query.list();
			transaction.commit();
			return rlist;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			return rlist;
		} finally {
			if (transaction != null) {
				transaction = null;
			}
		}
	}

	// 根据房间号获取房间信息
	public RoomEntity queryRoomByNumber(String roomnumber) {
		Transaction transaction = null;
		List<RoomEntity> clist = null;
		RoomEntity room = null;
		String hql = "";
		try {
			Session session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
			hql = "from RoomEntity where roomNum='" + roomnumber + "'";
			Query query = session.createQuery(hql);
			clist = query.list();
			room = clist.get(0);
			transaction.commit();
			return room;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			return room;
		} finally {
			if (transaction != null) {
				transaction = null;
			}
		}
	}

	// 保存订单信息
	public void saveNewOrder(OrderEntity order) {
		Transaction transaction = null;
		try {
			Session session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
			session.save(order);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			if (transaction != null) {
				transaction = null;
			}
		}
	}
	
	//根据id查询商品
	public CartEntity queryCartById(int id) {
		Transaction transaction = null;
		List<CartEntity> clist = null;
		CartEntity cart = null;
		String hql = "";
		try {
			Session session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
			hql = "from CartEntity where cartId="+id+"";
			Query query = session.createQuery(hql);
			clist = query.list();
			cart = clist.get(0);
			transaction.commit();
			return cart;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			return cart;
		} finally {
			if (transaction != null) {
				transaction = null;
			}
		}
	}

	// 保存订单信息
	public void saveNewOrd(OrdEntity order) {
		Transaction transaction = null;
		try {
			Session session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
			session.save(order);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			if (transaction != null) {
				transaction = null;
			}
		}
	}
	// 保存餐饮信息
		public void saveCart(CartEntity ca) {
			Transaction transaction = null;
			try {
				Session session = HibernateSessionFactory.getSession();
				transaction = session.beginTransaction();
				session.save(ca);
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				transaction.rollback();
			} finally {
				if (transaction != null) {
					transaction = null;
				}
			}
		}
		
	//购物车结算
	public List<OrdEntity> CountOrd(int tableid,String des,int hid) {
		Transaction transaction = null;
		List<OrdEntity> rlist = null;
		String hqlString = "";
		try {
			Session session = HibernateSessionFactory.getSession();
			transaction = session.beginTransaction();
			hqlString = "from OrdEntity where tableId="+ tableid
					+"and des='"+des+"'and hyId="+hid+"";
			Query query = session.createQuery(hqlString);
			rlist = query.list();
			transaction.commit();
			return rlist;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			return rlist;
		} finally {
			if (transaction != null) {
				transaction = null;
			}
		}
	}
	
	//提交餐饮订单
		public void saveCartOrd(OrdEntity ord) {
//			Transaction transaction = null;
//			List<OrdEntity> rlist = null;
//			String hqlString = "";
//			try {
//				Session session = HibernateSessionFactory.getSession();
//				transaction = session.beginTransaction();
//				hqlString = "from OrdEntity where tableId="+ tableid
//						+"";
//				Query query = session.createQuery(hqlString);
//				rlist = query.list();
//				transaction.commit();
//				return rlist;
//			} catch (Exception e) {
//				e.printStackTrace();
//				transaction.rollback();
//				return rlist;
//			} finally {
//				if (transaction != null) {
//					transaction = null;
//				}
//			}
			Transaction transaction = null;
			try {
				Session session = HibernateSessionFactory.getSession();
				transaction = session.beginTransaction();
				session.save(ord);
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				transaction.rollback();
			} finally {
				if (transaction != null) {
					transaction = null;
				}
			}
		}
		
		public OrdEntity queryOrdById(int id) {
			Transaction transaction = null;
			List<OrdEntity> clist = null;
			OrdEntity ord = null;
			String hql = "";
			try {
				Session session = HibernateSessionFactory.getSession();
				transaction = session.beginTransaction();
				hql = "from OrdEntity where ordId=" + id + "";
				Query query = session.createQuery(hql);
				clist = query.list();
				ord = clist.get(0);
				transaction.commit();
				return ord;
			} catch (Exception e) {
				e.printStackTrace();
				transaction.rollback();
				return ord;
			} finally {
				if (transaction != null) {
					transaction = null;
				}
			}
		}
		//客房订单
		public OrderEntity queryOrderById(int id) {
			Transaction transaction = null;
			List<OrderEntity> clist = null;
			OrderEntity ord = null;
			String hql = "";
			try {
				Session session = HibernateSessionFactory.getSession();
				transaction = session.beginTransaction();
				hql = "from OrderEntity where orderId=" + id + "";
				Query query = session.createQuery(hql);
				clist = query.list();
				ord = clist.get(0);
				transaction.commit();
				return ord;
			} catch (Exception e) {
				e.printStackTrace();
				transaction.rollback();
				return ord;
			} finally {
				if (transaction != null) {
					transaction = null;
				}
			}
		}
		// 查询酒店所有农产品
		public List<VegetableEntity> queryVeg() {
			Transaction transaction = null;
			List<VegetableEntity> rlist = null;
			String hqlString = "";
			try {
				Session session = HibernateSessionFactory.getSession();
				transaction = session.beginTransaction();
				hqlString = "from VegetableEntity";
				Query query = session.createQuery(hqlString);
				rlist = query.list();
				transaction.commit();
				return rlist;
			} catch (Exception e) {
				e.printStackTrace();
				transaction.rollback();
				return rlist;
			} finally {
				if (transaction != null) {
					transaction = null;
				}
			}
		}
		public TableEntity queryTableById(int id) {
			Transaction transaction = null;
			List<TableEntity> clist = null;
			TableEntity veg = null;
			String hql = "";
			try {
				Session session = HibernateSessionFactory.getSession();
				transaction = session.beginTransaction();
				hql = "from TableEntity where tableId=" + id + "";
				Query query = session.createQuery(hql);
				clist = query.list();
				veg = clist.get(0);
				transaction.commit();
				return veg;
			} catch (Exception e) {
				e.printStackTrace();
				transaction.rollback();
				return veg;
			} finally {
				if (transaction != null) {
					transaction = null;
				}
			}
		}
		public VegetableEntity queryVegById(int id) {
			Transaction transaction = null;
			List<VegetableEntity> clist = null;
			VegetableEntity veg = null;
			String hql = "";
			try {
				Session session = HibernateSessionFactory.getSession();
				transaction = session.beginTransaction();
				hql = "from VegetableEntity where vegId=" + id + "";
				Query query = session.createQuery(hql);
				clist = query.list();
				veg = clist.get(0);
				transaction.commit();
				return veg;
			} catch (Exception e) {
				e.printStackTrace();
				transaction.rollback();
				return veg;
			} finally {
				if (transaction != null) {
					transaction = null;
				}
			}
		}
		// 保存餐饮信息
				public void saveVeg(VegetableEntity veg) {
					Transaction transaction = null;
					try {
						Session session = HibernateSessionFactory.getSession();
						transaction = session.beginTransaction();
						session.save(veg);
						transaction.commit();
					} catch (Exception e) {
						e.printStackTrace();
						transaction.rollback();
					} finally {
						if (transaction != null) {
							transaction = null;
						}
					}
				}
				//MD5加密
				public String getMD5String(String str) {
			        try {
			            // 生成一个MD5加密计算摘要
			            MessageDigest md = MessageDigest.getInstance("MD5");
			            // 计算md5函数
			            md.update(str.getBytes());
			            // digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
			            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
			            //一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方）
			            return new BigInteger(1, md.digest()).toString(16);
			        } catch (Exception e) {
			           e.printStackTrace();
			           return null;
			        }
			    }

				public void updateTable(TableEntity td) {
					Transaction transaction = null;
					try {
						Session session = HibernateSessionFactory.getSession();
						transaction = session.beginTransaction();
						session.update(td);
						transaction.commit();
		
					} catch (Exception e) {
						e.printStackTrace();
						transaction.rollback();
				
					} finally {
						if (transaction != null) {
							transaction = null;
						}
					}
				}
		
				
}
