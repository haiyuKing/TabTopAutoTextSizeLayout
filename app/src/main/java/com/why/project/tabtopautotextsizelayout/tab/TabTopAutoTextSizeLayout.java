package com.why.project.tabtopautotextsizelayout.tab;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.why.project.tabtopautotextsizelayout.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by HaiyuKing
 * Used TabTopAutoLayout的变形运用：文字字号横向列表
 */

public class TabTopAutoTextSizeLayout extends LinearLayout {

	private Context mContext;
	//选项卡标题
	//CharSequence与String都能用于定义字符串，但CharSequence的值是可读可写序列，而String的值是只读序列。
	private CharSequence[] toptab_Titles = {"16"};

	//选项卡的各个选项的标题的集合：用于切换时改变文字颜色
	private List<TextView> topTab_titles = new ArrayList<TextView>();

	public TabTopAutoTextSizeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);

		mContext = context;

		List<CharSequence> toptab_titleList = new ArrayList<CharSequence>();
		toptab_titleList = Arrays.asList(toptab_Titles);
		//初始化view：创建多个view对象（引用tab_bottom_item文件），设置图片和文字，然后添加到这个自定义类的布局中
		initAddBottomTabItemView(toptab_titleList);
	}

	//初始化控件
	private void initAddBottomTabItemView(List<CharSequence> tabTitleList){

		int countChild = this.getChildCount();
		if(countChild > 0){
			this.removeAllViewsInLayout();//清空控件
			//将各个选项卡的各个选项的标题添加到集合中
			topTab_titles.clear();
		}

		for(int index=0;index<tabTitleList.size();index++){

			//设置要添加的子布局view的参数
			LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//			params.weight = 1;//在tab_item文件的根节点RelativeLayout中是无法添加的，而这个是必须要写上的，否则只会展现一个view
			params.gravity = Gravity.CENTER;

			final int finalIndex = index;

			//============引用选项卡的各个选项的布局文件=================
			View toptabitemView = LayoutInflater.from(mContext).inflate(R.layout.tab_top_auto_textsize_item, this, false);

			//===========选项卡的根布局==========
			RelativeLayout toptabLayout = (RelativeLayout) toptabitemView.findViewById(R.id.toptabLayout);

			//===========设置选项卡的文字==========
			final TextView top_title = (TextView) toptabitemView.findViewById(R.id.top_title);
			//设置选项卡的文字
			top_title.setText(tabTitleList.get(index));
			//===========设置选项卡控件的Tag(索引)==========用于后续的切换更改图片和文字
			top_title.setTag("tag"+index);

			//设置内边距【第一个不设置左边距，最后一个不设置右边距】
			int paddingPx = mContext.getResources().getDimensionPixelOffset(R.dimen.tab_top_auto_padding);
			if(index == 0){
				params.setMargins(0,0,paddingPx,0);
			}else if(index == tabTitleList.size() - 1){
				params.setMargins(paddingPx,0,0,0);
			}else{
				params.setMargins(paddingPx,0,paddingPx,0);
			}

			//添加选项卡各个选项的触发事件监听
			toptabLayout.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					//设置当前选项卡状态为选中状态
					//修改View的背景颜色
					setTabsDisplay(finalIndex);
					//添加点击事件
					if(textSizeItemSelectListener != null){
						//执行activity主类中的onBottomTabSelected方法
						textSizeItemSelectListener.onTextSizeItemSelected(finalIndex);
					}
				}
			});

			//把这个view添加到自定义的布局里面
			this.addView(toptabitemView,params);

			//将各个选项卡的各个选项的标题添加到集合中
			topTab_titles.add(top_title);
		}
	}

	/**
	 * 设置底部导航中图片显示状态和字体颜色
	 */
	public void setTabsDisplay(int checkedIndex) {

		int size = topTab_titles.size();

		for(int i=0;i<size;i++){
			TextView topTabTitle = topTab_titles.get(i);
			//设置选项卡状态为选中状态
			if(topTabTitle.getTag().equals("tag"+checkedIndex)){
				topTabTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
				//修改文字颜色
				topTabTitle.setTextColor(getResources().getColor(R.color.tab_text_selected_color));
			}else{
				topTabTitle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
				//修改文字颜色
				topTabTitle.setTextColor(getResources().getColor(R.color.tab_text_normal_color));
			}
		}
	}

	public TextView getTabsItem(int checkedIndex){
		TextView topTabTitle = topTab_titles.get(checkedIndex);
		return topTabTitle;
	}

	/**设置显示的选项卡集合*/
	public void setTabList(ArrayList<CharSequence> toptab_titleList){
		initAddBottomTabItemView(toptab_titleList);
	}

	private OnTextSizeItemSelectListener textSizeItemSelectListener;

	//自定义一个内部接口，用于监听选项卡选中的事件,用于获取选中的选项卡的下标值
	public interface OnTextSizeItemSelectListener{
		void onTextSizeItemSelected(int index);
	}

	public void setOnTopTabSelectedListener(OnTextSizeItemSelectListener textSizeItemSelectListener){
		this.textSizeItemSelectListener = textSizeItemSelectListener;
	}
}
