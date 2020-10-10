package com.drumdie.barberdemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    private String ownerPhoneNumber= "+549113181-5561";
    //public static int CALL_PHONE_REQUEST_CODE = 0;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // makeCall();
                isWhatsappInstalled();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery_sponsors, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
  }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    private void isWhatsappInstalled(){
    boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
    if (isWhatsappInstalled) {
    Uri uri = Uri.parse("smsto:" + ownerPhoneNumber);
    Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
    sendIntent.putExtra(Intent.EXTRA_TEXT, "Hola, quisiera arreglar un turno en la barbería!");
    sendIntent.setPackage("com.whatsapp");
    startActivity(sendIntent);
        } else {
            Toast.makeText(this, "WhatsApp no Instalado", Toast.LENGTH_SHORT).show();
            Uri uri = Uri.parse("market://details?id=com.whatsapp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(goToMarket);
        }
    }















    /*
    private void makeCall(){
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ownerPhoneNumber));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
                startActivity(intent);
            } else {
                final String[] permissions = new String[]{CALL_PHONE};
                requestPermissions(permissions, CALL_PHONE_REQUEST_CODE);
            }
        } else {
            startActivity(intent);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // compara requestCode dado por el user con el dado por nosotros
        if(requestCode == CALL_PHONE_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults [0] == PackageManager.PERMISSION_GRANTED){
                makeCall();
            }
            else if (shouldShowRequestPermissionRationale(CALL_PHONE)){
                AlertDialog.Builder builder= new AlertDialog.Builder(this);
                builder.setTitle("Hacer llamadas");
                builder.setMessage("Debes aceptar el permiso para poder utilizar la app ");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String[] permissions = new String[]{CALL_PHONE};
                        requestPermissions(permissions, CALL_PHONE_REQUEST_CODE);
                    }
                });
                builder.show();
            }
        }

    }

     */
}

