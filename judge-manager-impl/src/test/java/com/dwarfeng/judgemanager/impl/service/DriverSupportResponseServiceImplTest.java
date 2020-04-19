package com.dwarfeng.judgemanager.impl.service;

import com.dwarfeng.judge.stack.bean.entity.DriverSupport;
import com.dwarfeng.judge.stack.service.DriverSupportMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class DriverSupportResponseServiceImplTest {

    private final List<DriverSupport> driverSupports = new ArrayList<>();
    @Autowired
    @Qualifier("driverSupportMaintainService")
    private DriverSupportMaintainService service;

    @Before
    public void setUp() {
        for (int i = 0; i < 5; i++) {
            DriverSupport driverSupport = new DriverSupport(
                    new StringIdKey("driver-support-" + (i + 1)),
                    "label-" + (i + 1),
                    "这是测试用的DriverSupport",
                    "1233211234567"
            );
            driverSupports.add(driverSupport);
        }
    }

    @After
    public void tearDown() {
        driverSupports.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            for (DriverSupport driverSupport : driverSupports) {
                driverSupport.setKey(service.insert(driverSupport));
                service.update(driverSupport);
                DriverSupport testDriverSupport = service.get(driverSupport.getKey());
                assertEquals(BeanUtils.describe(driverSupport), BeanUtils.describe(testDriverSupport));
            }
        } finally {
            for (DriverSupport driverSupport : driverSupports) {
                service.deleteIfExists(driverSupport.getKey());
            }
        }
    }
}
