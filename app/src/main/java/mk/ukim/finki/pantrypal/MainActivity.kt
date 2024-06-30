package mk.ukim.finki.pantrypal

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import mk.ukim.finki.pantrypal.ui.Login

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

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
    }
}