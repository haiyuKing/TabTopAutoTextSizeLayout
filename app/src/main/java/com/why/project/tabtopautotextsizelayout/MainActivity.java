package com.why.project.tabtopautotextsizelayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.why.project.tabtopautotextsizelayout.tab.TabTopAutoTextSizeLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	private Button mChangeBtn;
	private TextView mSelectedTextSizeTV;//选中的文字大小的展现view

	private static String[] fontSizes = {"10","12","14","16","18","20","22","24","26","30","36","42"};
	private String fontSizeUnit = "px";
	private TabTopAutoTextSizeLayout mTextSizeLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();
		initDatas();
		initEvents();
	}

	private void initViews() {
		mChangeBtn = (Button) findViewById(R.id.btn_change);
		mSelectedTextSizeTV = (TextView) findViewById(R.id.text_size_view);

		mTextSizeLayout = (TabTopAutoTextSizeLayout) findViewById(R.id.tab_textsizeLayout);
	}

	private void initDatas() {
		ArrayList<CharSequence> textSizeArray = new ArrayList<CharSequence>();//用于展现
		for(int i=0;i<fontSizes.length;i++){
			textSizeArray.add(fontSizes[i]);
		}

		mTextSizeLayout.setTabList(textSizeArray);
	}

	private void initEvents() {
		//模拟更改字体
		mChangeBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				int selectedTextSize = 24;
				mSelectedTextSizeTV.setText(selectedTextSize + fontSizeUnit);
				int selectedIndex = 0;
				for(int i = 0;i<fontSizes.length;i++){
					if(Integer.parseInt(fontSizes[i]) == selectedTextSize){
						selectedIndex = i;
						break;
					}
				}
				mTextSizeLayout.setTabsDisplay(selectedIndex);
			}
		});
		//字号的点击事件
		mTextSizeLayout.setOnTopTabSelectedListener(new TabTopAutoTextSizeLayout.OnTextSizeItemSelectListener() {
			@Override
			public void onTextSizeItemSelected(int index) {
				mSelectedTextSizeTV.setText(fontSizes[index] + fontSizeUnit);//带单位
			}
		});
	}
}
