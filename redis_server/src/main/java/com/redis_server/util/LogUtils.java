package com.redis_server.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogUtils {
	private static Log errorLogger = LogFactory.getLog("ERR");
	private static Log pushLogger = LogFactory.getLog("PUSH");
	private static Log changeLanguageLogger = LogFactory.getLog("LANGUAGE");
	private static Log loginLogger = LogFactory.getLog("LOGIN");
	private static Log vipLogger = LogFactory.getLog("VIP");
	private static Log createGroupLogger = LogFactory.getLog("CREATEG");
	private static Log searchGroupLogger = LogFactory.getLog("SEARCHGROUP");
	private static Log updateGroupLogger = LogFactory.getLog("UPDATEGROUP");
	private static Log addFriendLogger = LogFactory.getLog("ADDFRIEND");

	public static void logCreate4Info(String gp, String mp, int rs, double coin, int craeteStatus, int ifPersonal, String url, String uuid) {
		createGroupLogger.info(craeteStatus + "|" + rs + "|" + coin + "|" + ifPersonal + "|" + mp + "|" + gp + "|" + url + "|" + uuid);
	}

	public static void log4AddFriendInfo(int platform, int gid, String gp, String targetPassport, String name,String res) {
		addFriendLogger.info(platform + "|" + gid + "|" + gp + "|" + targetPassport + "|" + name+"|"+res);
	}

	public static void logCreate4Info(String groupPassport, String memberPassport, int platform, int createStatus, String source, String inf) {
		createGroupLogger.info(groupPassport + "|" + memberPassport + "|" + platform + "|" + createStatus + "|" + source + "|" + inf);
	}

	public static void log4Error(String info) {
		errorLogger.error(info);
	}

	public static void log4Error(String info, Throwable t) {
		errorLogger.error(info, t);
	}

	public static void log4Error(Throwable t) {
		errorLogger.error(t.getMessage(), t);
	}

	public static void log4Push(String groupPassport, String memberPassport, String msg, int epid) {
		pushLogger.info(groupPassport + "|" + memberPassport + "|" + msg + "|" + epid);
	}

	/**
	 * @param passport用户帐号
	 * @param ip用户IP
	 * @param loginType登录类型
	 *            1-msn 2-yahoo
	 * @param source来源
	 *            001-群网�?002-外部调用jurl自动登录 003，cookie中直接取�?
	 */
	public static void loginLog4Info(String passport, String ip, int loginType, String source, String jurl) {
		loginLogger.info(passport + "|" + ip + "|" + loginType + "|" + source + "|" + jurl);
	}

	public static void log4VipHandle(String groupPassport, String memberPassport, int handleType, int payResult, int updateResult, int isNeedDispose, String pid) {
		vipLogger.info(memberPassport + "|" + groupPassport + "|" + handleType + "|" + pid + "|" + payResult + "|" + updateResult + "|" + isNeedDispose);
	}

	/**
	 * 改变语言
	 * 
	 * @param groupPassport
	 * @param memberPassport
	 * @param language
	 * @param type
	 *            更新途径 type = 0 表示P4 type = 1 表示指令 type = 2 表示第一次打�?4时设置语�?type = 3 表示从群空间中设置语�?
	 */
	public static void log4ChangeLanguage(String memberPassport, String language, int type) {
		changeLanguageLogger.info("" + memberPassport + "|" + language + "|" + type);
	}

	/**
	 * 搜索�?
	 * 
	 * @param memberPassport
	 *            空字符串:未登�? 不为�?登陆
	 * @param keyWords
	 *            关键�?
	 * @param region
	 *            国家
	 * @param categoryId
	 *            分类 -1表示全部
	 */
	public static void log4SearchGroup(String memberPassport, String keyWords, String region, int categoryId) {
		searchGroupLogger.info("" + memberPassport + "|" + keyWords + "|" + region + "|" + categoryId);
	}

	public static void log4UpdateGroup(String action, String methodName, String groupPassport, int gid, String nodeId, int platform, String params) {
		updateGroupLogger.info("" + action + "|" + methodName + "|gp:" + groupPassport + "|gid:" + gid + "|nid:" + nodeId + "|pf:" + platform + "|" + params);
	}

	public static void rollingFile() {
		// System.out.println("Rolling Log File...");
		errorLogger.info("FORCE ROLLING FILE");
		pushLogger.info("FORCE ROLLING FILE");
		changeLanguageLogger.info("FORCE ROLLING FILE");
		loginLogger.info("FORCE ROLLING FILE");
		vipLogger.info("FORCE ROLLING FILE");
		createGroupLogger.info("FORCE ROLLING FILE");
		searchGroupLogger.info("FORCE ROLLING FILE");
		updateGroupLogger.info("FORCE ROLLING FILE");
		addFriendLogger.info("FORCE ROLLING FILE");
	}
}
