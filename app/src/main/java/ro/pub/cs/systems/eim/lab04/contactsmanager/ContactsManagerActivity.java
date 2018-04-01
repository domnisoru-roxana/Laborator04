package ro.pub.cs.systems.eim.lab04.contactsmanager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {

    Button additional_fields_button;
    Button save_button;
    Button cancel_button;
    LinearLayout additional_fields_container;
    EditText name_edit_text;
    EditText phone_edit_text;
    EditText email_edit_text;
    EditText address_edit_text;
    EditText job_edit_text;
    EditText company_edit_text;
    EditText website_edit_text;
    EditText im_edit_text;

    class ButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (((Button)view).getId()) {
                case R.id.show_additional_fields_button:
                    if (additional_fields_container.getVisibility() == View.GONE) {
                        additional_fields_container.setVisibility(View.VISIBLE);
                        additional_fields_button.setText(R.string.hide_additional_fields);
                    }
                    else {
                        additional_fields_container.setVisibility(View.GONE);
                        additional_fields_button.setText(R.string.show_additional_fields);
                    }
                    break;
                case R.id.save_button:
                    Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

                    String name = name_edit_text.getText().toString();
                    String phone = phone_edit_text.getText().toString();
                    String email = email_edit_text.getText().toString();
                    String address = address_edit_text.getText().toString();

                    String job = job_edit_text.getText().toString();
                    String company = company_edit_text.getText().toString();
                    String website = website_edit_text.getText().toString();
                    String im = im_edit_text.getText().toString();

                    if (name != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                    }
                    if (phone != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                    }
                    if (email != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                    }
                    if (address != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                    }
                    if (job != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, job);
                    }
                    if (company != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                    }
                    ArrayList<ContentValues> contactData = new ArrayList<>();
                    if (website != null) {
                        ContentValues websiteRow = new ContentValues();
                        websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                        websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                        contactData.add(websiteRow);
                    }
                    if (im != null) {
                        ContentValues imRow = new ContentValues();
                        imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                        imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                        contactData.add(imRow);
                    }
                    intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                    startActivityForResult(intent, Constants.CONTACTS_MANAGER_REQUEST_CODE);

                    break;
                case R.id.cancel_button:
                    setResult(Activity.RESULT_CANCELED, new Intent());
                    finish();
                    break;
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        ButtonOnClickListener onClickListener = new ButtonOnClickListener();
        additional_fields_button = (Button)findViewById(R.id.show_additional_fields_button);
        save_button = (Button)findViewById(R.id.save_button);
        cancel_button = (Button)findViewById(R.id.cancel_button);
        additional_fields_button.setOnClickListener(onClickListener);
        save_button.setOnClickListener(onClickListener);
        cancel_button.setOnClickListener(onClickListener);
        additional_fields_container = (LinearLayout)findViewById(R.id.additional_fields_container);

        name_edit_text = (EditText)findViewById(R.id.name_edit_text);
        phone_edit_text = (EditText)findViewById(R.id.phone_edit_text);
        email_edit_text = (EditText)findViewById(R.id.email_edit_text);
        address_edit_text = (EditText)findViewById(R.id.address_edit_text);

        job_edit_text = (EditText)findViewById(R.id.job_edit_text);
        company_edit_text = (EditText)findViewById(R.id.company_edit_text);
        website_edit_text = (EditText)findViewById(R.id.website_edit_text);
        im_edit_text = (EditText)findViewById(R.id.im_edit_text);

        Intent intent = getIntent();
        if (intent != null) {
            String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
            if (phone != null) {
                phone_edit_text.setText(phone);
            } else {
                Toast.makeText(this, getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch(requestCode) {
            case Constants.CONTACTS_MANAGER_REQUEST_CODE:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }
}
