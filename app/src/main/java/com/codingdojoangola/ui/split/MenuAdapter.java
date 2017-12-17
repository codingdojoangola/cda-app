package com.codingdojoangola.ui.split;

//:::::::::::::::: Android imports
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//:::::::::::::::: Import from third parties (com, junit, net, org)

//:::::::::::::::: Java and javax
import java.util.List;

//:::::::::::::::: Same project import
import com.codingdojoangola.R;
import com.codingdojoangola.models.split.ItemDrawer;

public class MenuAdapter extends BaseAdapter{

    private Context context;                        //context
    private List<ItemDrawer> itemDrawers;           //data source of the list adapter

    private int selectedPosition;
    private int colorBackground, colorBackgroundDefault;

    //************************************ CONSTRUCTOR *********************************************
    public MenuAdapter(Context context, List<ItemDrawer> itemDrawers){
        this.context = context;
        this.itemDrawers = itemDrawers;

        selectedPosition = 0;
        colorBackground = ContextCompat.getColor(context, R.color.Grey);
        colorBackgroundDefault = ContextCompat.getColor(context,R.color.colorPrimaryDark);

    }

    //*************************************
    @Override
    public int getCount() {
        return itemDrawers.size();
    }

    @Override
    public Object getItem(int position) {
        return itemDrawers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.item_split_menu, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        // get current item to be displayed
        ItemDrawer itemDrawer = (ItemDrawer) getItem(position);

        //sets the text for icon and item name from the current item object
        viewHolder.icon.setImageResource(itemDrawer.getIcon());
        viewHolder.itemName.setText(itemDrawer.getNameType());

        //Select Menu background
        if (position == selectedPosition) {
            //viewHolder.backgroundMenu.setBackgroundColor(colorBackground);
            viewHolder.itemName.setTextColor(colorBackgroundDefault);

            viewHolder.icon.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        }else{
            //viewHolder.backgroundMenu.setBackgroundColor(colorBackgroundDefault);
            viewHolder.itemName.setTextColor(colorBackground);

            viewHolder.icon.setColorFilter(ContextCompat.getColor(context, R.color.Grey));
        }


        // returns the view for the current row
        return convertView;
    }

    //:::::::::::::::::::::::
    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
    }
    //**********************************************************************************************
    private class ViewHolder {
        LinearLayout backgroundMenu;
        ImageView icon;
        TextView itemName;

        private ViewHolder(View view){
            icon = view.findViewById(R.id.image_item_split_menu);
            itemName = view.findViewById(R.id.text_item_split_menu_name);
            backgroundMenu = view.findViewById(R.id.name_titles_split);
        }
    }
    //**********************************************************************************************
}
