package com.codingdojoangola.ui.split;

//:::::::::::::::: Android imports
import android.content.Context;
import android.content.res.TypedArray;

//:::::::::::::::: Import from third parties (com, junit, net, org)


//:::::::::::::::: Java and javax
import java.util.ArrayList;
import java.util.List;

//:::::::::::::::: Same project import
import com.codingdojoangola.R;
import com.codingdojoangola.models.split.ItemDrawer;

public class MainDrawer {

    //variable
    private Context context;
    private static MainDrawer instance;
    private List<ItemDrawer> menuDrawer;

    //********************************** CREATE THE INSTANCE ***************************************
    public static void initInstance(Context context){
        if(instance == null){
            instance = new MainDrawer(context);
        }
    }
    //********************************** RETURN THE INSTANCE ***************************************
    public static MainDrawer getInstance(){
        return instance;
    }

    //****************************************** CONSTRUCT *****************************************
    private MainDrawer(Context context) {
        this.context = context;
        menuDrawer = new ArrayList<>();
        InitMenuDrawer();
    }

    //*********************************** METHODS GET AND SET ***************************************
    private void InitMenuDrawer(){
        final String []choiceType = {
                ""+R.string.project,
                ""+R.string.member,
                ""+R.string.cda,
                ""+R.string.settings,
                ""+R.string.about,
                ""+R.string.exit
        };

        String [] splitMenu = context.getResources().getStringArray(R.array.splitMenu);
        TypedArray typeIcons = context.getResources().obtainTypedArray(R.array.drawer_icons);

        if(menuDrawer.size()==0) {
            for (int i = 0; i < splitMenu.length; ++i) {
                ItemDrawer item = new ItemDrawer();
                item.setNameType(splitMenu[i]);
                item.setChoiceType(choiceType[i]);
                item.setIcon(typeIcons.getResourceId(i, -1));

                menuDrawer.add(item);
            }
        }
    }

    //******************************** GET
    public List<ItemDrawer> getMainDrawer(){
        return menuDrawer;
    }

    //**********************************************************************************************
}
