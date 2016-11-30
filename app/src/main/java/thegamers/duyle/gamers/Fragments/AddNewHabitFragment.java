package thegamers.duyle.gamers.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import thegamers.duyle.gamers.Activities.EditFile;
import thegamers.duyle.gamers.models.Feed;
import thegamers.duyle.gamers.Activities.MainActivity;
import thegamers.duyle.gamers.R;


/**
 * Created by Duy Le on 11/5/2016.
 */

public class AddNewHabitFragment extends Fragment {
    private Button takePictureB;
    private ImageView imageView;
    private Spinner typeOfLengthSpinner;
    private Spinner publicitySpinner;
    private EditText habitEditText;
    private EditText amountOfDaysEditText;
    private EditText descriptionEditText;
    private Button retakePictureButton;
    private Button addMorePictureButton;

    private static final int CAM_REQUEST=1;
    private Button doneButton;
    private static String day="day";
    private static int counter=1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_new_habit_fragment,container,false);

        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getContext(), // Context
                "us-east-1:0c67b780-c220-47d9-b361-fb192062f8a7", // Identity Pool ID
                Regions.US_EAST_1 // Region
        );
        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);
        final DynamoDBMapper mapper = new DynamoDBMapper(ddbClient);


        takePictureB=(Button) view.findViewById(R.id.takePicturebutton);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        doneButton = (Button) view.findViewById(R.id.doneButton);

        retakePictureButton = (Button) view.findViewById(R.id.retakePictureButton);
        addMorePictureButton = (Button) view.findViewById(R.id.addMorePictureButton);

        habitEditText=(EditText) view.findViewById(R.id.habitEditText);
        amountOfDaysEditText=(EditText)view.findViewById(R.id.amountOfDaysEditText);
        descriptionEditText=(EditText) view.findViewById(R.id.descriptionEditText);


        retakePictureButton.setVisibility(View.GONE);
        addMorePictureButton.setVisibility(View.GONE);

        habitEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //People selectedPeople = mapper.load(People.class, "habit1");
                //Toast.makeText(getContext(),"LOLO",Toast.LENGTH_LONG).show();
                descriptionEditText.setText("YAYA");
                amountOfDaysEditText.setText("12");
            }
        });



        //done button
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(habitEditText.getText().toString().isEmpty())){
                    if(!amountOfDaysEditText.getText().toString().isEmpty()){
                            if(!descriptionEditText.getText().toString().isEmpty()){
                                //Dynamo things  (not finished right now)
                                /*
                                Runnable runnable = new Runnable() {
                                    public void run() {
                                        //DynamoDB calls go here

                                        People people = new People();
                                        people.setUsername("admin");

                                        Calendar c = Calendar.getInstance();

                                        //SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
                                        //String formattedDate = df.format(c.getTime());

                                        people.setAddedTime(c.getTimeInMillis());

                                        people.setHabitName(habitEditText.getText().toString());
                                        people.setAmountOfDay(Integer.valueOf(amountOfDaysEditText.getText().toString()));
                                        people.setTypeOfLength(typeOfLengthSpinner.getSelectedItem().toString());
                                        people.setDescription(descriptionEditText.getText().toString());
                                        people.setPublicity(publicitySpinner.getSelectedItem().toString());



                                        mapper.save(people);
                                    }
                                };
                                Thread mythread = new Thread(runnable);
                                mythread.start();
    */


                                //save
                                    // File[] listFile = getSaveFolder(habitEditText.getText().toString()).listFiles();
                                    File[] trashFolderList = (new File(getTrashFolder())).listFiles();
                                    for(int i=0;i<trashFolderList.length;i++){
                                        Toast.makeText(getContext(),trashFolderList[i].getAbsolutePath(),Toast.LENGTH_LONG).show();
                                        Calendar c = Calendar.getInstance();
                                        Feed feed = new Feed(habitEditText.getText().toString(),Integer.valueOf(amountOfDaysEditText.getText().toString()),typeOfLengthSpinner.getSelectedItem().toString()
                                                ,descriptionEditText.toString(),publicitySpinner.getSelectedItem().toString()
                                                ,c.getTimeInMillis());
                                        String inputPath=getTrashFolder();
                                        String inputFile=(trashFolderList[i].getName());
                                        String outputPath = feed.getHabitPath();
                                        EditFile.moveFile(inputPath,inputFile,outputPath);
                                    }
                                counter=0;
                                //return to new feed fragment
                                MainActivity mainActivity = (MainActivity) getActivity();
                                mainActivity.loadNewFeedFragment();
                            }
                        else{
                            Toast.makeText(getContext(),"Please fill in the description!",Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(getContext(),"Please fill in the amount!",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getContext(),"Please fill in the habit name!",Toast.LENGTH_LONG).show();
                }




            }
        });


        //taking picture
        takePictureB.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getFile();
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(camera_intent,CAM_REQUEST);
                retakePictureButton.setVisibility(View.VISIBLE);
                addMorePictureButton.setVisibility(View.VISIBLE);
                takePictureB.setVisibility(View.GONE);
                Picasso.with(getContext()).load(getTrashFolder()+"/day1.jpg").resize(50,50).into(imageView);
            }
        });

        // spinner for day/month/year
        typeOfLengthSpinner = (Spinner) view.findViewById(R.id.typeOfLengthSpinner);
        //spinner drop down elements
        List<String> typeOfLength = new ArrayList<String>();
        typeOfLength.add("Day(s)");
        typeOfLength.add("Month(s)");
        typeOfLength.add("Year(s)");
        //adapter for spinner
        ArrayAdapter typeOfLengthAdapter = new ArrayAdapter<String>(view.getContext(),R.layout.support_simple_spinner_dropdown_item,typeOfLength);
        typeOfLengthSpinner.setAdapter(typeOfLengthAdapter);

        //spinner for public/private
        publicitySpinner = (Spinner) view.findViewById(R.id.publicitySpinner);
        List<String> publicity = new ArrayList<String>();
        publicity.add("Public");
        publicity.add("Private");
        //adapter for publicity spinner
        ArrayAdapter publicityAdapter = new ArrayAdapter<String>(view.getContext(),R.layout.support_simple_spinner_dropdown_item,publicity);
        publicitySpinner.setAdapter(publicityAdapter);

        return view;
    }
    private File getFile(){
        File trashFolder = new File(getTrashFolder()) ;
        if(!trashFolder.exists()){
            trashFolder.mkdir();
        }
        File saveFolder = new File(Environment.getExternalStorageDirectory().toString()+"/camera_app/save/");
        File[] listFolders = saveFolder.listFiles();

        File image_file = new File(trashFolder,day+counter+".jpg");

        if(image_file.exists()){
            counter++;
            image_file = new File(trashFolder,day+counter+".jpg");
        }
        counter++;
        return image_file;
    }
    private String getTrashFolder(){
        return Environment.getExternalStorageDirectory().toString()+"/camera_app/trash/";
    }
    private String getSaveFolder(String folderName){
        return Environment.getExternalStorageDirectory().toString()+"/camera_app/save/"+folderName;
    }
}


