package cn.com.siemens.novel.test;

import cn.com.siemens.novel.bean.Position;
import cn.com.siemens.novel.dao.UrlBeanDAO;

public class UrlBeanDAOTest {
    @org.junit.Test
    public void testGetUrlList() {

        System.out.println(UrlBeanDAO.getUrlByPosition(
                new Position("5279daa0ea5513b4c7e877c7"), 20).get(0));
    }
}
