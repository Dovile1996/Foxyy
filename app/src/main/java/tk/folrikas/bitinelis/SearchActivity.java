package tk.folrikas.bitinelis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends Activity {
    public static final String EXTRA_STORAGE_ITEM_ID = " tk.folrikas.bitinelis.STORAGE_ITEM_ID";

    private List<Produktas> produktasList;
    private ViewGroup produktasGroup;

    private View searchStatusView;
    private View searchFormView;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.searchStatusView = findViewById(R.id.search_status);
        this.searchFormView = findViewById(R.id.search_form);

        this.searchView = (SearchView) findViewById(R.id.search_view);
        this.searchView.setVisibility(View.GONE);

        Button mygtukas3 = (Button) findViewById(R.id.veikla);
        mygtukas3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(SearchActivity.this, NaujaPreke.class);

                // currentContext.startActivity(activityChangeIntent);
                // kazkas naujo

                SearchActivity.this.startActivity(activityChangeIntent);
            }
        });

        Button searchBtn = (Button) findViewById(R.id.search_button);
        searchBtn.setText(getString(R.string.search_saved_produktai));

        /*searchBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.font_size_extra_small));
        searchBtn.setTextColor(getResources().getColor(R.color.text_hint));*/

        searchBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                SearchActivity.this.searchView.setVisibility(View.VISIBLE);
                SearchActivity.this.searchView.onActionViewExpanded();
            }
        });

        AutoCompleteTextView searchText = (AutoCompleteTextView) this.searchView.findViewById(
                this.searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null));
        /*searchText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimensionPixelSize(R.dimen.font_size_extra_small));*/

        this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String location) {

                SearchActivity.this.searchView.clearFocus();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String location) {

                searchProduktai(location);

                return false;
            }
        });

    }

    private void updateProduktai() {

        this.produktasGroup = (ViewGroup) findViewById(R.id.saved_produktai_container);
        this.produktasGroup.removeAllViews();
/* TODO: padaryti call į db
        try {
           SearchActivity.this.produktasList = KITM.getStorage().getAllProduktai();
        } catch (StorageIOException e) {
            e.printStackTrace();
        }

        Produktas(String tipas, double kiekis, String uzsakymai, double kaina, String rusis, double turis, String ipakavimas) {*/
        /*List<MyType> myList = new ArrayList<MyType>();*/
        SearchActivity.this.produktasList = new ArrayList<Produktas>();
        Produktas cl = new Produktas("melyna",0.15,"privatūs",1.5,"raudona",1.20,"dovaninis");
        SearchActivity.this.produktasList.add(0,cl);
        for (Produktas produktas : SearchActivity.this.produktasList) {
            final View locationItem = getLayoutInflater().inflate(
                    R.layout.produktas_item, SearchActivity.this.produktasGroup, false);
            final TextView title = (TextView) locationItem.findViewById(R.id.title);
            title.setText(produktas.getRusis());
            /* TODO : papildyti produktas klasę id gaunant ją iš DB
            final Integer produktasStorageId = produktas.getStorageId();
             */
            title.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchActivity.this, NaujaPreke.class);
                   /*
                   TODO: kaip aukščiau ^^
                   intent.putExtra(SearchActivity.EXTRA_STORAGE_ITEM_ID, produktasStorageId);*/
                    startActivity(intent);

                }
            });
            this.produktasGroup.addView(locationItem);
        }
    }

    private void searchProduktai(String searchKey) {
        //showProgress(true);
        updateProduktai();
    }
}
