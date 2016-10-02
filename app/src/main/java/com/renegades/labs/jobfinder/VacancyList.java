package com.renegades.labs.jobfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.glomadrian.loadingballs.BallView;
import com.renegades.labs.jobfinder.model.DOUStrategy;
import com.renegades.labs.jobfinder.model.HHStrategy;
import com.renegades.labs.jobfinder.model.Model;
import com.renegades.labs.jobfinder.model.Provider;
import com.renegades.labs.jobfinder.model.WorkStrategy;
import com.renegades.labs.jobfinder.vo.Vacancy;

import java.util.ArrayList;
import java.util.List;

public class VacancyList extends Activity {
    private Controller controller;

    private String vacancyName;
    private String cityName;
    ListView listView;
    BallView ballView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vacancy_list);

        Intent intent = getIntent();
        vacancyName = intent.getStringExtra("vacancyName");
        cityName = intent.getStringExtra("cityName");
        boolean HHChecked = intent.getBooleanExtra("HHChecked", false);
        boolean DouChecked = intent.getBooleanExtra("DouChecked", false);
        boolean WorkChecked = intent.getBooleanExtra("WorkChecked", false);

        listView = (ListView) findViewById(R.id.listView);
        ballView = (BallView) findViewById(R.id.loadingBalls);

        ballView.setVisibility(View.VISIBLE);

        ArrayList<Provider> providers = new ArrayList<>();

        if (HHChecked) {
            providers.add(new Provider(new HHStrategy()));
        }
        if (DouChecked) {
            providers.add(new Provider(new DOUStrategy()));
        }
        if (WorkChecked) {
            providers.add(new Provider(new WorkStrategy()));
        }

        Model model = new Model(providers);
        controller = new Controller(model);

        ParseHtml parseHtml = new ParseHtml();
        parseHtml.execute();

    }

    class ParseHtml extends AsyncTask<Void, Void, List<Vacancy>> {

        protected void onPreExecute() {
            listView.setVisibility(View.GONE);
        }

        @Override
        protected List<Vacancy> doInBackground(Void... params) {
            return controller.findVacancies(vacancyName, cityName);
        }

        @Override
        protected void onPostExecute(List<Vacancy> vacancies) {
            final List<Vacancy> vacancyList = vacancies;
            ArrayAdapter<Vacancy> myAdapter = new MyAdapter(VacancyList.this, vacancies);
            listView.setAdapter(myAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String url = vacancyList.get(position).getUrl();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                }
            });
            ballView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }

    }

    public class MyAdapter extends ArrayAdapter<Vacancy> {

        public MyAdapter(Context context, List<Vacancy> dataList) {
            super(context, 0, dataList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Vacancy vacancy = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent, false);
            }

            TextView row1 = (TextView) convertView.findViewById(R.id.textView1);
            TextView row2 = (TextView) convertView.findViewById(R.id.textView2);

            row1.setText(vacancy.getTitle());
            row2.setText((vacancy.getCity() + " " + vacancy.getCompanyName() + " " + vacancy.getSalary()).trim());

            return convertView;
        }
    }
}
