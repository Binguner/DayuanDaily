package com.nenguou.dayuandaily.Model;

/**
 * Created by binguner on 2018/3/25.
 */

public class Evaluate {

    /**
     * teachersName : 任少斌-已评教;杨玲珍-已评教;王江荔-已评教;张兴忠-已评教;高保禄-已评教;杨永强-已评教;孙静宇-已评教;白亮-已评教;王茹-已评教;王茹-已评教;张淑蓉-已评教;梁寅菁-已评教;阎鹏飞-已评教;尔雅-已评教;尔雅-已评教;
     * total : 15
     * evaluated : 0
     * failed : 0
     */

    private String teachersName;
    private int total;
    private int evaluated;
    private int failed;

    public String getTeachersName() {
        return teachersName;
    }

    public void setTeachersName(String teachersName) {
        this.teachersName = teachersName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getEvaluated() {
        return evaluated;
    }

    public void setEvaluated(int evaluated) {
        this.evaluated = evaluated;
    }

    public int getFailed() {
        return failed;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }
}
