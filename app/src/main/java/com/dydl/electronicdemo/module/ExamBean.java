package com.dydl.electronicdemo.module;

import com.dydl.socketlib.callback.OkResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lwx on 2018/4/9.
 */

public class ExamBean implements OkResponse {


    /**
     * 0 : 005050000
     * 1 : 查询成功
     * 2 : 5
     * 3 : [{"0":"914570f4-f8f7-4831-89c7-60eed0b5a508","1":"2018-03-19 09:36","2":"2020-03-01 09:36","3":"120","4":"100","5":"60","6":"1","7":"2018年3月理论知识考试"},{"0":"f0e624ce-99c0-4673-921c-c4e41fef274b","1":"2018-03-05 09:00","2":"2018-03-05 11:00","3":"120","4":"100","5":"60","6":"0","7":"2018年3月业务知识考试"},{"0":"ce26a8e5-b759-4815-8e0e-9325a00318b3","1":"2018-02-05 20:10","2":"2018-02-05 22:10","3":"120","4":"100","5":"60","6":"0","7":"2018年2月业务知识考试"},{"0":"a3aef270-c434-4c55-9777-f842683bdc06","1":"2018-03-23 10:33","2":"2018-11-23 10:33","3":"120","4":"60","5":"36","6":"1","7":"2018年电力综合考试"},{"0":"c836f92d-cfbe-47cf-bf5f-dbf1653af3cc","1":"2018-02-05 14:40","2":"2020-02-01 16:40","3":"120","4":"100","5":"60","6":"1","7":"2018年1月业务知识考试"}]
     */

    @SerializedName("0")
    private String _$0;
    @SerializedName("1")
    private String _$1;
    @SerializedName("2")
    private String _$2;
    @SerializedName("3")
    private List<_$3Bean> _$3;

    public String get_$0() {
        return _$0;
    }

    public void set_$0(String _$0) {
        this._$0 = _$0;
    }

    public String get_$1() {
        return _$1;
    }

    public void set_$1(String _$1) {
        this._$1 = _$1;
    }

    public String get_$2() {
        return _$2;
    }

    public void set_$2(String _$2) {
        this._$2 = _$2;
    }

    public List<_$3Bean> get_$3() {
        return _$3;
    }

    public void set_$3(List<_$3Bean> _$3) {
        this._$3 = _$3;
    }

    public static class _$3Bean {
        /**
         * 0 : 914570f4-f8f7-4831-89c7-60eed0b5a508
         * 1 : 2018-03-19 09:36
         * 2 : 2020-03-01 09:36
         * 3 : 120
         * 4 : 100
         * 5 : 60
         * 6 : 1
         * 7 : 2018年3月理论知识考试
         */

        @SerializedName("0")
        private String _$0;
        @SerializedName("1")
        private String _$1;
        @SerializedName("2")
        private String _$2;
        @SerializedName("3")
        private String _$3;
        @SerializedName("4")
        private String _$4;
        @SerializedName("5")
        private String _$5;
        @SerializedName("6")
        private String _$6;
        @SerializedName("7")
        private String _$7;

        public String get_$0() {
            return _$0;
        }

        public void set_$0(String _$0) {
            this._$0 = _$0;
        }

        public String get_$1() {
            return _$1;
        }

        public void set_$1(String _$1) {
            this._$1 = _$1;
        }

        public String get_$2() {
            return _$2;
        }

        public void set_$2(String _$2) {
            this._$2 = _$2;
        }

        public String get_$3() {
            return _$3;
        }

        public void set_$3(String _$3) {
            this._$3 = _$3;
        }

        public String get_$4() {
            return _$4;
        }

        public void set_$4(String _$4) {
            this._$4 = _$4;
        }

        public String get_$5() {
            return _$5;
        }

        public void set_$5(String _$5) {
            this._$5 = _$5;
        }

        public String get_$6() {
            return _$6;
        }

        public void set_$6(String _$6) {
            this._$6 = _$6;
        }

        public String get_$7() {
            return _$7;
        }

        public void set_$7(String _$7) {
            this._$7 = _$7;
        }
    }
}
