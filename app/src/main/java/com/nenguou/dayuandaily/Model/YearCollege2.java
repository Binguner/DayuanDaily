package com.nenguou.dayuandaily.Model;

import java.util.List;

/**
 * Created by binguner on 2018/2/15.
 */

public class YearCollege2 {

    /**
     * code : 1
     * msg : success
     * data : {"terms":[
     *                      {
     *                          "id":1,
     *                          "start":1518279131000,
     *                          "name":"2017-2018-2-1"
     *                      }
     *                  ],
     *         "years":[
     *                      {
     *                          "id":2,
     *                          "year":2013
     *                      },
     *                      {
     *                          "id":3,
     *                          "year":2014
     *                      },
     *                      {
     *                          "id":4,
     *                          "year":2015
     *                      },{"id":5,"year":2016},
     *                        {"id":6,"year":2017}
     *                  ],
     *          "colleges":[
     *                         {"id":2,"college":"体育学院"},
     *                         {"id":3,"college":"信息与计算机学院"},
     *                         {"id":4,"college":"力学学院"},
     *                         {"id":5,"college":"化学化工学院"},
     *                         {"id":6,"college":"国际教育交流学院"},{"id":7,"college":"外国语学院"},{"id":8,"college":"大数据学院"},{"id":9,"college":"建筑与土木工程学院"},
     *                         {"id":10,"college":"政法学院"},{"id":11,"college":"数学学院"},{"id":12,"college":"机械工程学院"},{"id":13,"college":"材料科学与工程学院"},
     *                         {"id":14,"college":"水利科学与工程学院"},{"id":15,"college":"物理与光电工程学院"},{"id":16,"college":"环境科学与工程学院"},{"id":17,"college":"现代科技学院"},
     *                         {"id":18,"college":"电气与动力工程学院"},{"id":19,"college":"矿业工程学院"},{"id":20,"college":"经济管理学院"},{"id":21,"college":"艺术学院"},{"id":22,"college":"软件学院"},
     *                         {"id":23,"college":"轻纺工程学院"},{"id":24,"college":"马克思主义学院"}
     *                     ],
     *          "classNames":null,
     *          "majors":null,
     *          "urpClass":null
     *       }
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * terms : [{"id":1,"start":1518279131000,"name":"2017-2018-2-1"}]
         * years : [{"id":2,"year":2013},{"id":3,"year":2014},{"id":4,"year":2015},{"id":5,"year":2016},{"id":6,"year":2017}]
         * colleges : [{"id":2,"college":"体育学院"},{"id":3,"college":"信息与计算机学院"},{"id":4,"college":"力学学院"},{"id":5,"college":"化学化工学院"},{"id":6,"college":"国际教育交流学院"},{"id":7,"college":"外国语学院"},{"id":8,"college":"大数据学院"},{"id":9,"college":"建筑与土木工程学院"},{"id":10,"college":"政法学院"},{"id":11,"college":"数学学院"},{"id":12,"college":"机械工程学院"},{"id":13,"college":"材料科学与工程学院"},{"id":14,"college":"水利科学与工程学院"},{"id":15,"college":"物理与光电工程学院"},{"id":16,"college":"环境科学与工程学院"},{"id":17,"college":"现代科技学院"},{"id":18,"college":"电气与动力工程学院"},{"id":19,"college":"矿业工程学院"},{"id":20,"college":"经济管理学院"},{"id":21,"college":"艺术学院"},{"id":22,"college":"软件学院"},{"id":23,"college":"轻纺工程学院"},{"id":24,"college":"马克思主义学院"}]
         * classNames : null
         * majors : null
         * urpClass : null
         */

        private Object classNames;
        private Object majors;
        private Object urpClass;
        private List<TermsBean> terms;
        private List<YearsBean> years;
        private List<CollegesBean> colleges;

        public Object getClassNames() {
            return classNames;
        }

        public void setClassNames(Object classNames) {
            this.classNames = classNames;
        }

        public Object getMajors() {
            return majors;
        }

        public void setMajors(Object majors) {
            this.majors = majors;
        }

        public Object getUrpClass() {
            return urpClass;
        }

        public void setUrpClass(Object urpClass) {
            this.urpClass = urpClass;
        }

        public List<TermsBean> getTerms() {
            return terms;
        }

        public void setTerms(List<TermsBean> terms) {
            this.terms = terms;
        }

        public List<YearsBean> getYears() {
            return years;
        }

        public void setYears(List<YearsBean> years) {
            this.years = years;
        }

        public List<CollegesBean> getColleges() {
            return colleges;
        }

        public void setColleges(List<CollegesBean> colleges) {
            this.colleges = colleges;
        }

        public static class TermsBean {
            /**
             * id : 1
             * start : 1518279131000
             * name : 2017-2018-2-1
             */

            private int id;
            private long start;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public long getStart() {
                return start;
            }

            public void setStart(long start) {
                this.start = start;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class YearsBean {
            /**
             * id : 2
             * year : 2013
             */

            private int id;
            private int year;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }
        }

        public static class CollegesBean {
            /**
             * id : 2
             * college : 体育学院
             */

            private int id;
            private String college;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCollege() {
                return college;
            }

            public void setCollege(String college) {
                this.college = college;
            }
        }
    }
}
