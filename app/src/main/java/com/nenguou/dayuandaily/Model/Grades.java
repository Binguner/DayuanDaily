package com.nenguou.dayuandaily.Model;

import java.util.List;

/**
 * Created by binguner on 2018/2/24.
 */

public class Grades {

    /**
     * code : 1
     * msg : success
     * data : [{"grades":[{"classNumber":"00000131","classOrder":"08","className":"军事理论","englishName":"Military Theory","credit":"2","classInfo":"综合必修","grade":"65.0"},{"classNumber":"00003171","classOrder":"08","className":"程序设计技术B","englishName":"Programming Techniques B","credit":"3.5","classInfo":"综合必修","grade":"65.0"}],"name":"2016-2017学年秋(两学期)"},{"grades":[{"classNumber":"00003553","classOrder":"01","className":"高等数学E(一)","englishName":"Higher Mathematics E（Ⅰ）","credit":"5.5","classInfo":"通识必修","grade":"66.0"}],"name":"2016-2017学年春(两学期)"},{"grades":[{"classNumber":"00004469","classOrder":"08","className":"计算机基础","englishName":"Fundamentals of Computers","credit":"2.5","classInfo":"综合必修","grade":"76.0"},{"classNumber":"00007001","classOrder":"338","className":"体育(一)","englishName":"Physical Education （Ⅰ）","credit":"1","classInfo":"通识必修","grade":"80.0"},{"classNumber":"00007005","classOrder":"16","className":"思想道德修养与法律基础","englishName":"Ideological & Moral Cultivation and Fundamentals of Law","credit":"3","classInfo":"通识必修","grade":"71.0"}],"name":"2016-2017学年秋(两学期)"},{"grades":[{"classNumber":"00000004","classOrder":"16","className":"中国近现代史纲要","englishName":"Outline of Modern Chinese History","credit":"2","classInfo":"通识必修","grade":"87.0"},{"classNumber":"00000042","classOrder":"09","className":"大学物理B","englishName":"College Physics B","credit":"4.5","classInfo":"通识必修","grade":"62.0"},{"classNumber":"00007002","classOrder":"338","className":"体育(二)","englishName":"Physical Education（Ⅱ）","credit":"1","classInfo":"通识必修","grade":"88.0"},{"classNumber":"SJ000009","classOrder":"32","className":"软件工程教学实习","englishName":"Teaching Practice of Software Engineering","credit":"1","classInfo":"实践环节","grade":"75.0"}],"name":"2016-2017学年春(两学期)"},{"grades":[{"classNumber":"00000048","classOrder":"01","className":"物理实验B","englishName":"Physics Experiments B","credit":"2","classInfo":"通识必修","grade":"80.0"},{"classNumber":"00007003","classOrder":"01","className":"体育(三)","englishName":"Physical Education（Ⅲ）","credit":"1","classInfo":"通识必修","grade":"66.0"},{"classNumber":"00007006","classOrder":"12","className":"马克思主义基本原理","englishName":"The Fundamental Principles of Marxism","credit":"3","classInfo":"通识必修","grade":"63.0"},{"classNumber":"00008477","classOrder":"08","className":"电路与电子技术R","englishName":"Circuit and Electronic Technology R","credit":"3","classInfo":"学科选修","grade":"62.0"},{"classNumber":"00008483","classOrder":"08","className":"Java语言程序设计R","englishName":"Java Programming R","credit":"2.5","classInfo":"学科选修","grade":"60.0"},{"classNumber":"C0000513","classOrder":"17","className":"大学英语过程写作","englishName":"College English Process Writing","credit":"1","classInfo":"任选","grade":"75.0"}],"name":"2017-2018学年秋(两学期)"},{"grades":[{"classNumber":"00000012","classOrder":"01","className":"大学英语(二)","englishName":"College English（Ⅱ）","credit":"3.5","classInfo":"通识任选","grade":"48.0"},{"classNumber":"00000013","classOrder":"01","className":"大学英语(三)","englishName":"College English（Ⅲ）","credit":"3.5","classInfo":"通识必修","grade":"45.0"},{"classNumber":"00003345","classOrder":"06","className":"概率论与数理统计E","englishName":"Probability and Statistics E","credit":"3","classInfo":"通识必修","grade":"45.0"},{"classNumber":"00003346","classOrder":"08","className":"线性代数E","englishName":"Linear Algebra E","credit":"2.5","classInfo":"通识必修","grade":"45.0"},{"classNumber":"00003346","classOrder":"1","className":"线性代数E","englishName":"Linear Algebra E","credit":"2.5","classInfo":"通识必修","grade":"5.0"},{"classNumber":"00003554","classOrder":"08","className":"高等数学E(二)","englishName":"Higher Mathematics E（II）","credit":"5.5","classInfo":"通识必修","grade":"35.0"},{"classNumber":"00003554","classOrder":"1","className":"高等数学E(二)","englishName":"Higher Mathematics E（II）","credit":"5.5","classInfo":"通识必修","grade":"4.0"},{"classNumber":"00007732","classOrder":"08","className":"离散结构R","englishName":"Discrete Mathematical Structures R","credit":"3.5","classInfo":"通识必修","grade":"42.0"},{"classNumber":"00008472","classOrder":"08","className":"数据结构R","englishName":"Data Structure R","credit":"4","classInfo":"学科必修","grade":"45.0"},{"classNumber":"00008479","classOrder":"08","className":"面向对象程序设计基础R","englishName":"The Basis of Object-oriented Programming R","credit":"2.5","classInfo":"学科选修","grade":"48.0"},{"classNumber":"00008479","classOrder":"1","className":"面向对象程序设计基础R","englishName":"The Basis of Object-oriented Programming R","credit":"2.5","classInfo":"学科选修","grade":"45.0"},{"classNumber":"XX000178","classOrder":"01","className":"网页设计技术基础","englishName":"Web Design and Technological Foundation","credit":"2","classInfo":"任选","grade":"38.0"}],"name":"尚不及格"},{"grades":[{"classNumber":"00003553","classOrder":"08","className":"高等数学E(一)","englishName":"Higher Mathematics E（Ⅰ）","credit":"5.5","classInfo":"综合必修","grade":"35.0"}],"name":"曾不及格"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * grades : [{"classNumber":"00000131",
         *              "classOrder":"08",
         *              "className":"军事理论",
         *              "englishName":"Military Theory",
         *              "credit":"2",
         *              "classInfo":"综合必修",
         *              "grade":"65.0"},
         *              {"classNumber":"00003171","classOrder":"08","className":"程序设计技术B","englishName":"Programming Techniques B","credit":"3.5","classInfo":"综合必修","grade":"65.0"}]
         * name : 2016-2017学年秋(两学期)
         */

        private String name;
        private List<GradesBean> grades;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<GradesBean> getGrades() {
            return grades;
        }

        public void setGrades(List<GradesBean> grades) {
            this.grades = grades;
        }

        public static class GradesBean {
            /**
             * classNumber : 00000131
             * classOrder : 08
             * className : 军事理论
             * englishName : Military Theory
             * credit : 2
             * classInfo : 综合必修
             * grade : 65.0
             */

            private String classNumber;
            private String classOrder;
            private String className;
            private String englishName;
            private String credit;
            private String classInfo;
            private String grade;

            public String getClassNumber() {
                return classNumber;
            }

            public void setClassNumber(String classNumber) {
                this.classNumber = classNumber;
            }

            public String getClassOrder() {
                return classOrder;
            }

            public void setClassOrder(String classOrder) {
                this.classOrder = classOrder;
            }

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }

            public String getEnglishName() {
                return englishName;
            }

            public void setEnglishName(String englishName) {
                this.englishName = englishName;
            }

            public String getCredit() {
                return credit;
            }

            public void setCredit(String credit) {
                this.credit = credit;
            }

            public String getClassInfo() {
                return classInfo;
            }

            public void setClassInfo(String classInfo) {
                this.classInfo = classInfo;
            }

            public String getGrade() {
                return grade;
            }

            public void setGrade(String grade) {
                this.grade = grade;
            }
        }
    }
}