package com.sk.chaldal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sk.chaldal.Login_Activity.Mobile_Login_Activity;
import com.sk.chaldal.R;
import com.sk.chaldal.adapter.Expendable_listAdapter;
import com.sk.chaldal.adapter.ProductAdapter;
import com.sk.chaldal.model.HeaderDataModel;
import com.sk.chaldal.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ConstraintLayout constraintLayout;
    private TextView tv_login;

    private static final String TAG = "mainActivity";
    // list view
    private ExpandableListView expListView;
    private RecyclerView rv_productList;

    // adapters of list views
    private ProductAdapter productAdapter;
    private Expendable_listAdapter expListAdapter;

    // arraylists and hashmaps
    private List<HeaderDataModel> listDataHeader;
    private HashMap<String, List<String>> listHash;
    private ArrayList<Product> productArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init(toolbar);
    }

    private void init(Toolbar toolbar) {
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        tv_login = (TextView) findViewById(R.id.tv_login);
        setNavigationViewListner();

        //initializing arraylist and filling data to lists
        initData();

        //navigationbar list view
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        expListAdapter = new Expendable_listAdapter(this, listDataHeader, listHash);
        expListView.setAdapter(expListAdapter);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            tv_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToLoginPage();
                }
            });
        }

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //Nothing here ever fires
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                Toast.makeText(MainActivity.this, "parent " + groupPosition + "\nchild " + childPosition, Toast.LENGTH_SHORT).show();
                switch (groupPosition) {
                    case 1: //FOOD
                        switch (childPosition) {
                            case 0:
                                productArrayList.clear();
                                getFoodData();
                                productAdapter.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, "running this in child 0?", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                productArrayList.clear();
                                getGrocieryData();
                                productAdapter.notifyDataSetChanged();
                                break;
                        }
                        break;
                    case 2: //Home & cleaning
                        Toast.makeText(MainActivity.this, "no data found", Toast.LENGTH_SHORT).show();
                        break;
                }

                return true;
            }
        });

        productListRecyclerView();
    }

    private void goToLoginPage() {
        Intent intent = new Intent(this, Mobile_Login_Activity.class);
        startActivity(intent);
    }

    private void productListRecyclerView() {
        //product list view
        rv_productList = (RecyclerView) findViewById(R.id.rv_productList);
        productAdapter = new ProductAdapter(getApplicationContext(), productArrayList);
        rv_productList.setAdapter(productAdapter);
        rv_productList.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void initData() {
        listDataHeader = new ArrayList<>();
        HeaderDataModel headerDataModel = new HeaderDataModel();
        listHash = new HashMap<>();

        headerDataModel = new HeaderDataModel("POPULAR", R.drawable.tara);
        listDataHeader.add(headerDataModel);
        headerDataModel = new HeaderDataModel("FOOD", R.drawable.food);
        listDataHeader.add(headerDataModel);
        headerDataModel = new HeaderDataModel("Home & cleaning", R.drawable.spary);
        listDataHeader.add(headerDataModel);
        headerDataModel = new HeaderDataModel("Office producrs", R.drawable.offfice);
        listDataHeader.add(headerDataModel);
        headerDataModel = new HeaderDataModel("Baby Care", R.drawable.toy);
        listDataHeader.add(headerDataModel);
        headerDataModel = new HeaderDataModel("Beauty & Health", R.drawable.beauty);
        listDataHeader.add(headerDataModel);
        headerDataModel = new HeaderDataModel("Home Appliance", R.drawable.appliences);
        listDataHeader.add(headerDataModel);

        List<String> popular = new ArrayList<>();


        List<String> Food = new ArrayList<>();
        Food.add("Fruts & vegetables");
        Food.add("Breakfast");
        Food.add("Bevarages");
        Food.add("Meat& fish ");
        Food.add("Snakes");
        Food.add("Dairy");
        Food.add("Bread & Bekary");
        Food.add("Cooking ");

        List<String> Home = new ArrayList<>();
        Home.add("AIR FRESHENERS");
        Home.add("DISH DETERGENTS");
        Home.add("CLEANING SUPPLIES");
        Home.add("KITCHEN ACCESSORIES ");
        Home.add("LAUNDRY");
        Home.add("NAPKINS & PAPER PRODUCTS");
        Home.add("PEST CONTROL");
        Home.add("SHOE CARE ");


        List<String> uwp = new ArrayList<>();
        uwp.add("BATTERIES");
        uwp.add("WRITING & DRAWING");
        uwp.add("ORGANIZING");
        uwp.add("PRINTING ");

        List<String> Baby = new ArrayList<>();
        Baby.add("DIAPERS & WIPES");
        Baby.add("FOODING");
        Baby.add("BATH & SKINCARE");


        List<String> beauty = new ArrayList<>();
        beauty.add("HEALTH CARE");
        beauty.add("PERSONAL CARE");


        listHash.put(listDataHeader.get(0).getHeaderName(), popular);
        listHash.put(listDataHeader.get(1).getHeaderName(), Food);
        listHash.put(listDataHeader.get(2).getHeaderName(), Home);
        listHash.put(listDataHeader.get(3).getHeaderName(), Baby);
        listHash.put(listDataHeader.get(4).getHeaderName(), uwp);
        listHash.put(listDataHeader.get(5).getHeaderName(), beauty);

        getData();

    }

    private void getData() {
        Log.e(TAG, "getData: in mainactivity called");
        Product product = new Product();
        Product product1 = new Product("Sugar(This is sugar,hese  balance protein", "12kg", "$34", "$441", R.drawable.suger, "Most women should choose 3 selections of protein," +
                " carbohydrates and fats for each meal. " +
                "Most men should choose 4 selections of protein, " +
                "carbohydrates and fat for each meal. Then choose 1" +
                " of each for mid-afternoon and pre-bedtime snacks.");
        productArrayList.add(product1);
        product = new Product("Sugar(This is sugarhese food blocks  balance protein", "12kg", "$34", "$441", R.drawable.suger, "Most women should choose 3 selections of protein," +
                " carbohydrates and fats for each meal. " +
                "Most men should choose 4 selections of protein," +
                " carbohydrates and fat for each meal." +
                " Then choose 1 of each for mid-afternoon and pre-bedtime snacks");
        productArrayList.add(product);
        product = new Product("Moyda(This is sugar.hese food blocks  protein", "12kg", "$34", "$442", R.drawable.a2, "Most women should choose 3 selections of protein," +
                " carbohydrates and fats for each meal. " +
                "Most men should choose 4 selections of protein," +
                " carbohydrates and fat for each meal." +
                " Then choose 1 of each for mid-afternoon and pre-bedtime snacks");
        productArrayList.add(product);
        product = new Product("Sugi(It is suji.hese food blocks provide protein", "12kg", "$34", "$443", R.drawable.suji, "Most women should choose 3 selections of protein," +
                " carbohydrates and fats for each meal. " +
                "Most men should choose 4 selections of protein," +
                " carbohydrates and fat for each meal." +
                " Then choose 1 of each for mid-afternoon and pre-bedtime snacks");
        productArrayList.add(product);
        product = new Product("Onion(hese food blocks provide te protein", "12kg", "$34", "$444", R.drawable.onion, "Most women should choose 3 selections of protein," +
                " carbohydrates and fats for each meal. " +
                "Most men should choose 4 selections of protein," +
                " carbohydrates and fat for each meal." +
                " Then choose 1 of each for mid-afternoon and pre-bedtime snacks");
        productArrayList.add(product);
        product = new Product("Toilet Tishu,hese food blocks provide the most ", "2kg", "$34", "$445", R.drawable.tisu, "Most women should choose 3 selections of protein," +
                " carbohydrates and fats for each meal. " +
                "Most men should choose 4 selections of protein," +
                " carbohydrates and fat for each meal." +
                " Then choose 1 of each for mid-afternoon and pre-bedtime snacks");
        productArrayList.add(product);


        product = new Product("sugar(hese food blocks provide the most ", "12kg", "$34", "$441", R.drawable.suger, "Most women should choose 3 selections of protein," +
                " carbohydrates and fats for each meal. " +
                "Most men should choose 4 selections of protein," +
                " carbohydrates and fat for each meal." +
                " Then choose 1 of each for mid-afternoon and pre-bedtime snacks");
        productArrayList.add(product);
        product = new Product("moyda(hese food blocks provide the most precise ", "12kg", "$34", "$442", R.drawable.a2, "Most women should choose 3 selections of protein," +
                " carbohydrates and fats for each meal. " +
                "Most men should choose 4 selections of protein," +
                " carbohydrates and fat for each meal." +
                " Then choose 1 of each for mid-afternoon and pre-bedtime snacks");
        productArrayList.add(product);
        product = new Product("sugi(hese food blocks provide the most ", "12kg", "$34", "$443", R.drawable.suji, "Most women should choose 3 selections of protein," +
                " carbohydrates and fats for each meal. " +
                "Most men should choose 4 selections of protein," +
                " carbohydrates and fat for each meal." +
                " Then choose 1 of each for mid-afternoon and pre-bedtime snacks");
        productArrayList.add(product);
        product = new Product("Onion(hese food blocks  to balance protein", "12kg", "$34", "$444", R.drawable.onion, "Most women should choose 3 selections of protein," +
                " carbohydrates and fats for each meal. " +
                "Most men should choose 4 selections of protein," +
                " carbohydrates and fat for each meal." +
                " Then choose 1 of each for mid-afternoon and pre-bedtime snacks");
        productArrayList.add(product);
        product = new Product("Toilet Tishu,hese food blocks provide the most ", "2kg", "$34", "$445", R.drawable.tisu, "Most women should choose 3 selections of protein," +
                " carbohydrates and fats for each meal. " +
                "Most men should choose 4 selections of protein," +
                " carbohydrates and fat for each meal." +
                " Then choose 1 of each for mid-afternoon and pre-bedtime snacks");
        productArrayList.add(product);

        // product = new Product();
        product = new Product("sugar(hese food blocks provide the most ", "$34", "12kg", "$441", R.drawable.suger, "Most women should choose 3 selections of protein," +
                " carbohydrates and fats for each meal. " +
                "Most men should choose 4 selections of protein," +
                " carbohydrates and fat for each meal." +
                " Then choose 1 of each for mid-afternoon and pre-bedtime snacks");
        productArrayList.add(product);
        product = new Product("moyda(hese food blocks o balance protein", "$34", "12kg", "$442", R.drawable.a2, "Most women should choose 3 selections of protein," +
                " carbohydrates and fats for each meal. " +
                "Most men should choose 4 selections of protein," +
                " carbohydrates and fat for each meal." +
                " Then choose 1 of each for mid-afternoon and pre-bedtime snacks");
        productArrayList.add(product);
        product = new Product("sugi(hese food blocks provide the most ", "12kg", "$34", "$443", R.drawable.suji, "Most women should choose 3 selections of protein," +
                " carbohydrates and fats for each meal. " +
                "Most men should choose 4 selections of protein," +
                " carbohydrates and fat for each meal." +
                " Then choose 1 of each for mid-afternoon and pre-bedtime snacks");
        productArrayList.add(product);
        product = new Product("Onion(hese food blocks provide  balance protein", "12kg", "$34", "$444", R.drawable.onion);
        productArrayList.add(product);
        product = new Product("Toilet Tishu,hese food blocks provide the most ", "2kg", "$34", "$445", R.drawable.tisu, "Most women should choose 3 selections of protein," +
                " carbohydrates and fats for each meal. " +
                "Most men should choose 4 selections of protein," +
                " carbohydrates and fat for each meal." +
                " Then choose 1 of each for mid-afternoon and pre-bedtime snacks");
        productArrayList.add(product);

        //  product = new Product();
        product = new Product("sugar(hese food blocks provide the ", "12kg", "$34", "$441", R.drawable.suger, "Most women should choose 3 selections of protein," +
                " carbohydrates and fats for each meal. " +
                "Most men should choose 4 selections of protein," +
                " carbohydrates and fat for each meal." +
                " Then choose 1 of each for mid-afternoon and pre-bedtime snacks");
        productArrayList.add(product);
        product = new Product("moyda(hese food blocks provide the most ", "12kg", "$34", "$442", R.drawable.a2, "Most women should choose 3 selections of protein," +
                " carbohydrates and fats for each meal. " +
                "Most men should choose 4 selections of protein," +
                " carbohydrates and fat for each meal." +
                " Then choose 1 of each for mid-afternoon and pre-bedtime snacks");
        productArrayList.add(product);
        product = new Product("sugi(It is suji...", "12kg", "$34", "$443", R.drawable.suji);
        productArrayList.add(product);
        product = new Product("Onion(hese food blocks provide the most ", "12kg", "$34", "$444", R.drawable.onion);
        productArrayList.add(product);
        product = new Product("Toilet Tishu.hese food blocks provide the most ", "2kg", "$34", "$445", R.drawable.tisu);
        productArrayList.add(product);

        Log.e(TAG, "getData: " + productArrayList.size() + " data available");
    }


    private void getFoodData() {
        Product product = new Product();
        product = new Product("Cheaken Egg.hese food blocks provide the most ", "12", "$34", "$443", R.drawable.egg, "Most women should choose 3 selections of protein," +
                " carbohydrates and fats for each meal. " +
                "Most men should choose 4 selections of protein," +
                " carbohydrates and fat for each meal." +
                " Then choose 1 of each for mid-afternoon and pre-bedtime snacks");
        productArrayList.add(product);
        product = new Product("Vim bar.hese food blocks provide the most ", "12", "$34", "$444", R.drawable.vim, "Most women should choose 3 selections of protein," +
                " carbohydrates and fats for each meal. " +
                "Most men should choose 4 selections of protein," +
                " carbohydrates and fat for each meal." +
                " Then choose 1 of each for mid-afternoon and pre-bedtime snacks");
        productArrayList.add(product);
        product = new Product("Moshur dal.hese food blocks provide the most", "12", "$34", "$445", R.drawable.dal, "Most women should choose 3 selections of protein," +
                " carbohydrates and fats for each meal. " +
                "Most men should choose 4 selections of protein," +
                " carbohydrates and fat for each meal." +
                " Then choose 1 of each for mid-afternoon and pre-bedtime snacks");
        productArrayList.add(product);

    }

    private void getGrocieryData() {
        Product product = new Product();
        product = new Product("Danish condest milk.hese food blocks balance protein", "12", "$34", "$443", R.drawable.milk, "Most women should choose 3 selections of protein," +
                " carbohydrates and fats for each meal. " +
                "Most men should choose 4 selections of protein," +
                " carbohydrates and fat for each meal." +
                " Then choose 1 of each for mid-afternoon and pre-bedtime snacks");
        productArrayList.add(product);
        product = new Product("Miniket Rice.hese food blocks provide the most precise ", "12", "$34", "$444", R.drawable.ca, "Most women should choose 3 selections of protein," +
                " carbohydrates and fats for each meal. " +
                "Most men should choose 4 selections of protein," +
                " carbohydrates and fat for each meal." +
                " Then choose 1 of each for mid-afternoon and pre-bedtime snacks");
        productArrayList.add(product);
        product = new Product("Ispahani Mirjapur Tea Best Leaf.hese food blocks provide the most precise.", "12", "$34", "$445", R.drawable.j);
        productArrayList.add(product);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
//        searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
        return super.onCreateOptionsMenu(menu);
    }


    public void login(View view) {
        Toast.makeText(this, "dsfsdfsd", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, Mobile_Login_Activity.class);
        startActivity(i);
    }

    private void setNavigationViewListner() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.tv_login: {
                //do somthing
                Toast.makeText(this, "login activity", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        //close navigation drawer
        return true;
    }

//    option menu setting on top right of screen
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        return id == R.id.action_settings || super.onOptionsItemSelected(item);
//    }
}
