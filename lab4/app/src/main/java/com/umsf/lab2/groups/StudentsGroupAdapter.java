package com.umsf.lab2.groups;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.umsf.lab2.R;

import java.util.ArrayList;
import java.util.List;

public class StudentsGroupAdapter extends ArrayAdapter<StudentsGroup> {
    private static List<StudentsGroup> students = new ArrayList<>();

    public StudentsGroupAdapter(@NonNull Context context, ArrayList<StudentsGroup> list) {
        super(context, 0, list);
        students = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_of_list, parent, false);
        StudentsGroup group = students.get(position);
        TextView view = convertView.findViewById(R.id.textViewList);
        view.setText(group.getNumber());
        return convertView;
    }
}