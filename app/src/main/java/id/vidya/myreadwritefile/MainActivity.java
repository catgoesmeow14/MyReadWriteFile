package id.vidya.myreadwritefile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnNew;
    Button btnOpen;
    Button btnSave;
    EditText editContent;
    EditText editTitle;

    File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNew = (Button) findViewById(R.id.btnNew);
        btnOpen = (Button) findViewById(R.id.btnOpen);
        btnSave = (Button) findViewById(R.id.btnSave);
        editContent = (EditText) findViewById(R.id.edit_file);
        editTitle = (EditText) findViewById(R.id.edit_title);

        btnNew.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        path = getFilesDir();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnNew:
                newFile();
                break;
            case R.id.btnOpen:
                openFile();
                break;
            case R.id.btnSave:
                saveFile();
                break;
        }
    }

    public void newFile() {
        editTitle.setText("");
        editContent.setText("");

        Toast.makeText(this, "Membersihkan file...", Toast.LENGTH_SHORT).show();
    }

    private void loadData(String title) {
        String text = FileHelper.readFromFile(this, title);
        editTitle.setText(title);
        editContent.setText(text);

        Toast.makeText(this, "Loading file '" + title + "'", Toast.LENGTH_SHORT).show();
    }

    public void openFile() {
        showList();
    }

    private void showList() {
        final ArrayList<String> arrayList = new ArrayList<String>();
        for (String file : path.list()) {
            arrayList.add(file);
        }
        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file yang ingin dibuka");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                loadData(items[item].toString());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void saveFile() {
        if (editTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else {
            String title = editTitle.getText().toString();
            String text = editContent.getText().toString();
            FileHelper.writeToFile(title, text, this);

            Toast.makeText(this, "Menyimpan file '" + editTitle.getText().toString() + "'", Toast.LENGTH_SHORT).show();
        }
    }
}