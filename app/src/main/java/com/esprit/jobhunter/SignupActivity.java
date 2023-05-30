package com.esprit.jobhunter;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import info.hoang8f.android.segmented.SegmentedGroup;

public class SignupActivity extends AppCompatActivity {

    //UI Declaration
    SegmentedGroup userTypeSeg;

    //Global var Declaration
    private boolean isApplicantFragmentDisplayed = false;
    private boolean isCompanyFragmentDisplayed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.esprit.jobhunter.R.layout.activity_signup);

        userTypeSeg = (SegmentedGroup) findViewById(com.esprit.jobhunter.R.id.userTypeSeg);
        userTypeSeg.setTintColor(Color.parseColor("#FFFFFF"), Color.parseColor("#373447"));

        userTypeSeg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case com.esprit.jobhunter.R.id.normalUserSeg:
                        applicantFrag();
                        break;

                    case  com.esprit.jobhunter.R.id.companySeg:
                        companyFrag();
                        break;
                    default:
                }
            }
        });
    }

    public void applicantFrag(){
        if (isApplicantFragmentDisplayed == false) {
            closeCompanyFrag();
            displayApplicantFrag();
        } else {
            closeApplicantFrag();
        }
    }

    public void companyFrag(){
        if (isCompanyFragmentDisplayed == false) {
            closeApplicantFrag();
            displayCompanyFrag();
        } else {
            closeCompanyFrag();
        }
    }

    public void displayApplicantFrag(){
        ApplicantSignupFragment applicantSignupFragment = ApplicantSignupFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(com.esprit.jobhunter.R.id.signupFragContainer,applicantSignupFragment).commit();
        isApplicantFragmentDisplayed = true;
    }

    public void displayCompanyFrag(){
        CompanySignupFragment companySignupFragment = CompanySignupFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(com.esprit.jobhunter.R.id.signupFragContainer,companySignupFragment).commit();
        isCompanyFragmentDisplayed = true;
    }

    public void closeApplicantFrag(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        ApplicantSignupFragment applicantSignupFragment = (ApplicantSignupFragment) fragmentManager.findFragmentById(com.esprit.jobhunter.R.id.signupFragContainer);
        if (applicantSignupFragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(applicantSignupFragment).commit();
        }
        isApplicantFragmentDisplayed = false;
    }

    public void closeCompanyFrag(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        CompanySignupFragment companySignupFragment = (CompanySignupFragment) fragmentManager.findFragmentById(com.esprit.jobhunter.R.id.signupFragContainer);
        if (companySignupFragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(companySignupFragment).commit();
        }
        isCompanyFragmentDisplayed = false;
    }


}
