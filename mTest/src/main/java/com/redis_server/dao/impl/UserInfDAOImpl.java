package com.redis_server.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.redis_server.dao.UserInfDAO;
import com.redis_server.entity.UserInf;


/**
 * A data access object (DAO) providing persistence and search support for
 * UserInf entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.redis_server.entity.UserInf
 * @author MyEclipse Persistence Tools
 */

public class UserInfDAOImpl extends BaseDAO implements UserInfDAO{
	private static final Logger log = LoggerFactory.getLogger(UserInfDAOImpl.class);
	// property constants
	public static final String ACCOUNT = "account";
	public static final String PASSWORD = "password";
	public static final String EMAIL = "email";
	public static final String NICKNAME = "nickname";
	public static final String PROVINCE = "province";
	public static final String CITY = "city";
	public static final String REGION = "region";
	public static final String SEX = "sex";
	public static final String HEIGHT = "height";
	public static final String WEIGHT = "weight";
	public static final String SUMMARY = "summary";
	public static final String PROFESSIONAL = "professional";
	public static final String SALARY = "salary";
	public static final String COMPANY = "company";
	public static final String TOTAL_COIN = "totalCoin";
	public static final String FREEZE_COIN = "freezeCoin";
	public static final String POSI_LONGITUDE = "posiLongitude";
	public static final String POSI_LATITUDE = "posiLatitude";
	public static final String POSI_HEIGHT = "posiHeight";
	public static final String ONLINE_STATUS = "onlineStatus";
	public static final String SESSION_ID = "sessionId";
	public static final String LOGIN_SOURCE = "loginSource";
	public static final String DATA_FULL = "dataFull";
	public static final String STATUS = "status";

	protected void initDao() {
		// do nothing
	}

	public void save(UserInf transientInstance) {
		log.debug("saving UserInf instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(UserInf persistentInstance) {
		log.debug("deleting UserInf instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserInf findById(java.lang.Integer id) {
		log.debug("getting UserInf instance with id: " + id);
		try {
			UserInf instance = (UserInf) getHibernateTemplate().get(
					"com.redis_server.entity.UserInf", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(UserInf instance) {
		log.debug("finding UserInf instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding UserInf instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from UserInf as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByAccount(Object account) {
		return findByProperty(ACCOUNT, account);
	}

	public List findByPassword(Object password) {
		return findByProperty(PASSWORD, password);
	}

	public List findByEmail(Object email) {
		return findByProperty(EMAIL, email);
	}

	public List findByNickname(Object nickname) {
		return findByProperty(NICKNAME, nickname);
	}

	public List findByProvince(Object province) {
		return findByProperty(PROVINCE, province);
	}

	public List findByCity(Object city) {
		return findByProperty(CITY, city);
	}

	public List findByRegion(Object region) {
		return findByProperty(REGION, region);
	}

	public List findBySex(Object sex) {
		return findByProperty(SEX, sex);
	}

	public List findByHeight(Object height) {
		return findByProperty(HEIGHT, height);
	}

	public List findByWeight(Object weight) {
		return findByProperty(WEIGHT, weight);
	}

	public List findBySummary(Object summary) {
		return findByProperty(SUMMARY, summary);
	}

	public List findByProfessional(Object professional) {
		return findByProperty(PROFESSIONAL, professional);
	}

	public List findBySalary(Object salary) {
		return findByProperty(SALARY, salary);
	}

	public List findByCompany(Object company) {
		return findByProperty(COMPANY, company);
	}

	public List findByTotalCoin(Object totalCoin) {
		return findByProperty(TOTAL_COIN, totalCoin);
	}

	public List findByFreezeCoin(Object freezeCoin) {
		return findByProperty(FREEZE_COIN, freezeCoin);
	}

	public List findByPosiLongitude(Object posiLongitude) {
		return findByProperty(POSI_LONGITUDE, posiLongitude);
	}

	public List findByPosiLatitude(Object posiLatitude) {
		return findByProperty(POSI_LATITUDE, posiLatitude);
	}

	public List findByPosiHeight(Object posiHeight) {
		return findByProperty(POSI_HEIGHT, posiHeight);
	}

	public List findByOnlineStatus(Object onlineStatus) {
		return findByProperty(ONLINE_STATUS, onlineStatus);
	}

	public List findBySessionId(Object sessionId) {
		return findByProperty(SESSION_ID, sessionId);
	}

	public List findByLoginSource(Object loginSource) {
		return findByProperty(LOGIN_SOURCE, loginSource);
	}

	public List findByDataFull(Object dataFull) {
		return findByProperty(DATA_FULL, dataFull);
	}

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	public List findAll() {
		log.debug("finding all UserInf instances");
		try {
			String queryString = "from UserInf";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public UserInf merge(UserInf detachedInstance) {
		log.debug("merging UserInf instance");
		try {
			UserInf result = (UserInf) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(UserInf instance) {
		log.debug("attaching dirty UserInf instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UserInf instance) {
		log.debug("attaching clean UserInf instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public Integer getUidBySessionId(final String sessionId) {
		try {
			final String hql = "select uuid from UserInf u where u.sessionId=:sessionId and u.onlineStatus=1";
			Integer uid = this.getHibernateTemplate().execute(
					new HibernateCallback<Integer>() {
						public Integer doInHibernate(Session session)
								throws HibernateException, SQLException {
							Query q = session.createQuery(hql);
							q.setString("sessionId", sessionId);
							return (Integer) q.uniqueResult();
						}
					});
			return uid;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static UserInfDAOImpl getFromApplicationContext(ApplicationContext ctx) {
		return (UserInfDAOImpl) ctx.getBean("UserInfDAO");
	}
}