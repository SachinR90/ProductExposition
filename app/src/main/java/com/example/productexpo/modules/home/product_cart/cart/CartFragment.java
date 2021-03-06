package com.example.productexpo.modules.home.product_cart.cart;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.productexpo.R;
import com.example.productexpo.customviews.EmptyRecyclerView;
import com.example.productexpo.entities.Product;
import com.example.productexpo.modules.base.fragment.BaseFragment;
import com.example.productexpo.modules.home.product_cart.ProductCartView;

import java.util.List;

/**
 * Created on 9/17/2017.
 */

public class CartFragment extends BaseFragment implements CartView {
    private EmptyRecyclerView rvCart;
    private LinearLayout llHistoryEmptyView;
    private CartPresenter presenter;
    private AppCompatTextView tvTotalPrice;

    public static CartFragment newInstance() {
        Bundle args = new Bundle();
        CartFragment fragment = new CartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initialize the presenter
        presenter = new CartPresenterImpl(this);
    }

    @Override
    public int getResId() {
        return R.layout.fragment_cart;
    }

    @Override
    public void initializeUIComponents(View v) {
        rvCart = (EmptyRecyclerView) v.findViewById(R.id.rv_cart);
        llHistoryEmptyView = (LinearLayout) v.findViewById(R.id.ll_cart_empty_view);
        tvTotalPrice = (AppCompatTextView) v.findViewById(R.id.tv_total_price);
        presenter.handleEmptyRecyclerView(rvCart, llHistoryEmptyView);
        presenter.handlePriceText(tvTotalPrice);
    }

    @Override
    public void updateProductList(List<Product> listOfProducts) {
        presenter.updateProductList(listOfProducts);
    }

    @Override
    public void showProductGallery(Product product) {
        ProductCartView parentFragment = (ProductCartView) getParentFragment();
        parentFragment.loadGallery(product);
    }

    @Override
    public void switchToProductScreen() {
        ProductCartView parentFragment = (ProductCartView) getParentFragment();
        parentFragment.switchToProductTab();
    }

    @Override
    public void callVendor(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && presenter != null) {
            presenter.requestFocusOnEmptyView();
            presenter.refreshCartList();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (presenter!=null) {
            presenter.onConfigChanged(newConfig);
        }
    }
}
