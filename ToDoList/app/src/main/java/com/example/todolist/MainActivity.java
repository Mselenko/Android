package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    private ToDoListManager listManager;
    private ToDoItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView todoList = findViewById(R.id.todo_list);
        ImageButton addButton = findViewById(R.id.add_item);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddButtonClick();
            }
        });

        listManager = new ToDoListManager(this);
        adapter = new ToDoItemAdapter(this, listManager.getItems());

        todoList.setAdapter(adapter);
    }


    private void onAddButtonClick()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.add_item);

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setNegativeButton(
                android.R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }
        );

        builder.setPositiveButton(
                android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar calendar = Calendar.getInstance();
                        String timestamp = date.format(calendar.getTime());
                        ToDoItem item = new ToDoItem(
                                input.getText().toString(),
                                false,
                                timestamp
                        );
                        listManager.addItem(item);
                        adapter.updateItems(listManager.getItems());
                    }
                }
        );
        builder.show();
    }



    private class ToDoItemAdapter extends ArrayAdapter<ToDoItem>
    {
        private Context context;
        private List<ToDoItem> items;

        private ToDoItemAdapter(Context context, List<ToDoItem> items)
        {
            super(context, -1, items);
            this.context = context;
            this.items = items;
        }

        private void updateItems(List<ToDoItem> items)
        {
            this.items = items;
            notifyDataSetChanged();
        }


        @Override
        public int getCount()
        {
            return items.size();
        }


        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent)
        {
            final ItemViewHolder holder;

            if (convertView == null)
            {
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.to_do_item_layout,
                        parent, false);

                holder = new ItemViewHolder();
                holder.itemDescription = convertView.findViewById(R.id.itemTextView);
                holder.itemState = convertView.findViewById(R.id.completedCheckBox);
                holder.timestamp = convertView.findViewById(R.id.timeStampTextView);
                holder.removeItem = convertView.findViewById(R.id.delete_item);

                convertView.setTag(holder);

            }else {
                holder = (ItemViewHolder) convertView.getTag();
            }


            holder.itemDescription.setText(items.get(position).getDescription());
            holder.itemState.setChecked(items.get(position).isComplete());
            holder.timestamp.setText(items.get(position).getTimestamp());
            holder.removeItem.setTag(items.get(position));
            holder.itemState.setTag(items.get(position));


            final View.OnClickListener onClickListener = new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    ToDoItem item = (ToDoItem) holder.itemState.getTag();
                    item.toggleComplete();
                    listManager.updateItem(item);
                    notifyDataSetChanged();
                }
            };

            holder.removeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Delete Confirmation");
                    builder.setMessage("Are you sure you want to delete?");
                    builder.setNegativeButton(
                            android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }
                    );


                    builder.setPositiveButton(
                            android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ToDoItem item = (ToDoItem) holder.removeItem.getTag();
                                    listManager.deleteItem(item);
                                    adapter.updateItems(listManager.getItems());
                                    notifyDataSetChanged();
                                }
                            }
                    );

                    builder.show();
                }
            });


            convertView.setOnClickListener(onClickListener);

            holder.itemState.setOnClickListener(onClickListener);

            return convertView;
        }

    }


    public static class ItemViewHolder
    {
        public TextView itemDescription;
        public CheckBox itemState;
        public ImageButton removeItem;
        public TextView timestamp;
    }
}
