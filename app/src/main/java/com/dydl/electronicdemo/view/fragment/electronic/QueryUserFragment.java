package com.dydl.electronicdemo.view.fragment.electronic;

import android.widget.Button;
import android.widget.EditText;

import com.dydl.electronicdemo.R;
import com.dydl.electronicdemo.utils.EmptyUtils;
import com.dydl.electronicdemo.view.activity.electronic.EleSignActivity;
import com.dydl.electronicdemo.view.fragment.base.BaseFragment;
import com.dydl.socketlib.OkSocket;
import com.dydl.socketlib.callback.OkCallBack;
import com.dydl.socketlib.model.OkXmlBean;
import com.dydl.socketlib.utils.ToastUtils;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;

/**
 * Created by lwx on 2018/3/16.
 */

public class QueryUserFragment extends BaseFragment {
    @BindView(R.id.customer_num)
    EditText customerNum;
    @BindView(R.id.customer_pwd)
    EditText customerPwd;
    @BindView(R.id.btn_query)
    Button btnQuery;

    @Override
    public void operation() {

        RxView.clicks(btnQuery).subscribe(o -> {

            if (EmptyUtils.checkEditTextEmtity(customerNum)) {
                ToastUtils.show(getActivity(), "请输入用户编号");
                return;
            }

            if (EmptyUtils.checkEditTextEmtity(customerPwd)) {
                ToastUtils.show(getActivity(), "请输入用户密码");
                return;
            }

            String user_name = EmptyUtils.getEditTextEmtity(customerNum);
            String user_pwd = EmptyUtils.getEditTextEmtity(customerPwd);
            String xmlStr = "<ORDER>" +
                    "<servicecode>0207123</servicecode>" +
                    "<source>18</source>" +
                    "<target>37101</target>" +
                    "<data><consNo>" + user_name + "</consNo>" +
                    "<busiTypeCode></busiTypeCode>" +
                    "<reduceCapMode></reduceCapMode>" +
                    "</data>" +
                    "</ORDER>";
            OkXmlBean bean = new OkXmlBean();
            bean.setTransCode("030016");//交易代码
            bean.setCompCode("");//渠道商编号
            bean.setTransTime("");//交易时间
            bean.setSelrialNo("");//交易流水号
            bean.setSpotCode("");//网点代码
            bean.setOperNo("");//操作员编号
            bean.setTerminalNo("");//终端编号
            bean.setOrgNo("");//供电单位编号(终端管理单位)
            bean.setConsNo("");//用户编号
            bean.setXml(xmlStr);//发给营销的Xml串

            OkSocket.sendMsgXml(bean, 60001,//不知道
                    new OkCallBack(getActivity(), "正在进行用电客户信息查询...") {

                    });

            ((EleSignActivity) getActivity())
                    .replaceTransferQuery();
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_query_user;
    }


}
