package com.jkb.supportfragment.demo.business.auth.account;

import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jkb.commonlib.base.ui.BaseDialogFragment;
import com.jkb.commonlib.base.ui.BaseFrameFragment;
import com.jkb.commonlib.config.AppConfig;
import com.jkb.commonlib.helper.AppLauncher;
import com.jkb.support.helper.SupportManager;
import com.jkb.supportfragment.demo.R;
import com.jkb.supportfragment.demo.business.auth.account.contract.AccountContract;
import com.jkb.supportfragment.demo.business.auth.account.presenter.AccountPresenter;
import com.jkb.supportfragment.demo.business.helper.RepertoryInject;
import com.jkb.supportfragment.demo.databinding.FrgAuthAccountBinding;
import com.jkb.supportfragment.demo.entity.auth.AccountEntity;
import com.jkb.supportfragment.demo.entity.auth.AreaCodeEntity;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

/**
 * 帐号
 * Created by yj on 2017/5/5.
 */
@Route(path = AppConfig.RouterPath.AUTH_ACCOUNT)
public class AccountFragment extends BaseFrameFragment<AccountPresenter, FrgAuthAccountBinding> implements
        AccountContract.View, View.OnClickListener {

    private TextInputEditText etAccount;

    @Override
    public int getRootViewId() {
        return R.layout.frg_auth_account;
    }

    @Override
    public void initView() {
        etAccount = (TextInputEditText) findViewById(R.id.account_et_account);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        getPresenter().start();
    }

    @Override
    public void initListener() {
        findViewById(R.id.account_continue).setOnClickListener(this);
        findViewById(R.id.account_areaCode).setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initPresenter() {
        new AccountPresenter(this, RepertoryInject.provideAccountDR(mContext));
    }

    @Override
    public void setPresenter(AccountContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setAccountEntity(AccountEntity entity) {
        getBinding().setAccount(entity);
    }

    @Override
    public void showAreaCode() {
        BaseDialogFragment fragment = AppLauncher.launchAreaCode(getBinding().getAccount().getAreaCodes());
        if (!SupportManager.isFragmentAdded(mParentFm, fragment)) {
            fragment.show(mParentFm, SupportManager.getFragmentTAG(fragment));
        }
    }

    @Override
    public void launchLogin(String account) {
        startFragment(AppLauncher.launchLogin(account));
    }

    @Override
    public void launchVerCode(String account) {
        startFragment(AppLauncher.launchVerCode(account, AppConfig.VerCodeType.REGISTER));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account_continue:
                getPresenter().onContinueClick();
                break;
            case R.id.account_areaCode:
                showAreaCode();
                break;
        }
    }

    /**
     * 地区编号被点击
     */
    @Subscriber(tag = AppConfig.EventBusTAG.AUTH_AREA_CODE_SELECTE)
    public void onAreaCodeSelected(Message message) {
        AreaCodeEntity entity = (AreaCodeEntity) message.obj;
        getBinding().getAccount().setAreaCode(entity.getCode());
    }

    /**
     * 接收到登录成功的消息通知
     */
    @Subscriber(tag = AppConfig.EventBusTAG.LOGIN_SUCCESS)
    public void receiveMessageLoginSuccess(Message message) {
        close();
    }
}
