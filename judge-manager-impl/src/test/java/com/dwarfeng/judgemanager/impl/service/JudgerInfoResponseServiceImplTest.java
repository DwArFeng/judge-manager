package com.dwarfeng.judgemanager.impl.service;

import com.dwarfeng.judge.stack.bean.entity.JudgerInfo;
import com.dwarfeng.judge.stack.bean.entity.Section;
import com.dwarfeng.judge.stack.service.JudgerInfoMaintainService;
import com.dwarfeng.judge.stack.service.SectionMaintainService;
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
public class JudgerInfoResponseServiceImplTest {

    @Autowired
    @Qualifier("sectionMaintainService")
    private SectionMaintainService sectionMaintainService;
    @Autowired
    @Qualifier("judgerInfoMaintainService")
    private JudgerInfoMaintainService judgerInfoMaintainService;

    private Section parentSection;
    private List<JudgerInfo> judgerInfos;

    @Before
    public void setUp() {
        parentSection = new Section(
                null,
                "parent-section",
                "test-section"
        );
        judgerInfos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            JudgerInfo judgerInfo = new JudgerInfo(
                    null,
                    parentSection.getKey(),
                    true,
                    "filter-info-" + i,
                    "this is a test",
                    "test"
            );
            judgerInfos.add(judgerInfo);
        }
    }

    @After
    public void tearDown() {
        parentSection = null;
        judgerInfos.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            parentSection.setKey(sectionMaintainService.insertOrUpdate(parentSection));
            for (JudgerInfo judgerInfo : judgerInfos) {
                judgerInfo.setKey(judgerInfoMaintainService.insertOrUpdate(judgerInfo));
                judgerInfo.setSectionKey(parentSection.getKey());
                judgerInfoMaintainService.update(judgerInfo);
                JudgerInfo testJudgerInfo = judgerInfoMaintainService.get(judgerInfo.getKey());
                assertEquals(BeanUtils.describe(judgerInfo), BeanUtils.describe(testJudgerInfo));
            }
        } finally {
            for (JudgerInfo judgerInfo : judgerInfos) {
                judgerInfoMaintainService.deleteIfExists(judgerInfo.getKey());
            }
            sectionMaintainService.deleteIfExists(parentSection.getKey());
        }
    }
}
