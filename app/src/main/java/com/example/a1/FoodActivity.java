package com.example.a1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class FoodActivity extends AppCompatActivity {

    private LinearLayout expensesListLayout;
    private static final int ADD_EXPENSE_REQUEST_CODE = 1;
    private static final int EDIT_EXPENSE_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        // Lấy reference đến LinearLayout
        expensesListLayout = findViewById(R.id.expensesListLayout);

        // Xóa tất cả các mục chi tiêu hiện có
        expensesListLayout.removeAllViews();

        Button addExpenseButton = findViewById(R.id.addExpenseButton);

        addExpenseButton.setOnClickListener(v -> {
            Intent intent = new Intent(FoodActivity.this, AddExpenseActivity.class);
            startActivityForResult(intent, ADD_EXPENSE_REQUEST_CODE);
        });

        // Lấy reference đến ImageView backButton
        ImageView backButton = findViewById(R.id.backButton);

        // Thiết lập sự kiện nhấn vào nút mũi tên (backButton)
        backButton.setOnClickListener(v -> {
            // Quay lại CategoriesActivity
            Intent intent = new Intent(FoodActivity.this, CategoriesActivity.class);
            startActivity(intent);
            finish(); // Optional: Đóng FoodActivity để không thể quay lại bằng nút back
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String expenseTitle = data.getStringExtra("expense_title");
            String expenseAmount = data.getStringExtra("expense_amount");
            String expenseDate = data.getStringExtra("expense_date");
            int expenseImageResId = data.getIntExtra("expense_image_res_id", R.drawable.food); // Default image

            if (requestCode == ADD_EXPENSE_REQUEST_CODE) {
                // Thêm mục chi tiêu mới
                addExpenseItem(expenseTitle, expenseAmount, expenseDate, expenseImageResId);
            } else if (requestCode == EDIT_EXPENSE_REQUEST_CODE) {
                // Cập nhật mục chi tiêu hiện có
                int position = data.getIntExtra("position", -1);
                if (position != -1) {
                    updateExpenseItem(position, expenseTitle, expenseAmount, expenseDate, expenseImageResId);
                }
            }
        }
    }

    private void addExpenseItem(String title, String amount, String date, int imageResId) {
        LayoutInflater inflater = LayoutInflater.from(this);

        // Inflate layout cho mục chi tiêu mới từ XML
        View expenseItemView = inflater.inflate(R.layout.expense_item, expensesListLayout, false);

        // Gán các giá trị cho TextViews và ImageView trong mục chi tiêu
        TextView expenseTitleTextView = expenseItemView.findViewById(R.id.expense_title);
        TextView expenseAmountTextView = expenseItemView.findViewById(R.id.expense_amount);
        TextView expenseDateTextView = expenseItemView.findViewById(R.id.expense_date);
        ImageView expenseImageView = expenseItemView.findViewById(R.id.expense_image);

        expenseTitleTextView.setText(title);
        expenseAmountTextView.setText(amount);
        expenseDateTextView.setText(date);
        expenseImageView.setImageResource(imageResId);

        // Thêm mục chi tiêu mới vào danh sách
        expensesListLayout.addView(expenseItemView);

        // Thiết lập sự kiện nhấn vào mục chi tiêu
        expenseItemView.setOnClickListener(v -> showEditDeleteDialog(expenseItemView, title, amount, date, imageResId, expensesListLayout.indexOfChild(expenseItemView)));
    }

    private void updateExpenseItem(int position, String title, String amount, String date, int imageResId) {
        View expenseItemView = expensesListLayout.getChildAt(position);

        if (expenseItemView != null) {
            TextView expenseTitleTextView = expenseItemView.findViewById(R.id.expense_title);
            TextView expenseAmountTextView = expenseItemView.findViewById(R.id.expense_amount);
            TextView expenseDateTextView = expenseItemView.findViewById(R.id.expense_date);
            ImageView expenseImageView = expenseItemView.findViewById(R.id.expense_image);

            expenseTitleTextView.setText(title);
            expenseAmountTextView.setText(amount);
            expenseDateTextView.setText(date);
            expenseImageView.setImageResource(imageResId);
        }
    }

    private void showEditDeleteDialog(View expenseItemView, String title, String amount, String date, int imageResId, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FoodActivity.this);
        builder.setTitle("Chỉnh sửa hoặc Xóa");

        builder.setItems(new CharSequence[]{"Chỉnh sửa", "Xóa"}, (dialog, which) -> {
            switch (which) {
                case 0: // Chỉnh sửa
                    Intent editIntent = new Intent(FoodActivity.this, AddExpenseActivity.class);
                    editIntent.putExtra("expense_title", title);
                    editIntent.putExtra("expense_amount", amount);
                    editIntent.putExtra("expense_date", date);
                    editIntent.putExtra("expense_image_res_id", imageResId);
                    editIntent.putExtra("position", position); // Truyền vị trí để chỉnh sửa
                    startActivityForResult(editIntent, EDIT_EXPENSE_REQUEST_CODE);
                    break;
                case 1: // Xóa
                    expensesListLayout.removeView(expenseItemView);
                    break;
            }
        });

        builder.show();
    }
}
