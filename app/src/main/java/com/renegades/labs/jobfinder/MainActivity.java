package com.renegades.labs.jobfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonFind(View v) {
        EditText vacancyName = (EditText) findViewById(R.id.vacancy_name);
        EditText cityName = (EditText) findViewById(R.id.city_name);
        TextView vacancyTextView = (TextView) findViewById(R.id.vacancy_textview);
        CheckBox checkBoxHH = (CheckBox) findViewById(R.id.checkBoxHH);
        CheckBox checkBoxDou = (CheckBox) findViewById(R.id.checkBoxDou);
        CheckBox checkBoxWork = (CheckBox) findViewById(R.id.checkBoxWork);

        String vacancy = vacancyName.getText().toString();
        if (vacancy.length() == 0 || (!checkBoxHH.isChecked() && !checkBoxDou.isChecked() &&
                !checkBoxWork.isChecked())) {
            vacancyTextView.setText("Введіть назву вакансії та виберіть сайт пошуку");
        } else {
            Intent intent = new Intent(this, VacancyList.class);
            intent.putExtra("vacancyName", vacancy);
            intent.putExtra("cityName", cityName.getText().toString());
            intent.putExtra("HHChecked", checkBoxHH.isChecked());
            intent.putExtra("DouChecked", checkBoxDou.isChecked());
            intent.putExtra("WorkChecked", checkBoxWork.isChecked());
            startActivity(intent);
        }
    }

}
