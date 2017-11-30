package com.clickygame;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.clickygame.utils.OnSwipeTouchListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prashant.patel on 11/10/2017.
 */

public class ActListAnimation extends AppCompatActivity {

    // Animation
    Animation animFadein;
    private ImageView ivPhoto;
    LinearLayoutManager horizontalLayoutManagaer;
    SpeedyLinearLayoutManager speedyLinearLayoutManager;
    private RecyclerView vertical_recycler_view, horizontal_recycler_view;
    private ArrayList<ImageModel> horizontalList, verticalList;
    private HorizontalAdapter horizontalAdapter;
    private VerticalAdapter verticalAdapter;

    int selectedPos = 0;

    public class ImageModel {
        String id;
        int images;

        ImageModel(String id, int images) {
            this.id = id;
            this.images = images;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_list_animation);
        try {
            //vertical_recycler_view= (RecyclerView) findViewById(R.id.vertical_recycler_view);
            horizontal_recycler_view = (RecyclerView) findViewById(R.id.horizontal_recycler_view);
            ivPhoto = (ImageView) findViewById(R.id.ivPhoto);

            horizontalList = new ArrayList<>();
            horizontalList.add(new ImageModel("1", R.drawable.a));
            horizontalList.add(new ImageModel("2", R.drawable.b));
            horizontalList.add(new ImageModel("3", R.drawable.c));
            horizontalList.add(new ImageModel("4", R.drawable.d));
            horizontalList.add(new ImageModel("5", R.drawable.e));
            horizontalList.add(new ImageModel("6", R.drawable.f));
            horizontalList.add(new ImageModel("7", R.drawable.a));
            horizontalList.add(new ImageModel("8", R.drawable.h));
            horizontalList.add(new ImageModel("9", R.drawable.i));
            horizontalList.add(new ImageModel("10", R.drawable.c));

            horizontalList.add(new ImageModel("2", R.drawable.b));
            horizontalList.add(new ImageModel("3", R.drawable.c));
            horizontalList.add(new ImageModel("4", R.drawable.d));
            horizontalList.add(new ImageModel("5", R.drawable.e));
            horizontalList.add(new ImageModel("6", R.drawable.f));
            horizontalList.add(new ImageModel("7", R.drawable.a));
            horizontalList.add(new ImageModel("8", R.drawable.h));
            horizontalList.add(new ImageModel("9", R.drawable.i));
            horizontalList.add(new ImageModel("10", R.drawable.c));


        /*verticalList=new ArrayList<>();
        verticalList.add(new ImageModel("1",R.drawable.a));
        verticalList.add(new ImageModel("2",R.drawable.b));
        verticalList.add(new ImageModel("3",R.drawable.c));
        verticalList.add(new ImageModel("4",R.drawable.d));
        verticalList.add(new ImageModel("5",R.drawable.e));
        verticalList.add(new ImageModel("6",R.drawable.f));
        verticalList.add(new ImageModel("7",R.drawable.a));
        verticalList.add(new ImageModel("8",R.drawable.h));
        verticalList.add(new ImageModel("9",R.drawable.i));
        verticalList.add(new ImageModel("10",R.drawable.c));*/

            horizontalAdapter = new HorizontalAdapter(horizontalList);
       /* verticalAdapter=new VerticalAdapter(verticalList);*/


      /*  LinearLayoutManager verticalLayoutmanager
                = new LinearLayoutManager(ActListAnimation.this, LinearLayoutManager.VERTICAL, false);
        vertical_recycler_view.setLayoutManager(verticalLayoutmanager);
        */

//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        vertical_recycler_view.setLayoutManager(mLayoutManager);
            horizontalLayoutManagaer
                    = new LinearLayoutManager(ActListAnimation.this, LinearLayoutManager.HORIZONTAL, false);

            speedyLinearLayoutManager = new SpeedyLinearLayoutManager(ActListAnimation.this, LinearLayoutManager.HORIZONTAL, false);
            horizontal_recycler_view.setLayoutManager(speedyLinearLayoutManager);

            //111=vertical_recycler_view.setAdapter(verticalAdapter);
            horizontal_recycler_view.setAdapter(horizontalAdapter);


            ivPhoto.setOnTouchListener(new OnSwipeTouchListener(ActListAnimation.this) {
                public void onSwipeTop() {
                    App.showLog("Top");
                    // Toast.makeText(MyActivity.this, "top", Toast.LENGTH_SHORT).show();
                }

                public void onSwipeRight() {
                    try {
                        App.showLog("Right==" + selectedPos);
                        //Toast.makeText(MyActivity.this, "right", Toast.LENGTH_SHORT).show();


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    //horizontal_recycler_view.getLayoutManager().scrollToPosition(speedyLinearLayoutManager.findFirstVisibleItemPosition() -1);
                                    horizontal_recycler_view.smoothScrollToPosition(speedyLinearLayoutManager.findFirstVisibleItemPosition() - 1);
                                    if (horizontalList != null && horizontalList.size() >= speedyLinearLayoutManager.findFirstVisibleItemPosition() - 1) {
                                        ivPhoto.setImageResource(horizontalList.get(speedyLinearLayoutManager.findFirstVisibleItemPosition() - 1).images);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                public void onSwipeLeft() {
                    try {
                        App.showLog("Left==" + selectedPos);
                        //Toast.makeText(MyActivity.this, "left", Toast.LENGTH_SHORT).show();
                        //ivPhoto.setVisibility(View.GONE);
                        ivPhoto.setImageResource(0);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    //horizontal_recycler_view.getLayoutManager().scrollToPosition(speedyLinearLayoutManager.findLastVisibleItemPosition() + 1);
                                    horizontal_recycler_view.smoothScrollToPosition(speedyLinearLayoutManager.findLastVisibleItemPosition() + 1);

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {

                                                if (horizontalList != null && horizontalList.size() >= speedyLinearLayoutManager.findLastVisibleItemPosition()) {
                                                    //ivPhoto.setVisibility(View.VISIBLE);
                                                    ivPhoto.setImageResource(horizontalList.get(speedyLinearLayoutManager.findLastVisibleItemPosition()).images);

                                                    // Load the animation like this
                                                    // load the animation
                                                    animFadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);

// Start the animation like this
                                                    ivPhoto.startAnimation(animFadein);

                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }, 800);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                public void onSwipeBottom() {
                    App.showLog("Bottom");
                    //Toast.makeText(MyActivity.this, "bottom", Toast.LENGTH_SHORT).show();

                }

            });


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {

                        if (horizontalList != null && speedyLinearLayoutManager !=null  &&horizontalList.size() >= speedyLinearLayoutManager.findLastVisibleItemPosition()) {
                            //ivPhoto.setVisibility(View.VISIBLE);
                            ivPhoto.setImageResource(horizontalList.get(speedyLinearLayoutManager.findLastVisibleItemPosition()).images);

                            // Load the animation like this
                            // load the animation
                            animFadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);

// Start the animation like this
                            ivPhoto.startAnimation(animFadein);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, 800);

            ivPhoto.setImageResource(0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

        private List<ImageModel> horizontalList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView txtView;
            public ImageView ivPhoto;

            public MyViewHolder(View view) {
                super(view);
                txtView = (TextView) view.findViewById(R.id.tvTitle);
                ivPhoto = (ImageView) view.findViewById(R.id.ivPhoto);


            }
        }

        private void setBW(ImageView iv) {

            float brightness = 10; // change values to suite your need

            float[] colorMatrix = {
                    0.33f, 0.33f, 0.33f, 0, brightness,
                    0.33f, 0.33f, 0.33f, 0, brightness,
                    0.33f, 0.33f, 0.33f, 0, brightness,
                    0, 0, 0, 1, 0
            };

            ColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
            iv.setColorFilter(colorFilter);
        }


        public HorizontalAdapter(List<ImageModel> horizontalList) {
            this.horizontalList = horizontalList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.horizontal_item_view, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {


            App.showLog("=====selectedPos===" + selectedPos);

            holder.txtView.setText(position + " -- " + horizontalList.get(position).id);

            holder.ivPhoto.setImageResource(horizontalList.get(position).images);
            setBW(holder.ivPhoto);

            holder.txtView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(ActListAnimation.this,holder.txtView.getText().toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return horizontalList.size();
        }
    }


    public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.MyViewHolder> {

        private List<ImageModel> verticalList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView txtView;
            public ImageView ivPhoto;

            public MyViewHolder(View view) {
                super(view);
                txtView = (TextView) view.findViewById(R.id.tvTitle);
                ivPhoto = (ImageView) view.findViewById(R.id.ivPhoto);

            }
        }

        private void setBW(ImageView iv) {

            float brightness = 10; // change values to suite your need

            float[] colorMatrix = {
                    0.33f, 0.33f, 0.33f, 0, brightness,
                    0.33f, 0.33f, 0.33f, 0, brightness,
                    0.33f, 0.33f, 0.33f, 0, brightness,
                    0, 0, 0, 1, 0
            };

            ColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
            iv.setColorFilter(colorFilter);
        }

        public VerticalAdapter(List<ImageModel> verticalList) {
            this.verticalList = verticalList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.vertical_item_view, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.txtView.setText(verticalList.get(position).id);
            holder.ivPhoto.setImageResource(verticalList.get(position).images);
            setBW(holder.ivPhoto);
            holder.txtView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //   Toast.makeText(ActListAnimation.this,holder.txtView.getText().toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return verticalList.size();
        }
    }


    public class SpeedyLinearLayoutManager extends LinearLayoutManager {

        private static final float MILLISECONDS_PER_INCH = 1500f; //default is 25f (bigger = slower)

        public SpeedyLinearLayoutManager(Context context) {
            super(context);
        }

        public SpeedyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public SpeedyLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {

            try {
                final LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {

                    @Override
                    public PointF computeScrollVectorForPosition(int targetPosition) {
                        return SpeedyLinearLayoutManager.this.computeScrollVectorForPosition(targetPosition);
                    }

                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
                    }
                };

                linearSmoothScroller.setTargetPosition(position);
                startSmoothScroll(linearSmoothScroller);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class LinearLayoutManagerWithSmoothScroller extends LinearLayoutManager {

        public LinearLayoutManagerWithSmoothScroller(Context context) {
            super(context, VERTICAL, false);
        }

        public LinearLayoutManagerWithSmoothScroller(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state,
                                           int position) {
            RecyclerView.SmoothScroller smoothScroller = new TopSnappedSmoothScroller(recyclerView.getContext());
            smoothScroller.setTargetPosition(position);
            startSmoothScroll(smoothScroller);
        }

        private class TopSnappedSmoothScroller extends LinearSmoothScroller {
            public TopSnappedSmoothScroller(Context context) {
                super(context);

            }

            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return LinearLayoutManagerWithSmoothScroller.this
                        .computeScrollVectorForPosition(targetPosition);
            }

            @Override
            protected int getVerticalSnapPreference() {
                return SNAP_TO_START;
            }
        }
    }
}