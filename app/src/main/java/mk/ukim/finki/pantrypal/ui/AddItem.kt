package mk.ukim.finki.pantrypal.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Database
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import mk.ukim.finki.pantrypal.MainActivity
import mk.ukim.finki.pantrypal.R
import mk.ukim.finki.pantrypal.model.FoodItem
import java.time.LocalDate
import java.time.LocalDateTime

class AddItem : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    public override fun onStart() {
        super.onStart()

        auth = Firebase.auth

        val currentUser = auth.currentUser
        if (currentUser == null) {
            val intent = Intent(this, Login::class.java)
            startActivity(intent);
            finish();
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        val editTextItemName : EditText = findViewById(R.id.itemNameEditText);
        val editTextExpirationDate : EditText = findViewById(R.id.expirationDateEditText);
        val button : Button = findViewById(R.id.addItemButton);

        button.setOnClickListener {
            val itemName: String = editTextItemName.text.toString();

            //date format: YYYY-MM-DD
            val expirationDate : LocalDate = LocalDate.parse(editTextExpirationDate.text.toString())

            val db : FirebaseDatabase = FirebaseDatabase.getInstance("https://pantrypal-5ed45-default-rtdb.europe-west1.firebasedatabase.app/");
            val reference : DatabaseReference = db.reference

            val foodItem : FoodItem = FoodItem(itemName, expirationDate, auth.uid);

            val itemId = reference.push().key
            if (itemId != null) {
                reference.child("Food Items").child(itemId).setValue(foodItem)
                    .addOnSuccessListener {
                        // Item added successfully
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent);
                        finish()
                    }
                    .addOnFailureListener {
                        // Failed to add item
                        Toast.makeText(this, "Failed to add item", Toast.LENGTH_SHORT).show()
                    }
            }

        }

    }
}