package com.umsf.lab2.groups;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.umsf.lab2.R;
import com.umsf.lab2.database.StudentsDatabaseHelper;
import com.umsf.lab2.students.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentsListAdapter extends ArrayAdapter<Student> {
    private static List<Student> students = new ArrayList<>();

    public StudentsListAdapter(@NonNull Context context, ArrayList<Student> list) {
        super(context, 0, list);
        students = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_of_list, parent, false);
        }
        Student student = students.get(position);
        TextView view = convertView.findViewById(R.id.textViewList);
        view.setText(student.getName());

        view.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(getContext(), view);
            popup.getMenuInflater().inflate(R.menu.menu_u_d, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                final int itemId = item.getItemId();
                if (itemId == R.id.delete_i) {
                    SQLiteOpenHelper sqLiteOpenHelper = new StudentsDatabaseHelper(getContext());
                    SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
                    String deleteQuery = "DELETE FROM Students WHERE " + "Students.id" + " = " + student.getGroupNumber();
                    db.execSQL(deleteQuery);
                    db.close();
                } else if (itemId == R.id.update_i) {
                    getContext().startActivity(new Intent(getContext(), UpdateActivity.class)
                            .putExtra("id", student.getGroupNumber()));
                }
                return false;
            });
            popup.show();
        });

        return convertView;
    }
}