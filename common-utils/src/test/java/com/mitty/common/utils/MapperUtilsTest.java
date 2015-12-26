package com.mitty.common.utils;

import com.mitty.common.annotation.MapperAnnotation;
import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PILIANG on 2015/12/26.
 */
public class MapperUtilsTest {

    @Test
    public void testGetResultMap() throws Exception {

        School school = new School();
        school.name = "中心小学";
        school.schoolCountryName = "美国";
        school.schoolCountry="中国国家";

        School school1 = new School();
        school1.name = "中心小学1";

        School oldSchool = new School();
        oldSchool.name = "中心小学的就学校";

        school.datas.add(school1);
        school.datas.add(school1);
        school.datas.add(school1);

        school.oldSchool = oldSchool;

        System.out.println(JSONUtils.format(MapperUtils.getResultMap(school, MyMapperAnnotation.class)));


//        MapperUtils.getResultMap(school, MyMapperAnnotation.class);
    }

    @MapperAnnotation
    public class School {
        @MapperAnnotation("school_name")
        public String name;
        @MapperAnnotation("school.country.name")
        public String schoolCountryName;
        @MapperAnnotation("school.countryName")
//        @MyMapperAnnotation("dsfdsfds")
        public String schoolCountry;
        @MapperAnnotation("datas")
        public List<School> datas = new ArrayList<School>();
        @MapperAnnotation("school.old_school")
        public School oldSchool = null;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @MapperAnnotation
    public @interface MyMapperAnnotation {
        String value();
    }
}