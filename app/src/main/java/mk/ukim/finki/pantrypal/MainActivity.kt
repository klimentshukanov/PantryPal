package mk.ukim.finki.pantrypal

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.launch
import mk.ukim.finki.pantrypal.adapter.FoodItemAdapter
import mk.ukim.finki.pantrypal.model.FoodItem
import mk.ukim.finki.pantrypal.ui.AddItem
import mk.ukim.finki.pantrypal.ui.Login
import java.time.LocalDate


class MainActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        val user : FirebaseUser? = auth.currentUser

        val button : Button = findViewById(R.id.logout);
        val userDetails : TextView = findViewById(R.id.user_details);

        if (user == null)
        {
            val intent = Intent(this, Login::class.java)
            startActivity(intent);
            finish();
        }
        else
        {
            userDetails.setText("You are currently logged in as: "+user.email)
        }

        button.setOnClickListener{
            FirebaseAuth.getInstance().signOut();
            val intent = Intent(this, Login::class.java)
            startActivity(intent);
            finish();
        }

        var foodItems : MutableList<FoodItem> = mutableListOf()

        val addItemButton : Button = findViewById(R.id.add_item);
        addItemButton.setOnClickListener{
            val intent = Intent(this, AddItem::class.java)
            startActivity(intent);
            finish();
        }

        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        // Set Adapter
        var adapter = FoodItemAdapter(foodItems)
        recyclerView.adapter = adapter


        var db : DatabaseReference = FirebaseDatabase
            .getInstance("https://pantrypal-5ed45-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("Food Items")

        val query = db.orderByChild("uid").equalTo(auth.uid.toString())
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {

                        val name = snapshot.child("itemName").getValue(String::class.java)
                        val dayOfMonth = snapshot.child("expirationDate").child("dayOfMonth").getValue()
                        val month = snapshot.child("expirationDate").child("monthValue").getValue()
                        val year = snapshot.child("expirationDate").child("year").getValue()

                        var date : String

                        if (month.toString().length == 2)
                        {
                            if (dayOfMonth.toString().length == 2)
                            {
                                date= year.toString() + "-" + month.toString() + "-" + dayOfMonth.toString()
                            }
                            else
                            {
                                date= year.toString() + "-" + month.toString() + "-0" + dayOfMonth.toString()
                            }

                        }
                        else
                        {
                            if (dayOfMonth.toString().length == 2)
                            {
                                date= year.toString() + "-0" + month.toString() + "-" + dayOfMonth.toString()
                            }
                            else
                            {
                                date= year.toString() + "-0" + month.toString() + "-0" + dayOfMonth.toString()
                            }
                        }

                        val foodItem : FoodItem = FoodItem(name, LocalDate.parse(date), auth.currentUser?.uid)
                        foodItems.add(foodItem)
                        adapter = FoodItemAdapter(foodItems)
                        recyclerView.adapter = adapter
                    }

                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle possible errors
                println("Error: ${databaseError.message}")
            }

        })

    }
}