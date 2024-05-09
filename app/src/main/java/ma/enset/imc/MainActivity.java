package ma.enset.imc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private EditText weightEditText;
    private EditText heightEditText;
    private TextInputLayout weightInputLayout;
    private TextInputLayout heightInputLayout;
    private TextView resultNumberLeft;
    private TextView resultNumberRight;
    private ImageView resultImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weightEditText = findViewById(R.id.weightInputEditText);
        weightInputLayout = findViewById(R.id.weightInput);
        heightEditText = findViewById(R.id.heightInputEditText);
        heightInputLayout = findViewById(R.id.heightInput);

        resultNumberLeft = findViewById(R.id.resultNumberLeft);
        resultNumberRight = findViewById(R.id.resultNumberRight);
        resultImage = findViewById(R.id.resultImage);

        findViewById(R.id.calcButton).setOnClickListener(v -> calculateAndDisplayResult());
    }

    private void calculateAndDisplayResult() {
        if (inputsValid()) {
            double weight = Double.parseDouble(weightEditText.getText().toString());
            double height = Double.parseDouble(heightEditText.getText().toString());
            if (weight > 0 && height > 0) {
                double result = calculateIMC(weight, height);

                CharSequence resultLeft = ((int) result) + ".";
                CharSequence resultRight = String.valueOf(getDecimal(result));

                resultNumberLeft.setText(resultLeft);
                resultNumberRight.setText(resultRight);

                resultImage.setImageResource(getRelevantImage(result));
            }
        }
    }

    private int getRelevantImage(double imc) {
        if (imc < 18.5) return R.drawable.underweight;
        if (imc < 25.) return R.drawable.normal;
        if (imc < 30.) return R.drawable.overweight;
        if (imc < 35.) return R.drawable.obese;
        return R.drawable.extremly_obese;
    }

    private double calculateIMC(double weight, double height) {
        double heightM = height / 100;
        return weight / (heightM * heightM);
    }

    private int getDecimal(double inputNumber) {
        // Extract the decimal part
        double decimalPart = inputNumber % 1;

        // Multiply by 100 to move the two decimal places to the left
        double multiplied = decimalPart * 100;

        // Cast to int to remove the decimal places after the first two
        return (int) multiplied;
    }

    private boolean inputsValid() {
        String weightInput = weightEditText.getText().toString();
        String heightInput = heightEditText.getText().toString();

        if (weightInput.isEmpty()) {
            weightInputLayout.setError("This field is required");
            return false;
        }
        if (heightInput.isEmpty()) {
            heightInputLayout.setError("This field is required");
            return false;
        }
        weightEditText.setError(null);
        heightEditText.setError(null);
        return true;
    }
}