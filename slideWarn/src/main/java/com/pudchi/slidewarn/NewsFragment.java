package com.pudchi.slidewarn;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;

public class NewsFragment extends Fragment{
	
public static final String PAGE = "PAGE";
    final String[] coupon_type = {"便利商店","量販店"};
    final String n1_empty = "沒有新的天災訊息!";
    final String n2_empty = "沒有新的天氣訊息!";
    String n1 = "";
	String n1_link = "";
	String n2 = "";
	String n2_link = "";
	TextView refreshstate;
	EditText news1;
	EditText news2;
	int flag = 0;
    android.os.Handler getHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1001:
                    String str = msg.getData().getString("Result");
                    refreshstate.setText(str);
                    if (n1_link.length() < 10 && n2_link.length() < 10) {
                        news1.setText(n1_empty);
                        news2.setText(n2_empty);
                    } else if (n1_link.length() > 10 && n2_link.length() < 10) {
                        news1.setText(n1);
                        news2.setText(n2_empty);
                    } else if (n1_link.length() < 10 && n2_link.length() > 10) {
                        news1.setText(n1_empty);
                        news2.setText(n2);
                    } else if (n1_link.length() > 10 && n2_link.length() > 10) {
                        news1.setText(n1);
                        news2.setText(n2);
                    }
                    break;

                case 2000:
                    String str_nw = msg.getData().getString("Result");
                    refreshstate.setText(str_nw);

                    news1.setText("請檢查網路連線");
                    news2.setText("目前無法更新天氣訊息");


                default:
                    break;
            }
        }
    };
    private int mPage;

	public static NewsFragment newIns(int page)
	{
		Bundle args = new Bundle();
		args.putInt(PAGE, page);
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
	}

	public void Parsing() throws Exception
	{
		URL typ = new URL("http://www.cwb.gov.tw/rss/Data/cwb_warning.xml");
		URL wea = new URL("http://www.cwb.gov.tw/rss/forecast/36_08.xml");
		Document typdoc = Jsoup.parse(typ, 2000);
		Document weather = Jsoup.parse(wea, 2000);

		Elements typ_title = typdoc.select("title");
		Elements typ_weblink = typdoc.select("link");

		Elements wea_title = weather.select("title");
		Elements wea_weblink = weather.select("link");

		n1 = typ_title.get(1).text();
		n1_link = typ_weblink.get(1).text();
		n2 = wea_title.get(2).text();
		n2_link = wea_weblink.get(2).text();


		if (n1_link.length() < 5)
		{flag = 1;}

        if(n2_link.length() < 5)
        {
            flag = 2;
        }

		/*news1.setText(n1);
		news2.setText(n2);*/


	}

	private void getdata()
	{
		Runnable r = new Runnable() {
			@Override
			public void run()
			{
				String result = "";
				Message msg = new Message();
				msg.what = 1001;
				Bundle data = new Bundle();


				try {
                    if (is_Connecting_to_internet_news()) {
                        Parsing();
                    }

				} catch (Exception e) {
					e.printStackTrace();
				}

                if(n1_link.length()>5 && n2_link.length()<5)
                {
                    result = "刷新成功!";
                }
                else if (n1_link.length()<5 && n2_link.length()>5)
                {
                    result = "刷新成功!";
                }
                else if(n1_link.length()<5 && n2_link.length()<5)
                {
                    if (is_Connecting_to_internet_news()) {
                        result = "沒有新的訊息!";
                    } else {
                        result = "網路連線錯誤!";
                        msg.what = 2000;

                    }
                }
                else if(n1_link.length()>5 && n2_link.length()>5)
                {
                    result = "刷新成功!";
                }

				data.putString("Result", result);
				msg.setData(data);
				getHandler.sendMessage(msg);


			}
		};
		new Thread(r).start();
	}

	public void openlink(String inURL)
	{

		Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(inURL));

		startActivity(browse);

	}

    public boolean is_Connecting_to_internet_news() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;

    }

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mPage = getArguments().getInt(PAGE);
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub


		View view = inflater.inflate(R.layout.fragment_news, container, false);
		
		TextView tvnew = (TextView) view.findViewById(R.id.tvnew);
		TextView weather = (TextView) view.findViewById(R.id.weathertext);
		ImageButton refresh = (ImageButton) view.findViewById(R.id.refresh);
		this.refreshstate = (TextView) view.findViewById(R.id.reflash);
		news1 = (EditText) view.findViewById(R.id.news1);
		news2 = (EditText) view.findViewById(R.id.news2);
		Button coupon = (Button) view.findViewById(R.id.coupon);
		Button airpage = (Button) view.findViewById(R.id.airpagebutton);


		refresh.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {

				getdata();
                /*if(n1_link.length() < 10 && n2_link.length() < 10)
                {
                    news1.setText(n1_empty);
                    news2.setText(n2_empty);
                }
                else if(n1_link.length() > 10 && n2_link.length() < 10)
                {
                    news1.setText(n1);
                    news2.setText(n2_empty);
                }
                else if(n1_link.length() < 10 && n2_link.length() > 10)
                {
                    news1.setText(n1_empty);
                    news2.setText(n2);
                }
                else if(n1_link.length() > 10 && n2_link.length() > 10)
                {
                    news1.setText(n1);
                    news2.setText(n2);
                }*/

			}
		});



			news1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
                    if(n1_link.length() < 10) {
                        news1.setClickable(false);
                    }
                    else
                    {
                        openlink(n1_link);
                    }
				}
			});

			news2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
                    if(n2_link.length() < 10) {
                        news2.setClickable(false);
                    } else
                    {
                        openlink(n2_link);
                    }
				}
			});


			coupon.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

                    AlertDialog.Builder choose_coupon_type = new AlertDialog.Builder(getContext(), R.style.dialog);
                    choose_coupon_type.setIcon(R.drawable.store_black_48dp);
                    choose_coupon_type.setTitle("選擇優惠商店類型");
                    choose_coupon_type.setItems(coupon_type, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                Intent tostore = new Intent();
                                tostore.setClass(getContext(), News_coupon_store.class);
                                startActivity(tostore);

                            } else if (which == 1) {
                                Intent tomall = new Intent();
                                tomall.setClass(getContext(), News_coupon_mall.class);
                                startActivity(tomall);
                            }
                        }
                    });
                    choose_coupon_type.show();
				}
			});


		airpage.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent iair = new Intent();
				iair.setClass(getActivity(), News_air.class);
				startActivity(iair);
			}
		});



		return view;
	}

	
	

	

}
