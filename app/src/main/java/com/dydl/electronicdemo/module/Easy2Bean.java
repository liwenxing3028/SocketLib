package com.dydl.electronicdemo.module;

import com.dydl.socketlib.callback.OkResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lwx on 2018/4/8.
 */

public class Easy2Bean implements OkResponse {


    /**
     * 0 : 11111
     * 1 : 22222
     * 2 : 33333
     * 3 : 44444
     * 4 : [{"0":"333","1":"222","2":"111"},{"0":"222","1":"222","2":"111"},{"0":"111","1":"222","2":"111"},{"0":"000","1":"222","2":"111"}]
     * 5 : 343434
     * 6 : false
     * 7 : [{"0":"333","1":"222","2":"111"},{"0":"222","1":"222","2":"111"},{"0":"111","1":"222","2":"111"},{"0":"000","1":"222","2":"111"}]
     */

    @SerializedName("0")
    private String _$0;
    @SerializedName("1")
    private String _$1;
    @SerializedName("2")
    private String _$2;
    @SerializedName("3")
    private String _$3;
    @SerializedName("5")
    private String _$5;
    @SerializedName("6")
    private String _$6;
    @SerializedName("4")
    private List<_$4Bean> _$4;
    @SerializedName("7")
    private List<_$7Bean> _$7;

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

    public List<_$4Bean> get_$4() {
        return _$4;
    }

    public void set_$4(List<_$4Bean> _$4) {
        this._$4 = _$4;
    }

    public List<_$7Bean> get_$7() {
        return _$7;
    }

    public void set_$7(List<_$7Bean> _$7) {
        this._$7 = _$7;
    }

    public static class _$4Bean {
        /**
         * 0 : 333
         * 1 : 222
         * 2 : 111
         */

        @SerializedName("0")
        private String _$0;
        @SerializedName("1")
        private String _$1;
        @SerializedName("2")
        private String _$2;

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
    }

    public static class _$7Bean {
        /**
         * 0 : 333
         * 1 : 222
         * 2 : 111
         */

        @SerializedName("0")
        private String _$0;
        @SerializedName("1")
        private String _$1;
        @SerializedName("2")
        private String _$2;

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
    }
}
