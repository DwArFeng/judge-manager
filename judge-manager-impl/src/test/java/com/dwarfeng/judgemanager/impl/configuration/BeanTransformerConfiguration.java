package com.dwarfeng.judgemanager.impl.configuration;

import com.dwarfeng.judge.sdk.bean.entity.*;
import com.dwarfeng.judge.stack.bean.entity.*;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanTransformerConfiguration {

    @Autowired
    private Mapper mapper;

    @Bean
    public BeanTransformer<Section, JSFixedFastJsonSection> sectionJSFixedFastJsonSectionBeanTransformer() {
        return new DozerBeanTransformer<>(Section.class, JSFixedFastJsonSection.class, mapper);
    }

    @Bean
    public BeanTransformer<JudgerInfo, JSFixedFastJsonJudgerInfo> judgerInfoJSFixedFastJsonJudgerInfoBeanTransformer() {
        return new DozerBeanTransformer<>(JudgerInfo.class, JSFixedFastJsonJudgerInfo.class, mapper);
    }

    @Bean
    public BeanTransformer<DriverInfo, JSFixedFastJsonDriverInfo> driverInfoJSFixedFastJsonDriverInfoBeanTransformer() {
        return new DozerBeanTransformer<>(DriverInfo.class, JSFixedFastJsonDriverInfo.class, mapper);
    }

    @Bean
    public BeanTransformer<JudgerSupport, FastJsonJudgerSupport> judgerSupportFastJsonJudgerSupportBeanTransformer() {
        return new DozerBeanTransformer<>(JudgerSupport.class, FastJsonJudgerSupport.class, mapper);
    }

    @Bean
    public BeanTransformer<DriverSupport, FastJsonDriverSupport> driverSupportFastJsonDriverSupportBeanTransformer() {
        return new DozerBeanTransformer<>(DriverSupport.class, FastJsonDriverSupport.class, mapper);
    }
}
